package de.vsy.chat.shared_module.packet_creation;

import de.vsy.chat.shared_transmission.packet.property.communicator.CommunicationEndpoint;

public
interface OriginatingEntityProvider {

    CommunicationEndpoint getOriginatorEntity ();
}
