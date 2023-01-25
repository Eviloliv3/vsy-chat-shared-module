package de.vsy.shared_module.packet_processing;

import de.vsy.shared_module.packet_exception.PacketProcessingException;
import de.vsy.shared_module.packet_exception.PacketValidationException;
import de.vsy.shared_transmission.packet.Packet;

/**
 * The Interface PacketProcessor.
 */
public interface PacketProcessor {

  /**
   * Processes the specified Packet
   *
   * @param input Packet
   * @throws PacketValidationException if packet syntax or semantics are erroneous
   * @throws PacketProcessingException if any local state prevents the processing of the Packet
   */
  void processPacket(Packet input) throws PacketValidationException, PacketProcessingException;
}
