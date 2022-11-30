/*
 *
 */
package de.vsy.shared_module.packet_validation;

import de.vsy.shared_transmission.packet.Packet;
import de.vsy.shared_transmission.packet.content.PacketContent;
import de.vsy.shared_transmission.packet.property.PacketProperties;
import java.util.Optional;

/**
 * Provides checks for a Packet or Packet components like the content or the properties
 */
public interface PacketCheck {

  /**
   * Checks the whole Packet, using the other specified methods.
   *
   * @param toCheck Packet
   * @return Optional<String> if error found; empty Optional otherwise
   */
  Optional<String> checkPacket(Packet toCheck);

  /**
   * Checks PacketContent.
   *
   * @param toCheck PacketContent
   * @return Optional<String> if error found; empty Optional otherwise
   */
  Optional<String> checkPacketContent(PacketContent toCheck);

  /**
   * Checks PacketProperties.
   *
   * @param toCheck PacketProperties
   * @return Optional<String> if error found; empty Optional otherwise
   */
  Optional<String> checkPacketProperties(PacketProperties toCheck);
}
