package ua.volosiuk.mytokenservice.repository;

import ua.volosiuk.mytokenservice.entity.User;

import java.util.Optional;

public interface UserRepository {

    String getRepositoryIdentifier();
    Optional<User> findByUsername(String username);

}
