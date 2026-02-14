package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final HibernateUtil hibernateUtil = new HibernateUtil();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = hibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.createNativeQuery("""
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    lastName VARCHAR(255),
    age INT
)
""").executeUpdate();

        transaction.commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = hibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();

        transaction.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = hibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.save(new User(name, lastName, age));

        transaction.commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = hibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        User user = session.get(User.class, id);
        if (user != null) {
            session.delete(user);
        }

        transaction.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = hibernateUtil.getSessionFactory().openSession();
        List<User> users = session
                .createQuery("from User", User.class)
                .list();
        session.close();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = hibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.createNativeQuery("TRUNCATE TABLE users").executeUpdate();

        transaction.commit();
        session.close();
    }
}
