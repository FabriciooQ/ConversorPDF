package com.yo.pruebas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yo.Model.PDFReader;

public class PruebaContentMultiLine {

    public static void main(String[] args) {
       PDFReader reader = new PDFReader("C:/Users/alici/Downloads/Extracto_Cuentas_Galicia_2023_01_31.pdf");
       int cantOfPages = 5;
       int index = 1; 

       List<String> lineasMultiples = new ArrayList<>();
    
       while(index < cantOfPages){
            String[] lines = reader.readPage(index);
            for(String s : lines){
                if(!(s.trim().matches("^\\d\\d/\\d\\d/\\d\\d.*") && s.trim().matches(".*,\\d\\d$"))){
                    lineasMultiples.add(s);
                }
            }
            index++;
        }
        //imprimimos multilineas
/*         for(String s: lineasMultiples){
            System.out.println(s);
        } */
        //Data final 
        Map<Integer, String[]> data = new HashMap<>();

        //convertimos a array
        Iterator<String> iterator = lineasMultiples.iterator();
        for(int i = 0; i<32; i++){
            iterator.next();
        }
        //String renglon de data
        String[] dataLine = new String[6];

        //EXTRACCION 
        Integer row = 0;
        //FECHA Y PRIMERA PARTE DE DESCRIPCION
        Pattern pDateMultilane = Pattern.compile("^(\\d\\d/\\d\\d/\\d\\d)\\s(.*)");
        Matcher m;

        String descripcionCompleta = "";
        String renglon;
        while (iterator.hasNext()){
            renglon = iterator.next().trim();
            //System.out.println(renglon);
            if(renglon.trim().matches("^\\d\\d/\\d\\d/\\d\\d.*")){
                m = pDateMultilane.matcher(renglon);
                if(m.find()){
                    dataLine[0] = m.group(1);
                    descripcionCompleta += m.group(2);
                    while(iterator.hasNext()){ 
                        renglon = iterator.next().trim();
                        if(renglon.matches(".*\\d,\\d\\d$")){
                            break;
                        }else{
                            descripcionCompleta += " " + renglon;
                            System.out.println(renglon);
                        }
                    }
                    
                }
                dataLine[1] = descripcionCompleta;
                String[] separacion = renglon.trim().split("\\s+");
                System.out.println(renglon + " -- " +Arrays.toString(separacion));
                if(separacion.length == 3){
                    dataLine[2] = separacion[0];
                    if(separacion[1].startsWith("-")){
                        dataLine[4] = separacion[1];
                    }else{
                        dataLine[3] = separacion[1];
                    }
                    dataLine[5] = separacion[2];   
                }else{
                    if(separacion[0].startsWith("-")){
                        dataLine[4] = separacion[0];
                    }else{
                        dataLine[3] = separacion[0];
                    }
                    dataLine[5] = separacion[1];
                }
                data.put(row, dataLine);
                dataLine = new String[6];
                descripcionCompleta="";
                row++;

                    
            }
        }

        //imprimimos data
        data.forEach((k,v)->{
            System.out.println(k + " - " + Arrays.toString(v));
        });
        
    }
    



}
