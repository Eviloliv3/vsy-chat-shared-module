/*
 *
 */
package de.vsy.shared_module.data_element_validation;

import de.vsy.shared_utility.string_manipulation.StringShortener;
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
      final var illegalChars = new StringBuilder();

      for (var badCharacter : META_CHARS) {

        if (toCheck.contains(badCharacter)) {
          illegalChars.append(badCharacter).append(", ");
        }
      }
      if (illegalChars.length() > 2) {
        StringShortener.cutTrailingChars(illegalChars, 2);
        deadInfo.append("Contains invalid characters: ").append(illegalChars);
        return Optional.of(deadInfo.toString());
      }
    }
    return Optional.empty();
  }
}
