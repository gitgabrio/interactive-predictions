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
package org.kie.interactivepredictions.user.itf;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.kie.interactivepredictions.api.models.IPAvailableInputs;
import org.kie.interactivepredictions.api.models.IPInputDialogue;
import org.kie.interactivepredictions.api.models.IPInputExplainability;
import org.kie.interactivepredictions.api.models.IPInputPrediction;
import org.kie.interactivepredictions.api.models.IPModelFileTupla;
import org.kie.interactivepredictions.api.models.IPOutputDialogue;
import org.kie.interactivepredictions.api.models.IPOutputExplainability;
import org.kie.interactivepredictions.api.models.IPOutputPrediction;
import org.kie.interactivepredictions.api.services.DialogueService;
import org.kie.interactivepredictions.api.services.ExplainabilityService;
import org.kie.interactivepredictions.api.services.PredictionService;

import static java.lang.System.exit;
import static org.kie.interactivepredictions.api.utils.ServiceFinder.getDialogueService;
import static org.kie.interactivepredictions.api.utils.ServiceFinder.getExplainabilityService;
import static org.kie.interactivepredictions.api.utils.ServiceFinder.getPredictionService;
import static org.kie.interactivepredictions.user.itf.utils.InteractionUtils.getInputData;
import static org.kie.interactivepredictions.user.itf.utils.InteractionUtils.getModel;
import static org.kie.interactivepredictions.user.itf.utils.InteractionUtils.getOption;

public class Main {

    public static final DialogueService DIALOGUE_SERVICE = getDialogueService(true);
    public static final ExplainabilityService EXPLAINABILITY_SERVICE = getExplainabilityService(true);
    public static final PredictionService PREDICTION_SERVICE = getPredictionService(true);
    public static final String[] OPTIONS = {"1- Prediction",
            "2- Explanation",
            "3- Dialogue",
            "4- Exit",
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final List<IPModelFileTupla> availableModels = PREDICTION_SERVICE.availableModels();
        final Map<Integer, IPModelFileTupla> modelIndexMap = IntStream.range(0, availableModels.size())
                .boxed()
                .collect(Collectors.toMap(integer -> integer + 1,
                                          availableModels::get));
        application(scanner, modelIndexMap);
    }

    private static void application(Scanner scanner, final Map<Integer, IPModelFileTupla> modelIndexMap) {
        int option = getOption(scanner, OPTIONS);
        if (option == 4) {
            System.out.println("Bye");
            exit(0);
        }
        IPModelFileTupla model = getModel(scanner, modelIndexMap);
        IPAvailableInputs availableInputs = PREDICTION_SERVICE.availableInput(model.getModelName(),
                                                                              model.getFileName());
        switch (option) {
            case 1:
                predict(scanner, model, availableInputs);
                break;
            case 2:
                explain(scanner, model, availableInputs);
                break;
            case 3:
                dialogue(scanner, model, availableInputs);
                break;
            default:
                throw new IllegalArgumentException();
        }
        application(scanner, modelIndexMap);
    }

    // Options
    private static void predict(Scanner scanner, IPModelFileTupla model, IPAvailableInputs availableInputs) {
        System.out.println("Thanks for choosing predict; Prediction Service is " + PREDICTION_SERVICE);
        final Map<String, Object> inputData = getInputData(scanner, availableInputs);
        IPOutputPrediction retrieved = PREDICTION_SERVICE.predict(new IPInputPrediction(model.getFileName(),
                                                                                        model.getModelName(),
                                                                                        inputData));
        System.out.println("Retrieved " + retrieved);
    }

    private static void explain(Scanner scanner, IPModelFileTupla model, IPAvailableInputs availableInputs) {
        System.out.println("Thanks for choosing explain; Explanation Service is " + EXPLAINABILITY_SERVICE);
        final Map<String, Object> inputData = getInputData(scanner, availableInputs);
        IPOutputExplainability retrieved =
                EXPLAINABILITY_SERVICE.explain(new IPInputExplainability(model.getFileName(),
                                                                         model.getModelName(),
                                                                         inputData));
        System.out.println("Retrieved " + retrieved);
    }

    private static void dialogue(Scanner scanner, IPModelFileTupla model, IPAvailableInputs availableInputs) {
        System.out.println("Thanks for choosing dialogue; Dialogue Service is " + DIALOGUE_SERVICE);
        final Map<String, Object> inputData = getInputData(scanner, availableInputs);
        IPOutputDialogue retrieved = DIALOGUE_SERVICE.dialogue(new IPInputDialogue());
        System.out.println("Retrieved " + retrieved);
    }

}
