package com.yo.View;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.GroupLayout.Alignment;

import com.yo.SceneManager;
import com.yo.Controller.DatabaseController;
import com.yo.Model.Banco;
import com.yo.Model.OrdenRegla;
import com.yo.Model.Rule;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class ParameterSceneTable {
    private DatabaseController dbController;
    private Set<OrdenRegla> reglasModificadas;
    @FXML
    Text parametrosTitle;
    @FXML
    TableView<OrdenRegla> table;
    @FXML
    TableColumn<OrdenRegla, Integer> columnaOrden;
    @FXML
    TableColumn<OrdenRegla, String> columnaExpresion; 
    @FXML
    TableColumn<OrdenRegla, String> columnaCondicion; 
    @FXML
    TableColumn<OrdenRegla, String> columnaTexto; 
    @FXML
    TableColumn<OrdenRegla, Void> columnaEliminar;
    @FXML
    Button buttonCancelar;
    @FXML
    Button buttonGuardar;
    @FXML
    Button buttonNuevaRegla;


    public ParameterSceneTable(){
        this.dbController = DatabaseController.getDatabaseController();
        this.reglasModificadas = new HashSet<>();
    }

    private void actualizarOrdenes(List<OrdenRegla> ordenes, OrdenRegla actual, int incremento){
        boolean flag=false;
        if(actual.getOrden()<=1 && incremento<0){
            return;
        }
        for(OrdenRegla o:ordenes){

            if(o!=actual && o.getOrden() == actual.getOrden()+incremento){
                o.setOrden(actual.getOrden());
                reglasModificadas.add(o);
                reglasModificadas.add(actual);
                actual.setOrden(actual.getOrden()+incremento);
                flag=true;
                break;
            }
        }
        if(actual.getOrden()+incremento>=1 && !flag){
            actual.setOrden(actual.getOrden()+incremento);
            reglasModificadas.add(actual);

        }
    }

    private void actualizarExpresion(OrdenRegla r, String value){
        if(r.getRegla().getExpresion()==null || !r.getRegla().getExpresion().equals(value)){
            r.getRegla().setExpresion(value);
            reglasModificadas.add(r);
        }
    }

    private void borrarOrdenRegla(OrdenRegla r, List<OrdenRegla> ordenes){
        int orden = r.getOrden();
        ordenes.remove(r);
        //con las ordenes restantes modificamos los orden regla que quedan
        ordenes.forEach(o->{
            if(o.getOrden()>orden){
                o.setOrden(o.getOrden()-1);
                reglasModificadas.add(o);
            }
        });
        dbController.removeOrden(r);
    }
    
    private void actualizarTexto(OrdenRegla r, String value){
        if(r.getRegla().getTexto()==null || !r.getRegla().getTexto().equals(value)){
            r.getRegla().setTexto(value);
            reglasModificadas.add(r);
        }
    }

    private void actualizarCondicion(OrdenRegla r, String value){
        if(!r.getRegla().getCondicion().equals(value)){
            r.getRegla().setCondicion(value);
            reglasModificadas.add(r);
        }
    }


    @FXML
    public void cancelar(){
        SceneManager.switchTo("MainScene");
    }

    @FXML
    public void guardar(){
        if(reglasModificadas.size() > 0){
            dbController.modifyOrdenes(reglasModificadas);
        }

        reglasModificadas.clear();
    }

    @FXML
    public void agregarRegla(){
        //buscamos el orden mas alto
        int mayor = 0;
        List<OrdenRegla> ordenes = table.getItems();
        for(OrdenRegla r:ordenes){
            if(r.getOrden() > mayor){
                mayor = r.getOrden();
            }
        }
        //creamos nuevo OrdenRegla
        Banco banco = dbController.getBancoActual();
        Rule regla = new Rule();
        OrdenRegla nueva = new OrdenRegla(mayor+1, regla, banco);        

        reglasModificadas.add(nueva);
        
        table.getItems().add(nueva);
        table.sort();
    }

    @FXML
    public void initialize(){
        //SETEAMOS TITULO DEL BANCO
        parametrosTitle.setText("Parametros " + dbController.getBancoActual().getNombre());

        //AJUSTES TABLA
        table.setSelectionModel(null);

        //ORDEN
        this.columnaOrden.setPrefWidth(170);
        this.columnaOrden.setMinWidth(170);
        this.columnaOrden.setMaxWidth(170);
        //setea el valor de la celda
        this.columnaOrden.setCellValueFactory(data ->{
            return new SimpleIntegerProperty(Integer.valueOf(data.getValue().getOrden())).asObject();
        });
        //setea el aspecto de la celda
        this.columnaOrden.setCellFactory(col ->{
            return new TableCell<OrdenRegla, Integer>(){
                TextField ordenText = new TextField();
                Button buttonArriba = new Button();
                Button buttonAbajo = new Button();
                HBox hbox = new HBox(ordenText, buttonAbajo, buttonArriba);
                
                {
                    hbox.setSpacing(7);
                    hbox.setAlignment(Pos.CENTER);

                    ordenText.setPrefHeight(1);
                    ordenText.setDisable(true);
                    ordenText.setMaxWidth(65);
                    ordenText.setAlignment(Pos.CENTER);
                    //boton anterior
                    buttonAbajo.setOnAction(e->{
                        OrdenRegla r = this.getTableRow().getItem();
                        List<OrdenRegla> ordenes = table.getItems();
                        actualizarOrdenes(ordenes, r, -1);
                        table.refresh();
                        table.sort();
                        
                        
                    });
                    ImageView flechaAbajo = new ImageView(new Image(getClass().getResourceAsStream("/img/flecha arriba.png")));
                    flechaAbajo.setFitWidth(10);
                    flechaAbajo.setFitHeight(10);
                    flechaAbajo.setPreserveRatio(true);
                    this.buttonAbajo.setGraphic(flechaAbajo);

                    //boton siguiente
                    buttonArriba.setOnAction(e->{
                        OrdenRegla r = this.getTableRow().getItem();
                        List<OrdenRegla> ordenes = table.getItems();
                        actualizarOrdenes(ordenes, r, 1);
                        table.refresh();
                        table.sort();
                    });
                    ImageView flechaArriba = new ImageView(new Image(getClass().getResourceAsStream("/img/flecha abajo.png")));
                    flechaArriba.setFitWidth(10);
                    flechaArriba.setFitHeight(10);
                    flechaArriba.setPreserveRatio(true);
                    this.buttonArriba.setGraphic(flechaArriba);


                }

                @Override
                protected void updateItem(Integer orden, boolean empty){
                    super.updateItem(orden, empty);
                    if(empty){
                        setGraphic(null);
                    }else{
                        ordenText.setText(String.valueOf(orden));
                        setGraphic(hbox);
                        setAlignment(Pos.CENTER);
                    }
                }
            };
        });


        //EXPRESION
        this.columnaExpresion.setPrefWidth(350);
        this.columnaExpresion.setMinWidth(250);
        this.columnaExpresion.setMaxWidth(450);
        //Valor de cada celda
        this.columnaExpresion.setCellValueFactory(data->{
            return new SimpleStringProperty(data.getValue().getRegla().getExpresion());
        });

        //contruccion de celda
        this.columnaExpresion.setCellFactory(col->{
            return new TableCell<OrdenRegla, String>(){
                TextField textExpresion = new TextField();
                {
                    textExpresion.setPrefHeight(1);
                    textExpresion.textProperty().addListener(
                        (obs, oldVal, newVal)->{
                            OrdenRegla r = getTableRow().getItem();
                            actualizarExpresion(r, newVal);
                        }   
                    );
                }
                @Override
                protected void updateItem(String expresion, boolean empty) {
                    super.updateItem(expresion, empty);
                    if (empty){
                        setGraphic(null);
                        return;
                    }
                    textExpresion.setText(expresion);
                    setGraphic(textExpresion);
                    setAlignment(Pos.CENTER);
                }
            };
        });
        
        //CONDICION
        this.columnaCondicion.setPrefWidth(130);
        this.columnaCondicion.setMinWidth(130);
        this.columnaCondicion.setMaxWidth(130);
        //valor de cada celda
        this.columnaCondicion.setCellValueFactory(data->{
            return new SimpleStringProperty(data.getValue().getRegla().getCondicion());
        });
        
        //construccion de cada celda
        this.columnaCondicion.setCellFactory(col ->{
            return new TableCell<OrdenRegla, String>(){
                ComboBox<String> comboBox = new ComboBox<>();
                {
                    comboBox.getItems().addAll(new String[] {"Comienza", "Contiene", "Termina"});
                    comboBox.valueProperty().addListener(
                        (obs, oldValue,newValue)->{
                            OrdenRegla r = getTableRow().getItem();
                            String condicion = comboBox.getValue();
                            actualizarCondicion(r, condicion);
                        }
                    );
                }
                @Override
                public void updateItem(String opcion, boolean empty){
                    if(empty){
                        setGraphic(null);
                        return;
                    }
                    this.comboBox.setValue(opcion);
                    setGraphic(comboBox);
                    setAlignment(Pos.CENTER);
                }
            };
        });

        //TEXTO
        this.columnaTexto.setPrefWidth(350);
        this.columnaTexto.setMinWidth(250);
        this.columnaTexto.setMaxWidth(450);
        //valor de celda
        this.columnaTexto.setCellValueFactory(data->{
            return new SimpleStringProperty(data.getValue().getRegla().getTexto());
        });

        //construccion de la celda
        this.columnaTexto.setCellFactory(col ->{
            return new TableCell<OrdenRegla, String>(){
                TextField texto = new TextField();
                {
                    texto.setPrefHeight(1);
                    texto.textProperty().addListener(
                        (obs, oldVal, newVal)->{
                            OrdenRegla r = getTableRow().getItem();
                            actualizarTexto(r, newVal);
                        }
                    );
                }
                @Override
                public void updateItem(String textoNuevo, boolean empty){
                    super.updateItem(textoNuevo, empty);
                    if(empty){
                        setGraphic(null);
                        return;
                    }
                    texto.setText(textoNuevo);
                    setGraphic(texto);
                    setAlignment(Pos.CENTER);
                }
            };
        });

        //ELIMINAR
        this.columnaEliminar.setPrefWidth(80);
        this.columnaEliminar.setMinWidth(80);
        this.columnaEliminar.setMaxWidth(80);
        //Construccion de la celda
        columnaEliminar.setCellFactory(col->{
            return new TableCell<OrdenRegla,Void>(){
                Button botonBorrar = new Button();
                {
                    botonBorrar.setOnAction(e->{
                        OrdenRegla r = getTableRow().getItem();
                        List<OrdenRegla> ordenes = table.getItems();
                        borrarOrdenRegla(r, ordenes);
                        table.refresh();
                        table.sort();
                    });
                    ImageView imageBorrar = new ImageView(new Image(getClass().getResourceAsStream("/img/borrar blanco.png")));
                    imageBorrar.setFitWidth(25);
                    imageBorrar.setFitHeight(25);
                    imageBorrar.setPreserveRatio(true);
                    this.botonBorrar.setGraphic(imageBorrar);

                }
                
                @Override
                protected void updateItem(Void item, boolean empty){
                    if(empty){
                        setGraphic(null);
                        return;
                    }
                    setGraphic(botonBorrar);
                    setAlignment(Pos.CENTER);
                }
            };
        });
        

        //RECUPERAMOS REGLAS Y SETEAMOS EN LA TABLA
        List<OrdenRegla> rules = dbController.getRulesInOrderPerBank();
        System.out.println("Datos recuperados de la BD:" + rules.size());
        ObservableList<OrdenRegla> rulesTable = FXCollections.observableList(rules);
        this.table.setItems(rulesTable);
        //ordenamos por columna orden
        columnaOrden.setSortType(TableColumn.SortType.ASCENDING);
        table.getSortOrder().clear();
        table.getSortOrder().add(columnaOrden);
        table.sort();
    }

}
