package com.yo.Model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.yo.Model.interfaces.Extractor;

public class ExtractorForGalicia implements Extractor{
    public String[] extractDataInline(String line){
        //Array de resultados
        String[] res = new String[6];
        //matcher general
        Matcher m;
        
        //Patern que captura con origen
        Pattern patternOrigen = Pattern.compile("^(\\d{2}/\\d{2}/\\d{2})\\s(.*)\\s(\\d{4})\\s([0-9.,-]+)\\s([0-9,.-]+)$");
        //Patern que captura sin origen
        Pattern pattern = Pattern.compile("^(\\d{2}/\\d{2}/\\d{2})\\s(.*)\\s([0-9.,-]+)\\s([0-9,.-]+)$");
  
        //captura
        m = patternOrigen.matcher(line.trim());
        if(m.find()){
            res[0] = m.group(1);    //fecha
            res[1] = m.group(2).trim(); //descripcion
            res[2] = m.group(3);    //origen
            //si el siguiente grupo empieza con - es debito si no saldo
            if(m.group(4).startsWith("-")){
                res[3] = null;  //credito
                res[4] = m.group(4);    //debito
            }else{
                res[3] = m.group(4);
                res[4] = null;
                }
            res[5] = m.group(5).replaceFirst("-$", ""); //saldo
        }else{
            m = pattern.matcher(line.trim());
            if(m.find()){
                 res[0] = m.group(1);    //fecha
                res[1] = m.group(2).trim(); //descripcion
                res[3] = null;    //origen
                //si el siguiente grupo empieza con - es debito si no saldo
                if(m.group(3).startsWith("-")){
                    res[3] = null;  //credito
                    res[4] = m.group(3);    //debito
                }else{
                    res[3] = m.group(3);
                    res[4] = null;
                }
                //si el saldo termina con - lo borramos es un error que trae el pdf
                res[5] = m.group(4).replaceFirst("-$", ""); //saldo
            }
        }

        //retornamos resultado
        return res;


    }
}
