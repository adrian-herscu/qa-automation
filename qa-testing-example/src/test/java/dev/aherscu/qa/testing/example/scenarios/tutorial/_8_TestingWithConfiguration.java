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

package dev.aherscu.qa.testing.example.scenarios.tutorial;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.stringContainsInOrder;

import org.testng.annotations.*;

import dev.aherscu.qa.testing.example.*;

public class _8_TestingWithConfiguration
    extends ApplicationPerClassWebSessionTest {
    @Test
    public void shouldOpenCalculator() {
        assertThat(webDriver.get().asWindows().getTitle(),
            equalTo("Calculator"));
    }

    @Test(dataProvider = INTERNAL_DATA_PROVIDER)
    public void shouldCalculate(final String expression, final String result) {
        webDriver.get().asWindows()
            .findElementByAccessibilityId("CalculatorResults")
            .sendKeys(expression + "=");
        assertThat(webDriver.get().asWindows()
            .findElementByAccessibilityId("CalculatorResults")
            .getText(),
            stringContainsInOrder("Display is", result));
    }

    @DataProvider
    static Object[][] data() {
        return new Object[][] {
            { "7+8", "15" },
            { "7-8", "-1" },
            { "2*5", "11" },
            { "6/2", "3" },
        };
    }
}