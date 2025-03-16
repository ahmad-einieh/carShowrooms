package com.project.carshowrooms.helpers;

import java.util.regex.*;

public class EmailValidator {

    // Regular expression pattern for a valid email address
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    // Compile the regex pattern
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        // Match the input email with the regex pattern
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

}