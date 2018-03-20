package com.chubz.src;

import com.chubz.src.capture.ScreenController;
import com.chubz.src.data.storage.DataReader;
import com.chubz.src.data.storage.Element;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Program implements Runnable {
    public ScreenController screenController;

    private boolean isRunning;
    private Thread captureThread;
    private int selectedDisplay;
    private int sleepTime = 250;

    public Program() {
        try {
            loadConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }

        screenController = new ScreenController(selectedDisplay);
    }

    private void loadConfig() throws IOException {
        if (!Files.exists(Paths.get("config.txt"))) {
            Files.createFile(Paths.get("config.txt"));
            selectedDisplay = 0;
            return;
        }

        DataReader dataReader = new DataReader();
        Element[] elements = dataReader.read(Paths.get("config.txt"));
        for (Element element :
                elements) {
            switch (element.name) {
                case "display":
                    System.out.println("Setting selected display...");
                    selectedDisplay = Integer.parseInt(element.value);
                    break;
                case "sleep_interval":
                    System.out.println("Setting delay interval...");
                    sleepTime = Integer.parseInt(element.value);
                    break;
            }
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
