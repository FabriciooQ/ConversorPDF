package com.yo.Model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Table(name="REGLA")
public class Rule {
    @Id
    @SequenceGenerator(
        name = "REGLA_SEQ",
        sequenceName = "REGLA_SEQ",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "REGLA_SEQ"
    )
    private int id;
    private String expresion;
    private String texto;
    private int condicion;

    public Rule() {
        //TODO Auto-generated constructor stub
    }
    
    public Rule(int id, String expresion, String texto){
        this.id = id;
        this.expresion = expresion;
        this.texto = texto;

    }

    @Override
    public String toString() {
        return "Rule [id=" + id + ", expresion=" + expresion + ", condicion: "+ getCondicion() + ", texto=" + texto + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegularExpresion(){
        String start = null;
        String end = null;
        switch (this.condicion){
            case 0:
                start="^";
                end=".*";
                break;
            case 1:
                start=".*";
                end = ".*";
                break;
            case 2:
                start=".*";
                end="$";
                break;
        }
        String regExpresion = start + this.expresion + end;
        return regExpresion;
    }

    public String getExpresion() {
        return this.expresion;

    }

    public String getCondicion(){
        switch(condicion){
            case 0:
                return "Comienza";
            case 1:
                return "Contiene";
            case 2:
                return "Termina";
        }
        return null;
    }

    public void setExpresion(String expresion, String condicion) {
        this.expresion = expresion;
    }


    public void setExpresion(String expresion) {
        this.expresion = expresion;
    }

    public String getTexto() {
        return texto;
    }

    
    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setCondicion(String condicion) {
        switch (condicion) {
            case "Comienza":
                this.condicion=0;
                break;
            case "Contiene":
                this.condicion=1;
                break;
            case "Termina":
                this.condicion=2;
                break;
            default:
                break;
        }
    }

}
