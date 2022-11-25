package de.vsy.shared_module.packet_validation.content_validation;

import de.vsy.shared_transmission.packet.content.PacketContent;

public class InvalidContentMessageCreator {

  private static final String STANDARD_INVALID_CONTENT_MESSAGE = "Ungültiges Datenformat: ";

  private InvalidContentMessageCreator() {
  }

  /**
   * @param expectedType  der erwartete Pakettyp
   * @param actualContent der erhaltene Paketinhalt
   * @return Zeichenkette aus Standardmeldung und dem Vergleich von erwartetem Typ und erhaltenem
   * Typ
   * @throws IllegalArgumentException keine erwartete Klasse angegeben
   */
  static String createIllegalTypeMessage(Class<? extends PacketContent> expectedType,
      PacketContent actualContent) {
    var actualContentString = "null";

    if (actualContent != null) {
      actualContentString = actualContent.getClass().getSimpleName();
    }
    return STANDARD_INVALID_CONTENT_MESSAGE + actualContentString + " statt "
        + expectedType.getSimpleName();
  }
}
