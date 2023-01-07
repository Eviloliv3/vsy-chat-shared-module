package de.vsy.shared_module.packet_content_translation;

import de.vsy.shared_transmission.packet.content.Translatable;

public interface PacketContentTranslator {

    String translate(Translatable request);
}
