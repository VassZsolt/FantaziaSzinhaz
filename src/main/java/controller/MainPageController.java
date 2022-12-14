package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.*;

import database.entity.Ticket;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.Play;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {

    Connection conn;

    @FXML
    private GridPane grid;

    @FXML
    ListView listView = new ListView();

    List<Play> plays = new ArrayList<>();

    List<Ticket> tickets = new ArrayList<>();


    public MainPageController() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fantazia_szinhaz", "root", "");
            Statement stmt = conn.createStatement();
            String initialQuery = "call getAllPlays()";
            ResultSet rs = stmt.executeQuery(initialQuery);

            while (rs.next()) {
                setListItemData(rs);
            }
            listView.setOrientation(Orientation.HORIZONTAL);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void previousPlaysButtonPressed(ActionEvent event) throws IOException {
        String queryPrevious = "call getPreviousPlays()";
        playDataQuerying(queryPrevious);
    }

    public void nextPlaysButtonPressed(ActionEvent event) throws IOException {
        String queryNext="call getNextPlays()";
        playDataQuerying(queryNext);
    }

    public void startPageButtonPressed(ActionEvent event)throws IOException{
        String queryStart="call getAllPlays()";
        playDataQuerying(queryStart);
    }

    public void exitButtonPressed(ActionEvent event)throws IOException{
        Platform.exit();
    }

    public void getTickets(ActionEvent event) throws IOException {
        try {
            Statement stmt = conn.createStatement();
            String query = "call getUserTickets('" + Main.LOGGED_IN_USER.getId() + "')";
            ticketDataQuerying(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void ticketDataQuerying(String query) {
        if (tickets.size()!=0) {
            tickets.clear();
        }
        if (grid.getChildren().size()!=0) {
            grid.getChildren().clear();
        }
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fantazia_szinhaz", "root", "");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                setTicketListItemData(rs);
            }
            listView.setOrientation(Orientation.HORIZONTAL);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int row = 1;
        try {
            for (Ticket ticket : tickets) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ticketListLayout.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                TicketListItemController ticketListItemController = fxmlLoader.getController();
                ticketListItemController.setData(ticket);

                grid.add(anchorPane, 0, row++);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setTicketListItemData(ResultSet rs) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setPrice(rs.getInt("ar"));
        ticket.setPlayId(rs.getInt("szinid"));
        ticket.setSeatId(rs.getInt("ulohely"));
        ticket.setDate(rs.getDate("idopont"));
        ticket.setTitle(rs.getString("cim"));
        ticket.setHallName(rs.getString("helyszin"));
        ticket.setEmail(rs.getString("email"));
        ticket.setNickname(rs.getString("username"));
        tickets.add(ticket);
    }

    @FXML
    private TextField searchBar;

    public void searchButtonPressed(ActionEvent event)throws IOException{
        String text= searchBar.getText();
        searchBar.clear();
        String querySearch="call getPlaysContaining('"+text+"')";
        playDataQuerying(querySearch);
    }

    public void playDataQuerying(String query){
        if (plays.size()!=0) {
            plays.clear();
        }
        if (grid.getChildren().size()!=0) {
            grid.getChildren().clear();
        }
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fantazia_szinhaz", "root", "");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                setListItemData(rs);
            }
            listView.setOrientation(Orientation.HORIZONTAL);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int row = 1;
        try {
            for (Play play : plays) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ListItemLayout.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                ListItemController listItemController = fxmlLoader.getController();
                listItemController.setData(play);

                grid.add(anchorPane, 0, row++);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int row = 1;
        try {
            for (int i = 0; i < plays.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ListItemLayout.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                ListItemController listItemController = fxmlLoader.getController();
                listItemController.setData(plays.get(i));

                grid.add(anchorPane, 0, row++);
                GridPane.setMargin(anchorPane, new Insets(10));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setListItemData(ResultSet rs) throws SQLException {
        Play play = new Play();
        play.setDate(rs.getDate("idopont"));
        play.setTitle(rs.getString("cim"));
        play.setGenre(rs.getString("mufaj"));
        play.setName(rs.getString("szereplo"));
        play.setHall(rs.getString("helyszin"));
        play.setHallId(rs.getInt("helyszinid"));
        play.setEloadasId(rs.getInt("eloadasid"));
        plays.add(play);
    }

}
