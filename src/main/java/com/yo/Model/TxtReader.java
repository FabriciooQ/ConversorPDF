package com.yo.Model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TxtReader {

    public TxtReader(){
    }

    public void saveRules(List<String> rules){
        Path path = null;
        try {
            path = Paths.get(getClass().getResource("/reglas clasificacion.txt").toURI());
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()))) {
            Iterator<String> iterator = rules.iterator();
            while(iterator.hasNext()){
                String s = iterator.next()
                    .replace("\r\n", "")
                    .replace("\n","")
                    .replace("\r","")
                    .trim();
                try {
                    writer.write(s);
                    System.out.println(s);
                    if(iterator.hasNext()){
                        writer.newLine();

                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            writer.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    public void loadRules(Map<Integer,String[]> rules){
        rules.clear();
        // TO DO comprobar si existe el file
        List<String> lines = null;
        try {
            Path path = Paths.get(getClass().getResource("/reglas clasificacion.txt").toURI()); 

            lines = Files.readAllLines(path);
        } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        for(int i=0; i<lines.size(); i++){
            String[] cad = lines.get(i).split("-");
            cad[0] = cad[0]
                .replace("\r\n", "")
                .replace("\n","")
                .replace("\r","")
                .trim();
            cad[1] = cad[1]
                .replace("\r\n", "")
                .replace("\n","")
                .replace("\r","")
                .trim();
            rules.put(i, cad);
        }           
    }   
    
}
