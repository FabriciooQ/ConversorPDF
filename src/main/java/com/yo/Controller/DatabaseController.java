package com.yo.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.yo.Model.Banco;
import com.yo.Model.DatabaseAdministrator;
import com.yo.Model.OrdenRegla;
import com.yo.Model.Rule;


public class DatabaseController{
    private static DatabaseController databaseController = null;
    private DatabaseAdministrator dbAdmin;
    //id del banco que se quieren ver los parametros, seteado en mainScene
    private Banco bancoActual = null;

    public static DatabaseController getDatabaseController(){
        if(databaseController == null){
            databaseController = new DatabaseController();
            return databaseController;
        }else{
            return databaseController;
        }
    }

    private DatabaseController(){
        this.dbAdmin = DatabaseAdministrator.getInstance();
        this.setBancoActual("Galicia");
    }

    
    public String[] getNombresBancos(){
        List<Banco> bancos = this.dbAdmin.getBancos();
        if(bancos.size() == 0){
            return null;
        }else{
            List<String> nombresBancos = new ArrayList<>();
            bancos.forEach(b -> {
                nombresBancos.add(b.getNombre());
            });
            return nombresBancos.toArray(new String[0]);
        }
    }
    
    public List<OrdenRegla> getRulesInOrderPerBank(){
        List<OrdenRegla> reglasOrdenadas = this.dbAdmin.getOrdenReglasPerBank(this.bancoActual.getId());
        if(reglasOrdenadas.size() > 0){
            return reglasOrdenadas;
        }else{
            return null;
        }
    }

    public void setBancoActual(String nombre) {
        this.bancoActual = this.dbAdmin.getBancoByName(nombre);
    }

    public Banco getBancoActual(){
        return this.bancoActual;
    }
    
    public void modifyOrdenes(Set<OrdenRegla> ordenes){
        ordenes.forEach(o->{
            dbAdmin.modifyOrdenRegla(o);
        });
    }


    public void removeOrden(OrdenRegla r){
        dbAdmin.removeOrden(r);
    }

    public void closeDB(){
        dbAdmin.closeSession();
    }

    

}