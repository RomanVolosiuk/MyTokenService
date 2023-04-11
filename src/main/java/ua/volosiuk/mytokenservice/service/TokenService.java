package ua.volosiuk.mytokenservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ua.volosiuk.mytokenservice.dto.CredentialsDTO;
import ua.volosiuk.mytokenservice.entity.Role;
import ua.volosiuk.mytokenservice.entity.User;
import ua.volosiuk.mytokenservice.exception.UserDisabledException;
import ua.volosiuk.mytokenservice.exception.UserNotExistException;
import ua.volosiuk.mytokenservice.exception.WrongPasswordException;
import ua.volosiuk.mytokenservice.repository.UserRepository;
import ua.volosiuk.mytokenservice.util.HashMD5EncoderUtils;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class TokenService {
    private final UserRepository userRepository;

    private User loadUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        return optionalUser.orElseThrow(UserNotExistException::new);
    }

    public String isUserValid(CredentialsDTO credentialsDTO) {
        User user = loadUserByUsername(credentialsDTO.getUsername());

        if (!(passwordsValid(credentialsDTO.getPassword(), user.getPassword())))
            throw new WrongPasswordException();

        if (!(user.isEnabled()))
            throw new UserDisabledException();

        return tokenGenerator(user.getUsername(),user.getRole());
    }

    private boolean passwordsValid(String credentialsPassword, String userHashPassword) {

        final String result = new HashMD5EncoderUtils().stringToHashMD5(credentialsPassword);

        return userHashPassword.equals(result);
    }

    private String tokenGenerator(String username, Role role) {

        return "token" + ":" + username + "." + role + "." + System.currentTimeMillis();
    }

}
