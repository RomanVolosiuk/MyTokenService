package ua.volosiuk.mytokenservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Data
public class User {

    private Long id;
    private String username;
    private  String password;
    private boolean enabled;
    private  Role role;

}
