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

public class RegisterController {

    @FXML private TextField usernameField;
    @FXML private TextField firstnameField;
    @FXML private TextField lastnameField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    @FXML
    private void handleCreateAccount(ActionEvent event) {
        String enteredUsername = usernameField.getText();
        String enteredPassword = passwordField.getText();

        // Assuming you have a service layer to interact with your database
        IServiceUserImpl userService = new IServiceUserImpl(new UserDaoImpl());

        // Check if the username is available (you may want to add additional validation)
        if (userService.findUserbyNameAndPassword(enteredUsername, null) == null) {
            // Create a new User object with the entered credentials
            User newUser = new User(enteredUsername, enteredPassword);
            // Add the new user to the database
            userService.addUser(newUser);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Account created successfully!");
            // Switch to the login scene
            AppNavigator.loadLoginScene();
            alert.show();
        } else {
            // Username is not available, display an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Username is already taken. Please choose another username.");
            alert.show();
            usernameField.setText("");
            passwordField.setText("");
        }
    }

    @FXML
    private void switchToLogin(ActionEvent event) {
        AppNavigator.loadLoginScene();
    }
}

