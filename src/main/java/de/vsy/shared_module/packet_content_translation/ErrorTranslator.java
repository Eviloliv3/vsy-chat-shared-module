package de.vsy.shared_module.packet_content_translation;

import de.vsy.shared_transmission.packet.content.Translatable;
import de.vsy.shared_transmission.packet.content.notification.ErrorDTO;

public class ErrorTranslator {

  private ErrorTranslator() {
  }

  public static String translate(final Translatable error) {

    if (error instanceof final ErrorDTO errorData) {
      return errorData.getInformationString();
    }
    return null;
  }
}
