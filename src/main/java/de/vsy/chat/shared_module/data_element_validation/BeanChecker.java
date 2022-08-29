/*
 *
 */
package de.vsy.chat.shared_module.data_element_validation;

import de.vsy.chat.shared_transmission.dto.CommunicatorDTO;
import de.vsy.chat.shared_transmission.dto.authentication.AuthenticationDTO;
import de.vsy.chat.shared_transmission.dto.authentication.PersonalData;
import de.vsy.chat.shared_transmission.packet.content.chat.TextMessageDTO;

import static de.vsy.chat.shared_module.data_element_validation.IdCheck.checkData;
import static de.vsy.chat.shared_module.data_element_validation.StringCheck.checkString;

/** Simple tool: dataManagement bean checker. */
public
class BeanChecker {

    private
    BeanChecker () {}

    /**
     * Check bean.
     *
     * @param data the dataManagement
     *
     * @return the string
     */
    public static
    String checkBean (final CommunicatorDTO data) {
        String checkString;
        var deadInfo = new StringBuilder();

        if (data != null) {
            checkString = checkData(data.getCommunicatorId());

            if (checkString != null) {
                deadInfo.append(checkString);
            }
            checkString = checkString(data.getDisplayLabel());

            if (checkString != null) {
                deadInfo.append(" Ungültiger Anzeigename: ").append(checkString);
            }
        } else {
            deadInfo.append("Es sind keine Kommunikatordaten vorhanden.");
        }
        return (deadInfo.length() > 0) ?
                "Fehlerhafte Kommunikatordaten: " + deadInfo : null;
    }

    public static
    String checkBean (AuthenticationDTO authenticationData) {
        String checkString;
        var deadInfo = new StringBuilder();

        if (authenticationData != null) {
            checkString = checkString(authenticationData.getLogin());

            if (checkString != null) {
                deadInfo.append("Fehlerhafter Loginname: ").append(checkString);
            }
            checkString = checkString(authenticationData.getPassword());

            if (checkString != null) {
                deadInfo.append("Fehlerhaftes Passwort: ").append(checkString);
            }
        } else {
            deadInfo.append("Keine Authentifizierungsdaten enthalten.");
        }
        return (deadInfo.length() > 0) ?
                "Fehlerhafte Klientendaten:" + deadInfo : null;
    }

    public static
    String checkBean (PersonalData personalData) {
        String checkString;
        var deadInfo = new StringBuilder();

        if (personalData != null) {
            checkString = checkString(personalData.getForename());
            if (checkString != null) {
                deadInfo.append("Fehlerhafter Vorname: ").append(checkString);
            }

            checkString = checkString(personalData.getSurname());

            if (checkString != null) {
                deadInfo.append("Fehlerhafter Nachname: ").append(checkString);
            }
        } else {
            deadInfo.append("Keine Klientendaten enthalten.");
        }
        return (deadInfo.length() > 0) ? deadInfo.toString() : null;
    }

    public static
    String checkBean (final TextMessageDTO data) {
        throw new UnsupportedOperationException(
                "TextMessageDTO wird noch nicht geprueft.");
    }

    /**
     * Check bean.
     *
     * @param data the dataManagement
     *
     * @return the string
     */
    public static
    String checkBean (final Object data) {
        return "Unbekanntes Datenobjekt";
    }
}
