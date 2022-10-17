package de.vsy.shared_module.shared_module.packet_creation.identification_provider;

import static de.vsy.shared_transmission.shared_transmission.packet.property.packet_type.RelationType.CONTACT_RELATION;

import de.vsy.shared_transmission.shared_transmission.packet.content.relation.ContactRelationRequestDTO;
import de.vsy.shared_transmission.shared_transmission.packet.content.relation.ContactRelationResponseDTO;
import de.vsy.shared_transmission.shared_transmission.packet.property.packet_identifier.RelationIdentifier;

public class RelationIdentificationProvider extends AbstractIdentificationProvider {

  {
    identifiers.put(ContactRelationRequestDTO.class,
        () -> new RelationIdentifier(CONTACT_RELATION));
    identifiers.put(ContactRelationResponseDTO.class,
        () -> new RelationIdentifier(CONTACT_RELATION));
  }
}
