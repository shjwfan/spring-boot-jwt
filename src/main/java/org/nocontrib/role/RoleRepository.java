package org.nocontrib.role;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

  @Query("SELECT r FROM Role r WHERE r.name = :name")
  Optional<Role> findByName(String name);

  @Modifying
  @Query("DELETE FROM Role r WHERE r.name = :name")
  void deleteByName(String name);
}
