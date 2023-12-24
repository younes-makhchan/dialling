package com.exemple.dialing.presentation;

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

    public static void loadChatScene() {
        loadScene("/com/exemple/dialing/views/Chat.fxml", "Chat");
    }

    private static void loadScene(String fxml, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(AppNavigator.class.getResource(fxml));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}