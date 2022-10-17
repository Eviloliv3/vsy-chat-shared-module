/*
 *
 */
package de.vsy.shared_module.shared_module.collection_content_validation;

import static de.vsy.shared_module.shared_module.collection_content_validation.SetCheck.checkMessageDataSet;
import static de.vsy.shared_module.shared_module.data_element_validation.IdCheck.checkData;

import de.vsy.shared_module.shared_module.data_element_validation.BeanChecker;
import de.vsy.shared_transmission.shared_transmission.dto.CommunicatorDTO;
import de.vsy.shared_transmission.shared_transmission.packet.content.chat.TextMessageDTO;
import de.vsy.shared_transmission.shared_transmission.packet.content.relation.EligibleContactEntity;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Simple tools for checking map-contained dataManagement.
 */
public class MapCheck {

  private MapCheck() {
  }

  /**
   * Check dataManagement.
   *
   * @param mapToCheck the map to check
   * @return the string
   */
  public static Optional<String> checkMessageHistory(
      final Map<Integer, List<TextMessageDTO>> mapToCheck) {
    var deadInfo = new StringBuilder();

    for (var contactMessages : mapToCheck.entrySet()) {
      var checkString = checkData(contactMessages.getKey());

      checkString.ifPresent(deadInfo::append);
      checkString = checkMessageDataSet(contactMessages.getValue());

      checkString.ifPresent(s -> deadInfo.append("\nFehlerhafte Nachrichtendaten: ").append(s));
    }
    return (deadInfo.length() > 0) ? Optional.of(deadInfo.toString()) : Optional.empty();
  }

  public static Optional<String> checkActiveContacts(
      final Map<EligibleContactEntity, Set<CommunicatorDTO>> mapToCheck) {
    var deadInfo = new StringBuilder();

    for (var contactEntrySet : mapToCheck.entrySet()) {
      if (contactEntrySet.getKey() == null) {
        deadInfo.append("\nKontaktliste enthaelt ungültigen Schluessel (null).");
      } else {
        for (var currentContact : contactEntrySet.getValue()) {
          final var checkString = BeanChecker.checkBean(currentContact);

          checkString.ifPresent(s -> deadInfo.append("\n").append(s));
        }
      }
    }

    return (deadInfo.length() > 0) ? Optional.of(deadInfo.toString()) : Optional.empty();
  }
}
