package com.yo.View;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import com.yo.Controller.TransformationController;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

//la clase Application tiene un metodo abstracto llamado start que tenemos que implementar y es llamado
//despues de que el sistema de javafx se alla inicializado y es donde configuramos la intefaz y la mostramos
public class App extends Application{
    
    public static void main(String[] args) {
        //metodo que pertenece a la clase Application y inicializa el entorno de javafx
        launch(args); // esto arranca el JavaFX Application Thread
    }
    
    @Override
    //Stage seria la windows de la interfaz, el Stage recibido por parametro representa la ventana principal
    //de la GUI
    public void start(Stage primaryStage) {
        //creamos controller
        TransformationController controller = new TransformationController();

        //creamos los chooser para abrir y guardar
        PDFFileChooser chooserPDF = new PDFFileChooser(primaryStage);
        ExcelFileChooser chooserExcel = new ExcelFileChooser (primaryStage);

        //VBox es un layout vertical que va a ser el contenedor padre, despues ese vbox va a tener
        //otro hbox que son layouts horizontales donde ponemos las cosas 
        //y luego agregamos los hbox al vbox y el vbox a la escena y la escena al Stage
        VBox root = new VBox(15);
        // padding: arriba, derecha, abajo, izquierda
        root.setPadding(new Insets(20, 20, 20, 20));
        Scene scene = new Scene(root, 450, 450);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        
        //seteamos nombre y icono
        primaryStage.setTitle("Conversor PDF");
        Image image = new Image(getClass().getResourceAsStream("/img/logo.png"));
        primaryStage.getIcons().add(image);
        
        //Titulo
        Text title = new Text("Conversor de PDF"); 
        HBox titleLayout = new HBox(title);
        titleLayout.setAlignment(Pos.CENTER);
        root.getChildren().add(titleLayout);

        //selector archivo
        //text area selector de archivo
        Label labelSelector = new Label("Seleccionar pdf");
        TextArea textAreaArchive = new TextArea();
        textAreaArchive.setMaxWidth(285);
        textAreaArchive.setMaxHeight(1);
        //Button seleccionarArchivo
        Button buttonSelect = new Button("Seleccionar");
        buttonSelect.setOnAction(e -> {
            String filePath = chooserPDF.openFile();
            textAreaArchive.setText(filePath);
        });
        //agregamos
        HBox selectFileLayout = new HBox(10, textAreaArchive, buttonSelect);
        selectFileLayout.setAlignment(Pos.CENTER);
        VBox auxSelectFileLayout = new VBox(5, labelSelector, selectFileLayout);
        auxSelectFileLayout.setPadding(new Insets(20,20,20,20));
        root.getChildren().add(auxSelectFileLayout);

        //seleccion de carpeta para guardar archivo
        Label labelSaveFile = new Label("Seleccionar donde se va a guardar el excel");
        TextArea textAreaDestination = new TextArea();
        textAreaDestination.setMaxWidth(285);
        textAreaDestination.setMaxHeight(1);
        Button butonSelectDestination = new Button("Seleccionar");
        butonSelectDestination.setOnAction(e -> {
            String filePathDestination = chooserExcel.saveExcel();
            textAreaDestination.setText(filePathDestination);
        });
        //agregamos
        HBox destinationLayout = new HBox(10, textAreaDestination, butonSelectDestination);
        destinationLayout.setAlignment(Pos.CENTER);
        VBox auxLabelSaveFile = new VBox(5, labelSaveFile, destinationLayout);
        auxLabelSaveFile.setPadding(new Insets(20,20,20,20));
        root.getChildren().add(auxLabelSaveFile);

        //Selector de banco
        Label labelbankSelector = new Label("Seleccionar Banco");
        ChoiceBox<String> bankSelector = new ChoiceBox<>();
        bankSelector.getItems().addAll("Galicia");
        bankSelector.setValue("Galicia");
        //agregamos
        HBox bankSelectorLayout = new HBox(7, labelbankSelector, bankSelector);
        bankSelectorLayout.setAlignment(Pos.CENTER);
        root.getChildren().add(bankSelectorLayout);

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
                    controller.transformToExcel(textAreaArchive.getText(), textAreaDestination.getText());
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
        
        //mostramos
        primaryStage.show();
    }
}

