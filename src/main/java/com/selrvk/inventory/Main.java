package com.selrvk.inventory;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("Main.FXML"));
        stage.setTitle("Inventory");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args){

        launch(args);

    }
}
