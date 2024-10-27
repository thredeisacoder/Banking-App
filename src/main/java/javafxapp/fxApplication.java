package javafxapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class fxApplication extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(fxApplication.class.getResource("login.fxml"));
        Scene loginScene = new Scene(fxmlLoader.load(), 350, 650);
        stage.setTitle("Login");
        stage.setScene(loginScene);
        stage.show();
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}