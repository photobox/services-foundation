package com.photobox.services.foundation.server.thrift.dropwizard;

import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

import com.photobox.services.foundation.server.thrift.ThriftServer;

import org.apache.thrift.transport.TTransportException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ManagedThriftServerUTest {

  @Mock private ThriftServer internalThriftServer;
  @InjectMocks private ManagedThriftServer managedServer;

  @Test
  public void start() throws TTransportException {
    managedServer.start();
    verify(internalThriftServer, only()).start();
  }

  @Test
  public void stop() throws TTransportException {
    managedServer.stop();
    verify(internalThriftServer, only()).stop();
  }
}