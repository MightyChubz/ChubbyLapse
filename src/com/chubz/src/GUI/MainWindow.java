package com.chubz.src.GUI;

import com.chubz.src.Program;
import javax.swing.*;

public class MainWindow {
    private JPanel mainPanel;
    private JButton captureButton;
    private boolean capture;
    private Program program;

    private MainWindow() {
        program = new Program();

        captureButton.addActionListener(e -> {
            capture = !capture;

            if (capture) {
                program.start();
                captureButton.setText("Stop Capture");
            } else {
                program.stop();
                captureButton.setEnabled(false);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }

                captureButton.setEnabled(true);
                captureButton.setText("Start Capture");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainWindow");
        frame.setContentPane(new MainWindow().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
