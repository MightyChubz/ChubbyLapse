package com.chubz.src.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("TestWindow");
        primaryStage.setScene(new Scene(root, 650, 450));
        primaryStage.setMinWidth(650);
        primaryStage.setMinHeight(450);
        primaryStage.show();

        MainWindow window = loader.getController();
        window.load();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
