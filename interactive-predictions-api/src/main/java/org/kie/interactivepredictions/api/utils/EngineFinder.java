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
package org.kie.interactivepredictions.api.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import org.kie.interactivepredictions.api.engines.DialogueEngine;
import org.kie.interactivepredictions.api.engines.ExplainabilityEngine;
import org.kie.interactivepredictions.api.engines.IPEngine;
import org.kie.interactivepredictions.api.engines.PredictionEngine;
import org.kie.interactivepredictions.api.exceptions.InteractivePredictionsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EngineFinder {

    private EngineFinder() {
    }

    private static final Logger logger = LoggerFactory.getLogger(EngineFinder.class.getName());

    private static final ServiceLoader<DialogueEngine> dialogueEngineLoader = ServiceLoader.load(DialogueEngine.class);
    private static final ServiceLoader<ExplainabilityEngine> explainabilityEngineLoader = ServiceLoader.load(ExplainabilityEngine.class);
    private static final ServiceLoader<PredictionEngine> predictionEngineLoader = ServiceLoader.load(PredictionEngine.class);

    public static DialogueEngine getDialogueEngine(boolean refresh) {
        logger.debug("getDialogueEngine {}", refresh);
        return getEngine(dialogueEngineLoader, refresh);
    }

    public static ExplainabilityEngine getExplainabilityEngine(boolean refresh) {
        logger.debug("getExplainabilityEngine {}", refresh);
        return getEngine(explainabilityEngineLoader, refresh);
    }

    public static PredictionEngine getPredictionEngine(boolean refresh) {
        logger.debug("getPredictionEngine {}", refresh);
        return getEngine(predictionEngineLoader, refresh);
    }

    private static <T extends IPEngine> T getEngine(ServiceLoader<T> loader, boolean refresh) {
        if (refresh) {
            loader.reload();
        }
        Iterator<T> providers = loader.iterator();
        List<T> engines = new ArrayList<>();
        providers.forEachRemaining(engines::add);
        if (engines.size() != 1) {
            throw new InteractivePredictionsException(String.format("Expected exactly one engine, found %d", engines.size()));
        }
        return engines.get(0);
    }
}
