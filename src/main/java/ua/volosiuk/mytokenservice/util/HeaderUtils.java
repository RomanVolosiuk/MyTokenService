package ua.volosiuk.mytokenservice.util;

import ua.volosiuk.mytokenservice.exception.MalformedCredentialsException;
import ua.volosiuk.mytokenservice.exception.RequiresAuthorisationException;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public final class HeaderUtils {
    private HeaderUtils() {
    }

    public static List<String> credentialExtraction(String originalInput) {
        List<String> listCredentials = new ArrayList<>(2);

        if (originalInput == null || originalInput.isEmpty())
            throw new MalformedCredentialsException();

        String decodedString = getDecodedString(originalInput);
        if (decodedString.matches("^.{5,25}:(.{6,25})$")) {
            int colonIndex = decodedString.indexOf(':');
            listCredentials.add(decodedString.substring(0, colonIndex));
            listCredentials.add(decodedString.substring(colonIndex + 1));
        } else {
            throw new RequiresAuthorisationException();
        }
        return listCredentials;
    }
    private static String getDecodedString(String originalInput) {
        String cleanUserPass = originalInput.substring(6);
        byte[] decodedBytes = Base64.getDecoder().decode(cleanUserPass);
        return new String(decodedBytes);
    }
}
