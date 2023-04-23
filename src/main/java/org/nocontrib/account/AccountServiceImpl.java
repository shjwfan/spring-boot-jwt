package org.nocontrib.account;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.nocontrib.auth.CreateAccountRequestDto;
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

  @Transactional
  @Override
  public Account save(CreateAccountRequestDto dto) {
    Account account = new Account();

    account.setName(dto.username());
    account.setName(dto.password());
    account.setName(dto.name());
    account.setName(dto.email());

    return save(account);
  }

  @Transactional(readOnly = true)
  @Override
  public Account getByUsername(String username) {
    return accountRepository.findByUsername(username)
        .orElseThrow(() -> new NotFoundException(String.format("%s was not found", username)));
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
