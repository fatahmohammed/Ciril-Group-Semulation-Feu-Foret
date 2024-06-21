package com.fatah.simulationfeuenforet.image;

import com.fatah.simulationfeuenforet.grille.EtatcaseXY;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public class EtatParImage {
    EtatcaseXY[][] XY;
    int etat;
    public EtatParImage(EtatcaseXY[][] XY, int etat) {
    this.XY=XY;
    this.etat=etat;
    img();
    }
public void img()
    {
    // Dimensions de l'image
        int hauteur = XY.length;
        int largeur = XY[0].length;

        // Créer une image BufferedImage
        BufferedImage image = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_INT_RGB);

         /**
            * Remplir l'image avec les valeurs de la grille :
            * - CENDRE -> GRAY (gris)
            * - EN_FEU -> RED (rouge)
            * - ARBRE -> GREEN (vert)
        */
        for(int i = 0;i<hauteur;i++)
        {
            for (int j = 0; j < largeur; j++)
            {
                Color color = switch (XY[i][j])
                {
                    case CENDRE -> Color.GRAY;
                    case EN_FEU -> Color.RED;
                    case ARBRE -> Color.GREEN;
                };
                    image.setRGB(j, i, color.getRGB());
            }
        }
               File outputFile = new File("target/Etat "+etat+".png");
                try
                {
                    ImageIO.write(image, "png", outputFile);
                } catch (IOException e)
                {
                    throw new RuntimeException(e);
                }


    }

}