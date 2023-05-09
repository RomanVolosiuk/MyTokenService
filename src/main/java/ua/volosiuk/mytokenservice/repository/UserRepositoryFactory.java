package ua.volosiuk.mytokenservice.repository;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@AllArgsConstructor
public class UserRepositoryFactory {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SessionFactory sessionFactory;


    public UserRepository getUserRepository(String connectionType) {
        switch (connectionType) {
            case "jdbc":
                log.info("!!! Jdbc type connection");
                return new JdbcUserRepository(
                        System.getProperty("db.url"),
                        System.getProperty("db.username"),
                        System.getProperty("db.password")
                );
            case "jdbcTemplate":
                log.info("!!! Jdbctemplate type connection");
                return new JdbcTemplateUserRepository(namedParameterJdbcTemplate);
            case "hibernate":
                log.info("!!! Hibernate type connection");
                return new HibernateUserRepository(sessionFactory);
            default:
                throw new IllegalArgumentException("Invalid repository type: " + connectionType);
        }
    }
}
