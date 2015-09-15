package com.photobox.services.foundation.testing.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.junit.rules.ExternalResource;

/**
 * This JUnit Rule starts a Thrift server running the given processor and shuts it down at
 * the end of the test.
 */
public class ThriftServerRule extends ExternalResource {

  private final int port;

  private TProcessor processor;
  private TServer server;
  private TServerSocket serverSocket ;

  /**
   * @param port the port where the Thrift server will be started (host will always be localhost)
   * @param processor the Thrift processor implementing the service executed on the server
   */
  public ThriftServerRule(int port, TProcessor processor) {
    this.processor = processor;
    this.port = port;
  }

  /** The logic executed before the test - starts the server. */
  @Override
  public void before() throws TTransportException {
    serverSocket = new TServerSocket(port);
    server = new TSimpleServer(new TServer.Args(serverSocket).processor(processor));

    new Thread() {
      public void run() {
        server.serve();
      }
    }.start();
  }

  /** The logic executed after the test - stops the server. */
  @Override
  public void after() {
    server.stop();
    serverSocket.close();
  }
}
