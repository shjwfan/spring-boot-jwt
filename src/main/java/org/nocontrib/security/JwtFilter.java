package org.nocontrib.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class JwtFilter extends GenericFilterBean {

  private final JwtProvider provider;

  public JwtFilter(JwtProvider provider) {
    this.provider = provider;
  }

  public String pullToken(HttpServletRequest request) {
    String token = request.getHeader("Authorization");
    if (token != null && token.indexOf("Bearer ") == 0) {
      return token.replace("Bearer ", "");
    }
    return null;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    String token = pullToken(httpRequest);
    if (token != null && provider.validate(token)) {
      Authentication auth = provider.getAuth(token);
      if (auth != null) {
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(auth);
      }
    }
    chain.doFilter(request, response);
  }
}
