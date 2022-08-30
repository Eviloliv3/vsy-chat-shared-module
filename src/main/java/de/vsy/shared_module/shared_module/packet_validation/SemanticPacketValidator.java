package de.vsy.shared_module.shared_module.packet_validation;

import de.vsy.shared_transmission.shared_transmission.packet.content.PacketContent;
import de.vsy.shared_transmission.shared_transmission.packet.property.packet_category.PacketCategory;
import de.vsy.shared_transmission.shared_transmission.packet.property.packet_identifier.ContentIdentifier;
import de.vsy.shared_transmission.shared_transmission.packet.property.packet_type.PacketType;

import java.util.*;

/** The Class SemanticPacketValidator. */
public
class SemanticPacketValidator implements SemanticPacketValidationProvider {

    private final Map<PacketCategory, Map<PacketType, Set<Class<? extends PacketContent>>>> contentTypeValidator;

    /** Instantiates a new Packetidentification validator. */
    public
    SemanticPacketValidator () {
        this.contentTypeValidator = new EnumMap<>(PacketCategory.class);
    }

    /**
     * Adds the identification validation provider.
     *
     * @param category the category
     * @param associationsToAdd the associations to add
     */
    public
    void addCategoryAssociations (PacketCategory category,
                                  Map<PacketType, Set<Class<? extends PacketContent>>> associationsToAdd) {
        if (category == null || associationsToAdd == null) {
            throw new IllegalArgumentException(
                    "Parameter fehlen angegeben: " + "Kategorie (" + category +
                    ") " + "oder Assoziationen (" + associationsToAdd + ")");
        }
        var existingAssociations = this.contentTypeValidator.computeIfAbsent(
                category, cat -> new HashMap<>());

        for (var typeEntry : associationsToAdd.entrySet()) {
            var type = typeEntry.getKey();

            if (type != null) {
                var associatedTypes = existingAssociations.computeIfAbsent(type,
                                                                           t -> new HashSet<>());
                associatedTypes.addAll(typeEntry.getValue());
                existingAssociations.put(type, associatedTypes);
                this.contentTypeValidator.put(category, existingAssociations);
            }
        }
    }

    @Override
    public
    boolean contentMatchesIdentifier (final ContentIdentifier identifier,
                                      final PacketContent content) {
        Map<PacketType, Set<Class<? extends PacketContent>>> categoryToTypeMapping;
        Set<Class<? extends PacketContent>> typeToContentMapping;

        categoryToTypeMapping = this.contentTypeValidator.get(
                identifier.getPacketCategory());

        if (categoryToTypeMapping != null) {
            typeToContentMapping = categoryToTypeMapping.get(
                    identifier.getPacketType());

            if (typeToContentMapping != null) {
                return typeToContentMapping.contains(content.getClass());
            }
        }
        return false;
    }

    @Override
    public
    boolean typeMatchesCategory (final ContentIdentifier identifier) {
        Map<PacketType, Set<Class<? extends PacketContent>>> categoryToTypeMapping;

        categoryToTypeMapping = this.contentTypeValidator.get(
                identifier.getPacketCategory());

        if (categoryToTypeMapping != null) {
            return categoryToTypeMapping.containsKey(identifier.getPacketType());
        }
        return false;
    }
}
