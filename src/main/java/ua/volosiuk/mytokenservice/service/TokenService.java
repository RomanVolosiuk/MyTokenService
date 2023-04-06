package ua.volosiuk.mytokenservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;
import ua.volosiuk.mytokenservice.dto.CredentialsDTO;
import ua.volosiuk.mytokenservice.entity.User;
import ua.volosiuk.mytokenservice.exception.NoMD5AlgorithmException;
import ua.volosiuk.mytokenservice.exception.UserDisabledException;
import ua.volosiuk.mytokenservice.exception.UserNotExistException;
import ua.volosiuk.mytokenservice.exception.WrongPasswordException;
import ua.volosiuk.mytokenservice.repository.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class TokenService {

    private final UserRepository userRepository;

    public User loadUserByUsername(String username) {
        log.info("loadUserByUsername is started");
        Optional<User> optionalUser = userRepository.findByUsername(username);

        return optionalUser.orElseThrow(UserNotExistException::new);
    }

    public boolean userVerifier(CredentialsDTO credentialsDTO) {
        User user = loadUserByUsername(credentialsDTO.getUsername());

        if (comparePasswords(credentialsDTO.getPassword(), user.getPassword())) {
            log.info("Password is correct");
        } else {
            throw new WrongPasswordException();
        }

        if (user.isEnabled()) {
            log.info("User is enabled");
        } else {
            throw new UserDisabledException();
        }
        return true;
    }

    public boolean comparePasswords(String credentialsPassword, String userHashPassword) {
        final String result = stringToHashMD5(credentialsPassword);

        return userHashPassword.equals(result);
    }

    private String stringToHashMD5(String credentialsPassword) {
        final MessageDigest messageDigest;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new NoMD5AlgorithmException();
        }

        messageDigest.reset();
        messageDigest.update(credentialsPassword.getBytes(StandardCharsets.UTF_8));
        final byte[] resultByte = messageDigest.digest();

        return new String(Hex.encodeHex(resultByte));
    }
}
