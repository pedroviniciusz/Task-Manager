package com.example.user.core.util;

import static com.example.user.core.util.NullUtil.isNullOrEmpty;

public class CpfUtil {

    private CpfUtil() {
    }

    public static boolean isCpf(String cpf) {

        cpf = cpf.replaceAll("\\D", "");

        if (isNullOrEmpty(cpf)) {
            return false;
        }

        if (cpf.length() != 11)
            return false;

        if (cpf.matches("^(\\d)\\1{10}")) {
            return false;
        }

        return calculateDigits(cpf).equals(cpf.substring(9, 11));
    }

    private static String calculateDigits(String cpf) {
        String digits = cpf.substring(0, 9);

        int firstDigit = calculateFirstDigit(digits);
        int secondDigit = calculateSecondDigit(digits, firstDigit);

        return firstDigit + Integer.toString(secondDigit);
    }

    private static int calculateFirstDigit(String digits) {
        int firstDigit;
        int sum = 0;
        int peso = 10;
        for (int i = 0; i < digits.length(); i++) {
            sum += Integer.parseInt(digits.substring(i, i + 1)) * peso--;
        }

        if (sum % 11 == 0 | sum % 11 == 1) {
            firstDigit = 0;
        } else {
            firstDigit = 11 - (sum % 11);
        }

        return firstDigit;
    }

    private static int calculateSecondDigit(String digits, int firstDigit) {
        int secondDigit;
        int sum = 0;
        int weight = 11;
        for (int i = 0; i < digits.length(); i++) {
            sum += Integer.parseInt(digits.substring(i, i + 1)) * weight--;
        }
        sum += firstDigit * 2;
        if (sum % 11 == 0 | sum % 11 == 1) {
            secondDigit = 0;
        } else {
            secondDigit = 11 - (sum % 11);
        }

        return secondDigit;
    }

    public static String formatCpf(String cpf) {
        String formattedCpf = cpf.replaceAll("\\D+", "");

        return formattedCpf.substring(0, 3) + "." + formattedCpf.substring(3, 6) + "." + formattedCpf.substring(6, 9) + "-" + formattedCpf.substring(9);
    }

}
