/*
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
package org.kie.interactivepredictions.explainability.engine.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.kie.interactivepredictions.api.engines.ExplainabilityEngine;
import org.kie.interactivepredictions.api.models.IPInputExplainability;
import org.kie.interactivepredictions.api.models.IPInputPrediction;
import org.kie.interactivepredictions.api.models.IPOutputExplainability;
import org.kie.interactivepredictions.api.models.IPOutputPrediction;
import org.kie.interactivepredictions.api.services.PredictionService;
import org.kie.kogito.explainability.model.Saliency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExplainabilityEngineImplTest {

    private static ExplainabilityEngine explainabilityEngine;
    private static PredictionService predictionServiceMock;
    private static Map<String, Object> resultVariables;

    @BeforeAll
    public static void init() {
        explainabilityEngine = new ExplainabilityEngineImpl();
        resultVariables = new HashMap<>();
        resultVariables.put("score", -8.0);
        resultVariables.put("reason1", "characteristic2ReasonCode");
        IPOutputPrediction ipOutputPrediction = new IPOutputPrediction("OK", "class",
                                                                       resultVariables);
        predictionServiceMock = mock(PredictionService.class);
        when(predictionServiceMock.predict(any(IPInputPrediction.class))).thenReturn(ipOutputPrediction);
    }

    @Test
    void explain() {
        Map<String, Object> inputData = new HashMap<>();
        inputData.put("input1", -50);
        inputData.put("input2", "classB");
        IPOutputExplainability retrieved = explainabilityEngine.explain(new IPInputExplainability("filename",
                                                                                                  "modelname",
                                                                                                  inputData),
                                                                        predictionServiceMock);
        assertNotNull(retrieved);
        // Hardcoded expected values because the model is already known
        Map<String, String> retrievedResult = retrieved.getResult();
        assertNotNull(retrievedResult);
        assertEquals(resultVariables.size(), retrievedResult.size());
//        resultVariables.forEach((expected, o) -> {
//            assertTrue(retrievedResult.containsKey(expected));
//            assertEquals(expected, retrievedResult.get(expected).getOutput().getName());
//            assertEquals(o, retrievedResult.get(expected).getOutput().getValue().getUnderlyingObject());
//        });
    }
}