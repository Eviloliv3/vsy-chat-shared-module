package de.vsy.shared_module.packet_processing;

import de.vsy.shared_module.packet_exception.PacketProcessingException;
import de.vsy.shared_transmission.packet.content.PacketContent;

public interface ContentProcessor<T extends PacketContent> {

  /**
   * Processes a specific type of PacketContent.
   *
   * @param toProcess the PacketContent to process.
   * @throws PacketProcessingException if the PacketContent unexpectedly cannot be processed.
   */
  void processContent(T toProcess) throws PacketProcessingException;
}
