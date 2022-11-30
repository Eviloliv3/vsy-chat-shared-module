/*
 *
 */
package de.vsy.shared_module.data_element_validation;

import static de.vsy.shared_module.data_element_validation.IdCheck.checkData;
import static de.vsy.shared_module.data_element_validation.StringCheck.checkString;

import de.vsy.shared_transmission.dto.CommunicatorDTO;
import de.vsy.shared_transmission.dto.authentication.AuthenticationDTO;
import de.vsy.shared_transmission.dto.authentication.PersonalData;
import de.vsy.shared_transmission.packet.content.chat.ChatPacketDTO;
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
    Optional<String> checkResult;
    var deadInfo = new StringBuilder();

    if (data != null) {
      checkResult= checkData(data.getCommunicatorId());

      checkResult.ifPresent(s -> deadInfo.append("Invalid communicator id").append(s).append(". "));

      checkResult= StringCheck.checkString(data.getDisplayLabel());

      checkResult.ifPresent(s -> deadInfo.append("Invalid display name: ").append(s).append(". "));
    } else {
      deadInfo.append("No communicator data found.");
    }
    return (deadInfo.length() > 0) ? Optional.of("Invalid communicator data: " + deadInfo)
        : Optional.empty();
  }

  public static Optional<String> checkBean(AuthenticationDTO authenticationData) {
    Optional<String> checkResult;
    var deadInfo = new StringBuilder();

    if (authenticationData != null) {
      checkResult= StringCheck.checkString(authenticationData.getLogin());

      checkResult.ifPresent(s -> deadInfo.append("Invalid login name: ").append(s).append(". "));
      checkResult= StringCheck.checkString(authenticationData.getPassword());

      checkResult.ifPresent(s -> deadInfo.append("Invalid password: ").append(s).append(". "));
    } else {
      deadInfo.append("No authentication data found.");
    }
    return (deadInfo.length() > 0) ? Optional.of("Invalid credentials: " + deadInfo)
        : Optional.empty();
  }

  public static Optional<String> checkBean(PersonalData personalData) {
    Optional<String> checkResult;
    var deadInfo = new StringBuilder();

    if (personalData != null) {
      checkResult= StringCheck.checkString(personalData.getForename());
      checkResult.ifPresent(s -> deadInfo.append("Invalid first name: ").append(s).append(". "));

      checkResult= StringCheck.checkString(personalData.getSurname());
      checkResult.ifPresent(s-> deadInfo.append("Fehlerhafter Nachname: ").append(s).append(". "));
    } else {
      return Optional.of("No client data found.");
    }
    return deadInfo.length() > 0 ? Optional.of("Invalid personal data: " + deadInfo) : Optional.empty();
  }

  public static Optional<String> checkBean(final TextMessageDTO textMessage){
    Optional<String> checkResult = checkBean((ChatPacketDTO) textMessage);
    final var deadInfo = new StringBuilder();

    checkResult.ifPresent(deadInfo::append);

    checkResult = checkString(textMessage.getMessage());
    checkResult.ifPresent(s -> deadInfo.append("Invalid text message: ").append(s).append(". "));
    return deadInfo.length() > 0 ? Optional.of("Invalid text message: " + deadInfo) : Optional.empty();
  }

  private static Optional<String> checkBean(final ChatPacketDTO chatMessage){
    Optional<String> checkResult;
    final var deadInfo = new StringBuilder();

    if(chatMessage != null) {
      checkResult= checkData(chatMessage.getOriginatorId());
      checkResult.ifPresent(s -> deadInfo.append("Invalid originator id: ").append(s).append(". "));

      checkResult= checkData(chatMessage.getRecipientId());
      checkResult.ifPresent(s -> deadInfo.append("Invalid recipient id: ").append(s).append(". "));

      if(chatMessage.getContactType() == null){
        deadInfo.append("Missing contact type. ");
      }
    }else{
      return Optional.of("No chat message found.");
    }
    return deadInfo.length() > 0 ? Optional.of("Invalid chat message: " + deadInfo) : Optional.empty();
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
