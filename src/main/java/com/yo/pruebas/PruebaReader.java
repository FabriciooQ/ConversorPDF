package com.yo.pruebas;

import java.io.Reader;

import com.yo.Model.PDFReader;

public class PruebaReader {
    public static void main(String[] args) {
        PDFReader reader = new PDFReader("C:/Users/alici/Downloads/Extracto_Cuentas_Galicia_2023_01_31.pdf");


        String[] lineas = reader.readPage(9);

        for(int i=0;i<lineas.length; i++){
            System.out.println(i + " - " + lineas[i]);
        }
    }
    
}
