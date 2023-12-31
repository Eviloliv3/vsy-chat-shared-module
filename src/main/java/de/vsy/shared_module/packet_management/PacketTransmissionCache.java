package de.vsy.shared_module.packet_management;

import de.vsy.shared_transmission.packet.Packet;
import java.util.Deque;
import java.util.concurrent.LinkedBlockingDeque;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PacketTransmissionCache {

  private static final int MAX_PACKET_BUFFERED = 5;
  private final static Logger LOGGER = LogManager.getLogger();
  private final Deque<Packet> transmissionCache;

  public PacketTransmissionCache() {
    this.transmissionCache = new LinkedBlockingDeque<>(MAX_PACKET_BUFFERED);
  }

  /**
   * Adds the committed packet, if it is not null.
   *
   * @param toTransmit the packet to transmit
   */
  public void addPacket(Packet toTransmit) {
    if (toTransmit != null && !this.transmissionCache.contains(toTransmit)) {
      this.transmissionCache.push(toTransmit);
    } else {
      LOGGER.error("Packet was not added. No null values permitted.");
    }
  }

  /**
   * Discards all cached Packets and adds the notification packet instead.
   *
   * @param error the notification packet to transmit
   */
  public void putError(Packet error) {
    if (error != null) {
      this.transmissionCache.clear();
      this.transmissionCache.push(error);
    } else {
      LOGGER.error("Existing packets will not be removed. Error packet was not added "
          + ". No null values permitted.");
    }
  }

  /**
   * Uses a dispatcher object to transmit all cached packets.
   *
   * @param dispatcher the object used for transmission
   * @throws IllegalArgumentException if dispatcher argument is null
   */
  public void transmitPackets(PacketDispatcher dispatcher) {

    if (!this.transmissionCache.isEmpty()) {
      for (final var currentPacket : this.transmissionCache) {
        dispatcher.dispatchPacket(currentPacket);
      }
      this.transmissionCache.clear();
    } else {
      LOGGER.info("No packets to send: empty cache");
    }
  }
}
