package com.yo.Controller;

import java.util.List;

import com.yo.Model.DatabaseAdministrator;
import com.yo.Model.OrdenRegla;
import com.yo.Model.Rule;


public class DatabaseController{
    private DatabaseAdministrator dbAdmin;

    public DatabaseController(){
        this.dbAdmin = new DatabaseAdministrator();
    }

    public void cambiarOrden(int orden){

    }

    public void closeDB(){
        dbAdmin.closeSession();
    }

    public List<OrdenRegla> getRulesInOrderPerBank(int idBank){
        List<OrdenRegla> reglasOrdenadas = this.dbAdmin.getOrdenReglasPerBank(idBank);
        if(reglasOrdenadas.size() > 0){
            return reglasOrdenadas;
        }else{
            return null;
        }
    }
}