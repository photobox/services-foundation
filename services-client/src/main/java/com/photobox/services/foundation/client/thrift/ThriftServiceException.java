package com.photobox.services.foundation.client.thrift;

import org.apache.thrift.TException;

/**
 * Wraps Thrift checked exceptions making them as RuntimeException.
 */
public class ThriftServiceException extends RuntimeException {
  public ThriftServiceException(TException e) {
    super(e);
  }

  public ThriftServiceException(String m, TException e) {
    super(m, e);
  }
}
