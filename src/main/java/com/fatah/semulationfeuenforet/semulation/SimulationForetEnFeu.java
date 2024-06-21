package com.fatah.semulationfeuenforet.semulation;

import com.fatah.semulationfeuenforet.grille.GrilleXY;

import java.util.List;

public class SimulationForetEnFeu {
    private static GrilleXY grille;
    private static int etat;
    int hauteur;
    int largeur;
    public SimulationForetEnFeu(int hauteur, int largeur, double probabilite, List<int[]> feuxInitials) {

        this.largeur=largeur;
        this.hauteur=hauteur;

        grille = new GrilleXY(hauteur, largeur, probabilite);

        for (int[] feu : feuxInitials) {
            grille.etatInitial(feu[0],feu[1]);
        }

        etat = 0;
        grille.affich(etat);
    }
    public void lancerLeFeu() {
       while (grille.caseEnFeu())
       {
          grille.etape(grille.listPoint());
          etat++;
          grille.affich(etat);
       }
    }
    public static int getEtapes() {
        return etat;
    }

    public static int getNombreCendres() {
        return grille.compterCendres();
    }
}
