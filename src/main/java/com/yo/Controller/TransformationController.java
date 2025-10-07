package com.yo.Controller;

import com.yo.Model.Transformer;

public class TransformationController {
    Transformer transformer;

    public void transformToExcel(String pathFile, String pathDestinationFile){
        //TO DO agregar verificacion de rutas con regex
        Transformer.transform(pathFile, pathDestinationFile);
        

    }
}
