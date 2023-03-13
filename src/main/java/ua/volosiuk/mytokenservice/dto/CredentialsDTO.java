package ua.volosiuk.mytokenservice.dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredentialsDTO {
    private String username;
    private String password;
}
