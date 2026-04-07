package com.yo.Model;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yo.Model.interfaces.Extractor;
import com.yo.Model.interfaces.Parser;

public class ParserForBBVA implements Parser{
    private int jumps;
    private Extractor extractor;

    public ParserForBBVA(Extractor extractor){
        this.extractor = extractor;
        this.jumps = 0;
    }

    //cabecera comienza en el indice 29+jumps
    public Map<String, String> parseTableHeader(String[] lines){
        Map<String, String> data = new HashMap<>();
        data.put("Fecha", "Fecha");
        data.put("Descripción", "Descripción");
        data.put("Origen", "Origen");
        data.put("Crédito", "Crédito");
        data.put("Débito", "Débito");
        data.put("Saldo", "Saldo");
        return data;
    }
    
    public Map<Integer, String[]> parseContentTable(String[] lineas, boolean firstPage){
        Map<Integer, String[]> data = new HashMap<>();
        int contRows = 0;
        int index=55;
        if(firstPage){
            index = jumps;
        }
        System.out.println("Primera linea convertida:" + lineas[index]);

        while(index < lineas.length){
            //data del renglon de la tabla
            String[] dataLine = new String[6];

            dataLine = this.extractor.extractDataInline(lineas[index]);
            
            if(dataLine == null){
                break;
            }
            
            //agregamos datos al map con la llave del numero de fila
            data.put(contRows, dataLine);
            contRows+=1;
            //sumamos index para ver la proxima fila
            index+=1;
           
        } 
        System.out.println("Ultima linea convertida:" + lineas[index-1]);

        return data;
    }
        
    


    public Map<String, String> parseHeader(String[] lines){
        Map<String, String> data = new HashMap<>();

        Pattern pTipoCuenta = Pattern.compile("(Cuenta.*)\\nCONSOLIDADO");
        Pattern pLugarCuit = Pattern.compile("(.*)\\s\\((\\d{2}-\\d{8,9}-\\d{1})\\)\\R");
        Pattern pCuentaIva = Pattern.compile("Movimientos en cuenta.*\\R(.*) - (.*)\\R");
        Pattern pSaldoInicial = Pattern.compile("([0-9.,]+)$");

        String aux = "";
        for (int i=0; i<lines.length; i++){
            if(lines[i].startsWith("SALDO ANTERIOR")){
                jumps = i+1;
                aux += lines[i].trim();
                break;
            }else{
                aux += lines[i].trim() + "\n";
            }
        }

        System.out.println(aux);

        Matcher m = pTipoCuenta.matcher(aux);
        if(m.find()){
            data.put("tipo de cuenta", m.group(1));
        }else{
            data.put("tipo de cuenta", "NULL");
        }
        System.out.println("Tipo cuenta listo");
        m.reset();
        m = pLugarCuit.matcher(aux);
        if(m.find()){
            data.put("lugar", m.group(1));
            data.put("cuit", m.group(2));
        }else{
            data.put("lugar", "NULL");
            data.put("cuit", "NULL");
        }
        System.out.println("Lugar listo");
        m.reset();
        m = pCuentaIva.matcher(aux);
        if(m.find()){
            data.put("cuenta", m.group(1));
            data.put("iva", m.group(2));
        }else{
            data.put("cuenta", "NULL");
            data.put("iva", "NULL");
        }
        System.out.println("cuenta y iva listo");
        m.reset();
        m = pSaldoInicial.matcher(aux);
        if(m.find()){
            data.put("saldo inicial", m.group(1)
            .replace(".", "")
            .replace(",", ".")
            .replace("-", ""));
        }else{
            data.put("saldo inicial", "0.0");
        }
        System.out.println("saldo inicial listo");
        data.put("titulo", "Resumen de Cuenta Corriente en Pesos");
        data.put("inicio de periodo", "NULL");
        data.put("fin de periodo", "NULL");    
        data.put("saldo final", "0.0");

       System.out.println("Return de header listo");
       return data;
    }
}