package org.kie.interactiveexplainabilitys.explainability.engine.impl;/*
 * Copyright 2022 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.kie.interactivepredictions.api.engines.ExplainabilityEngine;
import org.kie.interactivepredictions.api.models.IPInputExplainability;
import org.kie.interactivepredictions.api.models.IPOutputExplainability;
import org.kie.interactivepredictions.explainability.engine.impl.ExplainabilityEngineImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ExplainabilityEngineImplTest {

    private static ExplainabilityEngine explainabilityEngine;

    @BeforeAll
    public static void init() {
        explainabilityEngine = new ExplainabilityEngineImpl();
    }

    @Test
    void explain() {
        IPOutputExplainability retrieved = explainabilityEngine.explain(new IPInputExplainability());
        assertNotNull(retrieved);
    }
}