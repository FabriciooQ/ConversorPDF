package com.yo.View;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yo.SceneManager;
import com.yo.Controller.DatabaseController;
import com.yo.Controller.TransformationController;

import javafx.concurrent.Task;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainScene {
    private TransformationController transformationController;
    private DatabaseController databaseController;
    private PDFFileChooser pdfChooser;
    private ExcelFileChooser excelChooser;
    @FXML
    private TextArea textAreaPDF;
    @FXML
    private Button buttonSelectPDF;
    @FXML
    private TextArea textAreaExcel;
    @FXML 
    private Button buttonSelectExcel;
    @FXML
    private ComboBox bankSelector;
    @FXML
    private Button parametrosButton;
    @FXML
    private CheckBox buttonClasificar;
    @FXML
    private Text textoConvirtiendo;
    @FXML
    private Button buttonConvertir;
    @FXML
    private HBox hboxConviertiendo;
    @FXML
    private Button buttonAbrir;


    public MainScene(){
        //obtenemos el singleton del controller
        this.transformationController = TransformationController.getTransformationController();
        this.databaseController = DatabaseController.getDatabaseController();
        this.pdfChooser = new PDFFileChooser();
        this.excelChooser= new ExcelFileChooser();
    }

    @FXML
    public void initialize(){
        //ponermos imagenes a buttons
        ImageView folderLogoPDF = new ImageView(new Image(getClass().getResourceAsStream("/img/folder.png")));
        folderLogoPDF.setFitWidth(25);
        folderLogoPDF.setFitHeight(25);
        folderLogoPDF.setPreserveRatio(true);
        this.buttonSelectPDF.setGraphic(folderLogoPDF);
        ImageView folderLogoExcel = new ImageView(new Image(getClass().getResourceAsStream("/img/folder.png")));
        folderLogoExcel.setFitWidth(25);
        folderLogoExcel.setFitHeight(25);
        folderLogoExcel.setPreserveRatio(true);
        this.buttonSelectExcel.setGraphic(folderLogoExcel);

        //el boton parametros arranca disabled por defecto
        this.parametrosButton.setDisable(true);
        
        //agregamos nombres de banco a selector
        String[] bancos = databaseController.getNombresBancos();
        this.bankSelector.getItems().addAll(bancos);
        bankSelector.setValue(bancos[0]);
    }

    @FXML
    public void selectPDF(){
        String filePath = this.pdfChooser.openFile();
        this.textAreaPDF.setText(filePath);
        this.textAreaExcel.setText(filePath.replace(".pdf", ".xlsx"));
    }   

    @FXML
    public void selectExcel(){
        String initString = this.textAreaExcel.getText().replaceAll("^.*\\\\", "");
        excelChooser.setInitialName(initString);
        String filePathDestination = excelChooser.saveExcel();
        this.textAreaExcel.setText(filePathDestination);
    }

    @FXML
    public void parametrosAction(){
        SceneManager.switchTo("ParameterScene");
    }

    @FXML
    public void selectBank(){
        this.databaseController.setBancoActual((String)this.bankSelector.getValue());
    }

    @FXML
    public void checkClasificar(){
        boolean flag = buttonClasificar.isSelected();
        if(flag){
            this.parametrosButton.setDisable(false);
        }else{
            this.parametrosButton.setDisable(true);
        }
    }

    @FXML
    public void abrirExcel(){
        File file = new File(textAreaExcel.getText());
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(file);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
    }

    @FXML
    public void clasificarAction(){ 
        this.textAreaPDF.setEditable(true);
        this.textAreaExcel.setEditable(true);
        this.buttonSelectPDF.setDisable(true);
        this.buttonSelectExcel.setDisable(true);
        this.hboxConviertiendo.setVisible(true);

        Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                transformationController.transformToExcel(textAreaPDF.getText(), textAreaExcel.getText(), databaseController.getBancoActual(), buttonClasificar.isSelected());
                buttonAbrir.setVisible(true);
                return null;
            }
        };

        task.setOnSucceeded(w->{
            this.textoConvirtiendo.setText("Archivo convertido, haga click para abrir:");
            this.textAreaPDF.setEditable(false);
            this.textAreaExcel.setEditable(false);
            this.buttonSelectPDF.setDisable(false);
            this.buttonSelectExcel.setDisable(false);
        });

        Thread thread = new Thread(task);
        thread.start();
    }       
 

    
    
}
