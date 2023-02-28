package ua.volosiuk.mytokenservice.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ua.volosiuk.mytokenservice.util.AuthEntity;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class SiteController {

    public static final Logger logger = Logger.getLogger(SiteController.class.getName());


    @PostMapping("/login")
    public AuthEntity test(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String credentials){
        logger.log(Level.INFO, "controller / test = " + credentials);
       return new AuthEntity(credentials);
    }


}
