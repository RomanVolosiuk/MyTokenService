package ua.volosiuk.mytokenservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.volosiuk.mytokenservice.dto.CredentialsDTO;
import ua.volosiuk.mytokenservice.entity.User;
import ua.volosiuk.mytokenservice.exception.UserDisabledException;
import ua.volosiuk.mytokenservice.exception.UserNotExistException;
import ua.volosiuk.mytokenservice.exception.WrongPasswordException;
import ua.volosiuk.mytokenservice.repository.UserRepository;
import ua.volosiuk.mytokenservice.util.HashMD5EncoderUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
// please create UserService and move user related stuff there
// this service should be called from controller and use token provider
public class TokenService {
    private final UserRepository userRepository;

    private User loadUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        return optionalUser.orElseThrow(UserNotExistException::new);
    }

    public User getUserObject(CredentialsDTO credentialsDTO) {
        User user = loadUserByUsername(credentialsDTO.getUsername());

        if (!(isPasswordValid(credentialsDTO.getPassword(), user.getPassword())))
            throw new WrongPasswordException();

        if (!(user.isEnabled()))
            throw new UserDisabledException();

        return user;
    }

    private boolean isPasswordValid(String credentialsPassword, String userHashedPassword) {
        final String result = new HashMD5EncoderUtils().stringToHashMD5(credentialsPassword);

        return userHashedPassword.equals(result);
    }
}
