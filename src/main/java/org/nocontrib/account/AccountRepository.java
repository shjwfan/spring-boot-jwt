package org.nocontrib.account;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

  @Query("SELECT a FROM Account a WHERE a.username = :username")
  Optional<Account> findByUsername(String username);

  @Modifying
  @Query("DELETE FROM Account a WHERE a.username = :username")
  void deleteByUsername(String username);
}
