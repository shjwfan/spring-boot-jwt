package org.nocontrib.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.nocontrib.entity.Account;
import org.nocontrib.entity.Role;
import org.nocontrib.repository.AccountRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AccountServiceImpl implements AccountService {

  private final AccountRepository repository;
  private final BCryptPasswordEncoder encoder;
  private final RoleService roleService;

  public AccountServiceImpl(AccountRepository repository, BCryptPasswordEncoder encoder,
      RoleService roleService) {
    this.repository = repository;
    this.encoder = encoder;
    this.roleService = roleService;
  }

  @Override
  public Account findById(UUID id) {
    Account account = repository.findById(id).orElse(null);
    if (account == null) {
      throw new ServiceException("Account is not defined");
    }
    return account;
  }

  @Override
  public List<Account> findAll() {
    return repository.findAll();
  }

  @Override
  public Account save(Account account) {
    Role role = roleService.findByName("ROLE_ACCOUNT");

    List<Role> roleList = List.of(role);
    account.setRoleList(roleList);

    String encoded = encoder.encode(account.getPassword());
    account.setPassword(encoded);

    Account result = repository.save(account);
    return result;
  }

  @Override
  public Account findByUsername(String username) {
    Account account = repository.findByUsername(username).orElse(null);
    if (account == null) {
      throw new ServiceException("Account is not defined");
    }
    return account;
  }

  @Override
  @Transactional
  public void updateByUsername(String username, Account account) {
    Account old = findByUsername(username);
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
    repository.deleteByUsername(username);
  }
}
