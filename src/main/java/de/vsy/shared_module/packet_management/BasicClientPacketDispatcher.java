/*
 *
 */
package de.vsy.shared_module.packet_management;

import static de.vsy.shared_utility.standard_value.StandardIdProvider.STANDARD_CLIENT_ID;

import de.vsy.shared_transmission.packet.Packet;
import java.util.Deque;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BasicClientPacketDispatcher implements MultiplePacketDispatcher {

  private static final Logger LOGGER = LogManager.getLogger();
  private final PacketBuffer serverBoundBuffer;
  private final PacketBuffer clientBoundBuffer;

  /**
   * Instantiates a new ClientPacketdispatcher.
   * @param clientBoundBuffer the client bound buffer
   * @param serverBoundBuffer the server bound buffer
   */
  public BasicClientPacketDispatcher(final PacketBuffer clientBoundBuffer, final PacketBuffer serverBoundBuffer) {
    this.clientBoundBuffer = clientBoundBuffer;
    this.serverBoundBuffer = serverBoundBuffer;
  }

  @Override
  public void dispatchPacket(final Deque<Packet> output) {

    while (!output.isEmpty()) {
      final Packet toDispatch = output.pop();
      dispatchPacket(toDispatch);
    }
  }

  @Override
  public void dispatchPacket(final Packet toAppend) {
    if (toAppend == null) {
      throw new IllegalArgumentException("Leeres Paket wird nicht gepuffert.");
    }
    final var recipient = toAppend.getPacketProperties().getRecipient();
    final var recipientId = recipient.getEntityId();

    if (this.isClientBound(recipientId)) {
      this.clientBoundBuffer.appendPacket(toAppend);
    } else {
      this.serverBoundBuffer.appendPacket(toAppend);
    }
  }

  /**
   * Packet is client bound if: local client not authenticated; recipient is STANDARD_CLIENT_ID
   * recipientId equals clientId
   *
   * @param recipientId the recipient id
   * @return true if client is recipient, else false
   */
  protected abstract boolean isClientBound(final int recipientId);
}
