package controller;

import database.repository.UserRepository;
import javafx.application.Application;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("persistence");

    public static final UserRepository USER_REPOSITORY = new UserRepository();

    public static void main(String[] args) {
        Application.launch(BaseApplication.class, args);
    }

}