package de.vsy.shared_module.shared_module.packet_creation;

import de.vsy.shared_transmission.shared_transmission.packet.content.PacketContent;
import de.vsy.shared_transmission.shared_transmission.packet.property.packet_identifier.ContentIdentifier;

public interface ContentIdentificationProvider {

  /**
   * Liefert einen Inhaltsidentifizierer zu den übergebenen Daten.
   *
   * @param data die zu identifizierenden Daten (PacketData)
   * @return der Inhaltsidentifizierer (ContentIdentifier)
   */
  ContentIdentifier getContentIdentifier(PacketContent data);
}
