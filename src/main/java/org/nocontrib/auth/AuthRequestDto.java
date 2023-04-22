package org.nocontrib.auth;

import lombok.Data;

@Data
public class AuthRequestDto {

  private String username;
  private String password;

  public static AuthRequestDto of(String username, String password) {
    AuthRequestDto dto = new AuthRequestDto();
    dto.setUsername(username);
    dto.setPassword(password);
    return dto;
  }

  @Override
  public String toString() {
    return String.format("%s:%s", username, password);
  }
}
