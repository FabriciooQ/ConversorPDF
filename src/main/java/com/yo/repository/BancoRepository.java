package com.yo.repository;

import org.hibernate.SessionFactory;

import java.util.List;

import javax.management.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.SelectionQuery;

import com.yo.Model.Banco;

public class BancoRepository {
    private static BancoRepository instance = null;
    private Configuration configuration;
    private SessionFactory factory;

    public static BancoRepository getInstance(){
        if(instance == null){
            instance = new BancoRepository();
            return instance;
        }else{
            return instance;
        }
    }

    private BancoRepository(){
        this.configuration = new Configuration();
        this.configuration.addAnnotatedClass(Banco.class);
        this.configuration.configure();
        this.factory = configuration.buildSessionFactory();
    }
    
    public List<Banco> getBancos(){
        Session session = factory.openSession();
        List<Banco> bancos = session.createSelectionQuery("FROM Banco", Banco.class).list();
        session.close();
        return bancos;
    }

    public Banco getBanco(int id){
        Session session = factory.openSession();
        Banco banco = session.createSelectionQuery("FROM Banco where id = :idBank", Banco.class)
            .setParameter("idBank", id)
            .uniqueResult();
        session.close();
        return banco;
    }

    public Banco getBancoByName(String name){
        Session session = factory.openSession();
        Banco banco = session.createSelectionQuery("SELECT b FROM Banco b WHERE b.nombre like :nombre", Banco.class)
            .setParameter("nombre", name)
            .uniqueResult();
        session.close();
        return banco;
    }

    public static void close(){
        if(instance == null){
            return;
        }else{
            instance.factory.close();
        }
    }
}
