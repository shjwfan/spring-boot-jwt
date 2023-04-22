package org.nocontrib.account;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.nocontrib.role.Role;
import org.nocontrib.role.RoleRepository;
import org.nocontrib.web.NotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class AccountServiceImpl implements AccountService {

  private final AccountRepository accountRepository;
  private final BCryptPasswordEncoder encoder;
  private final RoleRepository roleRepository;

  @Transactional
  @Override
  public Account save(Account account) {
    List<Role> roles = roleRepository.findAllByName("ROLE_ACCOUNT").toList();
    account.setRoles(roles);

    String encoded = encoder.encode(account.getPassword());
    account.setPassword(encoded);

    return accountRepository.save(account);
  }

  @Transactional(readOnly = true)
  @Override
  public Account getByUsername(String username) {
    return accountRepository.findByUsername(username)
        .orElseThrow(() -> new NotFoundException(String.format("%s was not found", username)));
  }

  @Override
  @Transactional
  public void updateByUsername(String username, Account account) {
    Account old = getByUsername(username);
    if (old != null && account != null) {
      if (account.getUsername() != null && !old.getUsername().equals(account.getUsername())) {
        old.setUsername(account.getUsername());
      }

      if (account.getPassword() != null) {
        String encoded = encoder.encode(account.getPassword());
        if (!old.getPassword().equals(encoded)) {
          old.setPassword(encoded);
        }
      }

      if (account.getName() != null && !old.getName().equals(account.getName())) {
        old.setName(account.getName());
      }

      if (account.getEmail() != null && !old.getEmail().equals(account.getEmail())) {
        old.setEmail(account.getEmail());
      }
      old.setCreated(new Date());
    }
  }

  @Override
  @Transactional
  public void deleteByUsername(String username) {
    accountRepository.deleteByUsername(username);
  }

  @Transactional(readOnly = true)
  @Override
  public List<Role> getRolesByUsername(String username) {
    return roleRepository.findByUsername(username);
  }
}
