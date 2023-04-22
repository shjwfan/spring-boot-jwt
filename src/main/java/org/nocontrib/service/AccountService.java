package org.nocontrib.service;

import java.util.List;
import java.util.UUID;
import org.nocontrib.entity.Account;

public interface AccountService {

  Account findById(UUID id);

  Account findByUsername(String username);

  List<Account> findAll();

  Account save(Account account);

  void updateByUsername(String username, Account account);

  void deleteByUsername(String username);
}
