package com.exemple.dialing.presentation.controllers;

import com.exemple.dialing.dao.UserDaoImpl;
import com.exemple.dialing.dao.entities.User;
import com.exemple.dialing.presentation.AppNavigator;
import com.exemple.dialing.service.IServiceUserImpl;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserListController {

    @FXML private ListView<User> userListView;
    @FXML private Button startChatButton;
    private User authenticatedUser;
    public void setAuthenticatedUser(User authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }
    @FXML
    private void initialize() {
        // Load the list of users from the database and populate the ListView
        Platform.runLater(this::loadUserList);
    }

    private void loadUserList() {
        // Assuming you have a service layer to interact with your database
        IServiceUserImpl userService = new IServiceUserImpl(new UserDaoImpl());
        List<User> users=userService.getAllUsers().stream()
                .filter(user -> !Objects.equals(user.getUsername(), authenticatedUser.getUsername()))
                .toList();
        // Get the list of all users
        ObservableList<User> userList = FXCollections.observableArrayList(users);

        // Populate the ListView with the list of users
        userListView.setItems(userList);
    }

    @FXML
    private void handleStartChat(ActionEvent event) {
        // Get the selected user from the ListView
        User selectedUser = userListView.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            // Start the chat with the selected user
            AppNavigator.loadChatScene( selectedUser);
        } else {
            // No user selected, display an error message or take appropriate action
            System.out.println("Please select a user to start the chat.");
        }
    }
}
