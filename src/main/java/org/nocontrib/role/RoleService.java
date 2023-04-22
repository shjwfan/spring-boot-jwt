package org.nocontrib.role;

import java.util.List;
import java.util.UUID;

public interface RoleService {

  Role findById(UUID id);

  Role findByName(String name);

  List<Role> findAll();

  Role save(Role role);

  void updateByName(String name, Role role);

  void deleteByName(String name);
}
