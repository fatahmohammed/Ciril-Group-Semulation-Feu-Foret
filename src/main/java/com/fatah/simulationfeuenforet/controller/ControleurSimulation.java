package com.fatah.simulationfeuenforet.controller;


import com.fatah.simulationfeuenforet.simulation.SimulationForetEnFeu;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController  // Indique que cette classe est un contrôleur REST
@RequestMapping("/api/simulation")
public class ControleurSimulation {


    private SimulationForetEnFeu simulationFeuForet;
    @PostMapping("/demarrer")
    public String demarrerSimulation(@RequestBody Map<String, Object> configuration) {

        // Extraction des paramètres de configuration depuis la requête JSON hauteur largeur
        int hauteur = (int) configuration.get("hauteur");
        int largeur = (int) configuration.get("largeur");

        // Extraction des paramètres de configuration depuis la requête JSON probabilite
        double probabilite = ((Number) configuration.get("probabilite")).doubleValue();

        // Extraction des paramètres de configuration depuis la requête JSON listes de Integer
        List<List<Integer>> feux = (List<List<Integer>>) configuration.get("feuxInitials");

        // Transformation de la liste de listes de Integer en liste de tableaux d'int (int[])
        List<int[]> feuxInitials = feux.stream()
                .map(feu -> new int[]{feu.get(0), feu.get(1)})
                .collect(Collectors.toList());


        // Initialisation de la simulation avec les paramètres extraits via la request HTTP
        simulationFeuForet = new SimulationForetEnFeu(hauteur, largeur, probabilite, feuxInitials);
        // Exécution de la simulation
        simulationFeuForet.lancerLeFeu();

        // return résultat en chaine de caractère
        return  "le nombre d'etat "+SimulationForetEnFeu.getEtapes()+" le nombre de cendre "+SimulationForetEnFeu.getNombreCendres();

    }
    }
