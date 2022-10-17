package de.vsy.shared_module.shared_module.packet_exception.handler;

import de.vsy.shared_transmission.shared_transmission.packet.Packet;

public interface PacketAnswerabilityCheck {

  /**
   * Prüft ob das Paket beantwortet werden kann.
   *
   * @param toCheck Paket, dessen Beantwortbarkeit geprueft werden soll.
   * @return True, falls das Paket beantwortet werden kann.
   */
  boolean checkPacketAnswerable(final Packet toCheck);
}
