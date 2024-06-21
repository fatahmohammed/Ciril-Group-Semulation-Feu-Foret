package com.fatah.simulationfeuenforet.mainRun;

import com.fatah.simulationfeuenforet.frame.ImageDisplayFrame;

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
