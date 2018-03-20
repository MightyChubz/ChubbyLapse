package com.chubz.src.gui;

import com.chubz.src.Program;

import javax.swing.*;

public class CaptureWindow {
    private JButton captureButton;
    private JTextArea logArea;
    private JPanel mainPanel;
    private JScrollPane scrollView;
    private boolean capture;
    private Program program;

    public CaptureWindow() {
        program = new Program();
        program.screenController.addOnWriteListener((filename, date) -> {
            logArea.append("Capture: " + filename + " at " + date + "\r\n");
        });

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

        scrollView.getVerticalScrollBar().addAdjustmentListener(e -> {
            JScrollBar verticalScrollBar = scrollView.getVerticalScrollBar();
            verticalScrollBar.setValue(verticalScrollBar.getMaximum());
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("CaptureWindow");
        frame.setContentPane(new CaptureWindow().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
