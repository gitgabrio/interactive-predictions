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
package org.kie.interactivepredictions.prediction.engine.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.kie.api.pmml.PMML4Result;
import org.kie.interactivepredictions.api.engines.PredictionEngine;
import org.kie.interactivepredictions.api.models.IPInputPrediction;
import org.kie.interactivepredictions.api.models.IPOutputPrediction;
import org.kie.pmml.api.runtime.PMMLRuntime;

import static org.kie.interactivepredictions.api.utils.FileUtils.getFilesByExtension;
import static org.kie.interactivepredictions.prediction.engine.utils.PMMLUtils.evaluate;
import static org.kie.interactivepredictions.prediction.engine.utils.PMMLUtils.getAvailableInputs;
import static org.kie.interactivepredictions.prediction.engine.utils.PMMLUtils.getAvailableModelsMap;
import static org.kie.interactivepredictions.prediction.engine.utils.PMMLUtils.getPMMLRuntime;

public class PredictionEngineImpl implements PredictionEngine {

    @Override
    public IPOutputPrediction predict(IPInputPrediction input) {
        final PMMLRuntime pmmlRuntime = getPMMLRuntime(input.getFileName());
        final PMML4Result result = evaluate(pmmlRuntime, input.getInputData(), input.getModelName());
        return new IPOutputPrediction(result.getResultCode(), result.getResultObjectName(),
                                      result.getResultVariables());
    }

    @Override
    public Map<String, List<String>> availableModels() {
        List<File> pmmlFiles = getFilesByExtension("pmml");
        return getAvailableModelsMap(pmmlFiles);
    }

    @Override
    public Map<String, Class<?>> availableInput(String modelName, String fileName) {
        return getAvailableInputs(modelName, fileName);
    }
}
