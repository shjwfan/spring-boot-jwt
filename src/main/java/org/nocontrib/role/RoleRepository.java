package org.nocontrib.role;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

  @Query("SELECT r FROM Role r WHERE r.name = :name")
  Stream<Role> findAllByName(String name);

  @Query("select r from Role r inner join r.accounts a where a.username = :username")
  List<Role> findByUsername(@Param("username") String username);
}
