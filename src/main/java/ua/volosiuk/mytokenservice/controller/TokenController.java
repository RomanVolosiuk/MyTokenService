package ua.volosiuk.mytokenservice.controller;

import lombok.extern.log4j.Log4j2;
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
    public String token(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String headerContent) {
       CredentialsDTO test = getCredentials(headerContent);
        return "plug";
    }
}
