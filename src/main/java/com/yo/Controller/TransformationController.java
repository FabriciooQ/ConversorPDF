package com.yo.Controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import com.yo.Model.Transformer;
import com.yo.Model.TxtReader;

public class TransformationController {
    private Transformer transformer;
    private TxtReader rulesReader;

    public TransformationController(){
        this.rulesReader = new TxtReader();
    }

    public void transformToExcel(String pathFile, String pathDestinationFile, boolean flagClasification){
        //TO DO agregar verificacion de rutas con regex
        //System.out.println("LLamando a convertir");
        Transformer.transform(pathFile, pathDestinationFile, flagClasification);
    }

    public void saveRules(List<String> rules){
        this.rulesReader.saveRules(rules);
    }

    public void loadRules(Map<Integer,String[]> map){
        this.rulesReader.loadRules(map);
    }

   
}
