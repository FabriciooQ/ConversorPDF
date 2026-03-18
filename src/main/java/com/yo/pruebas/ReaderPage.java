package com.yo.pruebas;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

import com.yo.Model.PDFReader;

public class ReaderPage {
    public static void main(String[] args){
        PDFReader reader = new PDFReader("C:\\Users\\alici\\Downloads\\Extracto_Cuentas_Galicia_2023_02_28 (1).pdf");

        String[] page5 = reader.readPage(5);
        String[] page7 = reader.readPage(7);

        Path file = Path.of("prueba.txt").toAbsolutePath();
        System.out.println(file);

        try {
            Files.createFile(file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try{
            Files.write(file, Arrays.asList(page5), StandardCharsets.UTF_8);
            Files.write(file, Arrays.asList(page7), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        }catch(Exception e){
            e.printStackTrace();
        }





        
    }
    
}
