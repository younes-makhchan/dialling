package com.exemple.dialing.presentation;

import com.exemple.dialing.dao.entities.User;
import com.exemple.dialing.presentation.controllers.ChatController;
import com.exemple.dialing.presentation.controllers.UserListController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppNavigator {

    private static Stage stage;
    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
    }
    public static void loadLoginScene() {
        loadScene("/com/exemple/dialing/views/Login.fxml", "Login");
    }
    public static void loadRegisterScene() {
        loadScene("/com/exemple/dialing/views/Register.fxml", "Register");
    }
    public static void loadUserListScene(User authenticatedUser) {
        //MainApplication.getInstance().showUserListScene(authenticatedUser);

        try {
            FXMLLoader loader = new FXMLLoader(AppNavigator.class.getResource("/com/exemple/dialing/views/user-list.fxml"));

            Parent root = loader.load();
            UserListController userListController= loader.getController();
            userListController.setAuthenticatedUser(authenticatedUser);
            stage.setScene(new Scene(root));
            stage.setTitle("userList");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void loadChatScene(User selectedUser) {
        FXMLLoader loader=loadScene("/com/exemple/dialing/views/Chat.fxml", "Chat");
        ChatController chatController= loader.getController();
        chatController.setSelectedUser(selectedUser);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(new Scene(root));
        stage.setTitle("Chat");
        stage.show();
    }
    private static FXMLLoader loadScene(String fxml, String title) {
        FXMLLoader loader=null;
        try {
            loader = new FXMLLoader(AppNavigator.class.getResource(fxml));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loader;
    }
}