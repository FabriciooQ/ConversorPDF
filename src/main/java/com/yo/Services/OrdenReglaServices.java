package com.yo.Services;

import java.util.List;

import com.yo.Model.OrdenRegla;
import com.yo.repository.OrdenReglaRepository;

public class OrdenReglaServices {
    public static OrdenReglaServices instance = null;
    private OrdenReglaRepository ordenReglaRepository;
    
    public static OrdenReglaServices getInstance(){
        if(instance == null){
            instance = new OrdenReglaServices();
            return instance;
        }else{
            return instance;
        }
    }

    private OrdenReglaServices(){
        this.ordenReglaRepository = OrdenReglaRepository.getInstance();
    }

    //CAMBIAR PARA QUE LA LOGICA DEL CAMBIO ESTE ACA NO EN EL FRONTEND SI SE PUEDE
    public void cambiarOrden(OrdenRegla o){
        if(o == null){
            return;
        }
        this.ordenReglaRepository.modifyRegla(o);
    }

    public List<OrdenRegla> getReglasBancos(int id){
        return this.ordenReglaRepository.getReglasPorBancoOrdenadas(id);
    }

    public void removeOrden(OrdenRegla o){
        if(o == null){
            return;
        }
        this.ordenReglaRepository.removeOrden(o);
    }


}
