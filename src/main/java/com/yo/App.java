package com.yo;

import java.awt.Checkbox;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.yo.Controller.TransformationController;
import com.yo.Controller.DatabaseController;
import com.yo.Model.Banco;
import com.yo.Model.DatabaseAdministrator;
import com.yo.View.MainScene;
import com.yo.View.ParameterSceneTable;
import com.yo.pruebas.peueba;

import javafx.application.Application;
import javafx.concurrent.Task;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

//la clase Application tiene un metodo abstracto llamado start que tenemos que implementar y es llamado
//despues de que el sistema de javafx se alla inicializado y es donde configuramos la intefaz y la mostramos
public class App extends Application{
    public static void main(String[] args) {       
        //metodo que pertenece a la clase Application y inicializa el entorno de javafx

        launch(args); // esto arranca el JavaFX Application Thread
        //peueba prueba = new peueba();
        //prueba.run();
    }
    
    @Override
    //Stage seria la windows de la interfaz, el Stage recibido por parametro representa la ventana principal
    //de la GUI
    public void start(Stage primaryStage) {
        //creamos el sceneManager


        //creamos controllers
/*         TransformationController transformationController = new TransformationController();
        DatabaseController databaseController = new DatabaseController();

        
        primaryStage.setResizable(false);
        //seteamos nombre y icono
        primaryStage.setTitle("Conversor PDF");
        Image image = new Image(getClass().getResourceAsStream("/img/logo.png"));
        primaryStage.getIcons().add(image);
        
        //creamos escena de de parametros
        ParameterSceneTable parameterScene = new ParameterSceneTable(primaryStage,)

        //creamos escena principal
        MainScene main = new MainScene(primaryStage, transformationController, 450, 450);
        Scene mainScene = main.getScene();



        //seteamos que cuando se cierrre se cierre la sessionFactory de hibernate
        primaryStage.setOnCloseRequest(event ->{
            databaseController.closeDB();
        });
        
        //seteamos la escena main
        primaryStage.setScene(mainScene);

            
        //mostramos
        primaryStage.show(); */
    }
}

