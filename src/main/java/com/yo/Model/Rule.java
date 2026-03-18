package com.yo.Model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="REGLA")
public class Rule {
    @Id
    @GeneratedValue
    private int id;
    private String expresion;
    private String texto;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "regla")
    List<OrdenRegla> ordenes;


    
    @Override
    public String toString() {
        return "Rule [id=" + id + ", expresion=" + expresion + ", texto=" + texto + "]";
    }

    public Rule(int id, String expresion, String texto){
        this.id = id;
        this.expresion = expresion;
        this.texto = texto;

    }
    
    public Rule() {
        //TODO Auto-generated constructor stub
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExpresion() {
        return expresion;
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

}
