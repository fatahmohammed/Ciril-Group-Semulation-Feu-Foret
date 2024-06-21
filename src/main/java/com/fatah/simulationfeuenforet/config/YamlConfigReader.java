package com.fatah.simulationfeuenforet.config;

import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;
import java.util.Map;
public class YamlConfigReader {


                            // Pour extraire les données d'un fichier application.yml
          public Map<String, Object> ymlConfig() {
            InputStream inputStream = YamlConfigReader.class.getClassLoader().getResourceAsStream("application.yml");
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(inputStream);
            return data;
        }
}