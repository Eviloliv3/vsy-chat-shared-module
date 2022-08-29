package de.vsy.chat.shared_module.collection_content_validation;

import de.vsy.chat.shared_module.data_element_validation.BeanChecker;
import de.vsy.chat.shared_transmission.packet.content.chat.TextMessageDTO;

import java.util.List;

public
class SetCheck {

    private
    SetCheck () {}

    /**
     * Check message dataManagement list.
     *
     * @param listData the list dataManagement
     *
     * @return the string
     */
    public static
    String checkMessageDataSet (final List<TextMessageDTO> listData) {
        var deadInfo = new StringBuilder();
        String checkString;

        if (listData != null) {

            for (var currentMessage : listData) {
                checkString = BeanChecker.checkBean(currentMessage);

                if (checkString != null) {
                    deadInfo.append("\nFehlerhafte Nachricht: ").append(checkString);
                }
            }
        } else {
            deadInfo.append("Nachrichtenliste nicht vorhanden.");
        }
        return (deadInfo.length() > 0) ? deadInfo.toString() : null;
    }
}
