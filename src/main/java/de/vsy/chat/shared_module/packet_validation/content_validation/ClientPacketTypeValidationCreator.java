package de.vsy.chat.shared_module.packet_validation.content_validation;

import de.vsy.chat.shared_module.packet_validation.SemanticPacketValidator;
import de.vsy.chat.shared_transmission.packet.content.PacketContent;
import de.vsy.chat.shared_transmission.packet.content.authentication.*;
import de.vsy.chat.shared_transmission.packet.content.chat.TextMessageDTO;
import de.vsy.chat.shared_transmission.packet.content.error.ErrorDTO;
import de.vsy.chat.shared_transmission.packet.content.relation.ContactRelationRequestDTO;
import de.vsy.chat.shared_transmission.packet.content.relation.ContactRelationResponseDTO;
import de.vsy.chat.shared_transmission.packet.content.status.ClientStatusChangeDTO;
import de.vsy.chat.shared_transmission.packet.content.status.ContactMessengerStatusDTO;
import de.vsy.chat.shared_transmission.packet.content.status.MessengerSetupDTO;
import de.vsy.chat.shared_transmission.packet.content.status.MessengerTearDownDTO;
import de.vsy.chat.shared_transmission.packet.property.packet_category.PacketCategory;
import de.vsy.chat.shared_transmission.packet.property.packet_type.AuthenticationType;
import de.vsy.chat.shared_transmission.packet.property.packet_type.PacketType;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static de.vsy.chat.shared_transmission.packet.property.packet_category.PacketCategory.*;
import static de.vsy.chat.shared_transmission.packet.property.packet_type.ChatType.TEXT_MESSAGE;
import static de.vsy.chat.shared_transmission.packet.property.packet_type.ErrorType.SIMPLE_ERROR;
import static de.vsy.chat.shared_transmission.packet.property.packet_type.RelationType.CONTACT_RELATION;
import static de.vsy.chat.shared_transmission.packet.property.packet_type.StatusType.CHAT_STATUS;
import static java.util.Set.of;

public
class ClientPacketTypeValidationCreator {

    private
    ClientPacketTypeValidationCreator () {}

    /**
     * Setup pending client Packetcontent validator.
     *
     * @return the client Packetcontent type validator
     */
    public static
    SemanticPacketValidator setupPendingPacketContentValidator () {
        var packetValidator = new SemanticPacketValidator();

        packetValidator.addCategoryAssociations(STATUS, setupStatusValidation());
        packetValidator.addCategoryAssociations(RELATION, setupRelationValidation());
        packetValidator.addCategoryAssociations(CHAT, setupChatValidation());
        packetValidator.addCategoryAssociations(ERROR, setupErrorValidation());

        return packetValidator;
    }

    /**
     * Setup status validation.
     *
     * @return the hash map< PacketType, Set< class<? extends Packetcontent>>>
     */
    public static
    Map<PacketType, Set<Class<? extends PacketContent>>> setupStatusValidation () {
        Map<PacketType, Set<Class<? extends PacketContent>>> statusMapping = new HashMap<>();

        statusMapping.put(CHAT_STATUS, of(ContactMessengerStatusDTO.class,
                                          ClientStatusChangeDTO.class,
                                          MessengerSetupDTO.class,
                                          MessengerTearDownDTO.class));

        return statusMapping;
    }

    /**
     * Setup relation validation.
     *
     * @return the hash map< PacketType, Set< class<? extends Packetcontent>>>
     */
    public static
    Map<PacketType, Set<Class<? extends PacketContent>>> setupRelationValidation () {
        Map<PacketType, Set<Class<? extends PacketContent>>> relationMapping = new HashMap<>();

        relationMapping.put(CONTACT_RELATION, of(ContactRelationRequestDTO.class,
                                                 ContactRelationResponseDTO.class));

        return relationMapping;
    }

    /**
     * Setup chat validation.
     *
     * @return the hash map< PacketType, Set< class<? extends Packetcontent>>>
     */
    public static
    Map<PacketType, Set<Class<? extends PacketContent>>> setupChatValidation () {
        Map<PacketType, Set<Class<? extends PacketContent>>> chatMapping = new HashMap<>();

        chatMapping.put(TEXT_MESSAGE, of(TextMessageDTO.class));

        return chatMapping;
    }

    /**
     * Setup error validation.
     *
     * @return the hash map< PacketType, Set< class<? extends Packetcontent>>>
     */
    public static
    Map<PacketType, Set<Class<? extends PacketContent>>> setupErrorValidation () {
        Map<PacketType, Set<Class<? extends PacketContent>>> errorMapping = new HashMap<>();

        errorMapping.put(SIMPLE_ERROR, of(ErrorDTO.class));

        return errorMapping;
    }

    public static
    SemanticPacketValidator createRegularPacketContentValidator () {
        var packetValidator = new SemanticPacketValidator();

        packetValidator.addCategoryAssociations(AUTHENTICATION,
                                                setupAuthenticationValidation());
        packetValidator.addCategoryAssociations(STATUS, setupStatusValidation());
        packetValidator.addCategoryAssociations(RELATION, setupRelationValidation());
        packetValidator.addCategoryAssociations(CHAT, setupChatValidation());
        packetValidator.addCategoryAssociations(ERROR, setupErrorValidation());

        return packetValidator;
    }

    /**
     * Setup authentication validation.
     *
     * @return the hash map< PacketType, Set< class<? extends Packetcontent>>>
     */
    public static
    Map<PacketType, Set<Class<? extends PacketContent>>> setupAuthenticationValidation () {
        Map<PacketType, Set<Class<? extends PacketContent>>> authMapping = new HashMap<>();

        authMapping.put(AuthenticationType.CLIENT_LOGIN,
                        of(LoginRequestDTO.class, LoginResponseDTO.class));

        authMapping.put(AuthenticationType.CLIENT_LOGOUT,
                        of(LogoutRequestDTO.class, LogoutResponseDTO.class));

        authMapping.put(AuthenticationType.CLIENT_NEW_ACCOUNT,
                        of(NewAccountRequestDTO.class, LoginResponseDTO.class));

        authMapping.put(AuthenticationType.CLIENT_RECONNECT,
                        of(ReconnectRequestDTO.class, ReconnectResponseDTO.class));

        return authMapping;
    }

    /**
     * Setup regular client Packetcontent validator.
     *
     * @return the client Packetcontent type validator
     */
    public static
    Map<PacketCategory, Map<PacketType, Set<Class<? extends PacketContent>>>> setupRegularPacketContentValidation () {
        Map<PacketCategory, Map<PacketType, Set<Class<? extends PacketContent>>>> packetValidator = new EnumMap<>(
                PacketCategory.class);

        packetValidator.put(AUTHENTICATION, setupAuthenticationValidation());
        packetValidator.put(STATUS, setupStatusValidation());
        packetValidator.put(RELATION, setupRelationValidation());
        packetValidator.put(CHAT, setupChatValidation());
        packetValidator.put(ERROR, setupErrorValidation());

        return packetValidator;
    }
}
