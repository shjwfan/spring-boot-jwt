package org.nocontrib.security;

import org.nocontrib.account.Account;
import org.nocontrib.account.AccountService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

  private final AccountService service;

  public UserDetailsServiceImpl(AccountService service) {
    this.service = service;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Account account = service.findByUsername(username);
    if (account == null) {
      throw new UsernameNotFoundException("Account " + username + " is not defined");
    }
    UserDetailsImpl details = UserDetailsImplProducer.produce(account);
    return details;
  }
}
