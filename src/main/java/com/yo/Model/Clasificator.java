package com.yo.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yo.Controller.DatabaseController;

public class Clasificator {
    private List<OrdenRegla> rules;


    public Clasificator(){
        this.rules = DatabaseController.getDatabaseController().getRulesInOrderPerBank();
    }

    public  Map<Integer, String> classify(Map<Integer,String[]> data){
        Map<Integer, String> res = new HashMap<>();
        data.forEach((k,v) ->{
            for(int i=0; i<rules.size(); i++){
                if(v[1].matches(rules.get(i).getRegla().getRegularExpresion())){
                    res.put(k, rules .get(i).getRegla().getTexto());
                    //System.out.println(res.get(k));
                    break;
                }
            }if(!res.containsKey(k)){
                res.put(k, "SIN CLASIFICAR");
            }
        });
        return res;
    }


    

    
}
