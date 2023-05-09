package ua.volosiuk.mytokenservice.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.volosiuk.mytokenservice.entity.User;

import java.util.Optional;

@Log4j2
@AllArgsConstructor
@Getter
@Setter
@Repository
public class HibernateUserRepository implements UserRepository {

    private final SessionFactory sessionFactory;

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        log.info("+++ Hibernate method works");
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("FROM User WHERE username = :username", User.class);
        query.setParameter("username", username);
        User user = query.uniqueResult();

        return Optional.ofNullable(user);

    }
}
