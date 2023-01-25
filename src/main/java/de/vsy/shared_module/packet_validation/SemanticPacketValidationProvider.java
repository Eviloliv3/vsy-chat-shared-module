package de.vsy.shared_module.packet_validation;

import de.vsy.shared_transmission.packet.content.PacketContent;
import de.vsy.shared_transmission.packet.property.packet_identifier.ContentIdentifier;

/**
 * The Interface SemanticPacketValidationProvider.
 */
public interface SemanticPacketValidationProvider {

  /**
   * Returns a boolean indicating, if the specified ContentIdentifier matches the specified
   * PacketContent's type.
   *
   * @param identifier the ContentIdentifier
   * @param content    the PacketContent
   * @return true, if ContentIdentifier matches PacketContent, false otherwise
   */
  boolean contentMatchesIdentifier(ContentIdentifier identifier, PacketContent content);

  /**
   * Returns a boolean indicating whether the specified ContentIdentifier is valid.
   *
   * @param identifier the ContentIdentifier
   * @return true, if ContentIdentifier is consistent, false otherwise
   */
  boolean typeMatchesCategory(ContentIdentifier identifier);
}
