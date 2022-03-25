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
package org.kie.interactivepredictions.explainability.collaboration.utils;

import org.kie.interactivepredictions.api.engines.ExplainabilityEngine;
import org.kie.interactivepredictions.api.engines.PredictionEngine;
import org.kie.interactivepredictions.api.utils.EngineFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExplainabilityCollaborationUtils {

    private static final Logger logger = LoggerFactory.getLogger(ExplainabilityCollaborationUtils.class.getName());

    private ExplainabilityCollaborationUtils() {
    }

    public static ExplainabilityEngine getExplainabilityEngine() {
        logger.debug("getExplainabilityEngine");
        return EngineFinder.getExplainabilityEngine(true);
    }

    public static PredictionEngine getPredictionEngine() {
        logger.debug("getPredictionEngine");
        return EngineFinder.getPredictionEngine(true);
    }
}
