/*
 *
 */
package de.vsy.shared_module.packet_processing;

import de.vsy.shared_module.packet_exception.PacketProcessingException;
import de.vsy.shared_module.packet_exception.PacketValidationException;
import de.vsy.shared_transmission.packet.Packet;

/**
 * The Interface PacketProcessor.
 */
public interface PacketProcessor {

  /**
   * Verarbeitet das übergebene Paket entsprechend der, in den Eigenschaften des Pakets angegebenen,
   * Typ und Kategorie.
   *
   * @param input Paket das verarbeitet wird
   */
  Packet processPacket(Packet input) throws PacketValidationException, PacketProcessingException;
}
