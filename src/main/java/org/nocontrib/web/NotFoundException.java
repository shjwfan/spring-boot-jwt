package org.nocontrib.web;

public class NotFoundException extends RuntimeException {

  public NotFoundException(Exception exception) {
    super(exception);
  }

  public NotFoundException(String message) {
    super(message);
  }
}
