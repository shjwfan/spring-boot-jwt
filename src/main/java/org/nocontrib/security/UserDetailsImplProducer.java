package org.nocontrib.security;

import java.util.Date;
import java.util.List;
import org.nocontrib.account.Account;
import org.nocontrib.role.Role;
import org.springframework.security.core.GrantedAuthority;

public final class UserDetailsImplProducer {

  public static UserDetailsImpl produce(Account account, List<Role> roles) {
    Date lastPasswordResetDate = new Date(); // TODO: Fix it
    return new UserDetailsImpl(account.getId(), account.getUsername(), account.getPassword(),
        account.getName(), account.getEmail(), account.getCreated(), account.getStatus(),
        lastPasswordResetDate, mapGrantedAuthorities(roles));
  }

  private static List<GrantedAuthority> mapGrantedAuthorities(List<Role> roles) {
    return roles.stream().map(role -> (GrantedAuthority) role::getName).toList();
  }
}
