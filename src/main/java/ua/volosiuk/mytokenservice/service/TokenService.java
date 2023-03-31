package ua.volosiuk.mytokenservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ua.volosiuk.mytokenservice.entity.User;
import ua.volosiuk.mytokenservice.repository.UserRepository;

import java.util.Optional;

@Service
@Log4j2
@AllArgsConstructor
public class TokenService {

    private final UserRepository userRepository;

    public User loadUserByUsername(String username) throws NoSuchFieldException {
        log.info("loadUserByUsername is started");
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {

            return new User(
                    optionalUser.get().getId(),
                    optionalUser.get().getUsername(),
                    optionalUser.get().getPassword(),
                    optionalUser.get().isEnabled(),
                    optionalUser.get().getRole());

        } else {
            throw new NoSuchFieldException("");
        }

    }


}
