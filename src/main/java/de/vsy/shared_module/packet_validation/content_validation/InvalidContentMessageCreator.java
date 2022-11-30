package de.vsy.shared_module.packet_validation.content_validation;

import de.vsy.shared_transmission.packet.content.PacketContent;

public class InvalidContentMessageCreator {

  private static final String STANDARD_INVALID_CONTENT_MESSAGE = "Invalid PacketContent type: ";

  private InvalidContentMessageCreator() {
  }

  /**
   * Creates a String message signalling, which PacketContent type was received instead of the
   * expected type.
   * @param expectedType  the expected PacketContent type
   * @param actualContent the received PacketContent type
   * @return String
   * @throws IllegalArgumentException if expectedType argument is not specified.
   */
  static String createIllegalTypeMessage(Class<? extends PacketContent> expectedType,
      PacketContent actualContent) {
    var actualContentString = "null";

    if (actualContent != null) {
      actualContentString = actualContent.getClass().getSimpleName();
    }
    return STANDARD_INVALID_CONTENT_MESSAGE + actualContentString + " instead of "
        + expectedType.getSimpleName();
  }
}
