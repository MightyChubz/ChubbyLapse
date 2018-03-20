package com.chubz.src.capture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ScreenController {
    private GraphicsDevice currentDevice;
    private int imageFrame;
    private ArrayList<ImageWriteListener> onWriteListener = new ArrayList<>();

    public ScreenController(int display) {
        currentDevice = GraphicsDeviceController.getDisplay(display);
    }

    public void addOnWriteListener(ImageWriteListener listener) {
        onWriteListener.add(listener);
    }

    public void captureScreen() throws AWTException, IOException {
        Rectangle screenRect = currentDevice.getDefaultConfiguration().getBounds();
        BufferedImage currentCapture = new Robot().createScreenCapture(screenRect);
        String pathname = String.format("Frames/frame%06d.png", imageFrame);
        if (ImageIO.write(currentCapture, "png", new File(pathname))) {
            System.out.println("Capture: " + pathname + " at " + LocalDate.now());
            for (ImageWriteListener listener : onWriteListener) {
                listener.onWrite(pathname, LocalDate.now().toString());
            }
        }

        imageFrame++;
    }
}
