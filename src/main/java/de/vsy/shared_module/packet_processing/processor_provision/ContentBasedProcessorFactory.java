package de.vsy.shared_module.packet_processing.processor_provision;

import de.vsy.shared_module.packet_processing.PacketProcessor;
import de.vsy.shared_transmission.packet.content.PacketContent;

public interface ContentBasedProcessorFactory {

  /**
   * Creates a PacketProcessor capable of processing a specified PacketContent type.
   *
   * @param contentType the content type to process
   * @return the PacketProcessor for the specified type, null if none could be created.
   */
  PacketProcessor createTypeProcessor(Class<? extends PacketContent> contentType);
}
