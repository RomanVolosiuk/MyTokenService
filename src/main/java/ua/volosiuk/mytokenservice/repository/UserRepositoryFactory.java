package ua.volosiuk.mytokenservice.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
@PropertySource("classpath:database.properties")
public class UserRepositoryFactory {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SessionFactory sessionFactory;

    @Value("${db.url}")
    private String url;
    @Value("${db.username}")
    private String username;
    @Value("${db.password}")
    private String password;

    public UserRepository getUserRepository(String connectionType) {
        switch (connectionType) {
            case "jdbc":
                log.info("!!! Jdbc type connection");

                return new JdbcUserRepository(url, username, password);
            case "jdbcTemplate":
                log.info("!!! JdbcTemplate type connection");

                return new JdbcTemplateUserRepository(namedParameterJdbcTemplate);
            case "hibernate":
                log.info("!!! Hibernate type connection");

                return new HibernateUserRepository(sessionFactory);
            default:
                log.warn("JdbcTemplate selected by default. Invalid repository type: " + connectionType);

                return new JdbcTemplateUserRepository(namedParameterJdbcTemplate);
        }
    }
}
