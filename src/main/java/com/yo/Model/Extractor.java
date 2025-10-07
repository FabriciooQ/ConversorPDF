package com.yo.Model;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Extractor {
    public static String[] extractDataInline(String line){
        //Array de resultados
        String[] res = new String[6];
        //matcher general
        Matcher m;
       
        //FECHA Y DESCRIPCION  (FECHA GRUPO 1, DESCRIPCION GRUPO 2))
        //Pattern para cadenas tipo: 02/01/23 ECHEQ 48 HS. NRO.      234 000 (si tiene origen)
        Pattern p1Descr = Pattern.compile("^(\\d\\d/\\d\\d/\\d\\d)\\s(.*\\s{2,}\\d+)");
        //Pattern para cadenas tipo: 02/01/23 IMP. DEB. LEY 2513 REDUC. 000 (si tiene origen)
        Pattern p2Descr = Pattern.compile("^(\\d\\d/\\d\\d/\\d\\d)\\s([A-Z .]+\\s[0-9]+\\s[A-Z.]+)");
        //Pattern para cadenas tipo IMP. CRE. LEY 25413 REDUC. 000 (si tiene origen)
        Pattern p3Descr = Pattern.compile("^(\\d\\d/\\d\\d/\\d\\d)\\s([A-Z. ]+)");
        //captura
        m = p1Descr.matcher(line.trim());
        if(m.find()){
            res[0] = m.group(1);
            res[1] = m.group(2).trim();
        }else{
            m = p2Descr.matcher(line.trim());
            if(m.find()){
                res[0] = m.group(1);
                res[1] = m.group(2).trim();
            }else{
                m = p3Descr.matcher(line.trim());
                if(m.find()){
                    res[0] = m.group(1);
                    res[1] = m.group(2).trim();
                }
            }
        }

        //ORIGEN
        //Pattern para cadenas tipo 02/01/23 ECHEQ 48 HS. NRO.      235 000 (origen es 3er grupo)
        Pattern p1Origin = Pattern.compile("^(\\d\\d/\\d\\d/\\d\\d)\\s(.*\\s{2,}\\d+)\\s(\\d+)");
        //Pattern para cadenas tipo IMP. DEB. LEY 25413 REDUC. 000 (origen en 3er grupo)
        Pattern p2Origin = Pattern.compile("^(\\d\\d/\\d\\d/\\d\\d)\\s([A-Z .]+\\s[0-9]+\\s[A-Z.]+)\\s(\\d+)");
        //Pattern para cadenas tipo IMP. CRE. LEY 25413 REDUC. 000 (origen en 3er grupo)
        Pattern p3Origin = Pattern.compile("^(\\d\\d/\\d\\d/\\d\\d)\\s([A-Z. ]+)\\s(\\d+)\\s[0-9-]");
        //captura
        m = p1Origin.matcher(line.trim());
        if(m.find()){
            res[2] = m.group(3);
        }else{
            m = p2Origin.matcher(line.trim());
            if(m.find()){
                res[2] = m.group(3);
            }else{
                m = p3Origin.matcher(line.trim());
                if(m.find()){
                    res[3] = m.group(3);
                }
            }
        }

        //SALDO Y DEBITO/CREDITO
        //Pattern debito/credito grupo 1, saldo grupo 2
        Pattern p1Balance = Pattern.compile("([0-9.,-]+)\\s([0-9,.]+)$");
        //capturar
        m = p1Balance.matcher(line.trim());
        if(m.find()){
            //System.out.println(m.groupCount());
            String creditOrDebit = m.group(1);
            if(creditOrDebit.startsWith("-")){
                res[4] = m.group(1);
            }else{
                res[3] = m.group(2);
            }
            res[5] = m.group(2);
        }  

        //retornamos resultado
        return res;


    }
}
