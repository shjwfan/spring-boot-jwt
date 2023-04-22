package org.nocontrib.service;

public class ServiceException extends RuntimeException {

  public ServiceException(Exception exception) {
    super(exception);
  }

  public ServiceException(String message) {
    super(message);
  }
}
