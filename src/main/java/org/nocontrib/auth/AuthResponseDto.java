package org.nocontrib.auth;

public record AuthResponseDto(String token) {

  public static AuthResponseDto of(String token) {
    return new AuthResponseDto(token);
  }
}
