package ua.volosiuk.mytokenservice.repository;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class UserRepositoryFactory {

    private final UserRepository jdbcUserRepository;
    private final UserRepository jdbcTemplateUserRepository;
    private final UserRepository hibernateUserRepository;

    public UserRepositoryFactory(@Qualifier("jdbcUserRepository") UserRepository jdbcUserRepository,
                                 @Qualifier("jdbcTemplateUserRepository") UserRepository jdbcTemplateUserRepository,
                                 @Qualifier("hibernateUserRepository") UserRepository hibernateUserRepository) {
        this.jdbcUserRepository = jdbcUserRepository;
        this.jdbcTemplateUserRepository = jdbcTemplateUserRepository;
        this.hibernateUserRepository = hibernateUserRepository;
    }

    public UserRepository getUserRepository(String connectionType) {

        switch (connectionType) {
            case "jdbc":
                log.info("!!! Jdbc type connection");

                return jdbcUserRepository;
            case "jdbcTemplate":
                log.info("!!! JdbcTemplate type connection");

                return jdbcTemplateUserRepository;
            case "hibernate":
                log.info("!!! Hibernate type connection");

                return hibernateUserRepository;

            default:
                log.warn("JdbcTemplate selected by default. Invalid repository type: " + connectionType);

                return jdbcUserRepository;
        }
    }
}
