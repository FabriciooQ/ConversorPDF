package com.yo.Model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private int jumps;

    public Parser(){
        this.jumps = 0;
    }

    //cabecera comienza en el indice 29+jumps
    public Map<String, String> parseTableHeader(String[] lines){
        Map<String, String> data = new HashMap<>();
        String[] tableHeader = lines[29+jumps].trim().split("\\s+");
        for(String s:tableHeader){
            data.put(s, s);
        }
        return data;
    }
    
    public Map<Integer, String[]> parseContentTable(String[] lineas, boolean firstPage){
        Map<Integer, String[]> data = new HashMap<>();
        int contRows = 0;
        int index = 55;
        //porque sino se confunde con los renglones que empiezan con fecha en el header
        if(firstPage){
            index=41;
        }
        while(index < lineas.length){
            //data del renglon de la tabla
            String[] dataLine = new String[6];
          
            dataLine = Extractor.extractDataInline(lineas[index]);
            if(dataLine != null){
                //agregamos datos al map con la llave del numero de fila
                data.put(contRows, dataLine);
                contRows++;
                //sumamos index para ver la proxima fila
                index++;
            }else{
                //si el extractor devuelve null no coincidio con ninguna fila y se acabo la tabla en la pagina
                break;
            }
           
        } 
        return data;
    }
        
    


    public Map<String, String> parseHeader(String[] lines){
        Map<String, String> data = new HashMap<>();
       
        //cuit y iva
        String[] wordsCuit = lines[0].split("    ");
        String cuit = wordsCuit[0].split(" ")[1];
        String iva = wordsCuit[1].split(" ")[1] + " " + wordsCuit[1].split(" ")[2];
        data.put("cuit", cuit);
        data.put("iva", iva);

        //numero de cuenta
        String[] arrays = lines[1].split(" ");
        String account = "";
        for(int i=1;i<arrays.length;i++){
            account += arrays[i];
        }
        data.put("cuenta", account);

        //lugar(puede estar en 2 renglones)
        String lugar = null;
        if(!lines[3].startsWith("Resumen")){
            lugar = lines[2].trim()+ lines[3].trim();
            jumps++;
        }else{
            lugar = lines[2];
        }
        data.put("lugar", lugar);
        
        //agregamos titulo
        data.put("titulo", lines[3+jumps]);

        //tipo de cuenta
        String tipo = lines[10+jumps];
        data.put("tipo de cuenta", tipo);

        //periodos de movimiento
        String periodo = lines[15+jumps];
        String inicioPeriodo = periodo.substring(10, 20);
        String finPeriodo = periodo.substring(0, 10);
        data.put("inicio de periodo", inicioPeriodo);
        data.put("fin de periodo", finPeriodo);    
        
        //saldos
        String saldoInicial = lines[18+jumps];
        String saldoFinal = lines[17+jumps];
        data.put("saldo inicial", saldoInicial);
        data.put("saldo final", saldoFinal);
        
        return data;

    }
}
