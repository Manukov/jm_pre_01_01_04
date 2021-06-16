package dao;

import model.User;
import org.hibernate.Session;
import util.Util;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    Session session = null;

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {

        final String QUERY ="CREATE TABLE IF NOT EXISTS users (id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100), lastname VARCHAR(100), age INT);";
        session = Util.getSesionFactory().openSession();
        session.createSQLQuery(QUERY).executeUpdate();
    }

    @Override
    public void dropUsersTable() {

        final String QUERY ="DROP TABLE IF EXISTS users";
        session = Util.getSesionFactory().openSession();
        session.createSQLQuery(QUERY).executeUpdate();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        session = Util.getSesionFactory().openSession();
        session.beginTransaction();
        session.save(new User(name, lastName, age));
        session.getTransaction().commit();
    }

    @Override
    public void removeUserById(long id) {
        session = Util.getSesionFactory().openSession();
        session.beginTransaction();
        User user = (User) session.load(User.class, id);
        session.delete(user);
        session.getTransaction().commit();
    }

    @Override
    public List<User> getAllUsers() {
        session = Util.getSesionFactory().openSession();
        return session.createCriteria(User.class).list();
    }

    @Override
    public void cleanUsersTable() {
        final String QUERY ="TRUNCATE TABLE users";
        session = Util.getSesionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery(QUERY).executeUpdate();
        session.getTransaction().commit();
    }
}
