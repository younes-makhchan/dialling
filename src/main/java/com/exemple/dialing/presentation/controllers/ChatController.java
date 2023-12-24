package com.exemple.dialing.presentation.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ChatController {

    @FXML private TextField messageField;
    @FXML private ListView<String> chatListView;

    @FXML
    private void handleSend(ActionEvent event) {
        // Implement your message sending logic here
    }
}

