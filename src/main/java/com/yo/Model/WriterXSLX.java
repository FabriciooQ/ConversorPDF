package com.yo.Model;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.scene.text.Font;

public class WriterXSLX {
    private XSSFWorkbook wb;
    private XSSFSheet sheet;
    private FileOutputStream os;
    private int indexRow;
    

    public WriterXSLX(XSSFWorkbook wb, FileOutputStream outputStream){
        this.wb = wb;
        this.os = outputStream;
        indexRow = 0;
        //estilo de celdas

    }

    public void write(){
        try {
            wb.write(os);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void closeAll(){
        try {
            wb.close();
            os.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void createSheet(String sheetName) {
        this.sheet = wb.createSheet(sheetName);
    }

    public void setTableHeader(Map<String,String> tableHeader){
        //creamos columna
        XSSFRow rowTableHeader = sheet.createRow(indexRow);
        indexRow++;

        //Fecha
        XSSFCell cellDateHeader = rowTableHeader.createCell(0);
        cellDateHeader.setCellValue(tableHeader.get("Fecha"));
        
        //descripcion
        XSSFCell cellDescriptionHeader = rowTableHeader.createCell(1);
        cellDescriptionHeader.setCellValue(tableHeader.get("Descripción"));
        
        //origen
        XSSFCell cellOriginHeader = rowTableHeader.createCell(2);
        cellOriginHeader.setCellValue(tableHeader.get("Origen"));
        
        //credito
        XSSFCell cellCreditHeader = rowTableHeader.createCell(3);
        cellCreditHeader.setCellValue(tableHeader.get("Crédito"));
        
        //debito
        XSSFCell cellDebitHeader = rowTableHeader.createCell(4);
        cellDebitHeader.setCellValue(tableHeader.get("Débito"));
        
        //saldo
        XSSFCell cellBalanceHeader = rowTableHeader.createCell(5);
        cellBalanceHeader.setCellValue(tableHeader.get("Saldo"));
    }
    

    public void setTableContent(Map<Integer,String[]> tableContent ){
        for(int i=0; i <tableContent.size(); i++){
            XSSFRow contentRow = sheet.createRow(indexRow);
            indexRow++;
            //fecha
            XSSFCell dateCell = contentRow.createCell(0);
            dateCell.setCellValue(tableContent.get(i)[0]);
            //descripcion
            XSSFCell descriptionCell = contentRow.createCell(1);
            descriptionCell.setCellValue(tableContent.get(i)[1]);
            //origen
            if(tableContent.get(i)[2] != null){
                XSSFCell originCell = contentRow.createCell(2);
                originCell.setCellValue(tableContent.get(i)[2]);
            }
            //credito
            if(tableContent.get(i)[3] != null){
                XSSFCell creditCell = contentRow.createCell(3);
                creditCell.setCellValue(tableContent.get(i)[3]);
            }
            //debito
            if(tableContent.get(i)[4] != null){
                XSSFCell debitCell = contentRow.createCell(4);
                debitCell.setCellValue(tableContent.get(i)[4]);
            }
            //saldo 
            XSSFCell balanceCell = contentRow.createCell(5);
            balanceCell.setCellValue(tableContent.get(i)[5]);
        }

    }



    public void setHeader(Map<String, String> headerMap){
        //titulo
        XSSFRow rowTitulo = sheet.createRow(indexRow);
        XSSFCell cellTitulo = rowTitulo.createCell(0);
        
        cellTitulo.setCellValue(headerMap.get("titulo"));

        XSSFFont f = wb.createFont();
        f.setBold(true);
        XSSFCellStyle s = wb.createCellStyle();
        s.setFont(f);

        indexRow++;

        //lugar
        XSSFRow rowLugar = sheet.createRow(indexRow);
        XSSFCell cellLugar = rowLugar.createCell(0);
        cellLugar.setCellValue(headerMap.get("lugar"));
        indexRow++;

        //cuit (cabecera y valor)
        XSSFRow rowCuit = sheet.createRow(indexRow);
        XSSFCell cellLabelCuit = rowCuit.createCell(0);
        cellLabelCuit.setCellValue("cuit");
        XSSFCell cellCuit = rowCuit.createCell(1);
        cellCuit.setCellValue(headerMap.get("cuit"));
        indexRow++;

        //IVA (cabecera y valor)
        XSSFRow rowIva = sheet.createRow(indexRow);
        XSSFCell cellLabelIva = rowIva.createCell(0);
        cellLabelIva.setCellValue("IVA");
        XSSFCell cellIva = rowIva.createCell(1);
        cellIva.setCellValue(headerMap.get("iva"));
        indexRow++;
        
        //numero de cuenta (cabecera y valor)
        XSSFRow rowTipoCuenta = sheet.createRow(indexRow);
        XSSFCell cellLabelTipoCuenta = rowTipoCuenta.createCell(0);
        cellLabelTipoCuenta.setCellValue("Tipo de cuenta");
        XSSFCell cellTipoCuenta = rowTipoCuenta.createCell(1);
        cellTipoCuenta.setCellValue(headerMap.get("tipo de cuenta"));
        indexRow++;

        //tipo de cuenta (cabecera y valor)
        XSSFRow rowCuenta = sheet.createRow(indexRow);
        XSSFCell cellLabelCuenta = rowCuenta.createCell(0);
        cellLabelCuenta.setCellValue("n° de cuenta");
        XSSFCell cellCuenta = rowCuenta.createCell(1);
        cellCuenta.setCellValue(headerMap.get("cuenta"));
        indexRow++;

        //inicio de periodo (cabecera y valor)
        XSSFRow rowPeriodoInicio = sheet.createRow(indexRow);
        XSSFCell cellLabelPeriodoInicio = rowPeriodoInicio.createCell(0);
        cellLabelPeriodoInicio.setCellValue("inicio de periodo");
        XSSFCell cellPeriodoInicio = rowPeriodoInicio.createCell(1);
        cellPeriodoInicio.setCellValue(headerMap.get("inicio de periodo"));
        indexRow++;

        //fin de periodo (cabecera y valor)
        XSSFRow rowPeriodoFin = sheet.createRow(indexRow);
        XSSFCell cellLabelPeriodoFin = rowPeriodoFin.createCell(0);
        cellLabelPeriodoFin.setCellValue("fin de periodo");
        XSSFCell cellPeriodoFin = rowPeriodoFin.createCell(1);
        cellPeriodoFin.setCellValue(headerMap.get("fin de periodo"));
        indexRow++;

        //saldo inicial (PDF) (cabecera y valor)
        XSSFRow rowSaldoInicial = sheet.createRow(indexRow);
        XSSFCell cellLabelSaldoInicial = rowSaldoInicial.createCell(0);
        cellLabelSaldoInicial.setCellValue("saldo inicial (PDF)");
        XSSFCell cellSaldoinicial = rowSaldoInicial.createCell(1);
        cellSaldoinicial.setCellValue(headerMap.get("saldo inicial"));
        indexRow++;

        //saldo final (PDF) (cabecera y valor)
        XSSFRow rowSaldoFinal = sheet.createRow(indexRow);
        XSSFCell cellLabelSaldoFinal = rowSaldoFinal.createCell(0);
        cellLabelSaldoFinal.setCellValue("saldo final (PDF)");
        XSSFCell cellSaldoFinal = rowSaldoFinal.createCell(1);
        cellSaldoFinal.setCellValue(headerMap.get("saldo final"));
        indexRow++;
    }
    
}
