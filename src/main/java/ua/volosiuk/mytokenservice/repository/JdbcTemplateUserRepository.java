package ua.volosiuk.mytokenservice.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ua.volosiuk.mytokenservice.entity.Role;
import ua.volosiuk.mytokenservice.entity.User;

import java.util.Optional;

@Log4j2
@Component
@RequiredArgsConstructor
public class JdbcTemplateUserRepository implements UserRepository {

    private static final String REPOSITORY_TYPE = "JdbcTemplateUserRepository";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public String getRepositoryIdentifier() {
        return REPOSITORY_TYPE;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        log.info("+++ JdbcTemplate method works");
        String query = "SELECT * FROM user_db WHERE username = :username";
        MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("username", username);

        return jdbcTemplate.queryForObject(query, parameters, (rs, rowNum) ->
                Optional.of(new User(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getBoolean("enabled"),
                        Role.valueOf(rs.getString("role"))
                ))
        );
    }
}
