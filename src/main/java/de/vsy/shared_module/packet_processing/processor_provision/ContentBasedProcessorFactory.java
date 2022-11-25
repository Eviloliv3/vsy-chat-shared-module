package de.vsy.shared_module.packet_processing.processor_provision;

import de.vsy.shared_module.packet_processing.PacketProcessor;
import de.vsy.shared_transmission.packet.content.PacketContent;

public interface ContentBasedProcessorFactory {

  PacketProcessor createTypeProcessor(Class<? extends PacketContent> contentType);
}
