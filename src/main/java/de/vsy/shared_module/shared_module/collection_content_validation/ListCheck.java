/*
 *
 */
package de.vsy.shared_module.shared_module.collection_content_validation;

import de.vsy.shared_module.shared_module.data_element_validation.BeanChecker;
import de.vsy.shared_transmission.shared_transmission.dto.CommunicatorDTO;
import de.vsy.shared_transmission.shared_transmission.packet.content.chat.TextMessageDTO;

import java.util.List;
import java.util.Set;

/** Simple tool: List check. */
public
class ListCheck {

    private
    ListCheck () {}

    /**
     * Check member list.
     *
     * @param listData the list dataManagement
     *
     * @return the string
     */
    public static
    String checkMemberList (final Set<CommunicatorDTO> listData) {
        var deadInfo = new StringBuilder();
        String checkString;

        if (listData != null) {

            for (var currentCommunicator : listData) {
                checkString = BeanChecker.checkBean(currentCommunicator);

                if (checkString != null) {
                    deadInfo.append("\nListenPosition: ")
                            .append(currentCommunicator)
                            .append(" / ")
                            .append(checkString);
                }
            }
        } else {
            deadInfo.append("Kontaktliste ist nicht vorhanden.");
        }
        return (deadInfo.length() > 0) ? deadInfo.toString() : null;
    }

    /**
     * Check message dataManagement list.
     *
     * @param listData the list dataManagement
     *
     * @return the string
     */
    public static
    String checkMessageDataList (final List<TextMessageDTO> listData) {
        var deadInfo = new StringBuilder();
        String checkString;

        if (listData != null) {

            for (var msgPos = listData.size() - 1; msgPos >= 0; msgPos--) {
                checkString = BeanChecker.checkBean(listData.get(msgPos));

                if (checkString != null) {
                    deadInfo.append("Position der fehlerhaften " + "Nachricht: ")
                            .append(msgPos)
                            .append(checkString);
                }
            }
        } else {
            deadInfo.append("Nachrichtenliste nicht vorhanden.");
        }
        return (deadInfo.length() > 0) ? deadInfo.toString() : null;
    }
}
