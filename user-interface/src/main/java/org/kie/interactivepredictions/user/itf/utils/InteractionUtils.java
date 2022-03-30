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
package org.kie.interactivepredictions.user.itf.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.kie.interactivepredictions.api.models.IPAvailableInputs;
import org.kie.interactivepredictions.api.models.IPModelFileTupla;

import static org.kie.interactivepredictions.user.itf.utils.ConversionUtils.convertFromString;
import static org.kie.interactivepredictions.user.itf.utils.ConversionUtils.getMappedClass;
import static org.kie.interactivepredictions.user.itf.utils.PrinterUtils.printInputData;
import static org.kie.interactivepredictions.user.itf.utils.PrinterUtils.printMenu;
import static org.kie.interactivepredictions.user.itf.utils.PrinterUtils.printModels;

public class InteractionUtils {

    private InteractionUtils() {
    }

    public static int getOption(Scanner scanner, String[] options) {
        int option = 1;
        printMenu(options);
        try {
            option = scanner.nextInt();
        } catch (Exception ex) {
            System.out.println("Please enter an integer value between 1 and " + options.length);
            scanner.next();
        }
        return option;
    }

    public static IPModelFileTupla getModel(Scanner scanner, final Map<Integer, IPModelFileTupla> modelIndexMap) {
        Integer modelIndex = 0;
        IPModelFileTupla model = null;
        printModels(modelIndexMap);
        try {
            modelIndex = scanner.nextInt();
            if (!modelIndexMap.containsKey(modelIndex)) {
                throw new IllegalArgumentException();
            }
            model = modelIndexMap.get(modelIndex);
        } catch (Exception ex) {
            System.out.println("Please enter an integer value between 1 and " + modelIndexMap.size());
            scanner.next();
        }
        return model;
    }

    public static Map<String, Object> getInputData(Scanner scanner, IPAvailableInputs availableInputs) {
        final Map<String, Object> toReturn = new HashMap<>();
        availableInputs.getInputMap().forEach((key, value) -> {
            String insertedValue = getInputValue(scanner, key);
            if (insertedValue != null && !insertedValue.isEmpty()) {
                Class<?> expectedClass = getMappedClass(value);
                if (expectedClass != null) {
                    Object actualObject = convertFromString(expectedClass, insertedValue);
                    toReturn.put(key, actualObject);
                }
            }
        });
        printInputData(toReturn);
        return toReturn;
    }

    private static String getInputValue(Scanner scanner, String inputName) {
        System.out.print("Insert value for " + inputName + ": ");
        String toReturn = scanner.nextLine();
        while (toReturn == null || toReturn.isEmpty()) {
            toReturn = scanner.nextLine();
        }
        return toReturn;
    }
}
