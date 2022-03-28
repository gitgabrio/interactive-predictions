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
package org.kie.interactivepredictions.dialogue.service.impl;

import org.kie.interactivepredictions.api.engines.DialogueEngine;
import org.kie.interactivepredictions.api.models.IPInputDialogue;
import org.kie.interactivepredictions.api.models.IPOutputDialogue;
import org.kie.interactivepredictions.api.services.DialogueService;
import org.kie.interactivepredictions.api.services.ExplainabilityService;

import static org.kie.interactivepredictions.api.utils.EngineFinder.getDialogueEngine;
import static org.kie.interactivepredictions.api.utils.ServiceFinder.getExplainabilityService;

public class DialogueServiceImpl implements DialogueService {

    @Override
    public IPOutputDialogue dialogue(IPInputDialogue input) {
        ExplainabilityService explainabilityService = getExplainabilityService(true);
        DialogueEngine dialogueEngine = getDialogueEngine(true);
        return dialogueEngine.dialogue(input, explainabilityService);
    }
}
