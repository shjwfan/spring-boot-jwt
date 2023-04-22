package org.nocontrib.account;

import java.util.List;
import org.nocontrib.role.Role;

public interface AccountService {

  Account getByUsername(String username);

  Account save(Account account);

  void updateByUsername(String username, Account account);

  void deleteByUsername(String username);

  List<Role> getRolesByUsername(String username);
}
