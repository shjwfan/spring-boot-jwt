package org.nocontrib.security;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.nocontrib.entity.Account;
import org.nocontrib.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public final class UserDetailsImplProducer {

  public static UserDetailsImpl produce(Account account) {
    Date lastPasswordResetDate = new Date(); // TODO: Fix it
    List<GrantedAuthority> authorities = toGrantedAuthorities(account.getRoleList());
    UserDetailsImpl details = new UserDetailsImpl(account.getId(), account.getUsername(),
        account.getPassword(), account.getName(), account.getEmail(), account.getCreated(),
        account.getStatus(), lastPasswordResetDate, authorities);
    return details;
  }

  private static List<GrantedAuthority> toGrantedAuthorities(List<Role> roleList) {
    List<GrantedAuthority> list = roleList.stream().map(role -> {
      String name = role.getName();
      GrantedAuthority authority = new SimpleGrantedAuthority(name);
      return authority;
    }).collect(Collectors.toList());
    return list;
  }
}
