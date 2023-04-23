package org.nocontrib.auth;

public record AuthRequestDto(String username, String password) {

  public static AuthRequestDto of(String username, String password) {
    return new AuthRequestDto(username, password);
  }

  @Override
  public String toString() {
    return String.format("%s:%s", username, password);
  }
}
