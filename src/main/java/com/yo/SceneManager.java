package com.yo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
    private static Stage stage;

    public void initialize(Stage s){
        this.stage = s;
    }

    public static void switchTo(String view){
        try{
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/com/yo/app" + view + ".fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
