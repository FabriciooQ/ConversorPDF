package com.yo.pruebas;

import java.awt.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.yo.Model.TxtReader;

public class peueba {
    public static void main(String[] args) {
        TxtReader r = new TxtReader();
        Map<Integer, String[]> rules = new HashMap<>();
        r.loadRules(rules);

        rules.forEach((k,v) ->{
            System.out.println(Arrays.toString(v));
            if(v[0].endsWith("$")){
                System.out.println(v[0].replace(".*","").replace("$", ""));
            }
        
        
        
        });

    }
    
}
