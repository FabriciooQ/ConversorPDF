package com.yo.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.yo.Model.Banco;
import com.yo.Services.BancoServices;
import com.yo.Services.OrdenReglaServices;
import com.yo.Model.OrdenRegla;
import com.yo.Model.Rule;


public class DatabaseController{
    private static DatabaseController databaseController = null;
    private OrdenReglaServices ordenReglaService;
    private BancoServices bancoServices;
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
        this.ordenReglaService = OrdenReglaServices.getInstance();
        this.bancoServices = bancoServices.getInstance();
        this.setBancoActual("Galicia");
    }

    
    public String[] getNombresBancos(){
        List<Banco> bancos = this.bancoServices.getBancos();
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
        List<OrdenRegla> reglasOrdenadas = this.ordenReglaService.getReglasBancos(bancoActual.getId());
        if(reglasOrdenadas.size() > 0){
            return reglasOrdenadas;
        }else{
            return null;
        }
    }

    public void setBancoActual(String nombre) {
        this.bancoActual = this.bancoServices.getBancoByName(nombre);

    }

    public void setBancoActual(Banco b) {
        this.bancoActual = b;
    }

    public Banco getBancoPorId(int id){
        Banco banco = this.bancoServices.getBanco(id);
        return banco;
    }

    public Banco getBancoActual(){
        return this.bancoActual;
    }
    
    public void modifyOrdenes(Set<OrdenRegla> ordenes){
        ordenes.forEach(o->{
            this.ordenReglaService.cambiarOrden(o);
        });
    }


    public void removeOrden(OrdenRegla O){
        this.ordenReglaService.removeOrden(O);
    }

    

}