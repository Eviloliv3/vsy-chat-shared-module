/*
 *
 */
package de.vsy.shared_module.packet_validation;

import de.vsy.shared_transmission.packet.Packet;
import de.vsy.shared_transmission.packet.content.PacketContent;
import de.vsy.shared_transmission.packet.property.PacketProperties;
import java.util.Optional;

/**
 * Erlaubt die Ueberpruefung von eines ganzen Pakets oder den einzelnen Eigenschaften bzw. des
 * Inhaltes.
 */
public interface PacketCheck {

  /**
   * Check Packet
   *
   * @param toCheck the to check
   * @return the string
   */
  Optional<String> checkPacket(Packet toCheck);

  /**
   * Check PacketDataManagement.
   *
   * @param toCheck the to check
   * @return the string
   */
  Optional<String> checkPacketContent(PacketContent toCheck);

  /**
   * Check PacketProperties.
   *
   * @param toCheck the to check
   * @return the string
   */
  Optional<String> checkPacketProperties(PacketProperties toCheck);
}