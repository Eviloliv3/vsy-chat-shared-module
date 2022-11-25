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

        if (checkString.isPresent()) {
          deadInfo.append("\nFehlerhafte Nachricht: ").append(checkString.get());
        }
      }
    } else {
      deadInfo.append("Nachrichtenliste nicht vorhanden.");
    }
    return (deadInfo.length() > 0) ? Optional.of(deadInfo.toString()) : Optional.empty();
  }
}
