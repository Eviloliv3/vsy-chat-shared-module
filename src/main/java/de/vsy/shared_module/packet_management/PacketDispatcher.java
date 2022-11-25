/*
 *
 */
package de.vsy.shared_module.packet_management;

import de.vsy.shared_transmission.packet.Packet;

/**
 * Provides a single method, that should send one or multiple packets towards their destination.
 */
public interface PacketDispatcher {

  /**
   * Uniform method to dispatch a single packet.
   *
   * @param output packet to dispatch
   */
  void dispatchPacket(Packet output);
}
