package dev.chatree.smarthomeapi.controller;

import dev.chatree.smarthomeapi.entity.AccountEntity;
import dev.chatree.smarthomeapi.model.ErrorResponse;
import dev.chatree.smarthomeapi.model.account.AccountResponse;
import dev.chatree.smarthomeapi.model.home.HomeRequest;
import dev.chatree.smarthomeapi.model.home.HomeResponse;
import dev.chatree.smarthomeapi.service.AccountService;
import dev.chatree.smarthomeapi.service.HomeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/homes")
public class HomeController {

    private final HomeService homeService;

    private final AccountService accountService;

    public HomeController(HomeService homeService, AccountService accountService) {
        this.homeService = homeService;
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<?> getHomeByAccountId(Authentication authentication,
                                                HttpServletRequest request) {
        log.info("API {}: {}", request.getMethod(), request.getServletPath());
        try {
            String subject = authentication.getName();
            AccountEntity accountEntity = accountService.getAccountBySubject(subject);
            List<HomeResponse> homeEntityList = homeService.getHomeByAccount(accountEntity);
            return ResponseEntity.ok(homeEntityList);
        } catch (HttpClientErrorException e) {
            log.info("Error: {} {}", e.getMessage(), e.getStatusText());
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponse(e.getStatusCode().value(), e.getStatusText()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createHome(@RequestBody HomeRequest homeRequest,
                                        Authentication authentication,
                                        HttpServletRequest request) {
        log.info("API {}: {}", request.getMethod(), request.getServletPath());
        try {
            String subject = authentication.getName();
            AccountEntity accountEntity = accountService.getAccountBySubject(subject);
            HomeResponse homeResponse = homeService.createHome(homeRequest, accountEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(homeResponse);
        } catch (HttpClientErrorException e) {
            log.info("Error: {} {}", e.getMessage(), e.getStatusText());
            return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponse(e.getStatusCode().value(), e.getStatusText()));
        }
    }
}
