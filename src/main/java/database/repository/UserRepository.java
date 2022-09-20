package database.repository;

import controller.Main;
import database.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.Objects;

public class UserRepository {

    public void saveUser(User user) {
        EntityManager em = Main.ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            Objects.requireNonNull(transaction).rollback();
        }
        em.close();
    }

    public boolean existsUserWithUsernameOrEmail(String username, String email) {
        EntityManager em = Main.ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        boolean result = false;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            TypedQuery<Long> query = em.createQuery("""
                            select count(u)
                            from User u
                            where u.username = :username or u.email = :email
                            """, Long.class)
                    .setParameter("username", username)
                    .setParameter("email", email);
            if (query.getSingleResult() > 0) {
                result = true;
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            Objects.requireNonNull(transaction).rollback();
        }
        em.close();
        return result;
    }

    public boolean loadUser(String email, String password) {
        EntityManager em = Main.ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        boolean result = false;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            TypedQuery<Long> query = em.createQuery("""
                            select count(u)
                            from User u
                            where u.email = :email and u.password = :password
                            """, Long.class)
                    .setParameter("email", email)
                    .setParameter("password", password);
            if (query.getSingleResult() > 0) {
                result = true;
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            Objects.requireNonNull(transaction).rollback();
        }
        em.close();
        return result;
    }

}
