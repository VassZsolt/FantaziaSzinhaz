package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class registerController {
    @FXML
    TextField nameField;
    @FXML
    TextField emailField;
    @FXML
    TextField passwordField;
    @FXML
    Hyperlink toSignInLink;
    @FXML
    Button registerButton;
    @FXML
    Label emptyFieldsLabel;

    @FXML
    public void signInButtonPressed(ActionEvent event) throws IOException {
        if (!fieldsEmpty()) {
           //regisztráció
        } else emptyFieldsLabel.setVisible(true);
    }

    private boolean fieldsEmpty() {
        return nameField.getText().isEmpty() || emailField.getText().isEmpty() || passwordField.getText().isEmpty();
    }

    @FXML
    public void openLogInPanel(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/logInLayout.fxml")));
        stage.setScene(new Scene(root));
        root.requestFocus();
        stage.show();
    }
}