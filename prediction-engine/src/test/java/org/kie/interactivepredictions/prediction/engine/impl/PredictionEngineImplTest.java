package org.kie.interactivepredictions.prediction.engine.impl;/*
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
import org.kie.interactivepredictions.api.engines.PredictionEngine;
import org.kie.interactivepredictions.api.models.IPInputPrediction;
import org.kie.interactivepredictions.api.models.IPOutputPrediction;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class PredictionEngineImplTest {

    private static PredictionEngine predictionEngine;

    @BeforeAll
    public static void init() {
        predictionEngine = new PredictionEngineImpl();
    }

    @Test
    void predict() {
        IPOutputPrediction retrieved = predictionEngine.predict(new IPInputPrediction());
        assertNotNull(retrieved);
    }
}