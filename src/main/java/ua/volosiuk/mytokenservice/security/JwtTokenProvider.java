package ua.volosiuk.mytokenservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.volosiuk.mytokenservice.entity.Role;
import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
@Log4j2
@Component
public class JwtTokenProvider {
    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.token.expiredInMs}")
    private Long expiration;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(String username, Role role) {
        Date dataNow = new Date();
        Date validity = new Date(dataNow.getTime() + this.expiration);

        Claims claims = Jwts.claims().setSubject(username); // io.jsonwebtoken.lang.UnknownClassException

        claims.put("role", role);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(dataNow)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, this.secret)
                .compact();
    }
}
