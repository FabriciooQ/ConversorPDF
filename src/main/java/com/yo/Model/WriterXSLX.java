package com.yo.Model;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
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
    private int initialRowData;
    private int finalRowData;
    private CellStyle headerStyle;
    private CellStyle titleStyle;
    

    public WriterXSLX(XSSFWorkbook wb, FileOutputStream outputStream){
        this.wb = wb;
        this.os = outputStream;
        indexRow = 0;
        this.initialRowData = 12;
        this.finalRowData = 13;
        //estilo de celdas
        this.headerStyle = wb.createCellStyle();
        XSSFFont bold = wb.createFont();
        bold.setBold(true);
        headerStyle.setFont(bold);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setBorderBottom(BorderStyle.THICK);
        headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());

        this.titleStyle = wb.createCellStyle();
        XSSFFont f = wb.createFont();
        f.setBold(true);
        titleStyle.setFont(f);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);


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

    public void setTableHeader(Map<String,String> tableHeader, boolean flagClasification){
        //reseamos el finalRowData
        this.finalRowData=13;
        //distanciamos de la cabecera en 1
        indexRow++;
        //creamos fila
        XSSFRow rowTableHeader = sheet.createRow(indexRow);
        indexRow++;

        //Fecha
        XSSFCell cellDateHeader = rowTableHeader.createCell(0);
        cellDateHeader.setCellStyle(headerStyle);
        cellDateHeader.setCellValue(tableHeader.get("Fecha"));
        
        //descripcion
        XSSFCell cellDescriptionHeader = rowTableHeader.createCell(1);
        cellDescriptionHeader.setCellStyle(headerStyle);
        cellDescriptionHeader.setCellValue(tableHeader.get("Descripción"));
        
        //origen
        XSSFCell cellOriginHeader = rowTableHeader.createCell(2);
        cellOriginHeader.setCellStyle(headerStyle);
        cellOriginHeader.setCellValue(tableHeader.get("Origen"));
        
        //credito
        XSSFCell cellCreditHeader = rowTableHeader.createCell(3);
        cellCreditHeader.setCellStyle(headerStyle);
        cellCreditHeader.setCellValue(tableHeader.get("Crédito"));
        
        //debito
        XSSFCell cellDebitHeader = rowTableHeader.createCell(4);
        cellDebitHeader.setCellStyle(headerStyle);
        cellDebitHeader.setCellValue(tableHeader.get("Débito"));
        
        //saldo
        XSSFCell cellBalanceHeader = rowTableHeader.createCell(5);
        cellBalanceHeader.setCellStyle(headerStyle);
        cellBalanceHeader.setCellValue(tableHeader.get("Saldo"));

        if(flagClasification){
            //clasificacion
            XSSFCell cellClasificationHeader = rowTableHeader.createCell(6);
            cellClasificationHeader.setCellStyle(headerStyle);
            cellClasificationHeader.setCellValue("Clasificacion");    

            XSSFCell cellAux = rowTableHeader.createCell(8);
            cellAux.setCellStyle(headerStyle);
            cellAux.setCellValue("Auxiliar");
        }


    }
    

    public void setTableContent(Map<Integer,String[]> tableContent, Map<Integer, String> clasification ){
        //tableContent.values().forEach((r) -> System.out.println(r));
        this.finalRowData += tableContent.size();
        for(int i=0; i <tableContent.size(); i++){
            XSSFRow contentRow = sheet.createRow(indexRow);
            indexRow++;
            //fecha
            XSSFCell dateCell = contentRow.createCell(0);
            dateCell.setCellValue(tableContent.get(i)[0]);
            //System.out.println("fecha escrita");
            //descripcion
            XSSFCell descriptionCell = contentRow.createCell(1);
            descriptionCell.setCellValue(tableContent.get(i)[1]);
            //System.out.println("Descripción escrita");
            //origen
            if(tableContent.get(i)[2] != null){
                XSSFCell originCell = contentRow.createCell(2);
                originCell.setCellValue(tableContent.get(i)[2]);
                //System.out.println("origen escrita");
                
            }
            //credito
            XSSFCell creditCell = contentRow.createCell(3);
            if(tableContent.get(i)[3] != null){
                creditCell.setCellValue(Double.valueOf(tableContent.get(i)[3]));
                //System.out.println("credito escrita");
            }
            //debito
            XSSFCell debitCell = contentRow.createCell(4);
            if(tableContent.get(i)[4] != null){
                debitCell.setCellValue(Double.valueOf("-"+tableContent.get(i)[4]));
                //System.out.println("debito escrita");
            }
            //saldo 
            XSSFCell balanceCell = contentRow.createCell(5);
            balanceCell.setCellValue(Double.valueOf(tableContent.get(i)[5]));
            //System.out.println("saldo escrito");
            //clasificacion y resumen
            if(clasification != null){
                XSSFCell clasificationCell = contentRow.createCell(6);
                clasificationCell.setCellValue(clasification.get(i));
                //creamos celda para sumar credito - debito
                XSSFCell auxCell = contentRow.createCell(8);
                String formula = creditCell.getReference()+"+"+debitCell.getReference();
                auxCell.setCellFormula(formula);
            }

            //System.out.println("Fila " + i + " seteada en xlslx");
        }
        int cant = 6;
        if (clasification != null){
            cant ++;
        }
        for (int j=0; j<cant; j++){
            sheet.autoSizeColumn(j);
        }
    }

    public void setResumen(String[] clasification){
        int index = 2;
        XSSFRow tituloResumen = sheet.createRow(finalRowData+index);
        XSSFCell tituloCell = tituloResumen.createCell(0);
        tituloCell.setCellValue("Resumen");
        tituloCell.setCellStyle(this.titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(finalRowData+index, finalRowData+index,0,2));
        index++;
        for(int i=0;i<clasification.length;i++){
            XSSFRow auxRow = sheet.createRow(finalRowData+index);
            XSSFCell categoryCell = auxRow.createCell(0);
            categoryCell.setCellValue(clasification[i]);
            XSSFCell valuCell = auxRow.createCell(1);
            String formula = "SUMIF(G"+(initialRowData+1)+":G"+(finalRowData-1)+","+'"'+clasification[i]+'"'+",I"+(initialRowData+1)+":"+"I"+(finalRowData-1)+")";
            valuCell.setCellFormula(formula);
            index++;
        } 
        XSSFRow auxRow = sheet.createRow(finalRowData+index);
        XSSFCell categoryCell = auxRow.createCell(0);
        categoryCell.setCellValue("SIN CLASIFICAR");
        XSSFCell valuCell = auxRow.createCell(1);
        String formula = "SUMIF(G"+(initialRowData+1)+":G"+(finalRowData-1)+","+'"'+"SIN CLASIFICAR"+'"'+",I"+(initialRowData+1)+":"+"I"+(finalRowData-1)+")";
        valuCell.setCellFormula(formula);

    }

    public void setHeader(Map<String, String> headerMap, boolean flagClasification){
        //titulo
        XSSFRow rowTitulo = sheet.createRow(indexRow);
        indexRow++;
        XSSFCell cellTitulo = rowTitulo.createCell(0);
        cellTitulo.setCellValue(headerMap.get("titulo"));
        cellTitulo.setCellStyle(titleStyle);
        int union = 5;
        if(flagClasification){
            union = 6;
        }
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,union));
        //System.out.println("    Titulo seteado");

        //cell style negrita para todos los datos restantes
        XSSFCellStyle boldStyle = wb.createCellStyle();
        XSSFFont bold = wb.createFont();
        bold.setBold(true);
        boldStyle.setFont(bold);

        //lugar
        XSSFRow rowLugar = sheet.createRow(indexRow);
        indexRow++;
        XSSFCell cellLugar = rowLugar.createCell(0);
        cellLugar.setCellStyle(boldStyle);
        cellLugar.setCellValue(headerMap.get("lugar"));
        sheet.addMergedRegion(new CellRangeAddress(1,1,0,1));
        //System.out.println("    lugar seteado");


        //cuit (cabecera y valor)
        XSSFRow rowCuit = sheet.createRow(indexRow);
        indexRow++;
        XSSFCell cellLabelCuit = rowCuit.createCell(0);
        cellLabelCuit.setCellStyle(boldStyle);
        cellLabelCuit.setCellValue("cuit");
        XSSFCell cellCuit = rowCuit.createCell(1);
        cellCuit.setCellValue(headerMap.get("cuit"));
        //System.out.println("    cuit seteado");


        //IVA (cabecera y valor)
        XSSFRow rowIva = sheet.createRow(indexRow);
        indexRow++;
        XSSFCell cellLabelIva = rowIva.createCell(0);
        cellLabelIva.setCellStyle(boldStyle);
        cellLabelIva.setCellValue("IVA");
        XSSFCell cellIva = rowIva.createCell(1);
        cellIva.setCellValue(headerMap.get("iva"));
        //System.out.println("    IVA seteado");

        
        //numero de cuenta (cabecera y valor)
        XSSFRow rowTipoCuenta = sheet.createRow(indexRow);
        indexRow++;
        XSSFCell cellLabelTipoCuenta = rowTipoCuenta.createCell(0);
        cellLabelTipoCuenta.setCellStyle(boldStyle);
        cellLabelTipoCuenta.setCellValue("Tipo de cuenta");
        XSSFCell cellTipoCuenta = rowTipoCuenta.createCell(1);
        cellTipoCuenta.setCellValue(headerMap.get("tipo de cuenta"));
        //System.out.println("    Numero cuenta seteado");


        //tipo de cuenta (cabecera y valor)
        XSSFRow rowCuenta = sheet.createRow(indexRow);
        indexRow++;
        XSSFCell cellLabelCuenta = rowCuenta.createCell(0);
        cellLabelCuenta.setCellStyle(boldStyle);
        cellLabelCuenta.setCellValue("n° de cuenta");
        XSSFCell cellCuenta = rowCuenta.createCell(1);
        cellCuenta.setCellValue(headerMap.get("cuenta"));
        //System.out.println("    Tipo cuenta seteado");


        //inicio de periodo (cabecera y valor)
        XSSFRow rowPeriodoInicio = sheet.createRow(indexRow);
        indexRow++;
        XSSFCell cellLabelPeriodoInicio = rowPeriodoInicio.createCell(0);
        cellLabelPeriodoInicio.setCellStyle(boldStyle);
        cellLabelPeriodoInicio.setCellValue("inicio de periodo");
        XSSFCell cellPeriodoInicio = rowPeriodoInicio.createCell(1);
        cellPeriodoInicio.setCellValue(headerMap.get("inicio de periodo"));
        //System.out.println("    Inicio periodo seteado");


        //fin de periodo (cabecera y valor)
        XSSFRow rowPeriodoFin = sheet.createRow(indexRow);
        indexRow++;
        XSSFCell cellLabelPeriodoFin = rowPeriodoFin.createCell(0);
        cellLabelPeriodoFin.setCellStyle(boldStyle);
        cellLabelPeriodoFin.setCellValue("fin de periodo");
        XSSFCell cellPeriodoFin = rowPeriodoFin.createCell(1);
        cellPeriodoFin.setCellValue(headerMap.get("fin de periodo"));
        //System.out.println("    Fin periodo seteado");


        //saldo inicial (PDF) (cabecera y valor)
        XSSFRow rowSaldoInicial = sheet.createRow(indexRow);
        indexRow++;
        XSSFCell cellLabelSaldoInicial = rowSaldoInicial.createCell(0);
        cellLabelSaldoInicial.setCellStyle(boldStyle);
        cellLabelSaldoInicial.setCellValue("saldo inicial (PDF)");
        XSSFCell cellSaldoinicial = rowSaldoInicial.createCell(1);
        cellSaldoinicial.setCellValue(Double.valueOf(headerMap.get("saldo inicial")));
        //System.out.println("    Saldo inicial seteado");


        //saldo final (PDF) (cabecera y valor)
        XSSFRow rowSaldoFinal = sheet.createRow(indexRow);
        indexRow++;
        XSSFCell cellLabelSaldoFinal = rowSaldoFinal.createCell(0);
        cellLabelSaldoFinal.setCellStyle(boldStyle);
        cellLabelSaldoFinal.setCellValue("saldo final (PDF)");
        XSSFCell cellSaldoFinal = rowSaldoFinal.createCell(1);
        cellSaldoFinal.setCellValue(Double.valueOf(headerMap.get("saldo final")));
        //System.out.println("    Saldo final seteado");

    }
    


}
