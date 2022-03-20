/*
 * Copyright 2022 Adrian Herscu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.aherscu.qa.tester.utils;

import static dev.aherscu.qa.tester.utils.ClassUtilsExtensions.getRelativeResourceAsStream;
import static dev.aherscu.qa.tester.utils.ImageUtils.*;
import static dev.aherscu.qa.tester.utils.ImageUtils.Pipeline.from;
import static javax.imageio.ImageIO.read;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.*;

import com.github.romankh3.image.comparison.*;
import com.github.romankh3.image.comparison.model.*;
import org.jooq.lambda.*;
import org.testng.annotations.*;

import lombok.*;

/**
 * Semi-automatic test; the resulting image files are produced in the working
 * directory and are meant to be visually inspected.
 */
@SuppressWarnings({ "javadoc", "static-method" })
public class ImageUtilsTest {

    @Test
    @SneakyThrows
    public void shouldReduceTo4Colors() {
        Pipeline.from(
            ImageUtilsTest.class
                .getResourceAsStream("colored-image.png"))
            .reduce(FOUR_BIT_COLOR_MODEL)
            .into(Unchecked.supplier(
                () -> new FileOutputStream("four-colors-image.png")),
                "png");
    }

    @Test
    @SneakyThrows
    public void shouldReduceToGreyScale() {
        Pipeline.from(
            ImageUtilsTest.class
                .getResourceAsStream("colored-image.png"))
            .reduce(GREY_SCALE_COLOR_MODEL)
            .into(Unchecked.supplier(
                () -> new FileOutputStream("greyed-image.png")), "png");
    }

    @Test
    @SneakyThrows
    public void shouldScaleDown() {
        Pipeline.from(
            ImageUtilsTest.class
                .getResourceAsStream("colored-image.png"))
            .scale(0.5f, 0.5f, DEFAULT_RENDERING_HINTS)
            .into(Unchecked.supplier(
                () -> new FileOutputStream("scaled-down-image.png")),
                "png");
    }

    @Test
    @SneakyThrows
    public void shouldScaleDownAndGray() {
        Pipeline.from(
            ImageUtilsTest.class
                .getResourceAsStream("colored-image.png"))
            .scale(1 / 4d, 1 / 4d)
            .reduce(GREY_SCALE_COLOR_MODEL)
            .into(Unchecked.supplier(
                () -> new FileOutputStream(
                    "scaled-down-grayed-image.png")),
                "png");
    }

    @Test
    @SneakyThrows
    public void shouldMismatchSize() {
        assertThat(Pipeline
            .from(read(getRelativeResourceAsStream(getClass(),
                "colored-image.png")))
            .diff(read(getRelativeResourceAsStream(getClass(),
                "colored-image-small.png")),
                p -> new ImageComparison(p.getRight(),
                    p.getLeft()),
                p -> p)
            .setAllowingPercentOfDifferentPixels(5)
            .compareImages()
            .getImageComparisonState(),
            is(ImageComparisonState.SIZE_MISMATCH));
    }

    @Test
    @SneakyThrows
    public void shouldBeSimilar() {
        assertThat(Pipeline
            .from(read(getRelativeResourceAsStream(getClass(),
                "colored-image.png")))
            .diff(read(getRelativeResourceAsStream(getClass(),
                "colored-image-small.png")),
                p -> new ImageComparison(p.getRight(), p.getLeft()))
            .setAllowingPercentOfDifferentPixels(5)
            .compareImages()
            .getImageComparisonState(),
            is(ImageComparisonState.MATCH));
    }
}
