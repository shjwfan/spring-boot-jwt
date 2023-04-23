package org.nocontrib.auth;

public record CreateAccountRequestDto(String username, String password, String name, String email) {

}
