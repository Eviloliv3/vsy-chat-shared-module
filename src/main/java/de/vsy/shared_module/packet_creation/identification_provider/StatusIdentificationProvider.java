package de.vsy.shared_module.packet_creation.identification_provider;

import de.vsy.shared_transmission.packet.content.status.ClientStatusChangeDTO;
import de.vsy.shared_transmission.packet.content.status.ContactStatusChangeDTO;
import de.vsy.shared_transmission.packet.content.status.MessengerSetupDTO;
import de.vsy.shared_transmission.packet.content.status.MessengerTearDownDTO;
import de.vsy.shared_transmission.packet.property.packet_identifier.StatusIdentifier;

import static de.vsy.shared_transmission.packet.property.packet_type.StatusType.CHAT_STATUS;

public class StatusIdentificationProvider extends AbstractIdentificationProvider {

    {
        identifiers.put(ClientStatusChangeDTO.class, () -> new StatusIdentifier(CHAT_STATUS));
        identifiers.put(ContactStatusChangeDTO.class, () -> new StatusIdentifier(CHAT_STATUS));
        identifiers.put(MessengerSetupDTO.class, () -> new StatusIdentifier(CHAT_STATUS));
        identifiers.put(MessengerTearDownDTO.class, () -> new StatusIdentifier(CHAT_STATUS));
    }
}
