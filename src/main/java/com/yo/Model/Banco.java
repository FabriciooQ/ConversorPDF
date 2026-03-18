package com.yo.Model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "BANCO")
public class Banco {
    @Id 
    @GeneratedValue
    private int id;

    @Column(name="NOMBRE")
    private String nombre;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "banco")
    List<OrdenRegla> listaOrdenes;

    @Override
    public String toString() {
        return "Banco [id=" + id + ", nombre=" + nombre + "]";
    }

    public Banco(){
        
    }

    public Banco(String nombre){
        this.nombre = nombre;
    }

    public Banco(int id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
}
