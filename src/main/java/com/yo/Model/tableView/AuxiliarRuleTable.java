package com.yo.Model.tableView;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

public class AuxiliarRuleTable {
    private OrdenColumn orden;
    private Text expresion;
    private ComboBox<String> comboBox;
    private Text texto;

    public AuxiliarRuleTable(){
        this.orden =  new OrdenColumn();
        this.expresion = new Text();
        this.comboBox = new ComboBox<>();
        this.comboBox.getItems().add("Inicia");
        this.comboBox.getItems().add("Contiene");
        this.comboBox.getItems().add("Termina");
        this.comboBox.setValue("Inicia");
        this.texto = new Text();
    }

    public OrdenColumn getOrden() {
        return orden;
    }

    public Text getExpresion() {
        return expresion;
    }

    public ComboBox<String> getComboBox() {
        return comboBox;
    }

    public Text getTexto() {
        return texto;
    }

    public void setExpresionText(String expresion){
        this.expresion.setText(expresion);
    }

    public void setTextoText(String texto){
        this.texto.setText(texto);
    }

    public void setCondicion(String condicion){
        this.comboBox.setValue(condicion);
    }



    
    
}
