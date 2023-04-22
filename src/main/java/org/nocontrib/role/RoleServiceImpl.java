package org.nocontrib.role;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.nocontrib.web.NotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RoleServiceImpl implements RoleService {

  private final RoleRepository repository;

  public RoleServiceImpl(RoleRepository repository) {
    this.repository = repository;
  }

  @Override
  public Role findById(UUID id) {
    Role role = repository.findById(id).orElse(null);
    if (role == null) {
      throw new NotFoundException("Role is not defined");
    }
    return null;
  }

  @Override
  public List<Role> findAll() {
    return repository.findAll();
  }

  @Override
  public Role save(Role role) {
    return repository.save(role);
  }

  @Override
  public Role findByName(String name) {
    Role role = repository.findByName(name).orElse(null);
    if (role == null) {
      throw new NotFoundException("Role is not defined");
    }
    return role;
  }

  @Override
  @Transactional
  public void updateByName(String name, Role role) {
    Role old = findByName(name);
    if (old != null && role != null) {
      if (old.getName() != null && !old.getName().equals(role.getName())) {
        old.setName(role.getName());
      }
      old.setCreated(new Date());
    }
  }

  @Override
  @Transactional
  public void deleteByName(String name) {
    repository.deleteByName(name);
  }
}
