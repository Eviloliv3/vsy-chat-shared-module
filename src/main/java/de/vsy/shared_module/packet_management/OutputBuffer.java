/*
 *
 */
package de.vsy.shared_module.packet_management;

import de.vsy.shared_transmission.packet.Packet;

/**
 * Allows for the appending or prepending of a unique Packet.
 */
public interface OutputBuffer {

  /**
   * Appends the specified packet, if it is not null and not already present.
   *
   * @param output Packet
   */
  void appendPacket(Packet output);

  /**
   * Prepends the specified Packet, if it is not null and not already present.
   * @param output Packet
   */
  void prependPacket(Packet output);
}
