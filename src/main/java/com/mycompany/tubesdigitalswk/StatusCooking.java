/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubesdigitalswk;

/**
 *
 * @author putra
 */
public class StatusCooking {
    String namaCustomer, namaMakanan, catatan;
    int jumlah, status;
    
    StatusCooking(String namaCustomer, String namaMakanan, int jumlah, String catatan, int status){
        this.namaCustomer = namaCustomer;
        this.namaMakanan = namaMakanan;
        this.jumlah = jumlah;
        this.catatan = catatan;
        this.status = status;
    }
    
    public String getNamaCustomer(){
        return namaCustomer;
    }
    
    public String getNamaMakanan(){
        return namaMakanan;
    }
    
    public String getCatatan(){
        return catatan;
    }
    
    public int getJumlah(){
        return jumlah;
    }
    
    public Boolean getStatus(){
        if (status == 1){
            return true;
        }else{
            return false;
        }
    }
    
}
