package com.pharmaease.utils;

public class InputSanitizer {

    public static String sanitize(String value) {
        if (value == null) return null;

        return value
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;")
                .replaceAll("\"", "&quot;")
                .replaceAll("'", "&#39;")
                .trim();
    }

    public static int toInt(String number) {
        try {
            return Integer.parseInt(number);
        } catch (Exception e) {
            return 0;
        }
    }

    public static double toDouble(String number) {
        try {
            return Double.parseDouble(number);
        } catch (Exception e) {
            return 0.0;
        }
    }
}
