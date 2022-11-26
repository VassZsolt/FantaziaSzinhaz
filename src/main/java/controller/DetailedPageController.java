package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class DetailedPageController {

    @FXML
    Button backButton;

    @FXML
    Button bookButton;

    @FXML
    Text detailedTitle;

    @FXML
    Text date;

    @FXML
    Text hall;

    @FXML
    Text genre;

    @FXML
    Text actors;
    @FXML
    Text pause;
    @FXML
    Text ageLimit;
    @FXML
    Text description;

    private int hallId;

    int playid,szindarabid,n=-1;
    String hallName;
    String actorsCharacters="Szereplők:\n";
    @FXML
    void initialize() {
        try {
            n++;
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fantazia_szinhaz", "root", "");
            Statement stmt = conn.createStatement();
            String initialQuery = "select  sdb.korhatar as korhatar, sdb.szunet_szam as szunet, sdb.szindarabid as szindarabid, eas.eloadasid as szinid, eas.idopont as idopont, sdb.nev as cim, mfj.mufajnev as mufaj, rzo.nev as szereplo,hszn.nev as helyszin, hszn.helyszinid as helyszinid,sdb.leiras as leiras\n" +
                    "            from fantazia_szinhaz.eloadas eas \n" +
                    "            inner join fantazia_szinhaz.szindarab sdb on eas.szindarabid=sdb.szindarabid\n" +
                    "            inner join fantazia_szinhaz.mufaj mfj on sdb.mufajid=mfj.mufajid\n" +
                    "            inner join fantazia_szinhaz.rendezo rzo on sdb.rendezoid=rzo.rendezoid\n" +
                    "            inner join fantazia_szinhaz.helyszin hszn on eas.helyszin=hszn.helyszinid\n" +
                    "            where eas.eloadasid="+playid+
                    "            order by eas.idopont";
            ResultSet rs = stmt.executeQuery(initialQuery);
            while (rs.next()) {
                szindarabid=rs.getInt("szindarabid");
                detailedTitle.setText(rs.getString("cim"));
                date.setText("Időpont: "+rs.getDate("idopont").toString()+" "+rs.getTime("idopont").toString());
                hallName=rs.getString("helyszin");
                hall.setText("Terem: "+hallName);
                genre.setText("Műfaj: "+rs.getString("mufaj"));
                description.setText("Leírás: "+rs.getString("leiras"));
                description.setWrappingWidth(590);
                hallId = rs.getInt("helyszinid");
                ageLimit.setText("Korhatár: "+rs.getString("korhatar"));
                pause.setText("Szünetek száma: "+rs.getString("szunet"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (n==1){
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fantazia_szinhaz", "root", "");
                Statement stmt = conn.createStatement();
                String charactersquery="SELECT distinct sz.szerep_nev as szerep, szin.nev as szinesz from eloado as ea inner join szerep as sz on ea.szerepid=sz.szerepid\n" +
                        "inner join szinesz as szin on ea.szineszid=szin.szineszid\n" +
                        "where sz.szindarabid="+szindarabid;
                ResultSet character=stmt.executeQuery(charactersquery);
                while (character.next()){
                    actorsCharacters+=character.getString("szerep")+" - "+character.getString("szinesz")+"\n";
                }
                actors.setText(actorsCharacters);
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
    @FXML
    public void openBookingPanel(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/"+hallName+".fxml"));
        Parent root = fxmlLoader.load();
        HallController hallController = fxmlLoader.getController();
        hallController.setChoosenHallId(hallId);
        hallController.initialize();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        root.requestFocus();
        stage.show();
    }

    @FXML
    public void closeWindow(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    public void setSelectedPlay(int playid){
        this.playid=playid;
    }
}
