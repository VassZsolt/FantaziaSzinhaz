package controller;

import java.io.IOException;
import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class baseApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Theater Ticket Browser");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/theaterIcon.png"))));
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/loginLayout.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        root.requestFocus();
        stage.setResizable(false);
        stage.show();
    }
}