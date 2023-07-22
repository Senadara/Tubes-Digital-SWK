/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubesdigitalswk;

/**
 *
 * @author putra
 */
public class Stan {
    int id, nomor, status;
    String nama;
    
    Stan(int id,int nomor,String nama,int status){
        this.id = id;
        this.nomor = nomor;
        this.nama = nama;
        this.status = status;
    }
    
    int getID(){
        return id;
    }
    
    int getNomor(){
        return nomor;
    }
    
    String getNama(){
        return nama;
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
