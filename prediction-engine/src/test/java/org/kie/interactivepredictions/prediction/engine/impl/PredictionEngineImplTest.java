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

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.kie.interactivepredictions.api.engines.PredictionEngine;
import org.kie.interactivepredictions.api.models.IPInputPrediction;
import org.kie.interactivepredictions.api.models.IPOutputPrediction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PredictionEngineImplTest {

    private static final String FILE_NAME = "LogisticRegression.pmml";

    private static final String MODEL_NAME = "LogisticRegression";

    private static PredictionEngine predictionEngine;

    @BeforeAll
    public static void init() {
        predictionEngine = new PredictionEngineImpl();
    }

    @Test
    void predict() {
        final Map<String, Object> inputData = new HashMap<>();
        inputData.put("variance", 2.3);
        inputData.put("skewness", 6.9);
        inputData.put("curtosis", 3.1);
        inputData.put("entropy", 5.1);
        IPOutputPrediction retrieved = predictionEngine.predict(new IPInputPrediction(FILE_NAME, MODEL_NAME,
                                                                                      inputData));
        assertNotNull(retrieved);
        // Hardcoded expected values because the model is already known
        String expected = "OK";
        assertEquals(expected, retrieved.getResultCode());
        expected = "class";
        assertEquals(expected, retrieved.getResultObjectName());
        Map<String, Object> retrievedResult = retrieved.getResultVariables();
        assertNotNull(retrievedResult);
        assertEquals(3, retrievedResult.size());
        expected = "probability(Authentic)";
        assertTrue(retrievedResult.containsKey(expected));
        assertEquals(0.999999999768916, retrievedResult.get(expected));
        expected = "probability(Counterfeit)";
        assertTrue(retrievedResult.containsKey(expected));
        assertEquals(2.310840601778274E-10, retrievedResult.get(expected));
        expected = "class";
        assertTrue(retrievedResult.containsKey(expected));
        assertEquals("Authentic", retrievedResult.get(expected));
    }
}