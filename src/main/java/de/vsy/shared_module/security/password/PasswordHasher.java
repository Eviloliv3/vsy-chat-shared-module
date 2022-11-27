package de.vsy.shared_module.security.password;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Berechnet Hashwerte aus Passwörtern und versalzt bestehende Hashwerte. Es werden
 * Standardcharsets.UTF_8 kodierte Zeichen vorausgesetzt.
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
   * Berechnet einen Hashwert für das ubergebene Passwort, mittels der intern vorgegebenen
   * Hashfunktion. Der Hashwert wird in Hexadezimaldarstellung als String ausgegeben.
   *
   * @param passwordChars das zu hashende Passwort (char [])
   * @return der Hashwert (String)
   * @throws NullPointerException wenn kein Passwort Charakterarray übergeben wurde
   */
  public static String calculateHash(char[] passwordChars) {

    for (var passwordChar : passwordChars) {
      passwordDigester.update((byte) passwordChar);
    }
    return getHexString(passwordDigester);
  }

  /**
   * Hilfsfunktion! Berechnet den Hashwert für die aktuell im this.passwordDigester eingetragenen
   * Werte. Dann wird das erhaltenen Bytearray in einen vorzeichenbehafteten BigInteger-Wert
   * umgewandelt. Abschließend wird der Wert zur Basis 16 als String erstellt und, nach der
   * Umwandlung aller Buchstaben zu Grossbuchstaben, ausgegeben.
   *
   * @return der Hashwert in Hexadezimaldarstellung
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
   * Berechnet einen Hashwert für das ubergebene Passwort, mittels des uebergebenen
   * Hashwertberechners. Der Hashwert wird in Hexadezimaldarstellung als String ausgegeben.
   *
   * @param passwordChars das zu hashende Passwort (char [])
   * @param digester      der Hashwertberechner
   * @return der Hashwert (String)
   * @throws NullPointerException wenn kein Passwort Charakterarray übergeben wurde
   */
  public static String calculateHash(char[] passwordChars, MessageDigest digester) {

    for (var passwordChar : passwordChars) {
      digester.update((byte) passwordChar);
    }
    return getHexString(digester);
  }

  /**
   * Berechnet einen Hashwert aus dem ubergebene Passworthashwert und einem übergebenen Salz,
   * mittels der intern vorgegebenen Hashfunktion. Der Hashwert wird in Hexadezimaldarstellung als
   * String ausgegeben.
   *
   * @param hashedPassword das gehashte Passwort (byte [])
   * @param salt           das einzurechnende Salz (byte [])
   * @return der Hashwert (String)
   * @throws NullPointerException wenn kein Passwort oder Salz übergeben wurde
   */
  public String calculateSaltedHash(byte[] hashedPassword, byte[] salt, MessageDigest digester) {
    digester.update(hashedPassword);
    digester.update(salt);

    return getHexString(digester);
  }
}
