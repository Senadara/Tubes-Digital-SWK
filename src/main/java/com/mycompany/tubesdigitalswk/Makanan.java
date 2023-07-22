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
    int id, status;
    String nama;
    float harga;
    
    Makanan(int id, String nama, float harga, int status){
        this.id = id;
        this.nama = nama;
        this.harga = harga;
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
    
    String getStatus(){
        String stts;
        if(status == 1){
            stts = "Buka";
        }else{
            stts = "Tutup";
        }
        return stts;
    }
    
}
