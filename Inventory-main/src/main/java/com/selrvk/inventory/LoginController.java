package com.selrvk.inventory;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class LoginController {

    @FXML
    private Button loginBtn;
    @FXML
    private TextField usernameInput;
    @FXML
    private TextField passwordInput;

    private final Controller controller = new Controller();
    private final DatabaseManager dbManager = new DatabaseManager();

    /*
    void login()
    Passes the string dbURL into class DatabaseManager verifyConnection() to check
    if the input username and password is valid.
    - Calls setUsername() and setPassword() if the inputs provided are valid.
     */
    public void login() throws Exception{
        String dbURL = "jdbc:mysql://192.168.1.2:3306/inventory";
        try(Connection con = dbManager.verifyConnection(dbURL, usernameInput.getText(), passwordInput.getText())){

            setUsername();
            setPassword();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main.FXML")));
            Stage stage = (Stage) loginBtn.getScene().getWindow();
            stage.setTitle("Main Page");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    /*
    void setUsername()
    Gets and passes the input username string into class Controller setUsername().
     */
    public void setUsername(){
        controller.setUsername(usernameInput.getText());
    }

    /*
    void setPassword()
    Gets and passes the input password string into class Controller setPassword().
     */
    public void setPassword(){
        controller.setPassword(passwordInput.getText());
    }
}
