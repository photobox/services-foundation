package com.photobox.services.foundation.client.thrift;

import com.photobox.services.foundation.client.ClientConfiguration;
import com.photobox.services.foundation.client.ServiceClient;
import com.photobox.services.foundation.client.SimpleServiceClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A base class that has to be used as a client proxy for Thrift services.
 *
 * This class handles the invocations to the exposed interface and redirect them
 * to the real service using a Thrift client.
 *
 * The actual Thrift client as well as the logic around the call is defined by the
 * sub-class implementing this template.
 */
abstract class ThriftServiceClientTemplate implements InvocationHandler {

  // the client's configuration
  final ClientConfiguration conf;

  // a basic implementation of the common functionalities defined in ServiceClient
  private final ServiceClient serviceClientTarget;


  /**
   * Initialize a new client with its configuration.
   *
   * @param conf the client's configuration
   */
  ThriftServiceClientTemplate(ClientConfiguration conf) {
    this.conf = conf;
    this.serviceClientTarget = new SimpleServiceClient(conf);
  }

  /**
   * @see InvocationHandler#invoke(Object, Method, Object[])
   * @throws ThriftServiceException to map all the checked exceptions thrown during the invocation
   */
  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

    // check if the method belongs to the ServiceClient interface
    if (ServiceClient.class.getName().equals(method.getDeclaringClass().getName())) {
      return method.invoke(serviceClientTarget, args);
    }

    // delegates the method invocation to the Thrift service client provided by the sub-class
    try (CloseableThriftClient client = getThriftClient()) {
      Object result = method.invoke(client.getIface(), args);
      afterInvocation(client);
      return result;
    } catch (InvocationTargetException e) {
      throw e.getTargetException();
    }
  }

  /**
   * Custom operations that have to be performed after the invocation of the service.
   *
   * @param client the Thrift client used to call the service
   */
  protected void afterInvocation(CloseableThriftClient client) {
    // open for extension
  }

  /**
   * This method is called at every invocation to obtain the reference to a
   * Thrift client for the service. The actual implementation of this client
   * will depend on the specific sub-class of this template.
   *
   * @return the Thrift client to use in the service invocation
   */
  protected abstract CloseableThriftClient getThriftClient();

}
