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
package org.kie.interactivepredictions.prediction.engine.utils;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.kie.api.pmml.PMML4Result;
import org.kie.api.pmml.PMMLRequestData;
import org.kie.pmml.api.PMMLRuntimeFactory;
import org.kie.pmml.api.enums.FIELD_USAGE_TYPE;
import org.kie.pmml.api.models.MiningField;
import org.kie.pmml.api.models.PMMLModel;
import org.kie.pmml.api.runtime.PMMLRuntime;
import org.kie.pmml.evaluator.assembler.factories.PMMLRuntimeFactoryImpl;
import org.kie.pmml.evaluator.core.PMMLContextImpl;
import org.kie.pmml.evaluator.core.utils.PMMLRequestDataBuilder;

import static org.kie.interactivepredictions.api.utils.FileUtils.getFile;

public class PMMLUtils {

    private static final PMMLRuntimeFactory PMML_RUNTIME_FACTORY = new PMMLRuntimeFactoryImpl();

    private PMMLUtils() {
    }

    public static Map<String, Class<?>> getAvailableInputs(String modelName, String fileName) {
        final PMMLRuntime pmmlRuntime = getPMMLRuntime(fileName);
        final PMMLModel pmmlModel = pmmlRuntime.getPMMLModels().stream()
                .filter(pmmlMod -> pmmlMod.getName().equals(modelName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Failed to find " + modelName + " inside " + fileName));
        return pmmlModel.getMiningFields().stream()
                .filter(miningField -> miningField.getUsageType() != FIELD_USAGE_TYPE.TARGET)
                .collect(Collectors.toMap(MiningField::getName,
                                          miningField -> miningField.getDataType().getMappedClass()));
    }

    public static Map<String, List<String>> getAvailableModelsMap(List<File> pmmlFiles) {
        return pmmlFiles.stream().collect(Collectors.toMap(File::getName,
                                                           pmmlFile -> {
                                                               PMMLRuntime runtime =
                                                                       PMML_RUNTIME_FACTORY.getPMMLRuntimeFromFile(pmmlFile);
                                                               return runtime.getPMMLModels().stream().map(PMMLModel::getName).collect(Collectors.toList());
                                                           }));
    }

    public static PMMLRuntime getPMMLRuntime(String fileName) {
        File pmmlFile = getFile(fileName);
        return PMML_RUNTIME_FACTORY.getPMMLRuntimeFromFile(pmmlFile);
    }

    public static PMML4Result evaluate(final PMMLRuntime pmmlRuntime,
                                       final Map<String, Object> inputData,
                                       final String modelName) {
        final PMMLRequestData pmmlRequestData = getPMMLRequestData(modelName, inputData);
        return pmmlRuntime.evaluate(modelName, new PMMLContextImpl(pmmlRequestData));
    }

    private static PMMLRequestData getPMMLRequestData(String modelName, Map<String, Object> parameters) {
        String correlationId = "CORRELATION_ID";
        PMMLRequestDataBuilder pmmlRequestDataBuilder = new PMMLRequestDataBuilder(correlationId, modelName);
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            Object pValue = entry.getValue();
            Class class1 = pValue.getClass();
            pmmlRequestDataBuilder.addParameter(entry.getKey(), pValue, class1);
        }
        return pmmlRequestDataBuilder.build();
    }
}
