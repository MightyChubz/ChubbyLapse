package com.chubz.src;

import com.chubz.src.capture.ScreenController;
import com.chubz.src.data.storage.DataReader;
import com.chubz.src.data.storage.Element;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

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
            Files.createFile(Paths.get(CONFIG_NAME));
            selectedDisplay = 0;
            return;
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
