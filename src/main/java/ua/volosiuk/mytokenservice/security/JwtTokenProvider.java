package ua.volosiuk.mytokenservice.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.volosiuk.mytokenservice.dto.CredentialsDTO;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;

import org.apache.commons.codec.binary.Base64;
import ua.volosiuk.mytokenservice.entity.User;
import ua.volosiuk.mytokenservice.exception.BadSha256HMACException;
import ua.volosiuk.mytokenservice.service.TokenService;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    @Value("${jwt.token.secretKey}")
    private String secretKey;
    private final TokenService tokenService;
    private static final String SHA256 = "HmacSHA256";

    public String createToken(CredentialsDTO credentialsDTO) {
        User user = tokenService.getUserObject(credentialsDTO);

        Map<String, String> header = new LinkedHashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        Map<String, String> claims = new LinkedHashMap<>();
        claims.put("name", user.getUsername());
        claims.put("roles", String.valueOf(user.getRole()));

        String headerAndPayload = getBase64UrlString(formatToString(header))
                + "."
                + getBase64UrlString(formatToString(claims));

        return "{\"token\":\""
                + getBase64UrlString(formatToString(header))
                + "."
                + getBase64UrlString(formatToString(claims))
                + "."
                + sign(headerAndPayload)
                + "\"}"
                ;
    }

    private String formatToString(Map<String, String> map) {
        String result = map.entrySet().stream()
                .map(e -> "\"" + e.getKey() + "\"" + ":" + "\"" + e.getValue() + "\"")
                .collect(Collectors.joining(","));

        return "{" + result + "}";
    }

    private String getBase64UrlString(String originalString) {

        return Base64.encodeBase64URLSafeString(originalString.getBytes(StandardCharsets.UTF_8));
    }

    private String sign(String data) {
        try {
            final Mac sha256HMAC = Mac.getInstance(SHA256);
            final SecretKeySpec secret = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), SHA256);
            sha256HMAC.init(secret);

            return Base64.encodeBase64URLSafeString(sha256HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            log.error("Bad Sha256HMAC algorithm in JwtTokenProvider.class");
            throw new BadSha256HMACException("Bad Sha256HMAC algorithm in JwtTokenProvider.class");
        }
    }
}
