package com.yo.Controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yo.Model.Banco;
import com.yo.Model.DatabaseAdministrator;
import com.yo.Model.Rule;
import com.yo.Model.Transformer;
import com.yo.Model.TxtReader;

//Singleton
public class TransformationController {
    private Transformer transformer;
    private TxtReader rulesReader;
    private DatabaseAdministrator dbAdmin;
    private static TransformationController transformationController = null;

    public static TransformationController getTransformationController(){
        if(transformationController != null){
            return transformationController;
        }else{
            transformationController = new TransformationController();
            return transformationController;
        }
    }
    
    private TransformationController(){
        this.rulesReader = new TxtReader();
        this.dbAdmin = new DatabaseAdministrator();
    }

    public void transformToExcel(String pathFile, String pathDestinationFile, boolean flagClasification){
        //TO DO agregar verificacion de rutas con regex
        //System.out.println("LLamando a convertir");
        Transformer.transform(pathFile, pathDestinationFile, flagClasification);
    }

    public String[] getNombresBancos(){
        List<Banco> bancos = getBancos();
        List<String> nombres = new ArrayList<>();

        bancos.forEach(banco -> {
            nombres.add(banco.getNombre());
        });

        return nombres.toArray(new String[0]);
    }

    private List<Banco> getBancos(){
        List<Banco> bancos = dbAdmin.getBancos();
        return bancos;
    }

    public void closeDB(){
        dbAdmin.closeSession();
    }

    public void saveRules(List<String> rules){
        this.rulesReader.saveRules(rules);
    }

    public boolean deleteRule(Rule r){
        return this.dbAdmin.deleteRule(r);
    }

    public List<Rule> getRule(){
        return this.dbAdmin.getRules();
    }

   
}
