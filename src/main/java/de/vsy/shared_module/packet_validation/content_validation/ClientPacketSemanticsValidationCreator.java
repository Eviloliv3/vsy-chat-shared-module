package de.vsy.shared_module.packet_validation.content_validation;

import de.vsy.shared_module.packet_validation.SemanticPacketValidator;
import de.vsy.shared_module.packet_validation.PermittedCategoryContentAssociationProvider;
import de.vsy.shared_transmission.packet.property.packet_category.PacketCategory;

public class ClientPacketSemanticsValidationCreator {

  private ClientPacketSemanticsValidationCreator() {
  }

  public static SemanticPacketValidator createSemanticValidator() {
    var packetValidator = new SemanticPacketValidator();

    for (final var category : PacketCategory.values()){
      packetValidator.addCategoryAssociations(category,
          PermittedCategoryContentAssociationProvider.getAssociations(category));
  }
    return packetValidator;
  }
}
