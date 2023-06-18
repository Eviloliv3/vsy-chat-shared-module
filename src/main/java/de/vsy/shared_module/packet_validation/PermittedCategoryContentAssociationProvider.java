package de.vsy.shared_module.packet_validation;


import static java.util.Set.of;

import de.vsy.shared_transmission.packet.content.PacketContent;
import de.vsy.shared_transmission.packet.content.authentication.AccountCreationRequestDTO;
import de.vsy.shared_transmission.packet.content.authentication.AccountDeletionRequestDTO;
import de.vsy.shared_transmission.packet.content.authentication.AccountDeletionResponseDTO;
import de.vsy.shared_transmission.packet.content.authentication.LoginRequestDTO;
import de.vsy.shared_transmission.packet.content.authentication.LoginResponseDTO;
import de.vsy.shared_transmission.packet.content.authentication.LogoutRequestDTO;
import de.vsy.shared_transmission.packet.content.authentication.LogoutResponseDTO;
import de.vsy.shared_transmission.packet.content.authentication.ReconnectRequestDTO;
import de.vsy.shared_transmission.packet.content.authentication.ReconnectResponseDTO;
import de.vsy.shared_transmission.packet.content.chat.ChatPacketDTO;
import de.vsy.shared_transmission.packet.content.chat.TextMessageDTO;
import de.vsy.shared_transmission.packet.content.notification.ErrorDTO;
import de.vsy.shared_transmission.packet.content.notification.SimpleInformationDTO;
import de.vsy.shared_transmission.packet.content.relation.ContactRelationRequestDTO;
import de.vsy.shared_transmission.packet.content.relation.ContactRelationResponseDTO;
import de.vsy.shared_transmission.packet.content.status.ClientStatusChangeDTO;
import de.vsy.shared_transmission.packet.content.status.ContactStatusChangeDTO;
import de.vsy.shared_transmission.packet.content.status.MessengerSetupDTO;
import de.vsy.shared_transmission.packet.content.status.MessengerTearDownDTO;
import de.vsy.shared_transmission.packet.property.packet_category.PacketCategory;
import de.vsy.shared_transmission.packet.property.packet_type.AuthenticationType;
import de.vsy.shared_transmission.packet.property.packet_type.ChatType;
import de.vsy.shared_transmission.packet.property.packet_type.ErrorType;
import de.vsy.shared_transmission.packet.property.packet_type.PacketType;
import de.vsy.shared_transmission.packet.property.packet_type.RelationType;
import de.vsy.shared_transmission.packet.property.packet_type.StatusType;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PermittedCategoryContentAssociationProvider {

  protected static final Map<PacketCategory, Map<PacketType, Set<Class<? extends PacketContent>>>> ASSOCIATIONS;

  static {
    ASSOCIATIONS = new EnumMap<>(PacketCategory.class);
  }

  private PermittedCategoryContentAssociationProvider() {
  }

  public static Map<PacketCategory, Map<PacketType, Set<Class<? extends PacketContent>>>> getAssociations() {
    Map<PacketCategory, Map<PacketType, Set<Class<? extends PacketContent>>>> fullAssociationMap = new EnumMap<>(
        PacketCategory.class);

    for (final var currentCategory : PacketCategory.values()) {
      fullAssociationMap.put(currentCategory, getAssociations(currentCategory));
    }
    return fullAssociationMap;
  }

  public static Map<PacketType, Set<Class<? extends PacketContent>>> getAssociations(
      final PacketCategory category) {
    var cachedAssociations = ASSOCIATIONS.get(category);

    if (cachedAssociations == null && category != null) {
      cachedAssociations = switch (category) {
        case AUTHENTICATION -> setupAuthenticationValidation();
        case STATUS -> setupStatusValidation();
        case RELATION -> setupRelationValidation();
        case NOTIFICATION -> setupErrorValidation();
        case CHAT -> setupChatValidation();
      };
      ASSOCIATIONS.put(category, cachedAssociations);
    }
    return cachedAssociations;
  }

  private static Map<PacketType, Set<Class<? extends PacketContent>>> setupErrorValidation() {
    Map<PacketType, Set<Class<? extends PacketContent>>> errorMapping = new HashMap<>();
    errorMapping.put(ErrorType.SIMPLE_ERROR, of(SimpleInformationDTO.class, ErrorDTO.class));
    return errorMapping;
  }

  private static Map<PacketType, Set<Class<? extends PacketContent>>> setupRelationValidation() {
    Map<PacketType, Set<Class<? extends PacketContent>>> relationMapping = new HashMap<>();
    relationMapping.put(RelationType.CONTACT_RELATION, of(ContactRelationRequestDTO.class,
        ContactRelationResponseDTO.class));
    return relationMapping;
  }

  private static Map<PacketType, Set<Class<? extends PacketContent>>> setupStatusValidation() {
    Map<PacketType, Set<Class<? extends PacketContent>>> statusMapping = new HashMap<>();
    statusMapping.put(StatusType.CHAT_STATUS,
        of(MessengerSetupDTO.class, MessengerTearDownDTO.class, ContactStatusChangeDTO.class,
            ClientStatusChangeDTO.class));
    return statusMapping;
  }

  private static Map<PacketType, Set<Class<? extends PacketContent>>> setupAuthenticationValidation() {
    Map<PacketType, Set<Class<? extends PacketContent>>> authenticationMapping = new HashMap<>();

    authenticationMapping.put(AuthenticationType.CLIENT_LOGIN,
        of(LoginRequestDTO.class, LoginResponseDTO.class));
    authenticationMapping.put(AuthenticationType.CLIENT_LOGOUT, of(LogoutRequestDTO.class,
        LogoutResponseDTO.class));
    authenticationMapping.put(AuthenticationType.CLIENT_ACCOUNT_CREATION,
        of(AccountCreationRequestDTO.class));
    authenticationMapping.put(AuthenticationType.CLIENT_RECONNECT,
        of(ReconnectRequestDTO.class, ReconnectResponseDTO.class));
    authenticationMapping.put(AuthenticationType.CLIENT_ACCOUNT_DELETION,
        of(AccountDeletionRequestDTO.class, AccountDeletionResponseDTO.class));

    return authenticationMapping;
  }

  private static Map<PacketType, Set<Class<? extends PacketContent>>> setupChatValidation() {
    Map<PacketType, Set<Class<? extends PacketContent>>> chatMapping = new HashMap<>();
    chatMapping.put(ChatType.TEXT_MESSAGE, of(ChatPacketDTO.class, TextMessageDTO.class));
    chatMapping.put(ChatType.GROUP_MESSAGE, of(ChatPacketDTO.class, TextMessageDTO.class));
    chatMapping.put(ChatType.GROUP_REGISTRATION, of());
    return chatMapping;
  }
}
