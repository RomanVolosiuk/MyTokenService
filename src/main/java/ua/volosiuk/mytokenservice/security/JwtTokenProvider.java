package ua.volosiuk.mytokenservice.security;

import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.volosiuk.mytokenservice.dto.CredentialsDTO;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;

import org.apache.commons.codec.binary.Base64;
import ua.volosiuk.mytokenservice.entity.User;
import ua.volosiuk.mytokenservice.exception.BadSha256HmacOrKeyException;
import ua.volosiuk.mytokenservice.service.TokenService;

import java.util.LinkedHashMap;
import java.util.Map;

@Log4j2
@Component
public class JwtTokenProvider {
    private final TokenService tokenService;
    private static final String SHA256 = "HmacSHA256";
    private final Mac sha256HMAC;

    public JwtTokenProvider(TokenService tokenService, @Value("${jwt.token.secretKey}") String secretKey) {
        this.tokenService = tokenService;
        try {
            SecretKeySpec secret = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), SHA256);
            this.sha256HMAC = Mac.getInstance(SHA256);
            this.sha256HMAC.init(secret);
        } catch (NoSuchAlgorithmException | InvalidKeyException ex) {
            log.error("Invalid Sha256HMAC algorithm or key in JwtTokenProvider.class");
            throw new BadSha256HmacOrKeyException("Invalid Sha256HMAC algorithm or key");
        }
    }

    public String createToken(CredentialsDTO credentialsDTO) {
        User user = tokenService.getUserObject(credentialsDTO);

        Map<String, String> headerMap = new LinkedHashMap<>();
        headerMap.put("alg", "HS256");
        headerMap.put("typ", "JWT");
        JSONObject header = new JSONObject(headerMap);

        Map<String, String> claimsMap = new LinkedHashMap<>();
        claimsMap.put("name", user.getUsername());
        claimsMap.put("roles", String.valueOf(user.getRole()));
        JSONObject claims = new JSONObject(claimsMap);

        String headerAndPayload = getBase64UrlString(header.toString())
                + "."
                + getBase64UrlString(claims.toString());

        JSONObject token = new JSONObject();
        token.put("token", headerAndPayload + "." + sign(headerAndPayload));

        return token.toString();
    }

    private String getBase64UrlString(String originalString) {

        return Base64.encodeBase64URLSafeString(originalString.getBytes(StandardCharsets.UTF_8));
    }

    private String sign(String data) {

        return Base64.encodeBase64URLSafeString(sha256HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8)));
    }
}
