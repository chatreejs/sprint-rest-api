package dev.chatree.smarthomeapi.controller;

import dev.chatree.smarthomeapi.model.ErrorResponse;
import dev.chatree.smarthomeapi.model.account.AccountRequest;
import dev.chatree.smarthomeapi.model.account.AccountResponse;
import dev.chatree.smarthomeapi.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@Log4j2
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/userinfo")
    public ResponseEntity<?> getUserInfo(Authentication authentication,
                                         HttpServletRequest request) {
        log.info("API {}: {}", request.getMethod(), request.getServletPath());
        try {
            AccountResponse accountResponse = accountService.getAccountInfoBySubject(authentication.getName());
            return ResponseEntity.ok(accountResponse);
        } catch (HttpClientErrorException e) {
            log.info("Error: {} {}", e.getMessage(), e.getCause());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody AccountRequest accountRequest,
                                           Authentication authentication,
                                           HttpServletRequest request) {
        log.info("API {}: {}", request.getMethod(), request.getServletPath());
        accountService.createAccount(accountRequest, authentication.getName());
        return ResponseEntity.created(null).build();
    }
}
