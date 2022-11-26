package database.repository;

import controller.Main;
import database.entity.Ticket;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Objects;

public class TicketRepository {

    public void saveTicket(Ticket ticket) {
        EntityManager em = Main.ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(ticket);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            Objects.requireNonNull(transaction).rollback();
        }
        em.close();
    }

}
