package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import coder.*;

public class LoginController {

    private coder coder = new coder();
    @FXML
    TextField emailField;
    @FXML
    TextField passwordField;
    @FXML
    Hyperlink toRegisterLink;
    @FXML
    Button loginButton;
    @FXML
    Label emptyFieldsLabel;
    @FXML
    Label errorLabel;

    @FXML
    public void initialize() {
        resetLabels();
    }

    @FXML
    public void logInButtonPressed(ActionEvent event) throws IOException {
        resetLabels();
        if (!fieldsEmpty()) {
            if (!Main.USER_REPOSITORY.loadUser(emailField.getText(), coder.encode(passwordField.getText()))) {
                errorLabel.setVisible(true);
                errorLabel.setManaged(true);

            } else {
                showAlert(emailField.getText());
                emailField.clear();
                passwordField.clear();
                openMainPagePanel(event);

            }
        } else {
            emptyFieldsLabel.setVisible(true);
            emptyFieldsLabel.setManaged(true);
        }
    }

    private void resetLabels() {
        emptyFieldsLabel.setVisible(false);
        emptyFieldsLabel.setManaged(false);
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
    }

    private void showAlert(String email) {
        ButtonType close = new ButtonType("OK");
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Sikeres bejelentkezés!", close);
        alert.setTitle("Sikeres bejelentkezés!");
        alert.setHeaderText("Sikeresen bejelentkeztél a következő fiókkal: " + email);
        Optional<ButtonType> result = alert.showAndWait();
        alert.close();
    }

    private boolean fieldsEmpty() {
        return emailField.getText().isEmpty() || passwordField.getText().isEmpty();
    }

    @FXML
    public void openRegisterPanel(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/SignUpLayout.fxml")));
        stage.setScene(new Scene(root));
        root.requestFocus();
        stage.show();
    }

    @FXML
    public void openMainPagePanel(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/MainPageLayout.fxml")));
        stage.setScene(new Scene(root));
        root.requestFocus();
        stage.show();
    }
}