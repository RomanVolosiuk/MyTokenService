package ua.volosiuk.mytokenservice.util;

import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Component;
import ua.volosiuk.mytokenservice.exception.NoMD5AlgorithmException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class HashMD5EncoderUtils {
private final MessageDigest messageDigest;

    public HashMD5EncoderUtils() {
        try {
            this.messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new NoMD5AlgorithmException();
        }
    }

    public String stringToHashMD5(String credentialsPassword) {
        messageDigest.reset();
        messageDigest.update(credentialsPassword.getBytes(StandardCharsets.UTF_8));
        final byte[] resultByte = messageDigest.digest();

        return new String(Hex.encodeHex(resultByte));
    }
}
