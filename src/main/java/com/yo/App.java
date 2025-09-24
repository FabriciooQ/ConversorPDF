package com.yo;
import java.io.File;
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

/**
 * Hello world!
 *
 */
public class App {
    //Esta variable se usa cuando un palabra ocupa mas de un renglon, se le suma 1 asi se sabe el desplazamiento de los demas renglones
    private static int jumps = 0;

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

                
                //leemos
                String textFirstPage = textStripper.getText(pdf);
                String[] renglones = textFirstPage.split("\n");
                
/*                 for(int i = 0; i<renglones.length; i++){
                    System.out.println(i + " - " + renglones[i]);


                } */


                //obtenemos los datos de cabecera
                Map<String,String> headerMap = getDataHeader(textFirstPage);
                /* headerMap.forEach((k,v) -> {
                    System.out.println(k + ": " + v);
                }); */
               

                //creamos la clase que permite manipular un archivo excel
                XSSFWorkbook wb = new XSSFWorkbook();

                //le agregamos una hoja de trabajo
                XSSFSheet sheet = wb.createSheet("Galicia");

                //escribimos la cabecera 
                //titulo
                XSSFRow rowTitulo = sheet.createRow(0);
                XSSFCell cellTitulo = rowTitulo.createCell(0);
                cellTitulo.setCellValue(headerMap.get("titulo"));

                //lugar
                XSSFRow rowLugar = sheet.createRow(1);
                XSSFCell cellLugar = rowLugar.createCell(0);
                cellTitulo.setCellValue(headerMap.get("lugar"));

                //cuit (cabecera y valor)
                XSSFRow rowCuit = sheet.createRow(2);
                XSSFCell cellLabelCuit = rowCuit.createCell(0);
                cellLabelCuit.setCellValue("cuit");
                XSSFCell cellCuit = rowCuit.createCell(1);
                cellCuit.setCellValue(headerMap.get("cuit"));

                //IVA (cabecera y valor)
                XSSFRow rowIva = sheet.createRow(3);
                XSSFCell cellLabelIva = rowIva.createCell(0);
                cellLabelIva.setCellValue("IVA");
                XSSFCell cellIva = rowIva.createCell(1);
                cellIva.setCellValue(headerMap.get("iva"));
               
                //numero de cuenta (cabecera y valor)
                XSSFRow rowTipoCuenta = sheet.createRow(4);
                XSSFCell cellLabelTipoCuenta = rowTipoCuenta.createCell(0);
                cellLabelTipoCuenta.setCellValue("Tipo de cuenta");
                XSSFCell cellTipoCuenta = rowTipoCuenta.createCell(1);
                cellTipoCuenta.setCellValue(headerMap.get("tipo de cuenta"));

                //tipo de cuenta (cabecera y valor)
                XSSFRow rowCuenta = sheet.createRow(5);
                XSSFCell cellLabelCuenta = rowCuenta.createCell(0);
                cellLabelCuenta.setCellValue("n° de cuenta");
                XSSFCell cellCuenta = rowCuenta.createCell(1);
                cellCuenta.setCellValue(headerMap.get("cuenta"));

                //inicio de periodo (cabecera y valor)
                XSSFRow rowPeriodoInicio = sheet.createRow(6);
                XSSFCell cellLabelPeriodoInicio = rowPeriodoInicio.createCell(0);
                cellLabelPeriodoInicio.setCellValue("inicio de periodo");
                XSSFCell cellPeriodoInicio = rowPeriodoInicio.createCell(1);
                cellPeriodoInicio.setCellValue(headerMap.get("inicio de periodo"));

                //fin de periodo (cabecera y valor)
                XSSFRow rowPeriodoFin = sheet.createRow(7);
                XSSFCell cellLabelPeriodoFin = rowPeriodoFin.createCell(0);
                cellLabelPeriodoFin.setCellValue("fin de periodo");
                XSSFCell cellPeriodoFin = rowPeriodoInicio.createCell(1);
                cellPeriodoFin.setCellValue(headerMap.get("fin de periodo"));

                //saldo inicial (PDF) (cabecera y valor)
                XSSFRow rowSaldoInicial = sheet.createRow(7);
                XSSFCell cellLabelSaldoInicial = rowSaldoInicial.createCell(0);
                cellLabelSaldoInicial.setCellValue("saldo inicial (PDF)");
                XSSFCell cellSaldoinicial = rowPeriodoInicio.createCell(1);
                cellSaldoinicial.setCellValue(headerMap.get("saldo inicial"));

                //saldo final (PDF) (cabecera y valor)
                XSSFRow rowSaldoFinal = sheet.createRow(7);
                XSSFCell cellLabelSaldoFinal = rowSaldoFinal.createCell(0);
                cellLabelSaldoFinal.setCellValue("saldo final (PDF)");
                XSSFCell cellSaldoFinal = rowSaldoFinal.createCell(1);
                cellSaldoFinal.setCellValue(headerMap.get("saldo final"));
               

                //creamos un FileOutputStream para que la clase wb escriba el archivo a traves del stream
                FileOutputStream fileOut = new FileOutputStream("Galicia.xlsx");
                //el wb crea el archivo y escribe los datos seteados a traves del output stream
                wb.write(fileOut);



                wb.close();
                fileOut.close();

                
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
        
        //dividimos los renglones de la pagina
        String[] lines = page.split("\n");
       
        //cuit y iva
        String[] wordsCuit = lines[0].split("    ");
        System.out.println(wordsCuit.length);
        String cuit = wordsCuit[0].split(" ")[1];
        String iva = wordsCuit[1].split(" ")[1] + " " + wordsCuit[1].split(" ")[2];
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
