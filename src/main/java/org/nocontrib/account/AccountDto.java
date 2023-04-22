package org.nocontrib.account;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.Data;
import org.nocontrib.role.RoleDto;
import org.nocontrib.role.Role;

@Data
public class AccountDto {

  private UUID id;
  private String username;
  private String password;
  private String name;
  private String email;
  private Date created;
  private Status status;
  private List<RoleDto> roleList;

  public static AccountDto of(Account account) {
    List<Role> roleList = account.getRoleList();
    List<RoleDto> roleDtoList = roleList.stream().map(role -> RoleDto.of(role))
        .collect(Collectors.toList());

    AccountDto dto = new AccountDto();
    dto.setId(account.getId());
    dto.setUsername(account.getUsername());
    dto.setPassword(account.getPassword());
    dto.setName(account.getName());
    dto.setEmail(account.getEmail());
    dto.setCreated(account.getCreated());
    dto.setStatus(account.getStatus());
    dto.setRoleList(roleDtoList);
    return dto;
  }
}
