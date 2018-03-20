package com.chubz.src.capture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class ScreenController {
    private GraphicsDevice currentDevice;
    private int imageFrame;

    public ScreenController(int display) {
        currentDevice = GraphicsDeviceController.getDisplay(display);
    }

    public void captureScreen() throws AWTException, IOException {
        Rectangle screenRect = currentDevice.getDefaultConfiguration().getBounds();
        BufferedImage currentCapture = new Robot().createScreenCapture(screenRect);
        String pathname = String.format("Frames/frame%06d.png", imageFrame);
        if (ImageIO.write(currentCapture, "png", new File(pathname))) {
            System.out.println("Capture: " + pathname + " at " + LocalDate.now());
        }

        imageFrame++;
    }
}
