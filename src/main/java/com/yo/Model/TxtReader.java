package com.yo.Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TxtReader {
    private Path externalFile;

    public TxtReader(){
        this.externalFile = Paths.get(System.getProperty("user.dir"), "reglas_clasificacion.txt");

        if(externalFile == null || !Files.exists(externalFile)){
            try {
                Files.createFile(externalFile);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void saveRules(List<String> rules){
        try{
            Files.write(externalFile, rules, StandardCharsets.UTF_8);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void loadRules(Map<Integer,String[]> rules){
        rules.clear();
        // TO DO comprobar si existe el file
        List<String> lines = null;
        try{
            lines = Files.readAllLines(externalFile);
        }catch(Exception e){
            e.printStackTrace();
        }

        for(int i=0; i<lines.size(); i++){
            String[] cad = lines.get(i).split("-");
            cad[0] = cad[0].replaceAll(" ","").replaceAll("\\p{Cntrl}", "").strip(); // \\p{Cntrl} → cualquier carácter de control (LF, CR, tab, etc.). strip()elimina espacios al inicio y final, incluyendo Unicode.
            cad[1] = cad[1].replaceAll(" ","").replaceAll("\\p{Cntrl}", "").strip();
            rules.put(i, cad);
        }           
    }   
    
}
