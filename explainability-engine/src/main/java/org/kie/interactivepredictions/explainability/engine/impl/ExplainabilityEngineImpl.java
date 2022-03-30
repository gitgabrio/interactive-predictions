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

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.kie.interactivepredictions.api.engines.ExplainabilityEngine;
import org.kie.interactivepredictions.api.exceptions.InteractivePredictionsException;
import org.kie.interactivepredictions.api.models.IPInputExplainability;
import org.kie.interactivepredictions.api.models.IPOutputExplainability;
import org.kie.interactivepredictions.api.services.PredictionService;
import org.kie.kogito.explainability.model.PredictionProvider;
import org.kie.kogito.explainability.model.Saliency;

import static org.kie.interactivepredictions.explainability.engine.utils.ExplainabilityUtils.evaluate;
import static org.kie.interactivepredictions.explainability.engine.utils.ExplainabilityUtils.getPredictionProvider;

public class ExplainabilityEngineImpl implements ExplainabilityEngine {

    @Override
    public IPOutputExplainability explain(IPInputExplainability input, PredictionService predictionService) {
        final PredictionProvider predictionProvider = getPredictionProvider(predictionService, input.getFileName(),
                                                                            input.getModelName());
        try {
            final Map<String, Saliency> saliencyMap = evaluate(predictionProvider, input.getInputData());
            final Map<String, String> result = saliencyMap.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey,
                                              entry -> entry.getValue().toString()));
            return new IPOutputExplainability(result);
        } catch (Exception e) {
            // Restore interrupted state...
            Thread.currentThread().interrupt();
            throw new InteractivePredictionsException("Failed to retrieve saliency map", e);
        }
    }
}
