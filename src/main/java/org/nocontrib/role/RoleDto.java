package org.nocontrib.role;

import java.util.Date;
import java.util.UUID;
import lombok.Data;

@Data
public class RoleDto {

  private UUID id;
  private String name;
  private Date created;

  public static RoleDto of(Role role) {
    RoleDto dto = new RoleDto();
    dto.setId(role.getId());
    dto.setName(role.getName());
    dto.setCreated(role.getCreated());
    return dto;
  }
}
