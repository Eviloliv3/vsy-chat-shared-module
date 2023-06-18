package de.vsy.shared_module.security.password;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Calculates hash values from char arrays and provides methods to salt those hash values. Input
 * will be treated as UTF_8 coded chars. Standard hash algorithm is SHA-1, but can be adjusted.
 */
public class PasswordHasher {

  private static final MessageDigest passwordDigester;

  static {
    try {
      passwordDigester = MessageDigest.getInstance("SHA-1");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Calculates a hex String hash value for the specified char array, using the standard hash
   * algorithm SHA-1.
   *
   * @param passwordChars char[]
   * @return String
   * @throws NullPointerException if char array is empty or null
   */
  public static String calculateHash(char[] passwordChars) {

    for (var passwordChar : passwordChars) {
      passwordDigester.update((byte) passwordChar);
    }
    return getHexString(passwordDigester);
  }

  /**
   * Helper function calculates the hash value for all currently existing values in MessageDigest.
   * The returned byte array values are then converted to hex values by twos. The resulting hex char
   * values are concatenated by a StringBuilder and then returned as a single String.
   *
   * @return String
   */
  private static String getHexString(MessageDigest digester) {
    StringBuilder packetHash = new StringBuilder();
    var digestedPassword = digester.digest();

    for (var digestPart : digestedPassword) {
      char[] hexDigits = new char[2];
      hexDigits[0] = Character.forDigit((digestPart >> 4) & 0xF, 16);
      hexDigits[1] = Character.forDigit((digestPart & 0xF), 16);
      packetHash.append(hexDigits);
    }
    return packetHash.toString();
  }

  /**
   * Calculates a hex String hash value for the specified char array, using the specified
   * algorithm.
   *
   * @param passwordChars char[]
   * @param digester      MessageDigest
   * @return String
   * @throws NullPointerException if one of the arguments is null
   */
  public static String calculateHash(char[] passwordChars, MessageDigest digester) {

    for (var passwordChar : passwordChars) {
      digester.update((byte) passwordChar);
    }
    return getHexString(digester);
  }

  /**
   * Calculates a salted hex String hash value for the specified char array and salt, using the
   * specified algorithm.
   *
   * @param hashedPassword byte[]
   * @param salt           byte []
   * @param digester       MessageDigest
   * @return String
   * @throws NullPointerException if one of the arguments is null
   */
  public String calculateSaltedHash(byte[] hashedPassword, byte[] salt, MessageDigest digester) {
    digester.update(hashedPassword);
    digester.update(salt);

    return getHexString(digester);
  }
}
