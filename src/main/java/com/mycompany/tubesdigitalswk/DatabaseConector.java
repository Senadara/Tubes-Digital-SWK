/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubesdigitalswk;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author putra
 */
public class DatabaseConector {
    Connection connection = null;
    
    public void openConnection(){
    try{
        Class.forName("com.mysql.cj.jdbc.Driver");
		connection = DriverManager.getConnection(                    				
                        "jdbc:mysql://localhost:3306/DigitalSWK", 
			"root", 
			""
		);  
        } catch (Exception exception) {
		System.out.println(exception);
	}
}
    public void viewData(){
        
    }
}

