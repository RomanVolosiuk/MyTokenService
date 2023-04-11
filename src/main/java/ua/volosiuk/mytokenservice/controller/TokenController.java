package ua.volosiuk.mytokenservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import ua.volosiuk.mytokenservice.dto.CredentialsDTO;
import ua.volosiuk.mytokenservice.service.TokenService;
import ua.volosiuk.mytokenservice.util.CredentialsUtils;

@Log4j2
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;
    private final CredentialsUtils credentialsUtils;

    @PostMapping
    public String token(@RequestHeader(HttpHeaders.AUTHORIZATION) String headerContent) {
        CredentialsDTO credentialsDTO = credentialsUtils.getCredentials(headerContent);

        return tokenService.isUserValid(credentialsDTO);
    }
}
