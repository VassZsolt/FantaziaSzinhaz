package controller;

import database.entity.User;
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
import java.util.regex.Pattern;

import coder.*;

public class RegisterController {
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
    Label errorLabel;
    @FXML
    Label invalidEmailLabel;

    private final String emailRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private coder coder=new coder();

    @FXML
    public void initialize() {
        resetLabels();
    }

    @FXML
    public void signInButtonPressed(ActionEvent event) throws IOException {
        resetLabels();
        if (!fieldsEmpty()) {
            if (!validateEmail(emailField.getText())) {
                invalidEmailLabel.setVisible(true);
                invalidEmailLabel.setManaged(true);
            } else if (Main.USER_REPOSITORY.existsUserWithUsernameOrEmail(nameField.getText(), emailField.getText())) {
                errorLabel.setVisible(true);
                errorLabel.setManaged(true);
            } else {
                User user = new User(
                        nameField.getText(),
                        emailField.getText(),
                        coder.encode(passwordField.getText())
                );
                Main.USER_REPOSITORY.saveUser(user);
                showAlert(emailField.getText());
                openLogInPanel(event);
                emailField.clear();
                passwordField.clear();
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
        invalidEmailLabel.setVisible(false);
        invalidEmailLabel.setManaged(false);
    }

    private boolean validateEmail(String email) {
        return Pattern.matches(emailRegex, email);
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

    private void showAlert(String email) {
        ButtonType close = new ButtonType("OK");
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Sikeres regisztráció!", close);
        alert.setTitle("Sikeres regisztráció!");
        alert.setHeaderText("Sikeresen regisztráltál a következő email-címmel: " + email);
        Optional<ButtonType> result = alert.showAndWait();
        alert.close();
    }


}