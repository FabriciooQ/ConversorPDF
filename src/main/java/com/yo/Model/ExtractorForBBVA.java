package com.yo.Model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yo.Model.interfaces.Extractor;

public class ExtractorForBBVA implements Extractor{
    public String[] extractDataInline(String line){
        //Array de resultados
        String[] res = new String[6];
        //matcher general
        Matcher m;
        
        //Patern fecha
        Pattern patternSinOrigen = Pattern.compile("^(\\d{2}/\\d{2})\\s\\s(.*)\\s(.+)\\s(.+)$");
        //Patern que captura con origen
        Pattern patternOrigen = Pattern.compile("^(\\d{2}/\\d{2})\\s([A-Z]\\s?\\d*)\\s(.*)\\s(.+)\\s(.+)$");
        //captura
        m = patternOrigen.matcher(line.trim());
        if(m.find()){
            res[0] = m.group(1);    //fecha
            res[1] = m.group(3).trim(); //descripcion
            res[2] = m.group(2);    //origen
            //si el siguiente grupo empieza con - es debito si no saldo
            if(m.group(4).startsWith("-")){
                res[3] = null;  //credito
                res[4] = m.group(4);    //debito
            }else{
                res[3] = m.group(4);
                res[4] = null;
            }
            res[5] = m.group(5); //saldo
        }else{
            m = patternSinOrigen.matcher(line.trim());
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
                res[5] = m.group(4); //saldo
            }else{
                //si no coincide con ningun patron signfica que estamos en el final
                return null;
            }
        }

        //retornamos resultado
        return res;


    }
}