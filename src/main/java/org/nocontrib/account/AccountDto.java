package org.nocontrib.account;

import java.util.Date;
import java.util.UUID;
import lombok.Data;

@Data
public class AccountDto {

  private UUID id;
  private String username;
  private String password;
  private String name;
  private String email;
  private Date created;
  private Status status;

  public static AccountDto of(Account account) {
    AccountDto dto = new AccountDto();
    dto.setId(account.getId());
    dto.setUsername(account.getUsername());
    dto.setPassword(account.getPassword());
    dto.setName(account.getName());
    dto.setEmail(account.getEmail());
    dto.setCreated(account.getCreated());
    dto.setStatus(account.getStatus());
    return dto;
  }
}
