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

//Singleton
public class TransformationController {
    private Transformer transformer;
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
        
    }

    public void transformToExcel(String pathFile, String pathDestinationFile, Banco banco, boolean flagClasification){
        //TO DO agregar verificacion de rutas con regex
        //System.out.println("LLamando a convertir");
        Transformer.transform(pathFile, pathDestinationFile, banco, flagClasification);
    }




   
}
