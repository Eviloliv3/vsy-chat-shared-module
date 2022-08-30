package de.vsy.shared_module.shared_module.packet_validation.content_validation;

import de.vsy.shared_transmission.shared_transmission.packet.content.PacketContent;

/**
 * Enthaelt exklusiv Paketinhalt oder Fehlernachricht sowie ein Flag, das den
 * jeweiligen Inhalt signalisiert. Bei fehlender Fehlernachricht wird eine
 * Standardnachricht ausgegeben.
 *
 * @param <T> Implementierung von PacketContent
 */
public
class ContentValidationResult<T extends PacketContent> {

    private static final String STANDARD_ERROR_MESSAGE;
    private boolean validationSuccessful;
    private T validatedContent;
    private String validationErrorMessage;

    static {
        STANDARD_ERROR_MESSAGE = "Keine spezifische Fehlernachricht angegeben";
    }

    public
    ContentValidationResult () {
        this.validationSuccessful = false;
        this.validatedContent = null;
        this.validationErrorMessage = STANDARD_ERROR_MESSAGE;
    }

    public
    T getValidatedContent () {
        return this.validatedContent;
    }

    /**
     * Setzt den gepruften Paketinhalt. Inhaltsflag entsprechend gesetzt.
     *
     * @param validatedContent !null - setze(geprueften Inhalt true); null -
     *         setze( (Standard-/)Fehlernachricht & false)
     */
    public
    void setValidatedContent (T validatedContent) {
        this.validationSuccessful = validatedContent != null;
        this.validatedContent = validatedContent;

        if (this.validationSuccessful) {
            this.validationErrorMessage = null;
        } else if (this.validationErrorMessage == null) {
            this.validationErrorMessage = STANDARD_ERROR_MESSAGE;
        }
    }

    public
    String getValidationErrorMessage () {
        return this.validationErrorMessage;
    }

    /**
     * Setzt die Fehlernachricht. Inhaltsflag entsprechend gesetzt. Gesetzter
     * Paketinhalt wird geloescht.
     *
     * @param errorMessage !null - setze(Nachricht & !Paketinhalt & false);
     *         null - Paketinhalt ? setze(Keine Nachricht & true) :
     *         setze(Standardnachricht & false)
     */
    public
    void setValidationErrorMessage (String errorMessage) {
        this.validationSuccessful = errorMessage == null;
        this.validationErrorMessage = errorMessage;

        if (!this.validationSuccessful) {
            this.validatedContent = null;
        } else if (this.validatedContent == null) {
            this.validationSuccessful = false;
            this.validationErrorMessage = STANDARD_ERROR_MESSAGE;
        }
    }

    public
    boolean wasValidationSuccessful () {
        return this.validationSuccessful;
    }
}
