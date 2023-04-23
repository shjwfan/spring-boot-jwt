package org.nocontrib.auth;

import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.nocontrib.account.Account;
import org.nocontrib.account.AccountDto;
import org.nocontrib.account.AccountService;
import org.nocontrib.security.JwtProvider;
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
  public ResponseEntity<AccountDto> signUp(@Valid @RequestBody CreateAccountRequestDto dto) {
    Account account = service.save(dto);
    log.info("Account " + account + " saved");
    return ResponseEntity.ok(AccountDto.of(account));
  }

  @RequestMapping(value = "sign/in", method = RequestMethod.POST)
  public ResponseEntity<AuthResponseDto> signIn(@Valid @RequestBody AuthRequestDto request) {
    try {
      log.info("Try auth: " + request.toString());

      String username = request.username();
      String password = request.password();

      Authentication auth = new UsernamePasswordAuthenticationToken(username, password);
      manager.authenticate(auth);

      Account account = service.getByUsername(username);
      log.info("Account " + request + " is sign in");

      AuthResponseDto response = AuthResponseDto.of(provider.jwt(account));
      return ResponseEntity.ok(response);

    } catch (Exception exception) {
      log.info("Account " + request + " is not sign in");
      throw new BadCredentialsException("Bad credentials");
    }
  }
}
