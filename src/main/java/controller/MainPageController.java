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

    @FXML
    private TextField searchBar;

    public void searchButtonPressed(ActionEvent event)throws IOException{
        String text= searchBar.getText();
        searchBar.clear();
        String quarySearch=
            "select distinct eas.idopont as idopont, sdb.nev as cim, mfj.mufajnev as mufaj, rzo.nev as szereplo, hszn.nev as helyszin, hszn.helyszinid as helyszinid \n" +
            "from fantazia_szinhaz.eloadas eas \n" +
            "inner join fantazia_szinhaz.szindarab sdb on eas.szindarabid=sdb.szindarabid \n" +
            "inner join fantazia_szinhaz.mufaj mfj on sdb.mufajid=mfj.mufajid \n" +
            "inner join fantazia_szinhaz.rendezo rzo on sdb.rendezoid=rzo.rendezoid \n" +
            "inner join fantazia_szinhaz.helyszin hszn on eas.helyszin=hszn.helyszinid \n" +
            "where \n" +
            "(eas.idopont>=DATE_ADD(CURRENT_DATE, INTERVAL -1 MONTH) \n" +
            "and eas.idopont<=DATE_ADD(CURRENT_DATE, INTERVAL 1 MONTH) \n" +
            "and  sdb.nev like \'%"+text+"%\' \n)" +
            "or CONVERT(eas.idopont,DATE)=REPLACE(REPLACE(LTRIM(RTRIM('"+text+"')),'.','-'),' ','-') \n" +
            "order by eas.idopont;";

        dataQuerying(quarySearch);
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
        ListItem listItem = new ListItem();
        listItem.setDate(rs.getDate("idopont"));
        listItem.setTitle(rs.getString("cim"));
        listItem.setGenre(rs.getString("mufaj"));
        listItem.setName(rs.getString("szereplo"));
        listItem.setHall(rs.getString("helyszin"));
        listItem.setHallId(rs.getInt("helyszinid"));
        items.add(listItem);
    }

}
