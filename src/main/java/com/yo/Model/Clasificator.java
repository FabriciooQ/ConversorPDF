package com.yo.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yo.Controller.DatabaseController;

public class Clasificator {
    private List<OrdenRegla> rules;


    public Clasificator(){
        //sacamos las reglas desde el controller porque aca esta el banco actual seteado
        this.rules = DatabaseController.getDatabaseController().getRulesInOrderPerBank();
    }

    public String[] getArrayOfRule(){
        return this.rules.stream()
            .map(o -> o.getRegla().getTexto())
            .toArray(String[]::new);
    }

    public  Map<Integer, String> classify(Map<Integer,String[]> data){
        Map<Integer, String> res = new HashMap<>();
        data.forEach((k,v) ->{
            for(int i=0; i<rules.size(); i++){
                if(v[1].matches(rules.get(i).getRegla().getRegularExpresion())){
                    res.put(k, rules.get(i).getRegla().getTexto());
                    break;
                }
            }if(!res.containsKey(k)){
                res.put(k, "SIN CLASIFICAR");
            }
        });
        return res;
    }


    

    
}
