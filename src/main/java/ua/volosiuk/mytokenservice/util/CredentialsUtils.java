package ua.volosiuk.mytokenservice.util;

import org.springframework.stereotype.Component;
import ua.volosiuk.mytokenservice.dto.CredentialsDTO;
import ua.volosiuk.mytokenservice.exception.MalformedCredentialsException;
import ua.volosiuk.mytokenservice.exception.RequiresAuthorisationException;

import java.util.Base64;

@Component
public class CredentialsUtils {

    public CredentialsDTO getCredentials(String headerContent) {
        if (headerContent == null || headerContent.isEmpty())
            throw new MalformedCredentialsException();

        String decodedString = getDecodedBase64String(headerContent);

        if (decodedString.matches("^.{5,25}:(.{6,25})$")) {
            int colonIndex = decodedString.indexOf(':');
            String username = (decodedString.substring(0, colonIndex));
            String password = (decodedString.substring(colonIndex + 1));
            return new CredentialsDTO(username, password);
        } else {
            throw new RequiresAuthorisationException();
        }
    }

    private String getDecodedBase64String(String originalInput) {
        String cleanUserPass = originalInput.substring(6);
        byte[] decodedBytes = Base64.getDecoder().decode(cleanUserPass);
        return new String(decodedBytes);
    }
}
