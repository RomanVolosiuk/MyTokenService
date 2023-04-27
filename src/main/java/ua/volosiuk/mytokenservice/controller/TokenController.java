package ua.volosiuk.mytokenservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import ua.volosiuk.mytokenservice.dto.CredentialsDTO;
import ua.volosiuk.mytokenservice.security.JwtTokenProvider;
import ua.volosiuk.mytokenservice.util.CredentialsUtils;

@Log4j2
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class TokenController {

    private final CredentialsUtils credentialsUtils;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public String token(@RequestHeader(HttpHeaders.AUTHORIZATION) String headerContent) {
        // move below line to the service
        CredentialsDTO credentialsDTO = credentialsUtils.getCredentials(headerContent);

        return jwtTokenProvider.createToken(credentialsDTO);
    }
}
