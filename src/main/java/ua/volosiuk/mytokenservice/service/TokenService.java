package ua.volosiuk.mytokenservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ua.volosiuk.mytokenservice.entity.User;
import ua.volosiuk.mytokenservice.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class TokenService {

    private final UserRepository userRepository;

    public User loadUserByUsername(String username) {
        log.info("loadUserByUsername is started");
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.orElseThrow(() -> new NoSuchElementException(""));
    }
}
