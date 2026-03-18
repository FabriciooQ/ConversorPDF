package com.yo.View;

import java.util.ArrayList;
import java.util.List;

import com.yo.Controller.DatabaseController;
import com.yo.Controller.TransformationController;

import com.yo.Model.OrdenRegla;


import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ParameterSceneTable {
    private Stage stage;
    private Scene mainScene;
    private Scene scene;
    private DatabaseController dbController;
    private List<OrdenRegla> rules;


    public ParameterSceneTable(Stage stage, DatabaseController dbController, double width, double height){
        this.stage = stage;
        this.dbController = dbController;
        this.rules = new ArrayList<>();
        this.scene = createScene(width, height);    
    }

    private Scene createScene(double width, double height){
        TableView table = new TableView();
        table.setEditable(true);

        TableColumn orderColumn = new TableColumn("Order");
        TableColumn expresionColumn = new TableColumn("Expresion");
        TableColumn condicionColumn = new TableColumn("Condicion");
        TableColumn textColumn = new TableColumn("Texto");
        TableColumn deleteColumn = new TableColumn("");

        table.getColumns().addAll(orderColumn, expresionColumn, condicionColumn, textColumn);

        final VBox vbox = new VBox();
        vbox.getChildren().addAll(table);

        Scene scene = new Scene(vbox, width, height);
        return scene;




    }


    
}
