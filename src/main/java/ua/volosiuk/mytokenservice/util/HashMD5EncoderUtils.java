package ua.volosiuk.mytokenservice.util;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Component;
import ua.volosiuk.mytokenservice.exception.BadMD5AlgorithmException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Log4j2
@Component
public class HashMD5EncoderUtils {
    private final MessageDigest messageDigest;

    public HashMD5EncoderUtils() {
        try {
            this.messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            log.error("Bad MD5 algorithm on HashMD5EncoderUtils.class");
            throw new BadMD5AlgorithmException("Bad MD5 algorithm on HashMD5EncoderUtils.class");
        }
    }

    public String stringToHashMD5(String credentialsPassword) {
        messageDigest.reset();
        messageDigest.update(credentialsPassword.getBytes(StandardCharsets.UTF_8));
        final byte[] resultByte = messageDigest.digest();

        return new String(Hex.encodeHex(resultByte));
    }
}
