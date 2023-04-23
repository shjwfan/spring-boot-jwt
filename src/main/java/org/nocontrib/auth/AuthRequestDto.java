package org.nocontrib.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record AuthRequestDto(
    @NotBlank(message = "username must be not blank") @Size(max = 256, message = "username must be under 256 characters") String username,
    @NotBlank(message = "password must be not blank") @Size(max = 256, message = "password must be under 256 characters") String password) {

  public static AuthRequestDto of(String username, String password) {
    return new AuthRequestDto(username, password);
  }
}
