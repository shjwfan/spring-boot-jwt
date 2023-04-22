package org.nocontrib.dto;

import java.util.Date;
import java.util.UUID;
import lombok.Data;
import org.nocontrib.entity.Role;

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
