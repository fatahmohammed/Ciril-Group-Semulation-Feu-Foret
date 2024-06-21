package com.fatah.semulationfeuenforet.mainRun;

import com.fatah.semulationfeuenforet.frame.ImageDisplayFrame;

import javax.swing.*;

public class main2 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ImageDisplayFrame().setVisible(true);
            }
        });
    }

}
