/*
 *
 */
package de.vsy.shared_module.shared_module.packet_validation;

import de.vsy.shared_transmission.shared_transmission.packet.Packet;
import de.vsy.shared_transmission.shared_transmission.packet.content.PacketContent;
import de.vsy.shared_transmission.shared_transmission.packet.property.PacketProperties;
import de.vsy.shared_transmission.shared_transmission.packet.property.communicator.CommunicationEndpoint;
import de.vsy.shared_transmission.shared_transmission.packet.property.communicator.EligibleCommunicationEntity;
import de.vsy.shared_transmission.shared_transmission.packet.property.packet_identifier.ContentIdentifier;

import java.time.Instant;

import static de.vsy.shared_module.shared_module.data_element_validation.IdCheck.checkData;

/** Checks. */
public
class SimplePacketChecker implements PacketCheck {

    final SemanticPacketValidationProvider validityCheckProvider;

    /**
     * Instantiates a new simple PacketChecker.
     *
     * @param provider the provider
     */
    public
    SimplePacketChecker (final SemanticPacketValidationProvider provider) {
        this.validityCheckProvider = provider;
    }

    @Override
    public
    String checkPacket (final Packet toCheck)
    throws NullPointerException {
        String response;
        PacketProperties properties;
        ContentIdentifier identifier;
        PacketContent content;

        properties = toCheck.getPacketProperties();

        response = checkPacketProperties(properties);

        if (response == null) {
            content = toCheck.getPacketContent();

            response = checkPacketContent(content);

            if (response == null) {
                identifier = properties.getContentIdentifier();

                response = checkContentTypeValidity(identifier, content);
            }

            if (response == null) {
                response = checkTimeStamp(toCheck.getPacketCreationTimeStamp());
            }
        }

        return response;
    }

    @Override
    public
    String checkPacketContent (final PacketContent toCheck) {
        String response = null;

        if (toCheck == null) {
            response = "Paketdaten sind nicht vorhanden.";
        }
        return response;
    }

    @Override
    public
    String checkPacketProperties (final PacketProperties toCheck) {
        String response;

        if (toCheck != null) {
            final var identifier = toCheck.getContentIdentifier();

            response = checkIdentifier(identifier);

            if (response == null) {

                final CommunicationEndpoint[] entities = {toCheck.getSenderEntity(),
                                                          toCheck.getRecipientEntity()};

                response = checkEntities(entities);
            }
        } else {
            response = "Keine Paketeigenschaften gefunden.";
        }
        return response;
    }

    /**
     * Checks whether PacketContent is consistent with ContentIdentifierImpl.
     *
     * @param identifier the identifier
     * @param toCheck the to check
     *
     * @return the string
     */
    private
    String checkContentTypeValidity (final ContentIdentifier identifier,
                                     final PacketContent toCheck) {
        StringBuilder response;
        String checkMessage;

        response = new StringBuilder();

        if ((checkMessage = checkIdentifier(identifier)) == null) {

            if (!this.validityCheckProvider.contentMatchesIdentifier(identifier,
                                                                     toCheck)) {
                response.append("Paketinhalt passt nicht zum angegebenen " +
                                "Paketidentifizierer:\n")
                        .append(identifier)
                        .append(toCheck);
            }
        } else {
            response.append(checkMessage);
        }

        return response.length() > 0 ? response.toString() : null;
    }

    /**
     * Check time stamp.
     *
     * @param timeStamp the time stamp
     *
     * @return the string
     */
    private
    String checkTimeStamp (final Instant timeStamp) {
        String response = null;

        if (timeStamp.isAfter(Instant.now())) {
            response = "Erstellungszeitpunkt des Pakets ungültig (nach dieser Prüfung)";
        }
        return response;
    }

    /**
     * Check identifier.
     *
     * @param identifier the identifier
     *
     * @return the string
     */
    private
    String checkIdentifier (final ContentIdentifier identifier) {
        final var response = new StringBuilder();

        if (identifier != null) {

            if (!this.validityCheckProvider.typeMatchesCategory(identifier)) {
                response.append("Ungültige Kombination von Paketkategorie (")
                        .append(identifier.getPacketType())
                        .append(") Pakettyp (")
                        .append(identifier.getPacketCategory())
                        .append(").");
            }
        } else {
            response.append(" Kein Paketidentifizierer vorhanden.");
        }

        return response.length() > 0 ? response.toString() : null;
    }

    /**
     * Check entities.
     *
     * @param entities the entities
     *
     * @return the string
     */
    private
    String checkEntities (final CommunicationEndpoint[] entities) {
        String response = null;

        for (var i = (entities.length - 1); i >= 0; i--) {
            final var entity = entities[i];

            if (entity != null) {
                response = checkEntity(entity);

                if (response != null) {
                    return response;
                }
            } else {
                response = "Ungültiger Kommunikationspartner: null";
                break;
            }
        }
        return response;
    }

    private
    String checkEntity (final CommunicationEndpoint entity) {
        String response = null;
        final var entityId = entity.getEntityId();

        if (entity.getEntity().equals(EligibleCommunicationEntity.CLIENT) &&
            (checkData(entityId) != null)) {
            response = "Ungültige Id verwendet (" + entityId + ").";
        }
        return response;
    }
}
