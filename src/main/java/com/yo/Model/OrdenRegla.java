package com.yo.Model;

import jakarta.persistence.Transient;

import com.yo.Model.Rule;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

@Entity
@Table(name="REGLA_POR_BANCO")
public class OrdenRegla {
    @Id 
    @SequenceGenerator(
        name = "REGLA_POR_BANCO_SEQ",
        sequenceName = "REGLA_POR_BANCO_SEQ",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "REGLA_POR_BANCO_SEQ"
    )
    private int id;

    private int orden;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="ID_REGLA")
    Rule regla;
    
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="ID_BANCO")
    Banco banco;

    @Override
    public String toString() {
        return "OrdenRegla [id=" + id + "orden=" + orden + "]";
    }

    public OrdenRegla(){

    }
   
    public OrdenRegla(int orden, Rule regla, Banco banco) {
        this.orden = orden;
        this.regla = regla;
        this.banco = banco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Rule getRegla() {
        return regla;
    }

    public void setRegla(Rule regla) {
        this.regla = regla;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

}
