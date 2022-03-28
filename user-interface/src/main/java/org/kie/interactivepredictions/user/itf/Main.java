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

import java.util.Scanner;

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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option = 1;
        while (option != 4) {
            printMenu();
            try {
                option = scanner.nextInt();
                switch (option) {
                    case 1:
                        predict();
                        break;
                    case 2:
                        explain();
                        break;
                    case 3:
                        dialogue();
                        break;
                    case 4:
                        exit(0);
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            } catch (Exception ex) {
                System.out.println("Please enter an integer value between 1 and " + OPTIONS.length);
                scanner.next();
            }
        }
    }

    // Options
    private static void predict() {
        System.out.println("Thanks for choosing predict; Prediction Service is " + PREDICTION_SERVICE);
    }

    private static void explain() {
        System.out.println("Thanks for choosing explain; Explanation Service is " + EXPLAINABILITY_SERVICE);
    }

    private static void dialogue() {
        System.out.println("Thanks for choosing dialogue; Dialogue Service is " + DIALOGUE_SERVICE);
    }

    private static void printMenu() {
        for (String option : OPTIONS) {
            System.out.println(option);
        }
        System.out.print("Choose your option : ");
    }
}
