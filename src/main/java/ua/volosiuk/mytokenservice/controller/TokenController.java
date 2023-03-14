package ua.volosiuk.mytokenservice.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.volosiuk.mytokenservice.dto.CredentialsDTO;

import static ua.volosiuk.mytokenservice.util.CredentialsUtils.getCredentials;

@RestController
@RequestMapping("/token")
public class TokenController {

    @PostMapping
    public String token(@RequestHeader(HttpHeaders.AUTHORIZATION) String headerContent) {
        CredentialsDTO test = getCredentials(headerContent);
        return "temp empty token string";
    }

}
