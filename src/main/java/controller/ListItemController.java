package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.ListItem;
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
    private Label titleText;

    @FXML
    private Label theaterHallText;

    public void setData(ListItem listitem) {
        dateText.setText(listitem.getDate().toString());
        genreText.setText("Műfaj: " + listitem.getGenre());
        titleText.setText(listitem.getTitle());
        actorText.setText("Rendező: " + listitem.getName());
        theaterHallText.setText(listitem.getHall());

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
    }

    @FXML
    public void openBookingPanel(ActionEvent event) throws IOException, SQLException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/"+theaterHallText.getText()+".fxml")));
        stage.setScene(new Scene(root));
        root.requestFocus();
        stage.show();
    }

}
