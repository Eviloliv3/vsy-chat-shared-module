package de.vsy.shared_module.shared_module.packet_processing.processor_provision;

import de.vsy.shared_module.shared_module.packet_processing.PacketProcessor;
import de.vsy.shared_transmission.shared_transmission.packet.content.PacketContent;
import de.vsy.shared_transmission.shared_transmission.packet.property.packet_category.PacketCategory;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public
class PacketProcessorProvider {

    private final Map<PacketCategory, ContentBasedPacketProcessorProvider> typeProcessingProvider;

    public
    PacketProcessorProvider () {
        this.typeProcessingProvider = new EnumMap<>(PacketCategory.class);
    }

    public
    void registerTypeProcessingProvider (PacketCategory category,
                                         ContentBasedPacketProcessorProvider processingProvider) {
        this.typeProcessingProvider.put(category, processingProvider);
    }

    public
    Optional<PacketProcessor> getProcessor (PacketCategory category,
                                            Class<? extends PacketContent> contentType) {
        var categoryProcessing = this.typeProcessingProvider.get(category);

        if (categoryProcessing != null) {
            return categoryProcessing.getProcessor(contentType);
        }
        return Optional.empty();
    }
}
