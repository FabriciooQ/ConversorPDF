package com.yo.Prueba;

import com.yo.Model.PDFReader;

public class Prueba {
    public void run(){
        PDFReader pdfReader = new PDFReader("C:/Users/fabri/OneDrive/Desktop/Enero25.pdf");
        int pages = pdfReader.getNumberOfPages();

        //la lectura arranca en 1 no en 0
         String[] lines = pdfReader.readPage(2);
            for(int j=0; j<lines.length; j++){
                System.out.println(lines[j]);
            }
/* 
        for(int i=1; i<pages+1; i++){
            String[] lines = pdfReader.readPage(i);
            for(int j=0; j<lines.length; j++){
                System.out.println(lines[j]);
            }
        } */

    }


}
