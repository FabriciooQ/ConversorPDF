package com.yo.pruebas;

import java.io.Reader;

import org.apache.pdfbox.pdmodel.PDDocument;

import com.yo.Model.PDFReader;

public class PruebaReader {
    public static void main(String[] args) {
        PDFReader reader = new PDFReader("C:/Users/fabri/OneDrive/Desktop/Enero25.pdf");

        PDDocument pdf = reader.getPDF();
        System.out.println(pdf.getNumberOfPages());

        int numberOfPages = reader.getNumberOfPages();

        for(int i=0;i<numberOfPages; i++){
            String[] pagina = reader.readPage(i);
            for(String p : pagina){
                System.out.println(p);
            }
        }
    }
    
}
