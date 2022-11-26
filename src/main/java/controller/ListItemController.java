package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Play;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class ListItemController {

    @FXML
    private Label actorText;

    @FXML
    private Button bookButton;

    @FXML
    private Label dateText;

    @FXML
    private Label genreText;

    @FXML
    private Hyperlink titleText;

    @FXML
    private Label theaterHallText;

    private int hallId;
    private int playId;

    public void setData(Play play) {
        hallId = play.getHallId();
        dateText.setText(play.getDate().toString());
        genreText.setText("Műfaj: " + play.getGenre());
        titleText.setText(play.getTitle());
        actorText.setText("Rendező: " + play.getName());
        theaterHallText.setText(play.getHall());
        playId=play.getEloadasId();

        bookButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    openBookingPanel(event);
                } catch (IOException | SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        titleText.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    openDetailedPagePanel(event);
                } catch (IOException /*| SQLException*/ e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @FXML
    public void openBookingPanel(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/"+theaterHallText.getText()+".fxml"));
        Parent root = fxmlLoader.load();
        HallController hallController = fxmlLoader.getController();
        hallController.setChoosenHallId(hallId);
        hallController.setPlayId(playId);
        hallController.initialize();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        root.requestFocus();
        stage.show();
    }

    @FXML
    public void openDetailedPagePanel(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/DetailedPage.fxml"));
        Parent root = fxmlLoader.load();
        DetailedPageController detailedPageController = fxmlLoader.getController();
        detailedPageController.setSelectedPlay(playId);
        detailedPageController.initialize();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        root.requestFocus();
        stage.show();
    }

}
