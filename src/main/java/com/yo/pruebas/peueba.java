package com.yo.pruebas;

import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.yo.Model.Banco;
import com.yo.Model.DatabaseAdministrator;
import com.yo.Model.TxtReader;

public class peueba {
    public static void main(String[] args) {
        DatabaseAdministrator dbAdmin = new DatabaseAdministrator();
        Banco banco = new Banco("prueba");

        dbAdmin.saveBanco(banco);
    }
    
}
