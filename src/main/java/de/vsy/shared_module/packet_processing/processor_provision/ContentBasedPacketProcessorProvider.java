package de.vsy.shared_module.packet_processing.processor_provision;

import de.vsy.shared_module.packet_processing.PacketProcessor;
import de.vsy.shared_transmission.packet.content.PacketContent;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ContentBasedPacketProcessorProvider {

  private final Map<Class<? extends PacketContent>, PacketProcessor> processors;
  private final ContentBasedProcessorFactory processorFactory;

  public ContentBasedPacketProcessorProvider(final ContentBasedProcessorFactory processorFactory) {
    this.processors = new HashMap<>();
    this.processorFactory = processorFactory;
  }

  public void registerTypeProcessingProvider(Class<? extends PacketContent> contentType,
      PacketProcessor processingProvider) {
    this.processors.put(contentType, processingProvider);
  }

  public Optional<PacketProcessor> getProcessor(Class<? extends PacketContent> contentType) {
    final PacketProcessor processor;
    processor = this.processors.computeIfAbsent(contentType,
        this.processorFactory::createTypeProcessor);
    if (processor != null) {
      this.processors.put(contentType, processor);
      return Optional.of(processor);
    }
    return Optional.empty();
  }
}
