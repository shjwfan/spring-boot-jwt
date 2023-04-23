package org.nocontrib.account;

import java.util.List;
import org.nocontrib.auth.CreateAccountRequestDto;
import org.nocontrib.role.Role;

public interface AccountService {

  Account getByUsername(String username);

  Account save(Account account);

  Account save(CreateAccountRequestDto dto);

  void deleteByUsername(String username);

  List<Role> getRolesByUsername(String username);
}
