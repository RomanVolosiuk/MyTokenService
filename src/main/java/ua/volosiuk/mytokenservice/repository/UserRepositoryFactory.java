package ua.volosiuk.mytokenservice.repository;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Component
public class UserRepositoryFactory {

    private final Map<String, UserRepository> userRepositoryMap;
    private static final String DEFAULT_USER_REPOSITORY_NAME = "JdbcUserRepository";

    @Autowired
    public UserRepositoryFactory(List<UserRepository> userRepositoryList) {
        this.userRepositoryMap = new HashMap<>();
        for (UserRepository userRepository : userRepositoryList) {
            this.userRepositoryMap.put(userRepository.getRepositoryIdentifier(), userRepository);
        }
    }

    public UserRepository getUserRepository(String connectionType) {
        if (userRepositoryMap.get(connectionType) == null) {
            log.warn("JdbcTemplate selected by default. Invalid repository type: " + connectionType);

            return userRepositoryMap.get(DEFAULT_USER_REPOSITORY_NAME);
        }
        return userRepositoryMap.get(connectionType);
    }
}

