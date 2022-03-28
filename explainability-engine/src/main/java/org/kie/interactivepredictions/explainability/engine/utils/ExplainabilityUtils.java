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
package org.kie.interactivepredictions.explainability.engine.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.kie.interactivepredictions.api.models.IPInputPrediction;
import org.kie.interactivepredictions.api.models.IPOutputPrediction;
import org.kie.interactivepredictions.api.services.PredictionService;
import org.kie.kogito.explainability.Config;
import org.kie.kogito.explainability.local.lime.LimeConfig;
import org.kie.kogito.explainability.local.lime.LimeExplainer;
import org.kie.kogito.explainability.model.Feature;
import org.kie.kogito.explainability.model.FeatureFactory;
import org.kie.kogito.explainability.model.Output;
import org.kie.kogito.explainability.model.PerturbationContext;
import org.kie.kogito.explainability.model.Prediction;
import org.kie.kogito.explainability.model.PredictionInput;
import org.kie.kogito.explainability.model.PredictionOutput;
import org.kie.kogito.explainability.model.PredictionProvider;
import org.kie.kogito.explainability.model.Saliency;
import org.kie.kogito.explainability.model.SimplePrediction;
import org.kie.kogito.explainability.model.Type;
import org.kie.kogito.explainability.model.Value;

public class ExplainabilityUtils {

    private ExplainabilityUtils() {
    }

    public static PredictionProvider getPredictionProvider(PredictionService predictionService, String fileName,
                                                           String modelName) {
        return inputs -> CompletableFuture.supplyAsync(() -> {
            List<PredictionOutput> toReturn = new ArrayList<>(inputs.size());
            for (PredictionInput predictionInput : inputs) {
                final Map<String, Object> inputData =
                        predictionInput.getFeatures().stream().collect(Collectors.toMap(Feature::getName,
                                                                                        Feature::getValue));
                IPOutputPrediction retrieved = predictionService.predict(new IPInputPrediction(fileName, modelName,
                                                                                               inputData));
                Map<String, Object> resultVariables = retrieved.getResultVariables();
                List<Output> outputs =
                        resultVariables.entrySet().stream().map(ExplainabilityUtils::getOutput).collect(Collectors.toList());
                toReturn.add(new PredictionOutput(outputs));
            }
            return toReturn;
        });
    }

    public static Map<String, Saliency> evaluate(final PredictionProvider predictionProvider, final Map<String,
            Object> inputData) throws ExecutionException, InterruptedException, TimeoutException {
        Random random = new Random();
        LimeConfig limeConfig = new LimeConfig()
                .withSamples(10)
                .withPerturbationContext(new PerturbationContext(0L, random, 1));
        LimeExplainer limeExplainer = new LimeExplainer(limeConfig);
        final Prediction prediction = getPrediction(predictionProvider, getPredictionInput(inputData));
        return limeExplainer.explainAsync(prediction, predictionProvider).get(Config.INSTANCE.getAsyncTimeout(),
                                                                              Config.INSTANCE.getAsyncTimeUnit());
    }

    private static PredictionInput getPredictionInput(final Map<String, Object> inputData) {
        List<Feature> fs =
                inputData.entrySet().stream().map(ExplainabilityUtils::getFeature).filter(Objects::nonNull).collect(Collectors.toList());
        return new PredictionInput(fs);
    }

    private static Prediction getPrediction(final PredictionProvider predictionProvider,
                                            final PredictionInput predictionInput) throws ExecutionException,
            InterruptedException, TimeoutException {
        List<PredictionOutput> predictionOutputs = predictionProvider.predictAsync(List.of(predictionInput))
                .get(Config.INSTANCE.getAsyncTimeout(), Config.INSTANCE.getAsyncTimeUnit());
        return new SimplePrediction(predictionInput, predictionOutputs.get(0));
    }

    private static Output getOutput(Map.Entry<String, Object> stringObjectEntry) {
        if (stringObjectEntry == null || stringObjectEntry.getValue() == null) {
            return null;
        }
        return new Output(stringObjectEntry.getKey(), Type.TEXT, new Value(stringObjectEntry.getValue()), 1d);
    }

    private static Feature getFeature(Map.Entry<String, Object> stringObjectEntry) {
        if (stringObjectEntry == null || stringObjectEntry.getValue() == null) {
            return null;
        }
        if (stringObjectEntry.getValue() instanceof String) {
            return FeatureFactory.newTextFeature(stringObjectEntry.getKey(), (String) stringObjectEntry.getValue());
        } else if (stringObjectEntry.getValue() instanceof Number) {
            return FeatureFactory.newNumericalFeature(stringObjectEntry.getKey(),
                                                      (Number) stringObjectEntry.getValue());
        } else if (stringObjectEntry.getValue() instanceof Boolean) {
            return FeatureFactory.newBooleanFeature(stringObjectEntry.getKey(), (Boolean) stringObjectEntry.getValue());
        } else {
            return null;
        }
    }
}
