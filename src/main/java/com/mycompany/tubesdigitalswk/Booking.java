/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubesdigitalswk;

/**
 *
 * @author DEO
 */
public class Booking {
    int id, nomorMeja, jumlahKursi, status;
    
    Booking(int id, int nomorMeja, int jumlahKursi, int status){
        this.id = id;
        this.nomorMeja = nomorMeja;
        this.jumlahKursi = jumlahKursi;
        this.status = status;
    }
    
    int getID(){
        return id;
    }
    
    int getNomorMeja(){
        return nomorMeja;
    }
    
    int getJumlahKursi(){
        return jumlahKursi;
    }
        
    String getStatus(){
        String stts;
        if(status == 1){
            stts = "Tersedia";
        }else{
            stts = "Booked";
        }
        return stts;
    }
    
    String getStatusMeja(){
        String stts;
        if(status == 1){
            stts = "Tersedia";
        } else if(status == 2){
            stts = "Selesai Makan";
        }else{
            stts = "Digunakan";
        }
        return stts;
    }
    
}
