package com.photobox.services.foundation.server.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A generic multi-threaded Thrift server.
 */
final class MultiThreadedThriftServer implements ThriftServer {

  private static final Logger LOG = LoggerFactory.getLogger(MultiThreadedThriftServer.class);

  private final int port;
  private final TProcessor processor;

  private TServer server;
  private TServerTransport serverTransport;

  /**
   * Creates a new Thrift server.
   * The service could then be started by calling the start method.
   *
   * @param port the tcp port used by the service
   * @param processor the Thrift Processor that will implement the logic of the service
   */
  MultiThreadedThriftServer(int port, TProcessor processor) {
    this.port = port;
    this.processor = processor;
  }

  /**
   * Starts the server.
   */
  @Override
  public void start() throws TTransportException {
    LOG.info("Starting the Thrift server on port " + port + " ...");
    serverTransport = new TServerSocket(port);
    server = new TThreadPoolServer(new Args(serverTransport).processor(processor));
    Thread thriftServerThread = new Thread() {
      public void run() {
        server.serve();
      }
    };
    thriftServerThread.setName("thrift-server");
    thriftServerThread.start();
  }

  /**
   * Stops the server.
   */
  @Override
  public void stop() {
    server.stop();
    serverTransport.close();
    LOG.info("Thrift server stopped.");
  }
}
