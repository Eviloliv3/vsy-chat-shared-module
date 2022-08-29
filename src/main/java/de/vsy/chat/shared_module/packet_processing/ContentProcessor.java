package de.vsy.chat.shared_module.packet_processing;

import de.vsy.chat.shared_module.packet_exception.PacketProcessingException;
import de.vsy.chat.shared_transmission.packet.content.PacketContent;

public
interface ContentProcessor<T extends PacketContent> {

    void processContent (T toProcess)
    throws PacketProcessingException;
}
