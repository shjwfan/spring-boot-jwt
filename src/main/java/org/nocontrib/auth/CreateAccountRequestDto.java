package org.nocontrib.auth;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record CreateAccountRequestDto(
    @NotBlank(message = "username must be not blank") @Size(max = 256, message = "username must be under 256 characters") String username,
    @NotBlank(message = "password must be not blank") @Size(max = 256, message = "password must be under 256 characters") String password,
    @NotBlank(message = "name must be not blank") @Size(max = 256, message = "name must be under 256 characters") String name,
    @Email String email) {

}
