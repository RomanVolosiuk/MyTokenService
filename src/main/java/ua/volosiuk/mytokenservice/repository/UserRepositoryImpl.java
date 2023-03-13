package ua.volosiuk.mytokenservice.repository;

import lombok.AllArgsConstructor;
import ua.volosiuk.mytokenservice.entity.User;

import java.util.Optional;

@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserRepository userRepository;

    @Override
    public Optional<User> getByUsername(String username) {
        return Optional.empty();
        //contacts.stream().filter(contact -> id.equals(contact.getId())).findFirst();
    }
}
