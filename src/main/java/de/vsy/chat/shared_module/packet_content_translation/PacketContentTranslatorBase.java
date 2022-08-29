package de.vsy.chat.shared_module.packet_content_translation;

import de.vsy.chat.shared_transmission.packet.content.Translatable;

public
class PacketContentTranslatorBase implements PacketContentTranslator {

    @Override
    public
    String translate (Translatable request) {
        return "Keine gültige Uebersetzung gefunden.";
    }
}
