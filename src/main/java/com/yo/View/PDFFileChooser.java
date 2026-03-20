package com.yo.View;

import java.io.File;

import com.yo.SceneManager;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PDFFileChooser {
    private FileChooser chooser;

    public PDFFileChooser(){
        this.chooser = new FileChooser();
        this.chooser.setTitle("Seleccione el PDF a convertir");

        chooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("pdf", "*.pdf")
        );

    }

    public String openFile(){
        File file = chooser.showOpenDialog(SceneManager.getStage());
        return file.getAbsolutePath();
    }

    
}
