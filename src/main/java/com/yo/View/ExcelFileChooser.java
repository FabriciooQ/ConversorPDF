package com.yo.View;

import java.io.File;

import com.yo.SceneManager;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ExcelFileChooser {
    FileChooser chooser;

    public ExcelFileChooser(){
        chooser = new FileChooser();
        this.chooser.setTitle("Seleccione donde guardar el PDF");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("excel", "*.xlsx"));
        
    }

    public void setInitialName(String name){
        chooser.setInitialFileName(name);
    }

    public String saveExcel(){
        File f = chooser.showSaveDialog(SceneManager.getStage());
        return f.getAbsolutePath();
    }
    
    
}
