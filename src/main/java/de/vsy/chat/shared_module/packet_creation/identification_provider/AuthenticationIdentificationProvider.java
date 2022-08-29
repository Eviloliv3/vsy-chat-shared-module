package de.vsy.chat.shared_module.packet_creation.identification_provider;

import de.vsy.chat.shared_transmission.packet.content.authentication.*;
import de.vsy.chat.shared_transmission.packet.property.packet_identifier.AuthenticationIdentifier;
import de.vsy.chat.shared_transmission.packet.property.packet_type.AuthenticationType;

public
class AuthenticationIdentificationProvider extends AbstractIdentificationProvider {

    {
        identifiers.put(LoginRequestDTO.class, () -> new AuthenticationIdentifier(
                AuthenticationType.CLIENT_LOGIN));
        identifiers.put(LoginResponseDTO.class, () -> new AuthenticationIdentifier(
                AuthenticationType.CLIENT_LOGIN));
        identifiers.put(LogoutRequestDTO.class, () -> new AuthenticationIdentifier(
                AuthenticationType.CLIENT_LOGOUT));
        identifiers.put(LogoutResponseDTO.class, () -> new AuthenticationIdentifier(
                AuthenticationType.CLIENT_LOGOUT));
        identifiers.put(ReconnectRequestDTO.class,
                        () -> new AuthenticationIdentifier(
                                AuthenticationType.CLIENT_RECONNECT));
        identifiers.put(ReconnectResponseDTO.class,
                        () -> new AuthenticationIdentifier(
                                AuthenticationType.CLIENT_RECONNECT));
        identifiers.put(NewAccountRequestDTO.class,
                        () -> new AuthenticationIdentifier(
                                AuthenticationType.CLIENT_NEW_ACCOUNT));
    }
}
