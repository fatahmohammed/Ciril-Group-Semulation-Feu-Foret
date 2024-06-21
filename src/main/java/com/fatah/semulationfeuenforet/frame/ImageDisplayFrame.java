package com.fatah.semulationfeuenforet.frame;

import com.fatah.semulationfeuenforet.semulation.SimulationForetEnFeu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ImageDisplayFrame extends JFrame {
    private JPanel imagePanel;
    private JLabel imageLabel;
    private JScrollPane scrollPane;
    private JButton nextButton;
    private JButton prevButton;
    private JButton simulateButton;
    private JTextField heightField;
    private JTextField widthField;
    private JTextField probabilityField;
    private JTextField initialFireField;
    private JLabel textLabel1;
    private JLabel textLabel2;
    private JLabel textLabel3;
    private int currentImageIndex = 0;
    private String[] imagePaths;
    int etat;

    public ImageDisplayFrame() {
        setTitle("Image Display and Simulation");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        imagePaths = new String[]{ "images/Etat 0.png" };

        imagePanel = new JPanel();
        imageLabel = new JLabel();
        scrollPane = new JScrollPane(imageLabel);

        nextButton = new JButton("Next"); nextButton.setEnabled(false);
        prevButton = new JButton("Previous");prevButton.setEnabled(false);
        simulateButton = new JButton("Lancer la simulation");

        heightField = new JTextField(5);
        widthField = new JTextField(5);
        probabilityField = new JTextField(5);
        initialFireField = new JTextField(20);

        textLabel1 = new JLabel("Text1");
        textLabel2 = new JLabel("Text2");
        textLabel3=  new JLabel("");

        imageLabel.setPreferredSize(new Dimension(800, 600));
        imagePanel.add(scrollPane);

        add(imagePanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(8, 2));
        controlPanel.add(new JLabel("Hauteur:"));
        controlPanel.add(heightField);
        controlPanel.add(new JLabel("Largeur:"));
        controlPanel.add(widthField);
        controlPanel.add(new JLabel("Probabilité:"));
        controlPanel.add(probabilityField);
        controlPanel.add(new JLabel("Feux Initiaux (ex: 4,4;5,3;8,4):"));
        controlPanel.add(initialFireField);
        controlPanel.add(textLabel3);
        controlPanel.add(simulateButton);
        controlPanel.add(textLabel1);
        controlPanel.add(textLabel2);


        controlPanel.add(prevButton);
        controlPanel.add(nextButton);
        controlPanel.add(new JLabel());


        add(controlPanel, BorderLayout.SOUTH);

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentImageIndex = (currentImageIndex + 1) % imagePaths.length;
                loadImage(currentImageIndex);
                textLabel3.setText("Etat N° :"+currentImageIndex);
            }
        });

        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentImageIndex = (currentImageIndex - 1 + imagePaths.length) % imagePaths.length;
                loadImage(currentImageIndex);
                textLabel3.setText("Etat N° :"+currentImageIndex);
            }
        });

        simulateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lancerSimulation();
            }
        });

        loadImage(currentImageIndex);
    }

    private void loadImage(int index) {
        if (imagePanel.getWidth() == 0 || imagePanel.getHeight() == 0) {
            return;
        }
        ImageIcon icon = new ImageIcon(imagePaths[index]);
        Image image = icon.getImage();
        int panelWidth = imagePanel.getWidth();
        int panelHeight = imagePanel.getHeight();
        int imageWidth = icon.getIconWidth();
        int imageHeight = icon.getIconHeight();
        double aspectRatio = (double) imageWidth / imageHeight;
        int newWidth;
        int newHeight;
        if (panelWidth / aspectRatio <= panelHeight) {
            newWidth = panelWidth;
            newHeight = (int) (panelWidth / aspectRatio);
        } else {
            newHeight = panelHeight;
            newWidth = (int) (panelHeight * aspectRatio);
        }
        Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));
        imageLabel.revalidate();
        imageLabel.repaint();
    }

    private void lancerSimulation() {
        try {
            int hauteur = Integer.parseInt(heightField.getText());
            int largeur = Integer.parseInt(widthField.getText());
            double probabilite = Double.parseDouble(probabilityField.getText());
            String[] initialFeuxStr = initialFireField.getText().split(";");
            List<int[]> feuxInitials = new ArrayList<>();
            for (String feuStr : initialFeuxStr) {
                String[] coords = feuStr.split(",");
                int x = Integer.parseInt(coords[0].trim());
                int y = Integer.parseInt(coords[1].trim());
                feuxInitials.add(new int[]{x, y});
            }

            new SimulationForetEnFeu(hauteur, largeur, probabilite, feuxInitials).lancerLeFeu();
            nextButton.setEnabled(true);
            prevButton.setEnabled(true);
            etat = SimulationForetEnFeu.getEtapes();
            imagePaths = new String[etat + 1];
            for (int i = 0; i <= etat; i++) {
                imagePaths[i] = "target/Etat " + i + ".png";
            }
            loadImage(0);
            textLabel1.setText("Nombre d'etat: "+etat );
            textLabel2.setText("Nombre de Case Brulé (CENDRE): "+SimulationForetEnFeu.getNombreCendres());

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer des valeurs valides.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
