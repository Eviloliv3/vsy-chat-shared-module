package de.vsy.shared_module.collection_content_validation;

import de.vsy.shared_module.data_element_validation.BeanChecker;
import de.vsy.shared_module.data_element_validation.IdCheck;
import de.vsy.shared_transmission.dto.CommunicatorDTO;
import de.vsy.shared_transmission.packet.content.chat.TextMessageDTO;
import de.vsy.shared_transmission.packet.content.relation.EligibleContactEntity;
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
   * Checks Map of Integer - TextMessageDTO for invalid entries. Entries can be invalid, if the
   * Integer is no valid client id or the mapped TextMessageDTO list contains invalid
   * TextMessageDTO. For each invalid entry and notification String will be created.
   *
   * @param mapToCheck the map to check
   * @return Optional containing notification String if invalid entries were found; empty Optional
   * otherwise
   */
  public static Optional<String> checkMessageHistory(
      final Map<Integer, List<TextMessageDTO>> mapToCheck) {
    var deadInfo = new StringBuilder();

    for (var contactMessages : mapToCheck.entrySet()) {
      var checkString = IdCheck.checkData(contactMessages.getKey());

      checkString.ifPresent(deadInfo::append);
      checkString = ListCheck.checkMessageDataSet(contactMessages.getValue());

      checkString.ifPresent(s -> deadInfo.append("\nErroneous messages: ").append(s));
    }
    return (deadInfo.length() > 0) ? Optional.of(deadInfo.toString()) : Optional.empty();
  }

  /**
   * Checks Map of EligibleContactEntity - CommunicatorDTO for invalid entries. Entries can be
   * invalid, if the EligibleContactEntity is no valid client id or the mapped CommunicatorDTO list
   * contains invalid CommunicatorDTO. For each invalid entry and notification String will be
   * created.
   *
   * @param mapToCheck the map to check
   * @return Optional containing notification String if invalid entries were found; empty Optional
   * otherwise
   */
  public static Optional<String> checkActiveContacts(
      final Map<EligibleContactEntity, Set<CommunicatorDTO>> mapToCheck) {
    var deadInfo = new StringBuilder();

    for (var contactEntrySet : mapToCheck.entrySet()) {
      if (contactEntrySet.getKey() == null) {
        deadInfo.append("\nContact list contains invalid null-value.");
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
