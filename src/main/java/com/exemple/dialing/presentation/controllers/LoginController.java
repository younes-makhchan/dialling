package com.exemple.dialing.presentation.controllers;

import com.exemple.dialing.dao.UserDaoImpl;
import com.exemple.dialing.dao.entities.User;
import com.exemple.dialing.presentation.AppNavigator;
import com.exemple.dialing.service.IServiceUserImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML
    private void handleLogin(ActionEvent event) {
        String enteredUsername = usernameField.getText();
        String enteredPassword = passwordField.getText();

        // Assuming you have a service layer to interact with your database
        IServiceUserImpl userService = new IServiceUserImpl(new UserDaoImpl());
        // Perform authentication
        User authenticatedUser = userService.findUserbyNameAndPassword(enteredUsername, enteredPassword);

        if (authenticatedUser != null) {
            // Authentication successful
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Login successful!");
            AppNavigator.loadUserListScene(authenticatedUser);
            alert.show();
        } else {
            // Authentication failed
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Login failed! Invalid username or password.");
            alert.show();
            usernameField.setText("");
            passwordField.setText("");
        }
    }

    @FXML
    private void switchToRegister(ActionEvent event) {
        AppNavigator.loadRegisterScene();
    }
}
