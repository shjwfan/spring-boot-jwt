package org.nocontrib.account;

import java.util.Date;
import java.util.UUID;

public record AccountDto(UUID id, String username, String password, String name, String email,
                         Date created, Status status) {

  public static AccountDto of(Account account) {
    return new AccountDto(account.getId(), account.getUsername(), account.getPassword(),
        account.getName(), account.getEmail(), account.getCreated(), account.getStatus());
  }
}
