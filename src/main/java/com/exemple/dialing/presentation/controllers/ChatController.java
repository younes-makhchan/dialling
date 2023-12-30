package com.exemple.dialing.presentation.controllers;

import com.exemple.dialing.dao.entities.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ChatController {

    @FXML private TextField messageField;
    @FXML private ListView<String> chatListView;
    private User selectedUser,authenticatedUser;
    private ObservableList<String> chatMessages;
    @FXML
    private void initialize() {
        chatMessages = FXCollections.observableArrayList();
        chatListView.setItems(chatMessages);
        listenForMessageUI();
    }
    @FXML
    private void handleSend(ActionEvent event) {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            String messageToSend=selectedUser.getIdUser()+"=>"+message;
            // Implement your message sending logic here
            ClientController.sendMessage(messageToSend);
            sendMessageUI(message);
            // Clear the message field after sending
            messageField.clear();
        }
    }

    public void  listenForMessageUI(){
        ClientController.listenForMessage((String usernameSender,String messageReceived)->{
            if(usernameSender.equals(authenticatedUser.getUsername()))usernameSender="Me";
            String formattedReceivedMessage = usernameSender + " : " + messageReceived;
            Platform.runLater(()->{
                chatMessages.add(formattedReceivedMessage);
            });
        });
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
        ClientController.sendMessage(selectedUser.getIdUser()+"=>$$loadMessages$$");

    }

    public void setAuthenticatedUser(User authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }

    private void sendMessageUI(String message) {
        // Assuming you have a service layer to handle message sending
        String sender = "Me";
        String formattedMessage = sender + ": " + message;
        chatMessages.add(formattedMessage);
    }

}

