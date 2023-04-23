package org.nocontrib.role;

import java.util.Date;
import java.util.UUID;

public record RoleDto(UUID id, String name, Date created) {

  public static RoleDto of(Role role) {
    return new RoleDto(role.getId(), role.getName(), role.getCreated());
  }
}
