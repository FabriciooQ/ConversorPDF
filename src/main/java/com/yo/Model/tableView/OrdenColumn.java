package com.yo.Model.tableView;

import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class OrdenColumn {
    private Text orden;
    private Button buttonMas;
    private Button buttonMenos;

    public OrdenColumn(){
        this.orden = new Text();
        this.buttonMas = new Button("+");
        this.buttonMenos = new Button("-");
    }

    public Text getOrden() {
        return orden;
    }

    public Button getButtonMas() {
        return buttonMas;
    }

    public Button getButtonMenos() {
        return buttonMenos;
    }

    public void setOrden(int number){
        this.orden.setText(String.valueOf(number));
    }
}
