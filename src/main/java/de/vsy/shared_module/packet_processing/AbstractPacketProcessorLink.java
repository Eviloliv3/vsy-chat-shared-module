package de.vsy.shared_module.packet_processing;

public abstract class AbstractPacketProcessorLink implements PacketProcessor {

  protected final PacketProcessor nextLink;

  protected AbstractPacketProcessorLink(PacketProcessor nextLink) {
    this.nextLink = nextLink;
  }
}
