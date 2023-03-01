package ua.volosiuk.mytokenservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class User {

    private Long id;
    private String username;
    private  String password;
    private boolean enabled;
    private  String role;
}
