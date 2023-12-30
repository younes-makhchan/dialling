package com.exemple.dialing.presentation.controllers;

import com.exemple.dialing.dao.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ChatController {

    @FXML private TextField messageField;
    @FXML private ListView<String> chatListView;
    private User selectedUser;
    @FXML
    private void handleSend(ActionEvent event) {
        // Implement your message sending logic here
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }
}

