package de.vsy.shared_module.packet_creation;

import de.vsy.shared_transmission.packet.property.communicator.CommunicationEndpoint;

public interface OriginatingEntityProvider {

  /**
   * Provides CommunicationEndpoint used as sender entity for Packet creation.
   *
   * @return the CommunicationEndpoint
   */
  CommunicationEndpoint getOriginatorEntity();
}
