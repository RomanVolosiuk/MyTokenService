package ua.volosiuk.mytokenservice.repository;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ua.volosiuk.mytokenservice.entity.User;

import java.util.Optional;

@Service
@Getter
@PropertySource("classpath:application.properties")
public class DatabaseService {
    private final UserRepository userRepository;

    private final String connectionType;

    public DatabaseService(@Value("${db.connectionType}") String connectionType, UserRepositoryFactory userRepositoryFactory) {
        this.connectionType = connectionType;
        this.userRepository = userRepositoryFactory.getUserRepository(connectionType);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
