package org.nocontrib.dto;

import lombok.Data;

@Data
public class AuthResponseDto {

  private String token;

  public static AuthResponseDto of(String token) {
    AuthResponseDto dto = new AuthResponseDto();
    dto.setToken(token);
    return dto;
  }
}
