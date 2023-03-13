package ua.volosiuk.mytokenservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class User {

    private Long id;
    private String username;
    private  String password;
    private boolean isEnabled;
    private  String role;
}
