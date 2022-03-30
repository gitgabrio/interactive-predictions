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
package org.kie.interactivepredictions.api.models;

import java.io.Serializable;
import java.util.Map;
import java.util.stream.Collectors;

public class IPAvailableInputs implements Serializable {

    private final String modelName;

    /**
     * The key is the input name; the value is the input class
     */
    private final Map<String, String> inputMap;

    public IPAvailableInputs(String modelName, Map<String, Class<?>> inputMap) {
        this.modelName = modelName;
        this.inputMap = inputMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
                                                                              stringClassEntry -> stringClassEntry.getValue().getCanonicalName()));
    }

    public String getModelName() {
        return modelName;
    }

    public Map<String, String> getInputMap() {
        return inputMap;
    }
}
