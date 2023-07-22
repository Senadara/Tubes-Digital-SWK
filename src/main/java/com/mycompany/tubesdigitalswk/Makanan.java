/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubesdigitalswk;

/**
 *
 * @author putra
 */
public class Makanan {
    int id, idStan, status;
    String nama;
    float harga;
    
    Makanan(int id, String nama, float harga,int idStan, int status){
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.idStan = idStan;
        this.status = status;
    }
    
    int getID(){
    return id;
    }
    
    String getNama(){
        return nama;
    }
    
    float getHarga(){
        return harga;
    }
    
    int getIdStan(){
        return idStan;
    }
    
    String getStatus(){
        String stts;
        if(status == 1){
            stts = "Tersedia";
        }else{
            stts = "Habis";
        }
        return stts;
    }
    
}
