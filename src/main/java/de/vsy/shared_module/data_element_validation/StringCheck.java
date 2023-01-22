
package de.vsy.shared_module.data_element_validation;

import java.util.Optional;

/**
 * Simple tool: checks String for null-value or length less than 4 characters.
 */
public class StringCheck {

    /**
     * The meta chars.
     */
    private static final String[] META_CHARS = {"/", "'", "°", "`", "|", "\\"};

    private StringCheck() {
    }

    /**
     * Check string: not null && length > 3.
     *
     * @param toCheck the to check
     * @return the error string
     */
    public static Optional<String> checkString(final String toCheck) {
        final var deadInfo = new StringBuilder();

        if (toCheck == null || toCheck.isEmpty()) {
            return Optional.of("null or empty");
        } else {
            int illegalCharCounter = 0;

            for (var badCharacter : META_CHARS) {

                if (toCheck.contains(badCharacter)) {
                    illegalCharCounter++;
                }
            }
            if (illegalCharCounter > 0) {
                deadInfo.append("Contains ").append(illegalCharCounter).append(" invalid characters");
                return Optional.of(deadInfo.toString());
            }
        }
        return Optional.empty();
    }
}
