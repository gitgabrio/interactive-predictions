package org.kie.interactivepredictions.api.models;
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

import java.util.Map;

/**
 * Input for Explainability engine
 */
public class IPInputExplainability {

    private final String fileName;
    private final String modelName;
    private final Map<String, Object> inputData;

    public IPInputExplainability(String fileName, String modelName, Map<String, Object> inputData) {
        this.fileName = fileName;
        this.modelName = modelName;
        this.inputData = inputData;
    }

    public String getFileName() {
        return fileName;
    }

    public String getModelName() {
        return modelName;
    }

    public Map<String, Object> getInputData() {
        return inputData;
    }
}
