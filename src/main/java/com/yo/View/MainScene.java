package com.yo.View;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yo.Controller.TransformationController;

import javafx.concurrent.Task;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainScene {
    private Scene scene;
    private Stage stage;
    private TransformationController controller;

    public MainScene(Stage stage, TransformationController controller, double width, double height){
        this.stage = stage;
        this.controller = controller;
        this.scene = createMainScene(width, height);
    }

    public Scene getScene(){
        return this.scene;
    }

    public Scene createMainScene(double width, double height){
        //creamos los chooser para abrir y guardar
        PDFFileChooser chooserPDF = new PDFFileChooser(this.stage);
        ExcelFileChooser chooserExcel = new ExcelFileChooser (this.stage);
        
        //creamos contenedor root
        VBox root = new VBox(15);
        root.setPadding(new Insets(20, 20, 20, 20));

        //Titulo
        Text title = new Text("Conversor de PDF"); 
        title.getStyleClass().add("title");
        HBox titleLayout = new HBox(title);
        titleLayout.setAlignment(Pos.CENTER);
        root.getChildren().add(titleLayout);

        //selector archivo
        Label labelSelector = new Label("Seleccionar pdf");
        labelSelector.getStyleClass().add("label");
        TextArea textAreaArchive = new TextArea();
        textAreaArchive.setMaxWidth(285);
        textAreaArchive.setMaxHeight(1);
        //Button seleccionarArchivo
        Button buttonSelect = new Button();
        ImageView folderLogoPDF = new ImageView(new Image(getClass().getResourceAsStream("/img/folder.png")));
        folderLogoPDF.setFitWidth(25);
        folderLogoPDF.setFitHeight(25);
        folderLogoPDF.setPreserveRatio(true);
        buttonSelect.setGraphic(folderLogoPDF);
        buttonSelect.getStyleClass().add("button");
        TextArea textAreaDestination = new TextArea();//la declaramos aca para poder usarlo en el onAction
        buttonSelect.setOnAction(e -> {
            String filePath = chooserPDF.openFile();
            textAreaArchive.setText(filePath);
            textAreaDestination.setText(filePath.replace(".pdf", ".xlsx"));
        });
        //agregamos
        HBox selectFileLayout = new HBox(10, textAreaArchive, buttonSelect);
        selectFileLayout.setAlignment(Pos.CENTER);
        VBox auxSelectFileLayout = new VBox(5, labelSelector, selectFileLayout);
        auxSelectFileLayout.setPadding(new Insets(20,20,20,20));
        root.getChildren().add(auxSelectFileLayout);

        //seleccion de carpeta para guardar archivo
        Label labelSaveFile = new Label("Seleccionar donde se va a guardar el excel");
        labelSaveFile.getStyleClass().add("label");
        textAreaDestination.getStyleClass().add("text-area");
        textAreaDestination.setMaxWidth(285);
        textAreaDestination.setMaxHeight(1);
        Button butonSelectDestination = new Button();
        ImageView folderLogoExcel = new ImageView(new Image(getClass().getResourceAsStream("/img/folder.png")));
        folderLogoExcel.setFitWidth(25);
        folderLogoExcel.setFitHeight(25);
        folderLogoExcel.setPreserveRatio(true);
        butonSelectDestination.setGraphic(folderLogoExcel);
        butonSelectDestination.getStyleClass().add("button");
        butonSelectDestination.setOnAction(e -> {
            //extraemos nombre
            String initString = textAreaDestination.getText().replaceAll("^.*\\\\", "");
            chooserExcel.setInitialName(initString);
            String filePathDestination = chooserExcel.saveExcel();
            textAreaDestination.setText(filePathDestination);
        });
        //agregamos
        HBox destinationLayout = new HBox(10, textAreaDestination, butonSelectDestination);
        destinationLayout.setAlignment(Pos.CENTER);
        VBox auxLabelSaveFile = new VBox(5, labelSaveFile, destinationLayout);
        auxLabelSaveFile.setPadding(new Insets(20,20,20,20));
        root.getChildren().add(auxLabelSaveFile);

        //Selector de banco y clasificacion
        Label labelbankSelector = new Label("Seleccionar Banco");
        labelbankSelector.getStyleClass().add("label");
        ChoiceBox<String> bankSelector = new ChoiceBox<>();
        bankSelector.getStyleClass().add("combo-box");
        bankSelector.getItems().addAll(controller.getNombresBancos());
        bankSelector.setValue("Galicia");
        Button buttonParams = new Button("Parametros");
        buttonParams.getStyleClass().add("button");
        buttonParams.setDisable(true);
        buttonParams.setOnAction(e->{
            stage.setScene();
        });
        CheckBox buttonClasification = new CheckBox("Clasificar");
        buttonClasification.getStyleClass().addAll("check-box");
        buttonClasification.setOnAction(e->{
           buttonParams.setDisable(!buttonParams.isDisable());
        });
        //agregamos
        HBox bankSelectorLayout = new HBox(7, labelbankSelector, bankSelector, buttonClasification, buttonParams);
        bankSelectorLayout.setAlignment(Pos.CENTER);
        root.getChildren().addAll(bankSelectorLayout);

        //texto que se va a mostrar cuando se este convirtiendo el archivo y despues
        Text textConverted = new Text();
        textConverted.setVisible(false);
        Button bunttonOpenFile= new Button("Abrir");
        bunttonOpenFile.setVisible(false);
        bunttonOpenFile.setOnAction(e->{
            File file = new File(textAreaDestination.getText());
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(file);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
        //agregamos
        HBox openFileLayout = new HBox(7, textConverted, bunttonOpenFile);
        openFileLayout.setAlignment(Pos.CENTER);
        root.getChildren().add(openFileLayout);

        //boton convertir
        Button buttonConvert = new Button("Convertir");
        buttonConvert.setOnAction(e->{
            //Desactivamos todos los botones y inputs
            textAreaArchive.setEditable(false);
            textAreaDestination.setEditable(false);
            buttonSelect.setDisable(true);
            butonSelectDestination.setDisable(true);
            bankSelector.setDisable(true);
            //hacemos visible el texto que se esta conviritiendo
            textConverted.setText("Conviertiendo ...");
            textConverted.setVisible(true);
            //convertimos (con una Task en otro hilo para no bloquear el de la interfaz)
            Task <Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    controller.transformToExcel(textAreaArchive.getText(), textAreaDestination.getText(), buttonClasification.isSelected());
                    //como no es void como tal si no la clase que lo representa necesitamos un return
                    return null;
                }
            };
            //seteamos que cuando la tarea tenga exito se habiliten las cosas y mostramos el texto y el boton
            task.setOnSucceeded(w->{
                //volvemos a habilitar todo
                textAreaArchive.setEditable(true);
                textAreaDestination.setEditable(true);
                buttonSelect.setDisable(false);
                butonSelectDestination.setDisable(false);
                bankSelector.setDisable(false);
                //cambiamos el texto y hacemos visible el boton para abrir
                textConverted.setText("Archivo convertido y guardado, haga click para abrir:");
                bunttonOpenFile.setVisible(true);
            });
            Thread t = new Thread(task);
            t.start();
        });
        HBox buttonConvertLayout = new HBox(buttonConvert);
        buttonConvertLayout.setAlignment(Pos.CENTER);
        root.getChildren().add(buttonConvertLayout);

        
        
        //creamos la escena y la seteamos al stage principal
        Scene scene = new Scene(root, width, height);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        
  
        return scene;

    }

    
    
}
