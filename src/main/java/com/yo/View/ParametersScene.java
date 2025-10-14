package com.yo.View;

import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yo.Controller.TransformationController;

import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ParametersScene {
    private Stage stage;
    private Scene rootScene;
    private Scene scene;
    private TransformationController controller;
    private Map<Integer,String[]> initialRules;
    private int ids;

    public ParametersScene(Stage stage, Scene rootScene, TransformationController controller, double width, double height){
        this.initialRules = new HashMap<>();
        this.stage = stage;
        this.rootScene = rootScene;
        this.controller = controller;
        this.ids =  0;  
        this.controller.loadRules(initialRules);
        this.scene = createScene(width, height);    
    }

    private Scene createScene(double width, double height){
        //box root
        VBox root = new VBox(15);

        //Titulo de escena
        Text title = new Text("Parametros clasificacion");
        root.getChildren().add(title);

        //cabeceras tabla
        Text expresionText = new Text("Expresion");
        Text clasificationText = new Text("Clasificaion");
        HBox tableHeader = new HBox(50, expresionText, clasificationText);
        tableHeader.setAlignment(Pos.CENTER);
        root.getChildren().add(tableHeader);
        
        //box parametros
        VBox paramsLayout = new VBox(9);
        //agregamos por cada parametro en el .txt una entrada
        for(int i=0; i<this.initialRules.size(); i++){
            //contador para ids
            ids++;
            //box horizontal de arriba
            TextArea expresionArea = new TextArea();
            expresionArea.setText(parseExpresion(this.initialRules.get(i)[0].trim()));
            expresionArea.setMaxHeight(1);
            expresionArea.setId("e"+String.valueOf(ids));
            TextArea clasificationArea = new TextArea();
            clasificationArea.setText(parseExpresion(this.initialRules.get(i)[1].trim()));
            clasificationArea.setMaxHeight(1);
            clasificationArea.setId("c"+String.valueOf(ids));
            Button delete = new Button("Eliminar");
            HBox inputsLayout = new HBox(50, expresionArea, clasificationArea, delete);
            //box horizontal de abajo
            ToggleGroup group = new ToggleGroup();
            RadioButton begin = new RadioButton("Empieza");
            begin.setId("bb"+String.valueOf(ids));
            RadioButton contains = new RadioButton("Contiene");
            contains.setId("bc"+String.valueOf(ids));
            RadioButton ends = new RadioButton("Termina");
            ends.setId("be"+String.valueOf(ids));
            begin.setToggleGroup(group);
            contains.setToggleGroup(group);
            ends.setToggleGroup(group);
            int option = getOption(initialRules.get(i)[0]);
            switch (option) {
                case 1:
                    begin.setSelected(true);                   
                    break;
                case 2: 
                    contains.setSelected(true);
                    break;
                case 3:
                    ends.setSelected(true);
                default:
                    break;
            }
            HBox optionsLayout = new HBox(5, begin, contains, ends);
            optionsLayout.setAlignment(Pos.CENTER_LEFT);
            //agregamos a los vbox
            VBox aux = new VBox(5, inputsLayout, optionsLayout);
            paramsLayout.getChildren().add(aux);
        } 
        root.getChildren().add(paramsLayout);

        //boton de agregar regla
        Button addRule = new Button("Agregar");
        addRule.setOnAction(e -> {
            //sumamos en 1 el id de reglas
            this.ids++;
            //box horizontal de arriba
            TextArea expresionArea = new TextArea();
            expresionArea.setMaxHeight(1);
            expresionArea.setId("e"+String.valueOf(this.ids));
            TextArea clasificationArea = new TextArea();
            clasificationArea.setMaxHeight(1);
            clasificationArea.setId("c"+String.valueOf(this.ids));
            Button delete = new Button("Eliminar");
            HBox inputsLayout = new HBox(50, expresionArea, clasificationArea, delete);
            //box horizontal de abajo
            ToggleGroup group = new ToggleGroup();
            RadioButton begin = new RadioButton("Empieza");
            begin.setId("bb"+String.valueOf(this.ids));
            RadioButton contains = new RadioButton("Contiene");
            contains.setId("bc"+String.valueOf(this.ids));
            RadioButton ends = new RadioButton("Termina");
            ends.setId("be"+String.valueOf(this.ids));
            begin.setToggleGroup(group);
            contains.setToggleGroup(group);
            ends.setToggleGroup(group);
            //agg
            HBox optionsLayout = new HBox(5, begin, contains, ends);
            optionsLayout.setAlignment(Pos.CENTER_LEFT);
            VBox aux = new VBox(5, inputsLayout, optionsLayout);
            paramsLayout.getChildren().add(aux);
        });
        HBox addLayout = new HBox(addRule);
        addLayout.setAlignment(Pos.CENTER);
        root.getChildren().add(addLayout);

        //botones cancelar y guardar
        Button cancelButton = new Button("Cancelar");
        cancelButton.setOnAction(e->{
            stage.setScene(rootScene);
        });
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e->{
            cancelButton.setDisable(true);
            paramsLayout.setDisable(true);
            addRule.setDisable(true);;
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() {
                    List<String> rulesToSave = getRulesToSave(); 
                    controller.saveRules(rulesToSave);
                    return null;
                }
            };
            task.setOnSucceeded(s->{
                cancelButton.setDisable(false);
                paramsLayout.setDisable(false);
                addRule.setDisable(false);
                stage.setScene(rootScene);
            });
            Thread thread = new Thread(task);
            thread.start();
        });
        HBox buttonsLayout = new HBox(5, cancelButton, saveButton);
        root.getChildren().add(buttonsLayout);

        //creamos escena
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        return scene = new Scene(scrollPane, width,height);
    }

    public List<String> getRulesToSave(){
        List<String> rulesToSave = new ArrayList<>();
        //recuperamos elementos de cada id y armamos el string
        for (int i=1; i<ids+1; i++){
            //botones
            boolean beginFlag = ((RadioButton)this.scene.lookup("#bb"+String.valueOf(i))).isSelected();
            boolean containsFlag = ((RadioButton)this.scene.lookup("#bc"+String.valueOf(i))).isSelected();
            boolean endsFlag = ((RadioButton)this.scene.lookup("#be"+String.valueOf(i))).isSelected();
            //expresion
            String expresion = ((TextArea)this.scene.lookup("#e"+String.valueOf(i))).getText().replaceAll("\\s$","");
            //clasificacion
            String clasification = ((TextArea)this.scene.lookup("#c"+String.valueOf(i))).getText().replaceAll("\\s$","");
            if(expresion.equals("") || clasification.equals("")){
                continue;
            }else{           
                //convertimos y agg a array
                String rule = "";
                if(beginFlag){
                    rule += "^";
                    rule += expresion;
                    rule += ".*";
                    rule += " - ";
                    rule += clasification;
                }else if(containsFlag){
                    rule += ".*";
                    rule += expresion;
                    rule += ".*";
                    rule += " - ";
                    rule += clasification;
                }else if(endsFlag){
                    rule += ".*";
                    rule += expresion;
                    rule += "$";
                    rule += " - ";
                    rule += clasification;
                }
                System.out.println(rule);
                rulesToSave.add(rule);
            }
        }
        rulesToSave.forEach(System.out::println);
        return rulesToSave;
    }

    public Scene getScene(){
        return this.scene;
    }

    private int getOption(String line){
       if(line.startsWith("^")){
        return 1;
       }else if(line.startsWith(".*")){
        return 2;
       }else if(line.endsWith("$")){
        return 3;
       }else{
        return 0;
       }
    }

    private String parseExpresion(String expresion){
        if(expresion.startsWith("^")){
            return expresion.replace("^", "").replace(".*", "");
        }else if(expresion.startsWith(".*")){
            return expresion.replace(".*", "");
        }else if(expresion.endsWith("$")){
            return expresion.replace(".*","").replace("$", "");
        }else{
            return expresion;
        }
    }

        
}


    

