package activities.model;

import org.apache.commons.text.StringEscapeUtils;

/**
 * Utility class for validating and sanitizing user input.
 */
public class InputValidator {

    /**
     * Escapes HTML special characters in the input string.
     * Converts characters like < > & " ' to their HTML entity equivalents.
     * @param input the raw user input
     * @return the escaped string, safe for inclusion in HTML output
     */
    public static String escapeHtml(String input) {
        if (input == null) {
            return null;
        }
        return StringEscapeUtils.escapeHtml4(input);
    }

    /**
     * Checks if a string is null or empty after trimming.
     */
    public static boolean isEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }
}
