package com.chubz.src.capture;

import java.awt.*;

public class GraphicsDeviceController {
    public static GraphicsDevice getDisplay(int display) {
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] graphicsDevices = graphicsEnvironment.getScreenDevices(); // all the monitors or extended devices.

        if (display > graphicsDevices.length - 1) {
            throw new IndexOutOfBoundsException("The display element is out of bounds!");
        }

        return graphicsDevices[display];
    }
}
