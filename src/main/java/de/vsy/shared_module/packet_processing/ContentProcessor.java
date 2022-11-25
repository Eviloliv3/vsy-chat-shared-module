package de.vsy.shared_module.packet_processing;

import de.vsy.shared_module.packet_exception.PacketProcessingException;
import de.vsy.shared_transmission.packet.content.PacketContent;

public interface ContentProcessor<T extends PacketContent> {

  PacketContent processContent(T toProcess) throws PacketProcessingException;
}
