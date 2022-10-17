package de.vsy.shared_module.shared_module.packet_exception.handler;

import static de.vsy.shared_transmission.shared_transmission.packet.property.packet_category.PacketCategory.ERROR;

import de.vsy.shared_transmission.shared_transmission.packet.Packet;
import de.vsy.shared_transmission.shared_transmission.packet.property.communicator.CommunicationEndpoint;
import de.vsy.shared_transmission.shared_transmission.packet.property.communicator.EligibleCommunicationEntity;
import de.vsy.shared_transmission.shared_transmission.packet.property.packet_identifier.ContentIdentifier;

public class BasicAnswerabilityCheck {

  public static boolean checkPacketAnswerable(Packet toCheck) {
    ContentIdentifier identifier;
    CommunicationEndpoint senderEntity;
    final var answerable = true;
    var properties = toCheck.getPacketProperties();

    if (properties != null) {
      senderEntity = properties.getSender();

      if ((senderEntity != null) && (senderEntity.getEntity()
          .equals(EligibleCommunicationEntity.CLIENT))) {
        identifier = properties.getPacketIdentificationProvider();

        if ((identifier != null) && (identifier.getPacketCategory().equals(ERROR))) {
          return !answerable;
        }
        return answerable;
      }
    }
    return !answerable;
  }
}
