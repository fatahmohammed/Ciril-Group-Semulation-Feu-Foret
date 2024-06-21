package com.fatah.simulationfeuenforet.json;

import com.fatah.simulationfeuenforet.grille.EtatcaseXY;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
public class EtatParJson {
    EtatcaseXY[][] XY;
    int etat;
    public EtatParJson(EtatcaseXY[][] XY, int etat) {
        this.XY=XY;
        this.etat=etat;
        jsonFile();
    }
    public void jsonFile() {
        JSONArray jsonArray = new JSONArray();

        for (int i=0;i<XY.length ;i++) {
            JSONArray rowArray = new JSONArray();
            for (int j=0;j<XY[0].length;j++) {
                rowArray.put(XY[i][j]);
            }
            jsonArray.put(rowArray);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Etat "+etat, jsonArray);

        try (FileWriter fileWriter = new FileWriter("target/Etat "+etat+".json"))
        {
            fileWriter.write(jsonObject.toString(4));
        } catch (IOException e)
        {
            e.printStackTrace();

        }
    }
}