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
package org.kie.interactivepredictions.prediction.service.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.kie.interactivepredictions.api.engines.PredictionEngine;
import org.kie.interactivepredictions.api.models.IPAvailableInputs;
import org.kie.interactivepredictions.api.models.IPInputPrediction;
import org.kie.interactivepredictions.api.models.IPModelFileTupla;
import org.kie.interactivepredictions.api.models.IPOutputPrediction;
import org.kie.interactivepredictions.api.services.PredictionService;

import static org.kie.interactivepredictions.api.utils.EngineFinder.getPredictionEngine;

public class PredictionServiceImpl implements PredictionService {

    private static final PredictionEngine PREDICTIONENGINE = getPredictionEngine(true);

    @Override
    public IPOutputPrediction predict(IPInputPrediction input) {
        return PREDICTIONENGINE.predict(input);
    }

    @Override
    public List<IPModelFileTupla> availableModels() {
        Map<String, List<String>> availableModels = PREDICTIONENGINE.availableModels();
        return availableModels.entrySet().stream()
                .map(stringListEntry -> stringListEntry.getValue().stream()
                        .map(modelName -> new IPModelFileTupla(modelName, stringListEntry.getKey())))
                .flatMap((Function<Stream<IPModelFileTupla>, Stream<IPModelFileTupla>>) ipModelFileTuplaStream -> ipModelFileTuplaStream)
                .collect(Collectors.toList());
    }

    @Override
    public IPAvailableInputs availableInput(String modelName, String fileName) {
        Map<String, Class<?>> availableInput = PREDICTIONENGINE.availableInput(modelName, fileName);
        return new IPAvailableInputs(modelName, availableInput);
    }
}
