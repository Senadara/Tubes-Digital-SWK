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
    private String nama;
    private long noTelp;
    
    Customer(String nama, long noTelp){
        this.nama = nama;
        this.noTelp = noTelp;
    }
    
    public String getNama(){
        return nama;
    }
    
    public long noTelp(){
        return noTelp;
    }
    
    
    
}