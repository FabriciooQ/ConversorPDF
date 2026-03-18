package com.yo.pruebas;

import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.yo.Controller.DatabaseController;
import com.yo.Model.Banco;
import com.yo.Model.DatabaseAdministrator;
import com.yo.Model.OrdenRegla;
import com.yo.Model.Rule;
import com.yo.Model.TxtReader;

import jakarta.persistence.criteria.CriteriaQuery;

public class peueba {
    public void run() {

        DatabaseController controllerDB = new DatabaseController();

        List<OrdenRegla> reglasOrdenadas = controllerDB.getRulesInOrderPerBank(1);

        reglasOrdenadas.forEach(r->{
            System.out.println(r.toString());
        });
        
    
    }
    
}
