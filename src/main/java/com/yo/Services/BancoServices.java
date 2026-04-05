package com.yo.Services;
import java.util.List;
import com.yo.Model.Banco;
import com.yo.repository.BancoRepository;

public class BancoServices {
    public static BancoServices instance = null;
    private BancoRepository bancoRepository;

    public static BancoServices getInstance(){
        if(instance == null){
            instance = new BancoServices();
            return instance;
        }else{
            return instance;
        }
    }

    private BancoServices(){
        this.bancoRepository = BancoRepository.getInstance();
    }

    public List<Banco> getBancos(){
        return bancoRepository.getBancos();
    }

    public Banco getBanco(int id){
        return bancoRepository.getBanco(id);
    }

    public Banco getBancoByName(String name){
        return bancoRepository.getBancoByName(name);
    }
}
