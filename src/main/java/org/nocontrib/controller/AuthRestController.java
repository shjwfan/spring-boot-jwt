package org.nocontrib.controller;

import lombok.extern.slf4j.Slf4j;
import org.nocontrib.dto.AccountDto;
import org.nocontrib.dto.AuthRequestDto;
import org.nocontrib.dto.AuthResponseDto;
import org.nocontrib.entity.Account;
import org.nocontrib.security.JwtProvider;
import org.nocontrib.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth/")
public class AuthRestController {

  private final AuthenticationManager manager;
  private final JwtProvider provider;
  private final AccountService service;

  public AuthRestController(AuthenticationManager manager, JwtProvider provider,
      AccountService service) {
    this.manager = manager;
    this.provider = provider;
    this.service = service;
  }

  @RequestMapping(value = "sign/up", method = RequestMethod.POST)
  public ResponseEntity<AccountDto> signUp(@RequestBody Account account) {
    AccountDto dto = AccountDto.of(service.save(account));
    log.info("Account " + dto + " is sign up");
    return ResponseEntity.ok(dto);
  }

  @RequestMapping(value = "sign/in", method = RequestMethod.POST)
  public ResponseEntity<AuthResponseDto> signIn(@RequestBody AuthRequestDto request) {
    try {
      log.info("Try auth: " + request.toString());

      String username = request.getUsername();
      String password = request.getPassword();

      Authentication auth = new UsernamePasswordAuthenticationToken(username, password);
      manager.authenticate(auth);

      Account account = service.findByUsername(username);
      log.info("Account " + request + " is sign in");

      AuthResponseDto response = AuthResponseDto.of(provider.jwt(account));
      return ResponseEntity.ok(response);

    } catch (Exception exception) {
      log.info("Account " + request + " is not sign in");
      throw new BadCredentialsException("Bad credentials");
    }
  }
}
