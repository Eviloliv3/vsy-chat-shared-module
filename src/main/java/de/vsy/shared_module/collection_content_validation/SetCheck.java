package de.vsy.shared_module.collection_content_validation;

import de.vsy.shared_module.data_element_validation.BeanChecker;
import de.vsy.shared_transmission.packet.content.chat.TextMessageDTO;
import java.util.List;
import java.util.Optional;

public class SetCheck {

  private SetCheck() {
  }

  /**
   * Check message dataManagement list.
   *
   * @param listData the list dataManagement
   * @return the string
   */
  public static Optional<String> checkMessageDataSet(final List<TextMessageDTO> listData) {
    var deadInfo = new StringBuilder();

    if (listData != null) {

      for (var currentMessage : listData) {
        final var checkString = BeanChecker.checkBean(currentMessage);

        checkString.ifPresent(s ->deadInfo.append("\nErroneous message: ").append(s).append(". "));
      }
    } else {
      deadInfo.append("No messages specified.");
    }
    return (deadInfo.length() > 0) ? Optional.of(deadInfo.toString()) : Optional.empty();
  }
}
