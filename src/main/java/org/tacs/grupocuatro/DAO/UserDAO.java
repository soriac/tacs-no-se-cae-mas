package org.tacs.grupocuatro.DAO;

import org.hibernate.Transaction;
import org.tacs.grupocuatro.HibernateUtil;
import org.tacs.grupocuatro.entity.Repository;
import org.tacs.grupocuatro.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class UserDAO implements DAO<User> {

    private static UserDAO instance;

    private static EntityManagerFactory emf;

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }

        return instance;
    }

    private List<User> users = new ArrayList<>();

    @Override
    public User get(long id) {
        User user = new User();
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            user = (User) session.createQuery("from User WHERE id = " + id).uniqueResult();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            users = session.createQuery("from User  ").getResultList();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return users;
    }

    @Override
    public void save(User user) {
        Transaction transaction = null;
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(user);
            transaction.commit();
            session.close();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        Transaction transaction = null;
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
            session.close();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(User user) {
        Transaction transaction = null;
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
            session.close();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    public User findByUser(String username) {
        User user = new User();
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            user = (User) session.createQuery("from User WHERE email = '" + username + "'").uniqueResult();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return user;
    }
    
    // Metodo momentaneo para probar redis
    public Optional<User> findUser(String email, String password){
    	User user = new User();
    	user.setEmail("prueba");
    	user.setPassword("prueba");
    	return Optional.ofNullable(user);
    	//return users.stream().filter(u -> u.getEmail().equals(email) && u.getPassword().equals(password)).findFirst();
    }

    public List<Repository> getFavoriteRepos(User user) {
        List <Repository> repos = new ArrayList<>();
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            repos = session.createQuery("from favoriteRepos WHERE user_id = " + user.getId()).getResultList();
            session.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return repos;
    }

    public List<Repository> getCommonRepos(User user1, User user2) {
        List <Repository> repos = new ArrayList<>();
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            List <Object[]> rows = session.createSQLQuery("SELECT r.id, r.added, r.name FROM repositories r " +
                    "JOIN favorite_repos f1 ON f1.repository_id = r.id " +
                    "JOIN favorite_repos f2 ON f2.repository_id = r.id " +
                    "WHERE f1.repository_id = f2.repository_id " +
                    "AND f1.user_id = " + user1.getId() +
                    " AND f2.user_id = " + user2.getId()).getResultList();
            session.close();
            for(Object[] row : rows) {
                Repository repo = new Repository();
                repo.setId(Long.parseLong(row[0].toString()));
                repo.setAdded(((Date) row[1]));
                repo.setName(row[2].toString());
                repos.add(repo);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return repos;
    }
}
