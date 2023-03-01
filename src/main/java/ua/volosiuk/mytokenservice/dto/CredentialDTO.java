package ua.volosiuk.mytokenservice.dto;

import lombok.Getter;
import ua.volosiuk.mytokenservice.util.HeaderUtils;

import java.util.List;

@Getter
public class CredentialDTO {
    private final String username;
    private final String password;
    public CredentialDTO(String headerContent) {
    List<String> credentialsList = HeaderUtils.credentialExtraction(headerContent);
    this.username = credentialsList.get(0);
    this.password = credentialsList.get(1);
    }
}
