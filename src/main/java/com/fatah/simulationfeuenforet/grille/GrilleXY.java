package com.fatah.simulationfeuenforet.grille;

import com.fatah.simulationfeuenforet.image.EtatParImage;
import com.fatah.simulationfeuenforet.json.EtatParJson;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class GrilleXY {
    private EtatcaseXY[][] grille;
    private int hauteur;
    private int largeur;
    private double probabilite;
    private Random random;
    List<int[]> listeIndicesFeu;
    public GrilleXY(int hauteur, int largeur, double probabilite) {
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.probabilite = probabilite;
        this.grille = new EtatcaseXY[hauteur][largeur];
        this.random = new Random();
        initialiserGrille();
    }

    // Méthode pour initialiser par défaut la grille avec des arbres
    private void initialiserGrille() {
        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                grille[i][j] = EtatcaseXY.ARBRE;
            }
        }
    }

    // Méthode pour initialiser les cases en feu (état initial) dans la grille
    public void etatInitial(int xInitial,int yInitial) {
        if (positionValide(xInitial, yInitial)) {
            grille[xInitial][yInitial] = EtatcaseXY.EN_FEU;
        }
    }

    // Méthode pour vérifier si les coordonnées (x, y) d'une case sont valides et appartiennent à la grille par rapport à sa hauteur et sa largeur
    private boolean positionValide(int xInitial, int yInitial) {
         return xInitial >= 0 && xInitial < hauteur && yInitial >= 0 && yInitial < largeur;
    }

    // Méthode pour enregistrer l'état de la grille sous forme d'une image et un fichier au format JSON
    public void affich(int etat) {
        new EtatParImage(grille,etat);
        new EtatParJson(grille,etat);
    }

    // Méthode pour vérifier si la grille contient encore des cases en feu et retourner un booléen (true ou false)
    public boolean caseEnFeu() {
        for (int i = 0; i < hauteur; i++) {
              for (int j = 0; j < largeur; j++) {
                if(grille[i][j]==EtatcaseXY.EN_FEU)
                {
                    return true;
                }
            }
        }
        return false;
    }

    // Méthode pour parcourir la grille et récupérer les indices des cases en feu, puis les stocker dans une liste
    public List<int[]> listPoint() {
        listeIndicesFeu=new ArrayList<>();
        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                if (grille[i][j] == EtatcaseXY.EN_FEU) {
                    listeIndicesFeu.add(new int[]{i, j});
                }
            }
        }
            return listeIndicesFeu;
    }


                   /*   C'est le cœur de l'algorithme demandé. */

    // La méthode pour chaque état qui suit l'état initial de l'état t à l'état t+1, puis de t+1 à t+2, et ainsi de suite
    // Qui prend en paramètre les indices x et y de les cases en question et qui ont l'état en feu

    public void etape(List<int[]> pointEnFeu) {
            for (int[] indices : pointEnFeu) {
            // pour chaque indice x y Rendre la case en question à l'état cendre
                grille[indices[0]][indices[1]]=EtatcaseXY.CENDRE;
                int x=indices[0];
                int y=indices[1];

                 for (int i = 0; i < 4; i++) {
                   int positionCasesAdjacenteX=x;
                   int positionCasesAdjacenteY=y;

                    if(i==0){
                        positionCasesAdjacenteX=x-1;  // La case adjacente au-dessus de la case en question
                    }else if(i==1)
                    {
                        positionCasesAdjacenteX=x+1; // La case adjacente en dessous de la case en question
                    }else if(i==2)
                    {
                        positionCasesAdjacenteY=y-1; // La case adjacente à gauche de la case en question
                    } else if (i==3)
                    {
                        positionCasesAdjacenteY=y+1; // La case adjacente à droite de la case en question
                    }
                    // Vérifier si cette case adjacente a une position valide dans la grille
                    // Vérifier si cette case adjacente est un arbre et n'est ni cendre ni feu
                    // Vérifier aussi si la probabilité générée aléatoirement pour cette case appartient à l'intervalle de probabilité donné
                     if (    positionValide(positionCasesAdjacenteX, positionCasesAdjacenteY) &&
                            grille[positionCasesAdjacenteX][positionCasesAdjacenteY] == EtatcaseXY.ARBRE &&
                            random.nextDouble() < probabilite)
                    {
                            // Si les trois conditions sont correctes, alors la case adjacente prend l'état en feu
                             grille[positionCasesAdjacenteX][positionCasesAdjacenteY] = EtatcaseXY.EN_FEU;
                    }
                }

            }
    }

    // Méthode pour calculer le nombre de cases cendre dans la grille
    public int compterCendres() {
        int compteur = 0;
        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                if (grille[i][j] == EtatcaseXY.CENDRE) {
                    compteur++;
                }
            }
        }
        return compteur;
    }
}
