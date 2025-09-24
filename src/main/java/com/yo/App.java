package com.yo;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.Loader;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
            File file = new File("C:/Users/fabri/Downloads/Extracto_Cuentas_Galicia_2023_01_31.pdf");
            System.out.println(file.exists());
            if(file.exists()){  
                //cargamos el pdf
                PDDocument pdf = Loader.loadPDF(file);
                //obtenemos la cantidad de hojas
                int quantityOfPages = pdf.getNumberOfPages();
                //creamos el objeto para leer el pdf
                PDFTextStripper textStripper = new PDFTextStripper();
               
                //seteamos hasta que hoja se va a leer 
                textStripper.setEndPage(1);

                for(int i = 0; i<)

                //leemos
                String textFirstPage = textStripper.getText(pdf);

                //obtenemos los datos de cabecera
                Map<String,String> headerMap = getDataHeader(textFirstPage);
                headerMap.forEach((k,d) ->{System.out.println(k +": " +  d);});

                
              //  String texto = textStripper.getText(pdf);
               // System.out.println(texto);
                
                pdf.close();


            }
          
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static Map<String, String> getDataHeader(String page){
        Map<String, String> data = new HashMap<>();
        //cuando un dato ocupa mas de un renglon me mueve los demas para abajo, por eso le tengo que sumar el salto
        int jumps = 0;
        
        //dividimos los renglones de la pagina
        String[] lines = page.split("\n");
       
        //cuit y iva
        String[] wordsCuit = lines[0].split("    ");
        System.out.println(wordsCuit.length);
        String cuit = wordsCuit[0].split(" ")[1];
        String iva = wordsCuit[1].split(" ")[1];
        data.put("cuit", cuit);
        data.put("iva", iva);

        //numero de cuenta
        String account = lines[1].split(" ")[1];
        data.put("cuenta", account);

        //lugar(puede estar en 2 renglones)
        String lugar = null;
        if(!lines[3].startsWith("Resumen")){
            lugar = lines[2].trim()+ lines[3].trim();
            jumps++;
        }else{
            lugar = lines[2];
        }
        data.put("lugar", lugar);
        
        //agregamos titulo
        data.put("titulo", lines[3+jumps]);

        //tipo de cuenta
        String tipo = lines[10+jumps];
        data.put("tipo de cuenta", tipo);

        //periodos de movimiento
        String periodo = lines[15+jumps];
        String inicioPeriodo = periodo.substring(10, 19);
        String finPeriodo = periodo.substring(0, 9);
        data.put("inicio de periodo", inicioPeriodo);
        data.put("fin de periodo", finPeriodo);    
        
        //saldos
        String saldoInicial = lines[18+jumps];
        String saldoFinal = lines[17+jumps];
        data.put("saldo inicial", saldoInicial);
        data.put("saldo final", saldoFinal);
        
        return data;

        
    }
}
