package org.interview.application.exception;

public class TwitterStreamClientException extends RuntimeException {

  public TwitterStreamClientException(String message) {
    super(message);
  }

  public TwitterStreamClientException(String message, Exception exception) {
    super(message, exception);
  }
}
