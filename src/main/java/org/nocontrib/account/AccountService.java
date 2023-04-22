package org.nocontrib.account;

import java.util.List;
import java.util.UUID;

public interface AccountService {

  Account findById(UUID id);

  Account findByUsername(String username);

  List<Account> findAll();

  Account save(Account account);

  void updateByUsername(String username, Account account);

  void deleteByUsername(String username);
}
