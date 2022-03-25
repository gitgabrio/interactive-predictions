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
 * Output from Prediction engine
 */
public class IPOutputPrediction {

    private final String resultCode;
    private final String resultObjectName;
    private final Map<String, Object> resultVariables;

    public IPOutputPrediction(String resultCode, String resultObjectName, Map<String, Object> resultVariables) {
        this.resultCode = resultCode;
        this.resultObjectName = resultObjectName;
        this.resultVariables = resultVariables;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getResultObjectName() {
        return resultObjectName;
    }

    public Map<String, Object> getResultVariables() {
        return resultVariables;
    }

    @Override
    public String toString() {
        return "IPOutputPrediction{" +
                "resultCode='" + resultCode + '\'' +
                ", resultObjectName='" + resultObjectName + '\'' +
                ", resultVariables=" + resultVariables +
                '}';
    }
}
