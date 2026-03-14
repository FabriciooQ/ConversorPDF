package com.yo.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class DatabaseAdministrator{
    private SessionFactory factory;
    private Configuration configuration;

    public DatabaseAdministrator(){
        this.configuration = new Configuration();
        this.configuration.addAnnotatedClass(com.yo.Model.Rule.class);
        this.configuration.addAnnotatedClass(com.yo.Model.Banco.class);
        this.configuration.configure();
        this.factory = configuration.buildSessionFactory();
    }

    public void saveBanco(Banco banco){
        Session session = this.factory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(banco);
        transaction.commit();
        session.close();
    }

    public void closeSession(){
        this.factory.close();
    }

    public boolean deleteRule(Rule r){
        if(r==null){
            return false;
        }
        Transaction transaction=null;
        Session session = null;
        try{
            session = this.factory.openSession();
            transaction = session.beginTransaction();
            session.remove(r);
            transaction.commit();
        }catch(Exception e){
            transaction.rollback();
            e.printStackTrace();
        }finally{
            session.close();
        }
        return true;
    }

    public List<Rule> getRules(){
        Session session = this.factory.openSession();
        List<Rule> rules = session.createQuery("FROM Rule", Rule.class).list();
        session.close();
        if(rules.size() > 0){
            return rules;
        }else{
            return null;
        }

    }

    public List<Banco> getBancos(){
        Session session = this.factory.openSession();
        List<Banco> bancos = session.createQuery("FROM Banco", Banco.class).list();
        session.close();
        return bancos;

    }

}

