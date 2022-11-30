package de.vsy.shared_module.packet_creation;

import de.vsy.shared_transmission.packet.content.PacketContent;
import de.vsy.shared_transmission.packet.property.packet_identifier.ContentIdentifier;

public interface ContentIdentificationProvider {

  /**
   * Returns a Packet ContentIdentifier for the specified PacketContent.
   *
   * @param data PacketContent to be identified
   * @return ContentIdentifier
   */
  ContentIdentifier getContentIdentifier(PacketContent data);
}
