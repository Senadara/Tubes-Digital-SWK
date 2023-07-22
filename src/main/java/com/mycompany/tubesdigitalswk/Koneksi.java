/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubesdigitalswk;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author putra
 */
public class Koneksi {
    private static Connection con;
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_NAME = "digitalswk";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
    private static final String DB_UNAME = "root";
    private static final String DB_PASS = "";
    
    public static Connection bukaKoneksi(){
        if(con == null){
            try{
                Class.forName(DB_DRIVER);
                con = DriverManager.getConnection(DB_URL, DB_UNAME, DB_PASS);
            } catch(ClassNotFoundException e){
                System.err.format("class not found");
            } catch(SQLException e){
                System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        return con;
    }        
}

