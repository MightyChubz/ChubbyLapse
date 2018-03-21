package com.chubz.src.gui;

import com.chubz.src.Program;
import com.chubz.src.WrongTypeException;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindow implements Initializable {
    public Button captureButton;
    public TextArea logArea;
    public ScrollPane scrollPane;
    private boolean capture;
    private Program program;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        program = new Program();
        program.addConfigListener(message -> Platform.runLater(() -> log(message)));
        program.screenController.addOnWriteListener((filename, dateTime) -> Platform.runLater(() -> log("Capture: " + filename + " at " + dateTime)));
    }

    public void load() {
        try {
            program.loadConfig();
        } catch (IOException | WrongTypeException e) {
            e.printStackTrace();
        }
    }

    private void log(String logMessage) {
        logArea.appendText(logMessage + "\r\n");
    }

    public void startCaptureOnClick(MouseEvent mouseEvent) {
        capture = !capture;

        if (capture) {
            program.start();
            captureButton.setText("Stop Capture");
        } else {
            program.stop();
            captureButton.setDisable(true);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            captureButton.setDisable(false);
            captureButton.setText("Start Capture");
        }
    }
}
