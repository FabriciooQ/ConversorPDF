package com.yo.Model;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.Loader;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Transformer {
    public static void transform( String originPath, String destinationPath ){
        //lector de pdf
        PDFReader reader = new PDFReader(originPath);
        //parseador de datos 
        Parser parser = new Parser();
        //definimos el writer para el xlsx
        WriterXSLX writer = null;
        //numero de paginas del documento (para poder leerlas)
        int numberOfPages = reader.getNumberOfPages();
    
        try{
            //creamos objeto que representa el archivo excel
            XSSFWorkbook wb = new XSSFWorkbook();
            //FileOutputStream para que se pueda escribir mediante ese stream en el excel
            FileOutputStream os = new FileOutputStream(destinationPath);
            writer = new WriterXSLX(wb, os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //creo hoja de trabajo en el excel
        writer.createSheet("Hoja 1");

        //leo y seteo cabecera en el excel
        String[] firstPage = reader.readPage(1);
        Map<String, String> headerMap = parser.parseHeader(firstPage);
        writer.setHeader(headerMap);
        //debugging
        System.out.println("Cabecera convertida");

        //leo y seteo la cabecera de la tabla
        Map<String,String> tableHeader = parser.parseTableHeader(firstPage);
        writer.setTableHeader(tableHeader); 
        //debugging
        System.out.println("Cabecera de tabla convertida");

        //leo datos de tabla en primera pagina y seteo en el excel
        Map<Integer,String[]> tableData = parser.parseContentTable(firstPage, true);
        writer.setTableContent(tableData);
        //debugging
        System.out.println("datos 1era hoja convertidos");

        for(int i=2;i<numberOfPages; i++){
            String[] page = reader.readPage(i);
            Map<Integer,String[]> data = parser.parseContentTable(page, false);
            if(data.size()>0){
                writer.setTableContent(data);
                //debugging
                System.out.println("Pagina " + i + " de " + numberOfPages + " convertida");
            }
        }

        //escribo cosas
        writer.write();
        //debugging
        System.out.println("Datos escritos en el excel");

        //cerramos cosas
        reader.closeAll();
        writer.closeAll();
        //debugging
        System.out.println("Finalizado");
    }
}


