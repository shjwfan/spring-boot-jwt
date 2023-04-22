package org.nocontrib.security;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.nocontrib.account.AccountService;
import org.nocontrib.role.Role;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

  private final AccountService accountService;

  @Override
  public UserDetails loadUserByUsername(String username) {
    List<Role> roles = accountService.getRolesByUsername(username);
    return UserDetailsImplProducer.produce(accountService.getByUsername(username), roles);
  }
}
