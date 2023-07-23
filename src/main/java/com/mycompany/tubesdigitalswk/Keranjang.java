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
    int idStan, jumlahBeli;
    String namaMakanan, catatan;
    float harga;
    
    
    Keranjang(int idStan, String namaMakanan, int jumlahBeli, float harga, String catatan){
    this.idStan = idStan;
    this.namaMakanan = namaMakanan;
    this.jumlahBeli = jumlahBeli;
    this.harga = harga;
    this.catatan = catatan;
    }
    
    int getIdStan(){
        System.out.println(idStan);
        return idStan;
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
