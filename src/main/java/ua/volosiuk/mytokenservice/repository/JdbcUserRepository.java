package ua.volosiuk.mytokenservice.repository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;
import ua.volosiuk.mytokenservice.entity.Role;
import ua.volosiuk.mytokenservice.entity.User;

import java.sql.*;
import java.util.Optional;

@Log4j2
@Repository
@PropertySource("classpath:database.properties")
public class JdbcUserRepository implements UserRepository {

    private final String url;
    private final String username;
    private final String password;

    public JdbcUserRepository(
            @Value("${db.url}") String url,
            @Value("${db.username}") String username,
            @Value("${db.password}") String password) {
        this.url = "jdbc:postgresql://localhost:5432/users_db";
        this.username = "postgres";
        this.password = "qwerty";
    }

    @Override
    public Optional<User> findByUsername(String username) {
        log.info("+++ Jdbc method works");
        try (Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
             PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM user_db WHERE username = ? LIMIT 1")) {
             statement.setString(1, username);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    User user = new User(
                            rs.getLong("id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getBoolean("enabled"),
                            Role.valueOf(rs.getString("role"))
                    );

                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            log.error("Error occurred while finding user_db by username {}", username);
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
