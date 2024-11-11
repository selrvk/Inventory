package com.selrvk.inventory;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.*;

import java.util.Objects;

public class LoginController {

    @FXML
    private Button loginBtn;

    public void login() throws Exception{

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main.FXML")));
        Stage stage = (Stage) loginBtn.getScene().getWindow();
        stage.setTitle("Main Page");
        stage.setScene(new Scene(root));
        stage.show();

    }
}
