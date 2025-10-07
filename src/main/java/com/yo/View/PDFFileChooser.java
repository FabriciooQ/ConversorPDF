package com.yo.View;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PDFFileChooser {
    private FileChooser chooser;
    private Stage stage;
    public PDFFileChooser(Stage stage){
        this.stage = stage;
        this.chooser = new FileChooser();
        this.chooser.setTitle("Seleccione el PDF a convertir");

        chooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("pdf", "*.pdf")
        );

    }

    public String openFile(){
        File file = chooser.showOpenDialog(stage);
        return file.getAbsolutePath();
        
    }

    
}
