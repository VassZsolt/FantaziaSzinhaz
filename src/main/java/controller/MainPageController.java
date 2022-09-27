package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javassist.bytecode.stackmap.BasicBlock;
import model.ListItem;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {
    Connection conn;

    @FXML
    private GridPane grid;

    @FXML
    private Button nextButton;

    @FXML
    private Button previousButton;

    @FXML
    private ScrollPane scroll;

    @FXML
    private TextField searchBar;

    @FXML
    private Button searchButton;

    @FXML
    private Button ticketButton;
    @FXML
    ListView listView = new ListView();

    List<ListItem> items = new ArrayList<>();

    public MainPageController() {


        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fantazia_szinhaz", "root", "");
            Statement stmt = conn.createStatement();
            String query = "select eloadas.idopont as idopont, szindarab.nev as cim, mufaj.mufajnev as mufaj, rendezo.nev as szereplo from eloadas inner join szindarab\n" +
                    "on eloadas.szindarabid=szindarab.szindarabid\n" +
                    "inner join mufaj\n" +
                    "on szindarab.mufajid=mufaj.mufajid\n" +
                    "inner join rendezo\n" +
                    "on szindarab.rendezoid=rendezo.rendezoid";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                ListItem listItem = new ListItem();
                listItem.setDate(rs.getDate("idopont"));
                listItem.setTitle(rs.getString("cim"));
                listItem.setGenre(rs.getString("mufaj"));
                listItem.setName(rs.getString("szereplo"));

                items.add(listItem);
            }
            listView.setOrientation(Orientation.HORIZONTAL);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int row=1;
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

        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
