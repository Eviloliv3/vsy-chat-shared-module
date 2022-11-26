/*
 *
 */
package de.vsy.shared_module.packet_validation;

import static de.vsy.shared_module.data_element_validation.IdCheck.checkData;

import de.vsy.shared_transmission.packet.Packet;
import de.vsy.shared_transmission.packet.content.PacketContent;
import de.vsy.shared_transmission.packet.property.PacketProperties;
import de.vsy.shared_transmission.packet.property.communicator.CommunicationEndpoint;
import de.vsy.shared_transmission.packet.property.communicator.EligibleCommunicationEntity;
import de.vsy.shared_transmission.packet.property.packet_identifier.ContentIdentifier;
import java.time.Instant;
import java.util.Optional;

/**
 * Checks.
 */
public class SimplePacketChecker implements PacketCheck {

  final SemanticPacketValidationProvider validityCheckProvider;

  /**
   * Instantiates a new simple PacketChecker.
   *
   * @param provider the provider
   */
  public SimplePacketChecker(final SemanticPacketValidationProvider provider) {
    this.validityCheckProvider = provider;
  }

  @Override
  public Optional<String> checkPacket(final Packet toCheck) throws NullPointerException {
    Optional<String> response;
    PacketProperties properties;
    ContentIdentifier identifier;
    PacketContent content;

    properties = toCheck.getPacketProperties();

    response = checkPacketProperties(properties);

    if (response.isEmpty()) {
      content = toCheck.getPacketContent();

      response = checkPacketContent(content);

      if (response.isEmpty()) {
        identifier = properties.getPacketIdentificationProvider();

        response = checkContentTypeValidity(identifier, content);
      }

      if (response.isEmpty()) {
        response = checkTimeStamp(toCheck.getPacketCreationTimestamp());
      }
    }
    return response;
  }

  @Override
  public Optional<String> checkPacketContent(final PacketContent toCheck) {

    if (toCheck == null) {
      return Optional.of("Paketdaten sind nicht vorhanden.");
    } else {
      // TODO hier sollte Semantik von Paketinhalten geprüft werden
      // Beispiel: Nachricht vom Klienten darf nicht als "Empfangen"
      // gekennzeichnet sein
      return Optional.empty();
    }
  }

  @Override
  public Optional<String> checkPacketProperties(final PacketProperties toCheck) {
    Optional<String> response;

    if (toCheck != null) {
      final var identifier = toCheck.getPacketIdentificationProvider();

      response = checkIdentifier(identifier);

      if (response.isEmpty()) {

        final CommunicationEndpoint[] entities = {toCheck.getSender(), toCheck.getRecipient()};

        response = checkEntities(entities);
      }
    } else {
      response = Optional.of("Keine Paketeigenschaften gefunden.");
    }
    return response;
  }

  /**
   * Checks whether PacketContent is consistent with ContentIdentifierImpl.
   *
   * @param identifier the identifier
   * @param toCheck    the to check
   * @return the string
   */
  private Optional<String> checkContentTypeValidity(final ContentIdentifier identifier,
      final PacketContent toCheck) {
    Optional<String> checkMessage;
    final var response = new StringBuilder();

    checkMessage = checkIdentifier(identifier);

    if (checkMessage.isEmpty()) {

      if (!this.validityCheckProvider.contentMatchesIdentifier(identifier, toCheck)) {
        return Optional
            .of(response.append(
                    "Paketinhalt passt nicht zum angegebenen " + "Paketidentifizierer:\n")
                .append(identifier).append(toCheck).toString());
      }
    } else {
      return checkMessage;
    }
    return Optional.empty();
  }

  /**
   * Check time stamp.
   *
   * @param timeStamp the time stamp
   * @return the string
   */
  private Optional<String> checkTimeStamp(final Instant timeStamp) {

    if (timeStamp.isAfter(Instant.now())) {
      return Optional.of("Erstellungszeitpunkt des Pakets ungültig (nach dieser Prüfung)");
    }
    return Optional.empty();
  }

  /**
   * Check identifier.
   *
   * @param identifier the identifier
   * @return the string
   */
  private Optional<String> checkIdentifier(final ContentIdentifier identifier) {
    final var response = new StringBuilder();

    if (identifier != null) {

      if (!this.validityCheckProvider.typeMatchesCategory(identifier)) {
        return Optional.of(
            response.append("Ungültige Kombination von Paketkategorie (")
                .append(identifier.getPacketType())
                .append(") Pakettyp (").append(identifier.getPacketCategory()).append(").")
                .toString());
      }
    } else {
      return Optional.of(response.append(" Kein Paketidentifizierer vorhanden.").toString());
    }
    return Optional.empty();
  }

  /**
   * Check entities.
   *
   * @param entities the entities
   * @return the string
   */
  private Optional<String> checkEntities(final CommunicationEndpoint[] entities) {
    Optional<String> response;

    for (var i = (entities.length - 1); i >= 0; i--) {
      final var entity = entities[i];

      if (entity != null) {
        response = checkEntity(entity);

        if (response.isPresent()) {
          return response;
        }
      } else {
        return Optional.of("Ungültiger Kommunikationspartner: null");
      }
    }
    return Optional.empty();
  }

  private Optional<String> checkEntity(final CommunicationEndpoint entity) {
    final var entityId = entity.getEntityId();

    if (entity.getEntity().equals(EligibleCommunicationEntity.CLIENT) && checkData(
        entityId).isPresent()) {
      return Optional.of("Ungültige Id verwendet (" + entityId + ").");
    }
    return Optional.empty();
  }
}