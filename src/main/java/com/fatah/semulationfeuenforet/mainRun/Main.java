package com.fatah.semulationfeuenforet.mainRun;


import com.fatah.semulationfeuenforet.config.YamlConfigReader;
import com.fatah.semulationfeuenforet.semulation.SimulationForetEnFeu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    SimulationForetEnFeu s;
    public static void main(String[] args) {
        //System.out.println("Hello world!");
        Map<String, Object> data=new YamlConfigReader().ymlConfig();

        // Extraire les dimensions de la grille
        Map<String, Integer> dimensionsMap = (Map<String, Integer>) data.get("dimensions");
        int hauteur = dimensionsMap.get("hauteur");
        int largeur = dimensionsMap.get("largeur");

        // Extraire les cases initialement en feu
        List<List<Integer>> casesEnFeuList = (List<List<Integer>>) data.get("casesEnFeu");
        List<int[]> casesEnFeu = new ArrayList<>();
        for (List<Integer> caseList : casesEnFeuList) {
            int[] position = {caseList.get(0), caseList.get(1)};
            casesEnFeu.add(position);
        }

        // Extraire la probabilité de propagation
        Map<String, Double> probabiliteMap = (Map<String, Double>) data.get("probabilite");
        double probabilitePropagation = probabiliteMap.get("propagation");

        // lancer la semulation
         new SimulationForetEnFeu(hauteur, largeur, probabilitePropagation, casesEnFeu).lancerLeFeu();

        int etapes = SimulationForetEnFeu.getEtapes();
        int cendres = SimulationForetEnFeu.getNombreCendres();
        System.out.println("le nombre d'etat : " + etapes + " le nombre de Cendre : " + cendres);
        }
    }