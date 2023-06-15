package ua.volosiuk.mytokenservice.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.volosiuk.mytokenservice.entity.User;

import java.util.Optional;

@Log4j2
@Component("hibernateUserRepository")
@RequiredArgsConstructor
public class HibernateUserRepository implements UserRepository {

    private static final String REPOSITORY_TYPE = "HibernateUserRepository";
    private final SessionFactory sessionFactory;

    @Override
    public String getRepositoryIdentifier() {
        return REPOSITORY_TYPE;
    }

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
