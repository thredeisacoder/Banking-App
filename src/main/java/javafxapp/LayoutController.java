package javafxapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class LayoutController {
    @FXML
    private Label logOutBtn;
    @FXML
    private Label accountBtn;

    public void redirectToLogin(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(fxApplication.class.getResource("login.fxml"));
        Scene loginScene = new Scene(fxmlLoader.load(),350,650);
        stage.setScene(loginScene);
        stage.setTitle("Login");
        stage.show();
    }
    @FXML
    protected void handleLogOut() throws IOException {
        Stage stage = (Stage) this.logOutBtn.getScene().getWindow();
        redirectToLogin(stage);
    }
}