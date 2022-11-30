package de.vsy.shared_module.packet_transmission;

import de.vsy.shared_module.packet_management.ThreadPacketBufferLabel;
import de.vsy.shared_module.packet_management.ThreadPacketBufferManager;
import de.vsy.shared_module.packet_transmission.cache.UnconfirmedPacketTransmissionCache;
import de.vsy.shared_module.thread_manipulation.ThreadStatusManipulator;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Manages Setup and destruction of a single reading thread operating on an ObjectInputStream and a
 * single writing thread operating on an ObjectOutputStream. Packets are read from and written to
 * PacketBuffers, to be provided.
 */
public class ConnectionThreadControl implements ConnectionThreadSynchronizer {

  private static final Logger LOGGER = LogManager.getLogger();
  private final ThreadPacketBufferManager bufferManager;
  private final Socket connectionSocket;
  private final ThreadStatusManipulator connectionSocketManipulator;
  /** Signals leader role on socket connection, resulting in inverted setup of read and write threads */
  private final boolean leaderRole;
  private final UnconfirmedPacketTransmissionCache packetCache;
  private final List<Thread> connectionSocketThreads;
  private Timer cachedPacketResentTimer;

  /**
   * Instantiates a new this.connectionSocket control.
   *
   * @param connectionSocket the this.connectionSocket
   * @param bufferManager    the buffer manager
   */
  public ConnectionThreadControl(final Socket connectionSocket,
      final ThreadPacketBufferManager bufferManager) {
    this(connectionSocket, bufferManager, false);
  }

  /**
   * Instantiates a new this.connectionSocket control.
   *
   * @param connectionSocket the this.connectionSocket
   * @param bufferManager    the buffer manager
   * @param leaderRole       the leader role
   */
  public ConnectionThreadControl(final Socket connectionSocket,
      final ThreadPacketBufferManager bufferManager,
      final boolean leaderRole) {
    this(connectionSocket, bufferManager, new UnconfirmedPacketTransmissionCache(1000), leaderRole);
  }

  /**
   * Instantiates a new this.connectionSocket control.
   *
   * @param connectionSocketSocket the this.connectionSocket socket
   * @param threadBufferManager    the thread buffer manager
   * @param transmissionCache      the transmission cache
   * @param leaderRole             the leader role
   */
  public ConnectionThreadControl(final Socket connectionSocketSocket,
      final ThreadPacketBufferManager threadBufferManager,
      final UnconfirmedPacketTransmissionCache transmissionCache, final boolean leaderRole) {
    this.connectionSocket = connectionSocketSocket;
    this.bufferManager = threadBufferManager;
    this.packetCache = transmissionCache;
    this.leaderRole = leaderRole;
    this.connectionSocketThreads = new ArrayList<>(2);
    this.connectionSocketManipulator = new ThreadStatusManipulator();
  }

  /**
   * Close this.connectionSocket.
   */
  public void closeConnection() {
    LOGGER.info("Client connection termination initiated.");
    this.connectionSocketManipulator.terminateThreads();
    this.cachedPacketResentTimer.cancel();

    try {
      this.connectionSocket.close();
    } catch (IOException ioe) {
      Thread.currentThread().interrupt();
      LOGGER.error("Socket could not be closed. Error:\n{}: {}",
          ioe.getClass().getSimpleName(), ioe.getMessage());
    }

    for (var connThread : this.connectionSocketThreads) {
      connThread.interrupt();
      LOGGER.info("Waiting for thread: {}:{}", connThread.getName(), connThread.getId());
      try {
        connThread.join(500);
      } catch (InterruptedException e) {
        LOGGER.error("{}:{} shutdown failed.", connThread.getName(), connThread.getId(), e);
      }
      LOGGER.info("{}:{} shutdown successfully.", connThread.getName(), connThread.getId());
    }
    this.connectionSocketThreads.clear();
    LOGGER.info("Client connection terminated.");
  }

  /**
   * Gets the keep alive state.
   *
   * @return the keep alive state
   */
  @Override
  public boolean connectionIsLive() {
    if (this.connectionSocket.isClosed()) {
      return false;
    } else {

      for (final var currentThread : this.connectionSocketThreads) {

        if (!currentThread.isAlive()) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Initiate this.connectionSocket.
   *
   * @return true, if successful
   */
  public boolean initiateConnectionThreads() {
    final var connected = true;

    try {
      setupPacketResendTimer();
      if (this.leaderRole) {
        setupReadThread();
        setupWriteThread();
      } else {
        setupWriteThread();
        setupReadThread();
      }
    } catch (final IOException ioe) {
      LOGGER.error("Connection setup failed. Input/outputStream invalid. Connection: {}",
          this.connectionSocket);
      return !connected;
    }
    return connected;
  }

  /**
   * Setup read thread.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private void setupReadThread() throws IOException {
    final var in = this.connectionSocket.getInputStream();
    final var reader = new PacketReadThread(this.packetCache, in, this.bufferManager);
    var readThread = new Thread(reader);
    readThread.setName("ReadThread");
    readThread.start();
    this.connectionSocketThreads.add(readThread);
  }

  /**
   * Setup write thread.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private void setupWriteThread() throws IOException {
    final var out = this.connectionSocket.getOutputStream();
    final var writer = new PacketWriteThread(this.packetCache, out,
        this.bufferManager.getPacketBuffer(ThreadPacketBufferLabel.OUTSIDE_BOUND));
    var writeThread = new Thread(writer);
    writeThread.setName("WriteThread");
    writeThread.start();
    this.connectionSocketThreads.add(writeThread);
  }

  private void setupPacketResendTimer() {
    final var resendTask = new CachedPacketResendTimer(this.packetCache,
        this.connectionSocketManipulator,
        this.bufferManager.getPacketBuffer(ThreadPacketBufferLabel.OUTSIDE_BOUND));
    this.cachedPacketResentTimer = new Timer("ResendUnansweredPackets");
    this.cachedPacketResentTimer.scheduleAtFixedRate(resendTask, 100, 500);
  }

  public UnconfirmedPacketTransmissionCache getPacketCache() {
    return this.packetCache;
  }
}
