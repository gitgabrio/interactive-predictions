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

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.kie.interactivepredictions.api.models.IPInputDialogue;
import org.kie.interactivepredictions.api.models.IPInputExplainability;
import org.kie.interactivepredictions.api.models.IPInputPrediction;
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

public class Main {

    private static final DialogueService DIALOGUE_SERVICE = getDialogueService(true);
    private static final ExplainabilityService EXPLAINABILITY_SERVICE = getExplainabilityService(true);
    private static final PredictionService PREDICTION_SERVICE = getPredictionService(true);
    private static final String[] OPTIONS = {"1- Prediction",
            "2- Explanation",
            "3- Dialogue",
            "4- Exit",
    };

    private static final Map<Integer, String> MODELINDEX_MAP;
    private static final Map<String, String> MODEL_MAP;

    static {
        MODEL_MAP = new HashMap<>();
        MODEL_MAP.put("LogisticRegression", "LogisticRegression.pmml");
        AtomicInteger counter = new AtomicInteger(0);
        MODELINDEX_MAP =
                MODEL_MAP.entrySet().stream().collect(Collectors.toMap(stringStringEntry -> counter.incrementAndGet(),
                                                                                Map.Entry::getKey));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option = getOption(scanner);
        String model = getModel(scanner);
        switch (option) {
            case 1:
                predict(model);
                break;
            case 2:
                explain(model);
                break;
            case 3:
                dialogue(model);
                break;
            case 4:
                exit(0);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    // Options
    private static void predict(String model) {
        System.out.println("Thanks for choosing predict; Prediction Service is " + PREDICTION_SERVICE);
        final Map<String, Object> inputData = new HashMap<>();
        inputData.put("variance", 2.3);
        inputData.put("skewness", 6.9);
        inputData.put("curtosis", 3.1);
        inputData.put("entropy", 5.1);
        IPOutputPrediction retrieved = PREDICTION_SERVICE.predict(new IPInputPrediction(MODEL_MAP.get(model), model,
                                                                                        inputData));
        System.out.println("Retrieved " + retrieved);
    }

    private static void explain(String model) {
        System.out.println("Thanks for choosing explain; Explanation Service is " + EXPLAINABILITY_SERVICE);
        final Map<String, Object> inputData = new HashMap<>();
        inputData.put("variance", 2.3);
        inputData.put("skewness", 6.9);
        inputData.put("curtosis", 3.1);
        inputData.put("entropy", 5.1);
        IPOutputExplainability retrieved =
                EXPLAINABILITY_SERVICE.explain(new IPInputExplainability(MODEL_MAP.get(model), model,
                                                                         inputData));
        System.out.println("Retrieved " + retrieved);
    }

    private static void dialogue(String model) {
        System.out.println("Thanks for choosing dialogue; Dialogue Service is " + DIALOGUE_SERVICE);
        final Map<String, Object> inputData = new HashMap<>();
        inputData.put("variance", 2.3);
        inputData.put("skewness", 6.9);
        inputData.put("curtosis", 3.1);
        inputData.put("entropy", 5.1);
        IPOutputDialogue retrieved = DIALOGUE_SERVICE.dialogue(new IPInputDialogue());
        System.out.println("Retrieved " + retrieved);
    }

    private static int getOption(Scanner scanner) {
        int option = 1;
        printMenu();
        try {
            option = scanner.nextInt();
        } catch (Exception ex) {
            System.out.println("Please enter an integer value between 1 and " + OPTIONS.length);
            scanner.next();
        }
        return option;
    }

    private static void printMenu() {
        for (String option : OPTIONS) {
            System.out.println(option);
        }
        System.out.print("Choose your option : ");
    }

    private static String getModel(Scanner scanner) {
        Integer modelIndex = 0;
        String model = "";
        printModels();
        try {
            modelIndex = scanner.nextInt();
            if (!MODELINDEX_MAP.containsKey(modelIndex)) {
                throw new IllegalArgumentException();
            }
            model = MODELINDEX_MAP.get(modelIndex);
            if (!MODEL_MAP.containsKey(model)) {
                throw new IllegalArgumentException();
            }
        } catch (Exception ex) {
            System.out.println("Please enter an integer value between 1 and " + MODELINDEX_MAP.size());
            scanner.next();
        }
        return model;
    }

    private static void printModels() {
        MODELINDEX_MAP.forEach((key, value) -> System.out.println(key + ": " + value));
        System.out.print("Choose your model : ");
    }
}
