package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.ListItem;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {
    Connection conn;

    @FXML
    private GridPane grid;

    @FXML
    ListView listView = new ListView();

    List<ListItem> items = new ArrayList<>();

    public MainPageController() {


        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fantazia_szinhaz", "root", "");
            Statement stmt = conn.createStatement();
            String query = "select eloadas.idopont as idopont, szindarab.nev as cim, mufaj.mufajnev as mufaj, rendezo.nev as szereplo, helyszin.nev as helyszin from eloadas inner join szindarab\n" +
                    "on eloadas.szindarabid=szindarab.szindarabid\n" +
                    "inner join mufaj\n" +
                    "on szindarab.mufajid=mufaj.mufajid\n" +
                    "inner join rendezo\n" +
                    "on szindarab.rendezoid=rendezo.rendezoid\n" +
                    "inner join helyszin\n" +
                    "on eloadas.helyszin=helyszin.helyszinid";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                setListItemData(rs);
            }
            listView.setOrientation(Orientation.HORIZONTAL);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void previousPlaysButtonPressed(ActionEvent event) throws IOException {
        items.clear();
        grid.getChildren().clear();
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fantazia_szinhaz", "root", "");
            Statement stmt = conn.createStatement();
            String query = "select eloadas.idopont as idopont, szindarab.nev as cim, mufaj.mufajnev as mufaj, rendezo.nev as szereplo, helyszin.nev as helyszin from eloadas inner join szindarab \n" +
                    "on eloadas.szindarabid=szindarab.szindarabid\n" +
                    "inner join mufaj\n" +
                    "on szindarab.mufajid=mufaj.mufajid\n" +
                    "inner join rendezo\n" +
                    "on szindarab.rendezoid=rendezo.rendezoid\n" +
                    "inner join helyszin\n" +
                    "on eloadas.helyszin=helyszin.helyszinid\n" +
                    "where eloadas.idopont<CURRENT_DATE()";
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

    public void nextPlaysButtonPressed(ActionEvent event) throws IOException {
        items.clear();
        grid.getChildren().clear();
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fantazia_szinhaz", "root", "");
            Statement stmt = conn.createStatement();
            String query = "select eloadas.idopont as idopont, szindarab.nev as cim, mufaj.mufajnev as mufaj, rendezo.nev as szereplo, helyszin.nev as helyszin from eloadas inner join szindarab \n" +
                    "on eloadas.szindarabid=szindarab.szindarabid\n" +
                    "inner join mufaj\n" +
                    "on szindarab.mufajid=mufaj.mufajid\n" +
                    "inner join rendezo\n" +
                    "on szindarab.rendezoid=rendezo.rendezoid\n" +
                    "inner join helyszin\n" +
                    "on eloadas.helyszin=helyszin.helyszinid\n" +
                    "where eloadas.idopont>CURRENT_DATE()";
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
        ListItem listItem = new ListItem();
        listItem.setDate(rs.getDate("idopont"));
        listItem.setTitle(rs.getString("cim"));
        listItem.setGenre(rs.getString("mufaj"));
        listItem.setName(rs.getString("szereplo"));
        listItem.setHall(rs.getString("helyszin"));
        items.add(listItem);
    }
}
