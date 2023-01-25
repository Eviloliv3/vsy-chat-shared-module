package de.vsy.shared_module.packet_management;

import de.vsy.shared_transmission.packet.Packet;
import java.util.Deque;

public interface MultiplePacketDispatcher extends PacketDispatcher {

  /**
   * Uniform method to dispatch multiple resulting packets.
   *
   * @param output queue of packets to dispatch
   */
  void dispatchPacket(Deque<Packet> output);
}
