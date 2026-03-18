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

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
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
        title.getStyleClass().add("title");
        HBox titleLayout = new HBox(title);
        titleLayout.setAlignment(Pos.CENTER);
        root.getChildren().add(titleLayout);

        //cabeceras tabla
        Text expresionText = new Text("Expresion");
        expresionText.getStyleClass().add("subtitle");
        Text clasificationText = new Text("Clasificacion");
        clasificationText.getStyleClass().add("subtitle");
        HBox tableHeader = new HBox(120, expresionText, clasificationText);
        tableHeader.setAlignment(Pos.CENTER);
        root.getChildren().add(tableHeader);
        
        //box parametros
        VBox paramsLayout = new VBox(9);
        //map que guarda los vbox de cada parametro
        Map<Integer, VBox> map = new HashMap<>();
        //agregamos por cada parametro en el .txt una entrada
        for(int i=0; i<this.initialRules.size(); i++){
            //contador para ids
            ids++;
            //box horizontal de arriba
            TextArea expresionArea = new TextArea();
            expresionArea.getStyleClass().add("inputs");
            expresionArea.setText(parseExpresion(this.initialRules.get(i)[0]));
            expresionArea.setMaxHeight(1);
            TextArea clasificationArea = new TextArea();
            clasificationArea.getStyleClass().add("inputs");
            clasificationArea.setText(this.initialRules.get(i)[1]);
            clasificationArea.setMaxHeight(1);
            Button delete = new Button();
            delete.getStyleClass().addAll("button");
            Image logo = new Image(this.getClass().getResourceAsStream("/img/borrar blanco.png"));
            ImageView imageView = new ImageView(logo);
            imageView.setFitWidth(25);
            imageView.setFitHeight(25);
            imageView.setPreserveRatio(true);
            delete.setGraphic(imageView); 
            delete.setPadding(new Insets(0));
            delete.setOnAction(ev->{
                VBox auxBox = (VBox)((Button)ev.getSource()).getParent().getParent().getParent();
                paramsLayout.getChildren().remove(auxBox);
                map.remove(Integer.valueOf(auxBox.getId()));
            });
            delete.setPadding(new Insets(20));
            HBox auxLayout = new HBox (25, clasificationArea, delete);
            HBox inputsLayout = new HBox(50, expresionArea, auxLayout);
            //box horizontal de abajo
            ToggleGroup group = new ToggleGroup();
            RadioButton begin = new RadioButton("Empieza");
            begin.getStyleClass().add("radio-button");
            RadioButton contains = new RadioButton("Contiene");
            contains.getStyleClass().add("radio-button");
            RadioButton ends = new RadioButton("Termina");
            ends.getStyleClass().add("radio-button");
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
                    break;
                default:
                    break;
            }
            HBox optionsLayout = new HBox(5, begin, contains, ends);
            optionsLayout.setAlignment(Pos.CENTER_LEFT);
            //agregamos a los vbox
            VBox aux = new VBox(5, inputsLayout, optionsLayout);
            aux.setId(String.valueOf(ids));
            map.put(ids,aux);
            paramsLayout.getChildren().add(aux);
        } 
        root.getChildren().add(paramsLayout);

        //boton de agregar regla
        Button addRule = new Button("Agregar");
        addRule.getStyleClass().addAll("button","secondary");
        addRule.setOnAction(e -> {
            //sumamos en 1 el id de reglas
            this.ids++;
            //box horizontal de arriba
            TextArea expresionArea = new TextArea();
            expresionArea.getStyleClass().add("inputs");
            expresionArea.setMaxHeight(1);
            TextArea clasificationArea = new TextArea();
            clasificationArea.getStyleClass().add("inputs");
            clasificationArea.setMaxHeight(1);
            Button delete = new Button();
            delete.getStyleClass().addAll("button");
            Image logo = new Image(this.getClass().getResourceAsStream("/img/borrar blanco.png"));
            ImageView imageView = new ImageView(logo);
            imageView.setFitWidth(25);
            imageView.setFitHeight(25);
            imageView.setPreserveRatio(true);
            delete.setGraphic(imageView); 
            delete.setOnAction(ev->{
                VBox auxBox = (VBox)((Button)ev.getSource()).getParent().getParent().getParent();
                paramsLayout.getChildren().remove(auxBox);
                map.remove(Integer.valueOf(auxBox.getId()));
            });
            delete.setPadding(new Insets(20));
            HBox auxLayout = new HBox (25, clasificationArea, delete);
            HBox inputsLayout = new HBox(50, expresionArea, auxLayout);
            //box horizontal de abajo
            ToggleGroup group = new ToggleGroup();
            RadioButton begin = new RadioButton("Empieza");
            begin.getStyleClass().add("radio-button");
            begin.setSelected(true);
            RadioButton contains = new RadioButton("Contiene");
            contains.getStyleClass().add("radio-button");
            RadioButton ends = new RadioButton("Termina");
            ends.getStyleClass().add("radio-button");
            begin.setToggleGroup(group);
            contains.setToggleGroup(group);
            ends.setToggleGroup(group);
            //agg
            HBox optionsLayout = new HBox(5, begin, contains, ends);
            optionsLayout.setAlignment(Pos.CENTER_LEFT);
            VBox aux = new VBox(5, inputsLayout, optionsLayout);
            aux.setId(String.valueOf(ids));
            map.put(ids,aux);
            //System.out.println(ids);
            paramsLayout.getChildren().add(aux);
        });
        HBox addLayout = new HBox(addRule);
        addLayout.setAlignment(Pos.CENTER);
        root.getChildren().add(addLayout);

        //botones cancelar y guardar
        Button cancelButton = new Button("Cancelar");
        cancelButton.getStyleClass().add("button");        
        cancelButton.setOnAction(e->{
            stage.setScene(rootScene);
        });
        Button saveButton = new Button("Guardar");
        saveButton.getStyleClass().add("button");
        saveButton.setOnAction(e->{
            cancelButton.setDisable(true);
            paramsLayout.setDisable(true);
            addRule.setDisable(true);
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() {
                    List<String> rulesToSave = getRulesToSave(map); 
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
        scrollPane.setPadding(new Insets(20));
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("scroll-pane");
        scene = new Scene(scrollPane, width,height);
        //apliamos css
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        //retornamos
        return scene;
    }

    public List<String> getRulesToSave(Map<Integer,VBox> map){
        List<String> rulesToSave = new ArrayList<>();
        //recuperamos elementos de cada id y armamos el string
        map.forEach((k,v)->{
            String expresion = ((TextArea)((HBox)v.getChildren().get(0)).getChildren().get(0)).getText();
            String clasification = ((TextArea)(((HBox)((HBox)v.getChildren().get(0)).getChildren().get(1))).getChildren().get(0)).getText();
            //sacamos inicio, contiene, termina
            boolean beginFlag =((RadioButton)((HBox)v.getChildren().get(1)).getChildren().get(0)).isSelected();
            boolean containsFlag =((RadioButton)((HBox)v.getChildren().get(1)).getChildren().get(1)).isSelected();
            boolean endsFlag =((RadioButton)((HBox)v.getChildren().get(1)).getChildren().get(2)).isSelected();
            if(!expresion.equals("") && !clasification.equals("")){
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
                rulesToSave.add(rule);
            }
        });
        return rulesToSave;
    }

    public Scene getScene(){
        return this.scene;
    }

    private int getOption(String line){
        if(line.endsWith("$")){
        return 3;
        }else if(line.startsWith("^")){
        return 1;
       }else if(line.startsWith(".*")){
        return 2;
       }else{
        return 0;
       }
    }

    private String parseExpresion(String expresion){
        if(expresion.endsWith("$")){
            return expresion.replace(".*","").replace("$", "");
        }else if(expresion.startsWith("^")){
            return expresion.replace("^", "").replace(".*", "");
        }else if(expresion.startsWith(".*")){
            return expresion.replace(".*", "");
        }else{
            return expresion;
        }
    }

        
}


    

