package de.vsy.shared_module.shared_module.packet_content_translation;

import de.vsy.shared_transmission.shared_transmission.packet.content.Translatable;

public class PacketContentTranslatorBase implements PacketContentTranslator {

  @Override
  public String translate(Translatable request) {
    return "Keine gültige Uebersetzung gefunden.";
  }
}
