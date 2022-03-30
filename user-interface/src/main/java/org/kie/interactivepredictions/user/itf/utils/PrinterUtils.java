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

import java.util.Map;

import org.kie.interactivepredictions.api.models.IPModelFileTupla;

public class PrinterUtils {

    private PrinterUtils() {
    }

    public static void printMenu(String[] options) {
        for (String option : options) {
            System.out.println(option);
        }
        System.out.print("Choose your option : ");
    }

    public static void printModels(final Map<Integer, IPModelFileTupla> modelIndexMap) {
        modelIndexMap.forEach((key, value) -> System.out.println(key + ": " + value.getModelName() + " (" + value.getFileName() + ")"));
        System.out.print("Choose your model : ");
    }

    public static void printInputData(final Map<String, Object> inputData) {
        System.out.print("Input data: ");
        inputData.forEach((key, value) -> System.out.println(key + ": " + value+ " (" + value.getClass() + ")"));
    }
}
