/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubesdigitalswk;

/**
 *
 * @author putra
 */
public class Keranjang {
    int idStan, idMakan, idMinum, jumlahBeli;
    String namaMakanan, catatan;
    float harga;

    
    
    Keranjang(int idStan, int idMakan, String namaMakanan, int jumlahBeli, float harga, String catatan){
    this.idStan = idStan;
    this.namaMakanan = namaMakanan;
    this.jumlahBeli = jumlahBeli;
    this.harga = harga;
    this.catatan = catatan;
    this.idMakan = idMakan;
    }
    
    int getIdStan(){
        System.out.println(idStan);
        return idStan;
    }
    
    int getIdMakan(){
        return idMakan;
    }
    
    String getNamaMakanan(){
        return namaMakanan;
    }
    
    int getJumlahBeli(){
        return jumlahBeli;
    }
    
    float getHarga(){
        return harga;
    }
    
    String getCatatan(){
        return catatan;
    }
}
