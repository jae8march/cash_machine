package com.company.app.util.valid;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

/**
 * Validator for input.
 */
public class Validator {
    /** Regex constant*/
    private static final String PASSWORD_REGEX = "^(?=\\S+$).{5,}$";
    private static final String DOUBLE_REGEX = "(\\.\\d\\d|\\d+\\.\\d+|\\d+\\.)";
    private static final String INT_REGEX = "(?<![-.])\\b[0-9]+\\b(?!\\.[0-9])";
    private static final String NAME_REGEX = "^[a-zA-Zа-яА-Я\\\\s]+";

    /**
     * Checks number integrity
     * @param value
     * @return true if value is double
     */
    public static boolean isValidDouble(String value) {
        return Pattern.compile(DOUBLE_REGEX).matcher(value).matches();
    }

    /**
     * Checks string integrity
     * @param value
     * @return true if value is string
     */
    public static boolean isValidString(String value) {
        return Pattern.compile(NAME_REGEX).matcher(value).matches();
    }

    /**
     * Checks number integrity
     * @param value
     * @return true if value is int
     */
    public static boolean isValidInt(String value) {
        return Pattern.compile(INT_REGEX).matcher(value).matches();
    }

    /**
     * Checks for null value.
     * @param value
     * @return true if value null
     */
    public static boolean isValidNull(String value){
        return value == null || value.isEmpty();
    }

    /**
     *
     * @param password
     * @return
     */
    public static boolean isValidPassword(String password){
        return password.matches(PASSWORD_REGEX);
    }

    public static boolean isLegalDate(String date) {
        SimpleDateFormat myFormat = new SimpleDateFormat("dd.MM.yyyy");
        myFormat.setLenient(false);
        try {
            myFormat.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
