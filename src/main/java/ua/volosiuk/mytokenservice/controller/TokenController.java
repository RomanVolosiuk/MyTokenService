package ua.volosiuk.mytokenservice.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ua.volosiuk.mytokenservice.util.Credentials;

@Log4j2
@RestController
public class TokenController {

    @PostMapping("/token")
    public Credentials token(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String headerContent) {
        log.info("controller / token = " + headerContent);
        Credentials credentialsObj = new Credentials(headerContent);
        log.info(credentialsObj.getUsername());
        log.info(credentialsObj.getPassword());
        return credentialsObj;
    }
}
