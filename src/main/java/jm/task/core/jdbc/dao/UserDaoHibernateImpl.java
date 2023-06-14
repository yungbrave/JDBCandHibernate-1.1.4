package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try (sessionFactory; session) {
            session.beginTransaction();

            session.createSQLQuery("create table if not exists users(" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(45) NOT NULL," +
                    "lastname VARCHAR(45) NOT NULL," +
                    "age TINYINT(10) NOT NULL)").executeUpdate();

            session.getTransaction().commit();

        } catch (RuntimeException e) {
            sessionFactory.getCurrentSession().getTransaction().rollback();
        }
    }

    @Override
    public void dropUsersTable() {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try (sessionFactory; session) {
            session.beginTransaction();

            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();

            session.getTransaction().commit();
        } catch (RuntimeException e) {
            sessionFactory.getCurrentSession().getTransaction().rollback();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try (session; sessionFactory) {
            session.beginTransaction();

            User user = new User(name, lastName, age);
            session.save(user);

            session.getTransaction().commit();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (RuntimeException e) {
            sessionFactory.openSession().getTransaction().rollback();
        }

    }

    @Override
    public void removeUserById(long id) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.getCurrentSession();


        try (sessionFactory; session) {
            session.beginTransaction();

            User user = session.get(User.class, id);
            session.delete(user);

            session.getTransaction().commit();
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        List<User> users = new ArrayList<>();
        try (sessionFactory; session) {
            session.beginTransaction();

            users = session.createQuery("FROM User", User.class).list();

            session.getTransaction().commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

//        try (sessionFactory; session) {

//        try (sessionFactory; session) {
//            session.setReadOnly(User.class,true);
//            users = session.createQuery("FROM User", User.class).getResultList();
//        } catch (RuntimeException e) {
//            e.printStackTrace();
//        }
//        CriteriaBuilder builder = session.getCriteriaBuilder();
//        CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
//        Root<User> root = criteriaQuery.from(User.class);
//        criteriaQuery.select(root);
//        users = session.createQuery(criteriaQuery).getResultList();
//        } catch (RuntimeException e) {
//            e.printStackTrace();
//        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try (sessionFactory; session) {
            session.beginTransaction();

            session.createQuery("delete from User").executeUpdate();

            session.getTransaction().commit();
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
        }
    }
}
