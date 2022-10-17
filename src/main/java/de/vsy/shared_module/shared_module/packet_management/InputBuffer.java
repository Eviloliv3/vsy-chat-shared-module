/*
 *
 */
package de.vsy.shared_module.shared_module.packet_management;

import de.vsy.shared_transmission.shared_transmission.packet.Packet;

/**
 * Bietet die Moeglichkeit das naechste Paket aus einem Puffer zu holen.
 */
public interface InputBuffer {

  /**
   * Gibt das naechste gepufferte Paket aus.
   *
   * @return das naechste Paket oder null, falls der Puffer leer ist.
   */
  Packet getPacket() throws InterruptedException;
}
