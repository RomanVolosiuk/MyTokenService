package ua.volosiuk.mytokenservice.util;

import lombok.Getter;
import lombok.Setter;
import ua.volosiuk.mytokenservice.controller.SiteController;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
public class AuthEntity {
    private String username;
    private String password;
    public static final Logger logger = Logger.getLogger(SiteController.class.getName());

    public AuthEntity(String originalInput) {
        // TODO add try/catch

        String decodedString = getDecodedString(originalInput);

        int colonIndex = decodedString.indexOf(':');
        this.username = decodedString.substring(0, colonIndex);
        this.password = decodedString.substring(colonIndex + 1);

        logger.log(Level.INFO, "username = " + username);
        logger.log(Level.INFO, "password = " + password);
    }

    private String getDecodedString(String originalInput) {
        String cleanUserPass = originalInput.substring(6);
        byte[] decodedBytes = Base64.getDecoder().decode(cleanUserPass);
        String decodedString = new String(decodedBytes);
        return decodedString;
    }
}
