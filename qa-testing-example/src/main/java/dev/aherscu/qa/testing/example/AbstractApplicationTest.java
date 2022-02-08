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

package dev.aherscu.qa.testing.example;

import dev.aherscu.qa.jgiven.commons.actions.*;
import dev.aherscu.qa.jgiven.commons.fixtures.*;
import dev.aherscu.qa.jgiven.commons.model.*;
import dev.aherscu.qa.jgiven.commons.utils.*;
import dev.aherscu.qa.jgiven.commons.verifications.*;

public abstract class AbstractApplicationTest<T extends AnyScenarioType, GIVEN extends GenericFixtures<T, ?> & ScenarioType<T>, WHEN extends GenericActions<T, ?> & ScenarioType<T>, THEN extends GenericVerifications<T, ?> & ScenarioType<T>>
    extends UnitilsScenarioTest<TestConfiguration, T, GIVEN, WHEN, THEN> {
    /**
     * Initializes with {@link TestConfiguration}.
     */
    protected AbstractApplicationTest() {
        super(TestConfiguration.class);
    }

    /**
     * Group labels to be used for defining <a href=
     * "https://testng.org/doc/documentation-main.html#dependent-methods">TestNG
     * dependencies</a>.
     */
    // TODO: consider removing these -- not used
    public static final class Groups {
        public static final String FUNCTIONAL = "mobile-functional";
        public static final String LOGIN      = "mobile-login";
    }
}