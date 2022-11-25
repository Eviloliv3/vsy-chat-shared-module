package de.vsy.shared_module.packet_creation;

import de.vsy.shared_transmission.packet.property.communicator.CommunicationEndpoint;

public interface OriginatingEntityProvider {

  CommunicationEndpoint getOriginatorEntity();
}
