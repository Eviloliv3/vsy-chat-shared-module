/*
 *
 */
package de.vsy.shared_module.data_element_validation;

import static de.vsy.shared_module.data_element_validation.IdCheck.checkData;

import de.vsy.shared_transmission.dto.CommunicatorDTO;
import de.vsy.shared_transmission.dto.authentication.AuthenticationDTO;
import de.vsy.shared_transmission.dto.authentication.PersonalData;
import de.vsy.shared_transmission.packet.content.chat.TextMessageDTO;
import java.util.Optional;

/**
 * Simple tool: dataManagement bean checker.
 */
public class BeanChecker {

  private BeanChecker() {
  }

  /**
   * Check bean.
   *
   * @param data the dataManagement
   * @return the string
   */
  public static Optional<String> checkBean(final CommunicatorDTO data) {
    Optional<String> checkString;
    var deadInfo = new StringBuilder();

    if (data != null) {
      checkString = checkData(data.getCommunicatorId());

      checkString.ifPresent(deadInfo::append);

      checkString = StringCheck.checkString(data.getDisplayLabel());

      checkString.ifPresent(s -> deadInfo.append(" Ungültiger Anzeigename: ").append(s));
    } else {
      deadInfo.append("Es sind keine Kommunikatordaten vorhanden.");
    }
    return (deadInfo.length() > 0) ? Optional.of("Fehlerhafte Kommunikatordaten: " + deadInfo)
        : Optional.empty();
  }

  public static Optional<String> checkBean(AuthenticationDTO authenticationData) {
    Optional<String> checkString;
    var deadInfo = new StringBuilder();

    if (authenticationData != null) {
      checkString = StringCheck.checkString(authenticationData.getLogin());

      checkString.ifPresent(s -> deadInfo.append("Fehlerhafter Loginname: ").append(s));
      checkString = StringCheck.checkString(authenticationData.getPassword());

      checkString.ifPresent(s -> deadInfo.append("Fehlerhaftes Passwort: ").append(s));
    } else {
      deadInfo.append("Keine Authentifizierungsdaten enthalten.");
    }
    return (deadInfo.length() > 0) ? Optional.of("Fehlerhafte Klientendaten:" + deadInfo)
        : Optional.empty();
  }

  public static Optional<String> checkBean(PersonalData personalData) {
    Optional<String> checkString;
    var deadInfo = new StringBuilder();

    if (personalData != null) {
      checkString = StringCheck.checkString(personalData.getForename());

      if (checkString.isPresent()) {
        return Optional.of(
            deadInfo.append("Fehlerhafter Vorname: ").append(checkString.get()).toString());
      } else {
        checkString = StringCheck.checkString(personalData.getSurname());

        if (checkString.isPresent()) {
          return Optional.of(
              deadInfo.append("Fehlerhafter Nachname: ").append(checkString.get()).toString());
        }
      }
    } else {
      return Optional.of("Keine Klientendaten enthalten.");
    }
    return Optional.empty();
  }

  public static Optional<String> checkBean(final TextMessageDTO data) {
    throw new UnsupportedOperationException("TextMessageDTO wird noch nicht geprueft.");
  }

  /**
   * Check bean.
   *
   * @param data the dataManagement
   * @return the string
   */
  public static String checkBean(final Object data) {
    return "Unbekanntes Datenobjekt";
  }
}
