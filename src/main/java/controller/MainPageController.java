package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.*;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Play;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {

    Connection conn;

    @FXML
    private GridPane grid;

    @FXML
    ListView listView = new ListView();

    List<Play> items = new ArrayList<>();

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
        dataQuerying(queryPrevious);
    }

    public void nextPlaysButtonPressed(ActionEvent event) throws IOException {
        String queryNext="call getNextPlays()";
        dataQuerying(queryNext);
    }

    public void startPageButtonPressed(ActionEvent event)throws IOException{
        String queryStart="call getAllPlays()";
        dataQuerying(queryStart);
    }

    public void exitButtonPressed(ActionEvent event)throws IOException{
        Platform.exit();
    }

    public void getTickets(ActionEvent event) throws IOException {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("call getUserTickets('" + Main.LOGGED_IN_USER.getId() + "')");
            while (rs.next()) {
                System.out.println(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //openTicketsPanel(event);
    }

    @FXML
    public void openTicketsPanel(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/TicketItemLayout.fxml")));
        stage.setScene(new Scene(root));
        root.requestFocus();
        stage.show();
    }

    @FXML
    private TextField searchBar;

    public void searchButtonPressed(ActionEvent event)throws IOException{
        String text= searchBar.getText();
        searchBar.clear();
        String querySearch="call getPlaysContaining('"+text+"')";
        dataQuerying(querySearch);
    }

    public void dataQuerying(String query){
        if (items.size()!=0) {
            items.clear();
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
            for (int i = 0; i < items.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ListItemLayout.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                ListItemController listItemController = fxmlLoader.getController();
                listItemController.setData(items.get(i));

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
            for (int i = 0; i < items.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ListItemLayout.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                ListItemController listItemController = fxmlLoader.getController();
                listItemController.setData(items.get(i));

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
        play.setEloadasId(rs.getInt("szinid"));
        items.add(play);
    }

}
