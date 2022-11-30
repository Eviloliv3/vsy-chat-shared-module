/*
 *
 */
package de.vsy.shared_module.collection_content_validation;

import de.vsy.shared_module.data_element_validation.BeanChecker;
import de.vsy.shared_transmission.dto.CommunicatorDTO;
import de.vsy.shared_transmission.packet.content.chat.TextMessageDTO;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Simple tool: List check.
 */
public class ListCheck {

  private ListCheck() {
  }

  /**
   * Check member list.
   *
   * @param listData the list dataManagement
   * @return the string
   */
  public static Optional<String> checkMemberList(final Set<CommunicatorDTO> listData) {
    var deadInfo = new StringBuilder();

    if (listData != null) {

      for (var currentCommunicator : listData) {
        final var checkString = BeanChecker.checkBean(currentCommunicator);

        checkString.ifPresent(
            s -> deadInfo.append("; list position: ").append(currentCommunicator).append("/")
                .append(s));
      }
    } else {
      deadInfo.append("Contact list not specified.");
    }
    return (deadInfo.length() > 0) ? Optional.of(deadInfo.toString()) : Optional.empty();
  }

  /**
   * Check message dataManagement list.
   *
   * @param listData the list dataManagement
   * @return the string
   */
  public static Optional<String> checkMessageDataList(final List<TextMessageDTO> listData) {
    var deadInfo = new StringBuilder();

    if (listData != null) {

      for (var msgPos = listData.size() - 1; msgPos >= 0; msgPos--) {
        final var checkString = BeanChecker.checkBean(listData.get(msgPos));

        if (checkString.isPresent()) {
          deadInfo.append("Erroneous message list position: ").append(msgPos)
              .append(checkString.get());
        }
      }
    } else {
      deadInfo.append("No message list specified.");
    }
    return (deadInfo.length() > 0) ? Optional.of(deadInfo.toString()) : Optional.empty();
  }
}
