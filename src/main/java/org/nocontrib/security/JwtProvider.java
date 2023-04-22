package org.nocontrib.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.nocontrib.entity.Account;
import org.nocontrib.entity.Role;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtProvider {

  private final String key = "potato";
  private final Long range = 3600000L;

  private final UserDetailsServiceImpl service;

  public JwtProvider(UserDetailsServiceImpl service) {
    this.service = service;
  }

  public String jwt(Account account) {
    String username = account.getUsername();
    List<Role> roleList = account.getRoleList();
    return jwt(username, roleList);
  }

  public String jwt(String subject, List<Role> roleList) {
    List<String> roleNameList = toRoleNameList(roleList);

    Claims claims = Jwts.claims().setSubject(subject);
    claims.put("role_list", roleNameList);

    Date issuedAt = new Date();
    Date expiration = new Date(issuedAt.getTime() + this.range);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(issuedAt)
        .setExpiration(expiration)
        .signWith(SignatureAlgorithm.HS256, key)
        .compact();
  }

  public Authentication getAuth(String jwt) {
    UserDetails details = service.loadUserByUsername(pullSubject(jwt));
    Collection<? extends GrantedAuthority> authorities = details.getAuthorities();
    Authentication token =
        new UsernamePasswordAuthenticationToken(details, "", authorities);
    return token;
  }

  public String pullSubject(String jwt) {
    Jws<Claims> parsed = parse(jwt);
    Claims body = parsed.getBody();
    String subject = body.getSubject();
    return subject;
  }

  public Jws<Claims> parse(String jwt) {
    Jws<Claims> parsed = Jwts.parser().setSigningKey(this.key).parseClaimsJws(jwt);
    return parsed;
  }

  public boolean validate(String jwt) {
    try {
      Jws<Claims> claims = parse(jwt);
      Claims body = claims.getBody();

      Date expiration = body.getExpiration();
      return !expiration.before(new Date());
    } catch (Exception exception) {
      throw new JwtAuthenticationException("JWT is expired or invalid");
    }
  }

  public List<String> toRoleNameList(List<Role> roleList) {
    List<String> roleNameList = roleList.stream().map(role -> role.getName())
        .collect(Collectors.toList());
    return roleNameList;
  }
}
