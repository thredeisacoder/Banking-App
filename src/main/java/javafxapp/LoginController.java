package javafxapp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class LoginController {
    @FXML
    private Label title;
    @FXML
    private Button loginBtn;
    @FXML
    private TextField usernameTxt;
    @FXML
    private TextField passwordTxt;
    @FXML
    private ImageView imageView;

    public void changeToHome(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(fxApplication.class.getResource("layout.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),350,650);
        stage.setScene(scene);
        stage.setTitle("HOME");
        stage.show();
    }
    @FXML
    public void handleLogin() throws IOException {
        Stage stage = (Stage) loginBtn.getScene().getWindow();
        changeToHome(stage);
    }

}
