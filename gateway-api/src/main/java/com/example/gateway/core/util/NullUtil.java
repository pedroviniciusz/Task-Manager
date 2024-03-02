package com.example.gateway.core.util;

public class NullUtil {

    public static boolean isNull(Object value) {
        return value == null;
    }

    public static boolean isNotNull(Object value) {
        return value != null;
    }

    public static boolean isNullOrEmpty(String value) {
        return (value == null) || (value.trim().isEmpty());
    }

    private NullUtil() {
    }
}
