package org.nocontrib.controller;

import lombok.extern.slf4j.Slf4j;
import org.nocontrib.dto.AccountDto;
import org.nocontrib.entity.Account;
import org.nocontrib.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/account/")
public class AccountRestController {

  private final AccountService service;

  public AccountRestController(AccountService service) {
    this.service = service;
  }

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public ResponseEntity<AccountDto> get(Authentication auth) {
    String username = auth.getName();
    AccountDto dto = AccountDto.of(service.findByUsername(username));
    return ResponseEntity.ok(dto);
  }

  @RequestMapping(value = "/", method = RequestMethod.PUT)
  public void update(Authentication auth, @RequestBody Account account) {
    String username = auth.getName();
    service.updateByUsername(username, account);
  }

  @RequestMapping(value = "/", method = RequestMethod.DELETE)
  public void delete(Authentication auth) {
    String username = auth.getName();
    service.deleteByUsername(username);
  }
}
