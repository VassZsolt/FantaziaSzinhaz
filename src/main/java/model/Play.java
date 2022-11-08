package model;

import javafx.scene.control.ListCell;

import java.util.Date;

public class Play extends ListCell<String>{
    String title;

    Date date;

    String name;

    String genre;

    String hall;
    String description;
    int hallId;

    int eloadasId;
    public int getEloadasId() {
        return eloadasId;
    }

    public void setEloadasId(int eloadasId) {
        this.eloadasId = eloadasId;
    }
    public int getHallId() {
        return hallId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }
}
