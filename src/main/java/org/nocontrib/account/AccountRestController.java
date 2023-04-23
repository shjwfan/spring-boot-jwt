package org.nocontrib.account;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nocontrib.role.RoleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/account/")
public class AccountRestController {

  private final AccountService service;

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public ResponseEntity<AccountDto> get(Authentication auth) {
    String username = auth.getName();
    AccountDto dto = AccountDto.of(service.getByUsername(username));
    return ResponseEntity.ok(dto);
  }

  @GetMapping(value = "/{username}/roles")
  public ResponseEntity<List<RoleDto>> getRoles(@PathVariable("username") String username) {
    List<RoleDto> dtos = service.getRolesByUsername(username).stream()
        .map(RoleDto::of)
        .toList();
    return ResponseEntity.ok(dtos);
  }

  @RequestMapping(value = "/", method = RequestMethod.DELETE)
  public void delete(Authentication auth) {
    String username = auth.getName();
    service.deleteByUsername(username);
  }
}
