package de.vsy.chat.shared_module.packet_processing.processor_provision;

import de.vsy.chat.shared_module.packet_processing.PacketProcessor;
import de.vsy.chat.shared_transmission.packet.content.PacketContent;

import java.util.HashMap;
import java.util.Map;

public
class ContentBasedPacketProcessorProvider {

    private final Map<Class<? extends PacketContent>, PacketProcessor> processors;
    private final ContentBasedProcessorFactory processorFactory;

    public
    ContentBasedPacketProcessorProvider (
            final ContentBasedProcessorFactory processorFactory) {
        this.processors = new HashMap<>();
        this.processorFactory = processorFactory;
    }

    public
    void registerTypeProcessingProvider (Class<? extends PacketContent> contentType,
                                         PacketProcessor processingProvider) {
        this.processors.put(contentType, processingProvider);
    }

    public
    PacketProcessor getProcessor (Class<? extends PacketContent> contentType) {
        final PacketProcessor processor;
        processor = this.processors.computeIfAbsent(contentType,
                                                    this.processorFactory::createTypeProcessor);
        this.processors.put(contentType, processor);
        return processor;
    }
}
