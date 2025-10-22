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

public class Clasificator {
    public Map<Integer,String[]> rules;
    private TxtReader rulesReader;


    public Clasificator(){
        this.rules = new HashMap<>();
        this.rulesReader = new TxtReader();
        rulesReader.loadRules(rules);

    }

    public  Map<Integer, String> classify(Map<Integer,String[]> data){
        Map<Integer, String> res = new HashMap<>();
        data.forEach((k,v) ->{
            for(int i=0; i<rules.size(); i++){
                if(v[1].matches(rules.get(i)[0].trim())){
                    res.put(k, rules .get(i)[1].trim());
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
