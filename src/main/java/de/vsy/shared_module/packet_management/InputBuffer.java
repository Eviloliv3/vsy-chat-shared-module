package de.vsy.shared_module.packet_management;

import de.vsy.shared_transmission.packet.Packet;

/**
 * Allows for the retrieval of the next Packet from a Packet provider.
 */
public interface InputBuffer {

  /**
   * Returns the next Packet.
   *
   * @return Packet or null if no Packet to be provided.
   */
  Packet getPacket() throws InterruptedException;
}
