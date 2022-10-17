/*
 *
 */
package de.vsy.shared_module.shared_module.packet_management;

import de.vsy.shared_transmission.shared_transmission.packet.Packet;

/**
 * Bietet die Moeglichkeiten ein Paket einem Puffer anzuhaengen oder voranzustellen.
 */
public interface OutputBuffer {

  /**
   * Haengt das Paket dem Puffer hinzu, sofern das Paket nicht null oder bereits gepuffert ist.
   *
   * @param output das zu puffernde Paket
   */
  void appendPacket(Packet output);

  /**
   * Stellt das uebergebene Paket dem Puffer voran, sofern das Paket nicht null oder bereits
   * gepuffert ist.
   *
   * @param output das zu puffernde Paket
   */
  void prependPacket(Packet output);
}
