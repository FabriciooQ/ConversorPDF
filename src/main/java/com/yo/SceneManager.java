package com.yo;

import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
    private static Stage stage;

    public void initialize(Stage s){
        this.stage = s;
    }

    //es static para usar el patron singleton, se inicializa la clase en start y despues se accede globalmente a este metodo
    public static void switchTo(String view){
        try{
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/" + view + ".fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static Stage getStage(){
        return SceneManager.stage;
    }
    
}
