package de.vsy.shared_module.packet_exception.handler;

import de.vsy.shared_transmission.packet.Packet;

public interface PacketAnswerabilityCheck {

  /**
   * Checks whether a response Packet can be created for the specified Packet.
   *
   * @param toCheck the Packet to check
   * @return true, if answerable; false otherwise.
   */
  boolean checkPacketAnswerable(final Packet toCheck);
}
