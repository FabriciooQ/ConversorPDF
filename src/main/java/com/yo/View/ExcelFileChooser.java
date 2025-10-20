package com.yo.View;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ExcelFileChooser {
    FileChooser chooser;
    Stage stage;

    public ExcelFileChooser(Stage stage){
        this.stage = stage;
        chooser = new FileChooser();
        this.chooser.setTitle("Seleccione donde guardar el PDF");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("excel", "*.xlsx"));
        
    }

    public void setInitialName(String name){
        chooser.setInitialFileName(name);
    }

    public String saveExcel(){
        File f = chooser.showSaveDialog(this.stage);
        return f.getAbsolutePath();
    }
    
    
}
