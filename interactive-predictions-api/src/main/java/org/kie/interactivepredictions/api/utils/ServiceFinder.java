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

import org.kie.interactivepredictions.api.services.DialogueService;
import org.kie.interactivepredictions.api.services.ExplainabilityService;
import org.kie.interactivepredictions.api.services.IPService;
import org.kie.interactivepredictions.api.services.PredictionService;
import org.kie.interactivepredictions.api.exceptions.InteractivePredictionsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceFinder {

    private ServiceFinder() {
    }

    private static final Logger logger = LoggerFactory.getLogger(ServiceFinder.class.getName());

    private static final ServiceLoader<DialogueService> dialogueServiceLoader = ServiceLoader.load(DialogueService.class);
    private static final ServiceLoader<ExplainabilityService> explainabilityServiceLoader = ServiceLoader.load(ExplainabilityService.class);
    private static final ServiceLoader<PredictionService> predictionServiceLoader = ServiceLoader.load(PredictionService.class);

    public static DialogueService getDialogueService(boolean refresh) {
        logger.debug("getDialogueService {}", refresh);
        return getService(dialogueServiceLoader, refresh);
    }

    public static ExplainabilityService getExplainabilityService(boolean refresh) {
        logger.debug("getExplainabilityService {}", refresh);
        return getService(explainabilityServiceLoader, refresh);
    }

    public static PredictionService getPredictionService(boolean refresh) {
        logger.debug("getPredictionService {}", refresh);
        return getService(predictionServiceLoader, refresh);
    }

    private static <T extends IPService> T getService(ServiceLoader<T> loader, boolean refresh) {
        if (refresh) {
            loader.reload();
        }
        Iterator<T> providers = loader.iterator();
        List<T> services = new ArrayList<>();
        providers.forEachRemaining(services::add);
        if (services.size() != 1) {
            throw new InteractivePredictionsException(String.format("Expected exactly one service, found %d", services.size()));
        }
        return services.get(0);
    }
}
