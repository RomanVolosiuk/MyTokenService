package ua.volosiuk.mytokenservice.repository;

import ua.volosiuk.mytokenservice.entity.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> getByUsername(String username);
}
