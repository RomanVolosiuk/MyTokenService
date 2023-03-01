package ua.volosiuk.mytokenservice.util;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import java.util.Base64;

@Log4j2
@Getter
public class Credentials {
    private String username;
    private String password;

    public Credentials(String originalInput) {
        // TODO add try/catch

        String decodedString = getDecodedString(originalInput);

        int colonIndex = decodedString.indexOf(':');
        this.username = decodedString.substring(0, colonIndex);
        this.password = decodedString.substring(colonIndex + 1);

        log.info("username = " + username);
        log.info("password = " + password);
    }

    private String getDecodedString(String originalInput) {
        String cleanUserPass = originalInput.substring(6);
        byte[] decodedBytes = Base64.getDecoder().decode(cleanUserPass);
        return new String(decodedBytes);
    }
}
