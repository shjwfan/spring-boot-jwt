package org.nocontrib.security;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import lombok.Data;
import org.nocontrib.entity.Status;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
public class UserDetailsImpl implements UserDetails {

  private final UUID id;
  private final String username;
  private final String password;
  private final String name;
  private final String email;
  private final Date created;
  private final Status status;
  private final Date lastPasswordResetDate;
  private final Collection<? extends GrantedAuthority> authorities;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return status != Status.LOCK;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return status == Status.ENABLED;
  }
}
