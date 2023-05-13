package ua.volosiuk.mytokenservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ua.volosiuk.mytokenservice.dto.CredentialsDTO;
import ua.volosiuk.mytokenservice.entity.User;
import ua.volosiuk.mytokenservice.exception.UserDisabledException;
import ua.volosiuk.mytokenservice.exception.UserNotExistException;
import ua.volosiuk.mytokenservice.exception.WrongPasswordException;
import ua.volosiuk.mytokenservice.repository.DatabaseService;
import ua.volosiuk.mytokenservice.util.HashMD5EncoderUtils;

import java.util.Optional;

@Log4j2
@Service
@AllArgsConstructor
public class TokenService {

    private final DatabaseService databaseService;

    private User loadUserByUsername(String username) {
        Optional<User> optionalUser = databaseService.getUserByUsername(username);

        return optionalUser.orElseThrow(() -> {
            log.warn("User with username {} does not exist", username);

            return new UserNotExistException("User does not exist");
        });
    }

    public User getUserObject(CredentialsDTO credentialsDTO) {
        User user = loadUserByUsername(credentialsDTO.getUsername());

        if (!(isPasswordValid(credentialsDTO.getPassword(), user.getPassword()))) {
            log.warn("Wrong password for username {}", user.getUsername());
            throw new WrongPasswordException("Wrong password");
        }
        if (!(user.isEnabled())) {
            log.warn("User {} is disabled", user.getUsername());
            throw new UserDisabledException("User is disabled");
        }

        return user;
    }

    private boolean isPasswordValid(String credentialsPassword, String userHashedPassword) {
        final String result = new HashMD5EncoderUtils().stringToHashMD5(credentialsPassword);

        return userHashedPassword.equals(result);
    }
}
