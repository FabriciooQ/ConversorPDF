package com.yo.pruebas;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.etsi.uri.x01903.v13.impl.ClaimedRolesListTypeImpl;

import com.yo.Model.Clasificator;

public class clasificacion {
    public static void main(String[] args) {
        Clasificator clasificator = new Clasificator();
        clasificator.rules.forEach((k,v)->{
            System.out.println(k + " - " + v);
        });
        
    }
}
    

