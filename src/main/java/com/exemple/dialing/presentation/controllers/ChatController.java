package com.exemple.dialing.presentation.controllers;

import com.exemple.dialing.dao.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ChatController {

    @FXML private TextField messageField;
    @FXML private ListView<String> chatListView;
    private User selectedUser;
    private ObservableList<String> chatMessages;
    @FXML
    private void initialize() {
        chatMessages = FXCollections.observableArrayList();
        chatListView.setItems(chatMessages);
    }
    @FXML
    private void handleSend(ActionEvent event) {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            String messageToSend=selectedUser+"=>"+message;
            // Implement your message sending logic here
            //ClientController.sendMessage(messageToSend);
            sendMessageUI(message);
            // Clear the message field after sending
            messageField.clear();
        }
    }

    public void  listenForMessageUI(){
        ClientController.listenForMessage((String messageReceived)->{
            String receiverUser = selectedUser.getUsername();
            String formattedReceivedMessage = receiverUser + " : " + messageReceived;
            chatMessages.add(formattedReceivedMessage);
        });
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    private void sendMessageUI(String message) {
        // Assuming you have a service layer to handle message sending
        String sender = "Me";
        String formattedMessage = sender + ": " + message;
        chatMessages.add(formattedMessage);
    }
}

