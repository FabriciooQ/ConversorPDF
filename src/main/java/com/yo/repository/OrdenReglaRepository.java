package com.yo.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.yo.Model.Banco;
import com.yo.Model.OrdenRegla;
import com.yo.Model.Rule;

public class OrdenReglaRepository {
    public static OrdenReglaRepository instance = null;
    private SessionFactory factory;
    private Configuration configuration;

    public static OrdenReglaRepository getInstance(){
        if(instance == null){
            instance = new OrdenReglaRepository();
            return instance;
        }else{
            return instance;
        }
    }

    private OrdenReglaRepository(){
        this.configuration = new Configuration();
        this.configuration.addAnnotatedClass(OrdenRegla.class);
        this.configuration.addAnnotatedClass(Banco.class);
        this.configuration.addAnnotatedClass(Rule.class);
        this.configuration.configure();
        this.factory = configuration.buildSessionFactory();
    }

    public List<OrdenRegla> getReglasPorBancoOrdenadas(int idBank){
        Session session = this.factory.openSession();
        List<OrdenRegla> ordenesRegla = session.createSelectionQuery("SELECT o FROM OrdenRegla o WHERE o.banco.id = :idBank ORDER BY o.orden", OrdenRegla.class)
            .setParameter("idBank", idBank)
            .list();
        session.close();
        return ordenesRegla;
    }

    public void modifyRegla(OrdenRegla o){
        Session session = null;
        Transaction transaction = null;
        try{
            session = this.factory.openSession();
            transaction = session.beginTransaction();
            session.merge(o);
            transaction.commit();

        }catch(Exception e){
            transaction.rollback();
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    public void removeOrden(OrdenRegla o){
        Session session = null;
        Transaction transaction = null;
        try{
            session = this.factory.openSession();
            transaction = session.beginTransaction();
            session.remove(o);
            transaction.commit();
        }catch(Exception e){
            transaction.rollback();
            e.printStackTrace();
        }finally{
            session.close();
        } 
    }

    public static void close(){
        if(instance == null){
            return;
        }else{
            instance.factory.close();
        }
    }
}
