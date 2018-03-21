package com.chubz.src;

import com.chubz.src.capture.ScreenController;
import com.chubz.src.data.DataGenerator;
import com.chubz.src.data.storage.DataReader;
import com.chubz.src.data.storage.Element;

import javax.xml.crypto.Data;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Program implements Runnable {
    private static final String CONFIG_NAME = "Config.conf";

    public ScreenController screenController;

    private boolean isRunning;
    private Thread captureThread;
    private ArrayList<ConfigListener> configListeners = new ArrayList<>();

    private int selectedDisplay;
    private int sleepTime = 250;

    public Program() {
        screenController = new ScreenController();
    }

    public void addConfigListener(ConfigListener listener) {
        configListeners.add(listener);
    }

    public void loadConfig() throws IOException {
        if (!Files.exists(Paths.get(CONFIG_NAME))) {
            Path path = Files.createFile(Paths.get(CONFIG_NAME));
            LinkedList<String> data = new LinkedList<>(Files.readAllLines(path));
            DataGenerator.addComment(data, "This is the current display that will be used.");
            DataGenerator.addVariable(data, "display", 0);
            DataGenerator.addBreak(data);
            DataGenerator.addComment(data, "This is how long the program will pause before taking another " +
                    "screenshot.");
            DataGenerator.addVariable(data, "sleep_interval", 250);
            Files.write(path, data);
        }

        DataReader dataReader = new DataReader();
        Element[] elements = dataReader.read(Paths.get(CONFIG_NAME));
        String message;
        for (Element element :
                elements) {
            switch (element.name) {
                case "display":
                    message = "Setting selected display...";
                    System.out.println(message);
                    triggerListeners(message);
                    selectedDisplay = Integer.parseInt(element.value);
                    break;
                case "sleep_interval":
                    message = "Setting delay interval...";
                    System.out.println(message);
                    triggerListeners(message);
                    sleepTime = Integer.parseInt(element.value);
                    break;
            }
        }

        screenController.setDisplay(selectedDisplay);
    }

    private void triggerListeners(String message) {
        for (ConfigListener listener : configListeners) {
            listener.onLoad(message);
        }
    }

    public void start() {
        isRunning = true;

        captureThread = new Thread(this, "Capture Thread");
        captureThread.start();
    }

    public void stop() {
        isRunning = false;

        try {
            captureThread.join(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        if (!Files.exists(Paths.get("Frames/"))) {
            try {
                Files.createDirectory(Paths.get("Frames"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        mainLoop();
    }

    private void mainLoop() {
        while (isRunning) {
            try {
                screenController.captureScreen();
                Thread.sleep(sleepTime);
            } catch (InterruptedException | AWTException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
