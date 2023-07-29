/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubesdigitalswk;

/**
 *
 * @author putra
 */
public class Customer {
    private String nama, noTelp;
    int idCustomer;
    
    Customer(String nama, String noTelp){
        this.nama = nama;
        this.noTelp = noTelp;
    }
    
    public String getNama(){
        return nama;
    }
    
    public String getNoTelp(){
        return noTelp;
    }
    
    public void setId(int idCustomer){
        this.idCustomer = idCustomer;
    }
    
    public int getId(){
        return idCustomer;
    }
    
}
