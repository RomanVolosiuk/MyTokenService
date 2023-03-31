package ua.volosiuk.mytokenservice.repository;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;
import ua.volosiuk.mytokenservice.entity.Role;
import ua.volosiuk.mytokenservice.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Log4j2
@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private static List<User> users;

    static {
        users = new ArrayList<>(Arrays.asList(
                new User(1L, "ErikClapton", "7c6a180b36896a0a8c02787eeafb0e4c", true, Role.USER),
                new User(2L, "JimmyHendrix", "6cb75f652a9b52798eb6cf2201057c73", true, Role.ADMIN),
                new User(3L, "JonLord", "819b0643d6b89dc9b579fdfc9094f28e", true, Role.TEA_POT)

        ));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        log.info("findByUsername started");
        return users.stream().filter(user -> username.equals(user.getUsername())).findFirst();
    }

}
