package controller;

import database.entity.User;
import database.repository.TicketRepository;
import database.repository.UserRepository;
import javafx.application.Application;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    public static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("persistence");

    public static final UserRepository USER_REPOSITORY = new UserRepository();

    public static final TicketRepository TICKET_REPOSITORY = new TicketRepository();

    public static User LOGGED_IN_USER = null;

    public static void main(String[] args) {
        Application.launch(BaseApplication.class, args);
    }

}