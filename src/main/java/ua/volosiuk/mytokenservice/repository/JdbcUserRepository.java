package ua.volosiuk.mytokenservice.repository;

import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;
import ua.volosiuk.mytokenservice.entity.Role;
import ua.volosiuk.mytokenservice.entity.User;

import java.sql.*;
import java.util.Optional;

@Log4j2
@Repository
@RequiredArgsConstructor
public class JdbcUserRepository implements UserRepository {

    private String url;
    private String username;
    private String password;

    public JdbcUserRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        log.info("+++ Jdbc method starts");
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
