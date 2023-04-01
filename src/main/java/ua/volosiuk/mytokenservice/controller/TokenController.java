package ua.volosiuk.mytokenservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.volosiuk.mytokenservice.dto.CredentialsDTO;
import ua.volosiuk.mytokenservice.entity.User;
import ua.volosiuk.mytokenservice.service.TokenService;
import ua.volosiuk.mytokenservice.util.CredentialsUtils;

@Log4j2
@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;
    private final CredentialsUtils credentialsUtils;

    @PostMapping
    public String token(@RequestHeader(HttpHeaders.AUTHORIZATION) String headerContent) {
        CredentialsDTO credentialsDTO = credentialsUtils.getCredentials(headerContent);

        //
        User getUser = tokenService.loadUserByUsername(credentialsDTO.getUsername());
        return "temp empty token string";
    }

}
