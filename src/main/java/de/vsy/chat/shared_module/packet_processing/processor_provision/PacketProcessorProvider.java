package de.vsy.chat.shared_module.packet_processing.processor_provision;

import de.vsy.chat.shared_module.packet_processing.PacketProcessor;
import de.vsy.chat.shared_transmission.packet.content.PacketContent;
import de.vsy.chat.shared_transmission.packet.property.packet_category.PacketCategory;

import java.util.EnumMap;
import java.util.Map;

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
    PacketProcessor getProcessor (PacketCategory category,
                                  Class<? extends PacketContent> contentType) {
        PacketProcessor contentProcessor = null;
        var categoryProcessing = this.typeProcessingProvider.get(category);

        if (categoryProcessing != null) {
            contentProcessor = categoryProcessing.getProcessor(contentType);
        }
        return contentProcessor;
    }
}
