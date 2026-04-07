package com.yo.pruebas;

import java.io.Reader;

import org.apache.pdfbox.pdmodel.PDDocument;

import com.yo.Model.PDFReader;

public class PruebaReader {
    public static void run() {
        PDFReader reader = new PDFReader("/C://Users/alici/Downloads/Enero25.pdf");

        PDDocument pdf = reader.getPDF();
        System.out.println(pdf.getNumberOfPages());

        int numberOfPages = reader.getNumberOfPages();

        for(int i=2;i<3; i++){
            String[] pagina = reader.readPage(i);
            for(String p : pagina){
                System.out.println(p);
            }
        }
    }
    
}
