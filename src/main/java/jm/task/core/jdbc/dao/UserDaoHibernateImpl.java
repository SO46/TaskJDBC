package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Session session;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        session = Util.getSessionFactory().openSession();
    }

    @Override
    public void dropUsersTable() {
        if(session != null) {
            try {
                session.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(new User(name, lastName, age));
            transaction.commit();
        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
                throw e;
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        User user = session.load(User.class, id);
        session.delete(user);
    }

    @Override
    public List<User> getAllUsers() {
        return session.createQuery("From " + User.class.getSimpleName()).list();
    }

    @Override
    public void cleanUsersTable() {
        session.beginTransaction();
        session.createSQLQuery("truncate table users").executeUpdate();
        session.getTransaction().commit();
    }

    public void close(){
        session.close();
    }
}
