/*
 *
 */
package de.vsy.shared_module.shared_module.data_element_validation;

import de.vsy.shared_transmission.shared_transmission.dto.CommunicatorDTO;
import de.vsy.shared_transmission.shared_transmission.dto.authentication.AuthenticationDTO;
import de.vsy.shared_transmission.shared_transmission.dto.authentication.PersonalData;
import de.vsy.shared_transmission.shared_transmission.packet.content.chat.TextMessageDTO;

import static de.vsy.shared_module.shared_module.data_element_validation.IdCheck.checkData;

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
            checkString = StringCheck.checkString(data.getDisplayLabel());

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
            checkString = StringCheck.checkString(authenticationData.getLogin());

            if (checkString != null) {
                deadInfo.append("Fehlerhafter Loginname: ").append(checkString);
            }
            checkString = StringCheck.checkString(authenticationData.getPassword());

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
            checkString = StringCheck.checkString(personalData.getForename());
            if (checkString != null) {
                deadInfo.append("Fehlerhafter Vorname: ").append(checkString);
            }

            checkString = StringCheck.checkString(personalData.getSurname());

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
