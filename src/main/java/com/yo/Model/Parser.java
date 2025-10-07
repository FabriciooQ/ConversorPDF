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
        int index = 4;
        //porque sino se confunde con los renglones que empiezan con fecha en el header
        if(firstPage){
            index=31;
        }
        //pattern, matcher y cosas para multilinea, no se usa Extractor porque hay que iterar sobre muchos renglones
        Pattern pDateMultilane = Pattern.compile("^(\\d\\d/\\d\\d/\\d\\d)\\s(.*)");
        Matcher m;
        String description;

        while(index < lineas.length){
            //data del renglon de la tabla
            String[] dataLine = new String[6]; 
            //if para saber si todo esta en un mismo renglon
            if(lineas[index].trim().matches("^\\d\\d/\\d\\d/\\d\\d.*") && 
                (lineas[index].trim().matches(".*,\\d\\d$") || lineas[index].trim().matches(".*,\\d\\d-$"))){
                dataLine = Extractor.extractDataInline(lineas[index]);

                //agregamos datos al map con la llave del numero de fila
                data.put(contRows, dataLine);
                contRows++;
                //sumamos index para ver la proxima fila
                index++;
            //si empieza con fecha pero no termina con numero tiene descripcion larga
            }else if(lineas[index].trim().matches("^\\d\\d/\\d\\d/\\d\\d .*")){
                description = "";
                m = pDateMultilane.matcher(lineas[index]);
                if(m.find()){
                    dataLine[0] = m.group(1);
                    description += m.group(2);
                    index++;
                    while(index<lineas.length){
                        if(lineas[index].trim().matches(".*\\d,\\d\\d$") || lineas[index].trim().matches(".*\\d,\\d\\d-$")){
                            break;
                        }else{
                            description += " " + lineas[index];
                        }
                        index++;
                    }
                    dataLine[1] = description;
                    String[] remainingData = lineas[index].trim().split("\\s+");
                    if(remainingData.length == 3){
                        dataLine[2] = remainingData[0];
                        if(remainingData[1].startsWith("-")){
                            dataLine[4] = remainingData[1];
                        }else{
                            dataLine[3] = remainingData[1];
                        }
                        dataLine[5] = remainingData[2];   
                    }else{
                        if(remainingData[0].startsWith("-")){
                            dataLine[4] = remainingData[0];
                        }else{
                            dataLine[3] = remainingData[0];
                        }
                        dataLine[5] = remainingData[1];
                    }
                    data.put(contRows, dataLine);
                    contRows++;
                    index++;  
                }
            }else{
                //si no entra por los otros 2 llegamos al final de la tabla y salimos
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
        String account = lines[1].split(" ")[1];
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
