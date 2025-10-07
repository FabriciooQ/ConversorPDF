package com.yo.Model;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFReader {
    private String path;
    private PDDocument pdf;
    private PDFTextStripper textStripper;
    private int numberOfPages;

    public PDFReader(String path){
        this.path = path;
        this.pdf = loadPDF();
        this.textStripper = new PDFTextStripper();
        this.numberOfPages = pdf.getNumberOfPages();
    }

    public int getNumberOfPages(){
        return this.numberOfPages;
    }

    private PDDocument loadPDF(){
        File file = new File(this.path);
        try {
            PDDocument pdf = Loader.loadPDF(file);
            return pdf;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public void closeAll(){
        try {
            this.pdf.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public String[] readPage(int index){
        textStripper.setStartPage(index);
        textStripper.setEndPage(index);

        try {
            String page = textStripper.getText(pdf);
            return page.split("\n");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
