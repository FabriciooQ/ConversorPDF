package com.yo.Model;

import com.yo.Model.Rule;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="REGLA_POR_BANCO")
public class OrdenRegla {
    @Id 
    @GeneratedValue
    private int id;

    private int orden;

    @ManyToOne
    @JoinColumn(name="ID_REGLA")
    Rule regla;
    
    @ManyToOne
    @JoinColumn(name="ID_BANCO")
    Banco banco;
    
    @Override
    public String toString() {
        return "OrdenRegla [id=" + id + "orden=" + orden + "]";
    }

    public OrdenRegla(){

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


}
