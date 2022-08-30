/*
 *
 */
package de.vsy.shared_module.shared_module.data_element_validation;

import de.vsy.shared_utility.string_manipulation.StringShortener;

/**
 * Simple tool: checks String for null-value or length less than 4 charachters.
 */
public
class StringCheck {

    /** The meta chars. */
    private static final String[] META_CHARS = {"/", "'", "°", "`", "|", "\\"};

    private
    StringCheck () {
    }

    /**
     * Check string: not null && length > 3.
     *
     * @param toCheck the to check
     *
     * @return the error string
     */
    public static
    String checkString (final String toCheck) {
        final var deadInfo = new StringBuilder();

        if (toCheck == null || toCheck.isEmpty()) {
            deadInfo.append("null oder leer");
        } else {
            final var illegalChars = new StringBuilder();

            for (var badCharacter : META_CHARS) {

                if (toCheck.contains(badCharacter)) {
                    deadInfo.append(badCharacter).append(", ");
                }
            }
            if (illegalChars.length() > 2) {
                StringShortener.cutTrailingChars(illegalChars, 2);
                deadInfo.append("Enthaelt ungültige(s) Zeichen: ")
                        .append(illegalChars);
            }
        }
        return (deadInfo.length() > 0) ? deadInfo.toString() : null;
    }
}
