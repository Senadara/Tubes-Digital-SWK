/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubesdigitalswk;

/**
 *
 * @author putra
 */
public class Meja {
    String idMeja, no, kursi;
    
    Meja(String idMeja, String no, String Kursi){
        this.idMeja = idMeja;
        this.no = no;
        this.kursi = kursi;
    }
    
    public String getIdMeja(){
        return idMeja;
    }
    
    public String getNo(){
        return no;
    }
        
        public String getKursi(){
        return kursi;
    }
    
}
