/* package com.yo.pruebas;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.print.attribute.HashAttributeSet;

import com.yo.Model.Extractor;
import com.yo.Model.PDFReader;

public class PruebaContentInLine {
    public static void main(String[] args) {    
       PDFReader reader = new PDFReader("C:/Users/alici/Downloads/Extracto_Cuentas_Galicia_2023_01_31.pdf");
       int cantOfPages = reader.getNumberOfPages();
       int index = 1; 
       
       System.out.println(cantOfPages);

       List<String> lineasEnRenglon = new ArrayList<>();
       List<String> lineasEnRenglonSinFecha = new ArrayList<>();

       while(index <= cantOfPages){
           String[] pagina = reader.readPage(index);
           for(String l : pagina){  
               //System.out.println(l);
                if(l.trim().matches("^\\d\\d/\\d\\d/\\d\\d.*,\\d\\d$")){
                    //System.out.println("si");
                    lineasEnRenglon.add(l);
                    lineasEnRenglonSinFecha.add(l.replaceFirst("\\d\\d/\\d\\d/\\d\\d ", "").trim());
                }
           }
           index++;
       }


       System.out.println("Tamaño lineas originales: " + lineasEnRenglon.size());
       Map<Integer,String[]> data = new HashMap<>();

       for(int i=0; i<lineasEnRenglon.size(); i++){
        data.put(i, Extractor.extractDataInline(lineasEnRenglon.get(i).trim()));

       }
       
       data.forEach((k,v) -> {
        System.out.println(k + " - " + Arrays.toString(v));
       });





  
    }
    
}
 */