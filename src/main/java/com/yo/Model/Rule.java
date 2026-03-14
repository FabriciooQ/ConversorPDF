package com.yo.Model;

public class Rule {
    private int id;
    private String expresion;
    private String texto;
    private Banco banco;

    public Rule(int id, String expresion, String texto, Banco banco){
        this.id = id;
        this.expresion = expresion;
        this.texto = texto;
        this.banco = banco;

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

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }
    

}
