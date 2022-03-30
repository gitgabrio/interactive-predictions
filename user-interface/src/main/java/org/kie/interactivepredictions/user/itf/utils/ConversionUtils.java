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
import java.util.function.Predicate;

public class ConversionUtils {

    static final String FAILED_CONVERSION = "Failed to convert %s to %s";
    private static Predicate<Class<?>> IS_BOOLEAN =
            expectedClass -> expectedClass.isAssignableFrom(Boolean.class) || expectedClass.isAssignableFrom(boolean.class);
    private static Predicate<Class<?>> IS_INTEGER =
            expectedClass -> expectedClass.isAssignableFrom(Integer.class) || expectedClass.isAssignableFrom(int.class);
    private static Predicate<Class<?>> IS_LONG =
            expectedClass -> expectedClass.isAssignableFrom(Long.class) || expectedClass.isAssignableFrom(long.class);
    private static Predicate<Class<?>> IS_DOUBLE =
            expectedClass -> expectedClass.isAssignableFrom(Double.class) || expectedClass.isAssignableFrom(double.class);
    private static Predicate<Class<?>> IS_FLOAT =
            expectedClass -> expectedClass.isAssignableFrom(Float.class) || expectedClass.isAssignableFrom(float.class);
    private static Predicate<Class<?>> IS_CHARACTER =
            expectedClass -> expectedClass.isAssignableFrom(Character.class) || expectedClass.isAssignableFrom(char.class);
    private static Predicate<Class<?>> IS_BYTE =
            expectedClass -> expectedClass.isAssignableFrom(Byte.class) || expectedClass.isAssignableFrom(byte.class);
    private static Predicate<Class<?>> IS_SHORT =
            expectedClass -> expectedClass.isAssignableFrom(Short.class) || expectedClass.isAssignableFrom(short.class);

    private static final Map<String, Class> PRIMITIVE_BOXED_MAP = new HashMap<>();

    static {
        PRIMITIVE_BOXED_MAP.put(Boolean.TYPE.toString(), Boolean.class);
        PRIMITIVE_BOXED_MAP.put(Byte.TYPE.toString(), Byte.class);
        PRIMITIVE_BOXED_MAP.put(Character.TYPE.toString(), Character.class);
        PRIMITIVE_BOXED_MAP.put(Float.TYPE.toString(), Float.class);
        PRIMITIVE_BOXED_MAP.put(Integer.TYPE.toString(), Integer.class);
        PRIMITIVE_BOXED_MAP.put(Long.TYPE.toString(), Long.class);
        PRIMITIVE_BOXED_MAP.put(Short.TYPE.toString(), Short.class);
        PRIMITIVE_BOXED_MAP.put(Double.TYPE.toString(), Double.class);
    }

    private ConversionUtils() {
    }

    public static Class<?> getMappedClass(String fullClassName) {
        try {
            if (PRIMITIVE_BOXED_MAP.containsKey(fullClassName)) {
                return PRIMITIVE_BOXED_MAP.get(fullClassName);
            } else {
                return Class.forName(fullClassName);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to find Class " + fullClassName);
            return null;
        }
    }

    public static Object convertFromString(Class<?> expectedClass, String originalObject) {
        try {
            if (expectedClass.equals(String.class)) {
                return originalObject;
            }
            if (IS_BOOLEAN.test(expectedClass)) {
                return parseBoolean(originalObject);
            } else if (IS_INTEGER.test(expectedClass)) {
                return Integer.parseInt(originalObject);
            } else if (IS_LONG.test(expectedClass)) {
                return Long.parseLong(originalObject);
            } else if (IS_DOUBLE.test(expectedClass)) {
                return Double.parseDouble(originalObject);
            } else if (IS_FLOAT.test(expectedClass)) {
                return Float.parseFloat(originalObject);
            } else if (IS_CHARACTER.test(expectedClass)) {
                return parseChar(originalObject);
            } else if (IS_BYTE.test(expectedClass)) {
                return Byte.parseByte(originalObject);
            } else if (IS_SHORT.test(expectedClass)) {
                return Short.parseShort(originalObject);
            } else {
                throw new RuntimeException(String.format(FAILED_CONVERSION, originalObject, expectedClass.getName()));
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format(FAILED_CONVERSION, originalObject,
                                                     expectedClass.getName()));
        }
    }

    private static boolean parseBoolean(String value) {
        if ("true".equalsIgnoreCase(value)) {
            return true;
        } else if ("false".equalsIgnoreCase(value)) {
            return false;
        } else {
            throw new IllegalArgumentException("Impossible to parse as boolean " + value);
        }
    }

    private static char parseChar(String value) {
        if (value == null || value.length() != 1) {
            throw new IllegalArgumentException("Impossible to transform " + value + " as char");
        }
        return value.charAt(0);
    }
}
