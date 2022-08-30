/*
 *
 */
package de.vsy.shared_module.shared_module.collection_content_validation;

import de.vsy.shared_module.shared_module.data_element_validation.BeanChecker;
import de.vsy.shared_transmission.shared_transmission.dto.CommunicatorDTO;
import de.vsy.shared_transmission.shared_transmission.packet.content.chat.TextMessageDTO;
import de.vsy.shared_transmission.shared_transmission.packet.content.relation.EligibleContactEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static de.vsy.shared_module.shared_module.collection_content_validation.SetCheck.checkMessageDataSet;
import static de.vsy.shared_module.shared_module.data_element_validation.IdCheck.checkData;

/** Simple tools for checking map-contained dataManagement. */
public
class MapCheck {

    private
    MapCheck () {}

    /**
     * Check dataManagement.
     *
     * @param mapToCheck the map to check
     *
     * @return the string
     */
    public static
    String checkMessageHistory (
            final Map<Integer, List<TextMessageDTO>> mapToCheck) {
        String checkString;
        var deadInfo = new StringBuilder();

        for (var contactMessages : mapToCheck.entrySet()) {
            checkString = checkData(contactMessages.getKey());

            if (checkString != null) {
                deadInfo.append(checkString);
            }
            checkString = checkMessageDataSet(contactMessages.getValue());

            if (checkString != null) {
                deadInfo.append("\nFehlerhafte Nachrichtendaten: ")
                        .append(checkString);
            }
        }
        return (deadInfo.length() > 0) ? deadInfo.toString() : null;
    }

    public static
    String checkActiveContacts (
            final Map<EligibleContactEntity, Set<CommunicatorDTO>> mapToCheck) {
        var deadInfo = new StringBuilder();

        for (var contactEntrySet : mapToCheck.entrySet()) {
            if (contactEntrySet == null) {
                deadInfo.append(
                        "\nKontaktliste enthaelt ungültiges Schluessel (null).");
            } else {
                for (var currentContact : contactEntrySet.getValue()) {
                    var checkString = BeanChecker.checkBean(currentContact);

                    if (checkString != null) {
                        deadInfo.append("\n").append(checkString);
                    }
                }
            }
        }

        return (deadInfo.length() > 0) ? deadInfo.toString() : null;
    }
}
