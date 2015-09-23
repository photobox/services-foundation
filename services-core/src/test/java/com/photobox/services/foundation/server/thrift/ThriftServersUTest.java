package com.photobox.services.foundation.server.thrift;

import org.apache.thrift.TProcessor;
import org.junit.Assert;
import org.junit.Test;

public class ThriftServersUTest {

  private static final int PORT = 1234;
  private static final TProcessor PROCESSOR = (tProtocol, tProtocol1) -> true;

  @Test
  public void createMultiThreadedThriftServer() {
    ThriftServer server = ThriftServers.createMultiThreadedThriftServer(PORT, PROCESSOR);
    Assert.assertTrue(server instanceof MultiThreadedThriftServer);
  }
}
