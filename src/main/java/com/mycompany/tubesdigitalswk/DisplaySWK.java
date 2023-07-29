/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.tubesdigitalswk;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author putra
 */
public class DisplaySWK extends javax.swing.JFrame {

    private Connection con;
    private DefaultTableModel modelStan = new DefaultTableModel(){public boolean isCellEditable(int row, int column) {return false;}};
    private DefaultTableModel modelMakan = new DefaultTableModel(){public boolean isCellEditable(int row, int column) {return false;}};
    private DefaultTableModel modelMinum = new DefaultTableModel(){public boolean isCellEditable(int row, int column) {return false;}};
    private DefaultTableModel modelKeranjang = new DefaultTableModel(){public boolean isCellEditable(int row, int column) {return false;}};
    private DefaultTableModel modelPesanan = new DefaultTableModel(){public boolean isCellEditable(int row, int column) {return false;}};
    private DefaultTableModel modelMeja = new DefaultTableModel(){public boolean isCellEditable(int row, int column) {return false;}};
    private DefaultTableModel modelTransaksi = new DefaultTableModel(){public boolean isCellEditable(int row, int column) {return false;}};

    private ButtonGroup buttonGroup = new ButtonGroup();
    private Customer customer;
    private Stan seller;
    private Meja meja;
    
    private ArrayList<Stan> stn = new ArrayList<>();
    private ArrayList<Makanan> mkn = new ArrayList<>();
    private ArrayList<Minuman> mnm = new ArrayList<>();
    private ArrayList<Keranjang> krnjg = new ArrayList<>();
    private ArrayList<Booking> bm = new ArrayList<>();
    
    private float tHarga = 0;
    private int idMenu;

    private void loadKolomStan() {
        modelStan.addColumn("Nomor Stan");
        modelStan.addColumn("ID Stan");
        modelStan.addColumn("Nama Stan");
        modelStan.addColumn("Status");
        TabelStan.setModel(modelStan);
    }

    private void loadKolomMakan() {
        modelMakan.addColumn("ID Stan");
        modelMakan.addColumn("ID Makanan");
        modelMakan.addColumn("Nama Makanan");
        modelMakan.addColumn("Harga");
        modelMakan.addColumn("Status");
        TabelMenuMakanan.setModel(modelMakan);
        TabelEditMakanan.setModel(modelMakan);
    }

    private void loadKolomMinum() {
        modelMinum.addColumn("ID Stan");
        modelMinum.addColumn("ID Minuman");
        modelMinum.addColumn("Nama Minuman");
        modelMinum.addColumn("Harga");
        modelMinum.addColumn("Status");;
        TabelMenuMinuman.setModel(modelMinum);
        TabelEditMinuman.setModel(modelMinum);
    }

    private void loadKolomKeranjang() {
        modelKeranjang.addColumn("No Stan");
        modelKeranjang.addColumn("Makanan/Minuman");
        modelKeranjang.addColumn("Harga");
        modelKeranjang.addColumn("Jumlah beli");
        modelKeranjang.addColumn("Catatan");
        TabelKeranjang.setModel(modelKeranjang);
    }
    
    private void loadKolomPesanan() {
        modelPesanan.addColumn("Nama Pembeli");
        modelPesanan.addColumn("No Meja");
        modelPesanan.addColumn("Makanan/Minuman");
        modelPesanan.addColumn("Jumlah beli");
        modelPesanan.addColumn("Harga");
        modelPesanan.addColumn("Catatan");
        modelPesanan.addColumn("Jam Pesanan");
        tbPesanan.setModel(modelPesanan);
    }
    
    private void loadKolomBookingMeja(){
        modelMeja.addColumn("No. Meja");
        modelMeja.addColumn("Jumlah Kursi");
        modelMeja.addColumn("ID Meja");
        modelMeja.addColumn("Status");
        jtBooking.setModel(modelMeja);
    }
    
    private void loadKolomTransaksi(){
        modelTransaksi.addColumn("ID Transaksi");
        modelTransaksi.addColumn("Tanggal Transaksi");
        modelTransaksi.addColumn("Nama Customer");
        TableTransaksi.setModel(modelTransaksi);
    }

    private void loadStan() {
        if (con != null) {
            stn = new ArrayList<>();
            String kueri = "Select * from stan;";
            try {
                PreparedStatement ps = con.prepareStatement(kueri);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("ID_Stan");
                    int nomor = rs.getInt("Nomor_Stan");
                    String nama = rs.getString("Nama_Stan");
                    int status = rs.getInt("status");

                    Stan stan = new Stan(id, nomor, nama, status);
                    stn.add(stan);
                }
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(DisplaySWK.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //Method ambil menu ke database untuk seller
    private void loadMenu(int metode) {
        if (con != null) {    
            ArrayList<Makanan> mknn = new ArrayList<>();
            ArrayList<Minuman> mnmn = new ArrayList<>();
            if (metode == 1){
            int idStn = seller.getID();
            String kueri = "Select * from menu WHERE ID_Stan = " + idStn;
            try {
                PreparedStatement ps = con.prepareStatement(kueri);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("ID_Menu");
                    String nama = rs.getString("Nama");
                    float harga = rs.getFloat("Harga");
                    int status = rs.getInt("status");
                    int idStan = rs.getInt("ID_Stan");
                    int type = rs.getInt("Type");
                    if (type == 1){
                    Makanan makanan = new Makanan(id, nama, harga, idStan, status);
                    mknn.add(makanan);
                    }else{
                    Minuman minuman = new Minuman(id, nama, harga, idStan, status);
                    mnmn.add(minuman);   
                    }
                }
                mkn = mknn;
                mnm = mnmn;
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(DisplaySWK.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
                String kueri = "Select * from menu;";
            try {
                PreparedStatement ps = con.prepareStatement(kueri);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("ID_Menu");
                    String nama = rs.getString("Nama");
                    float harga = rs.getFloat("Harga");
                    int status = rs.getInt("status");
                    int idStan = rs.getInt("ID_Stan");
                    int type = rs.getInt("Type");
                    if (type == 1){
                    Makanan makanan = new Makanan(id, nama, harga, idStan, status);
                    mknn.add(makanan);
                    }else{
                    Minuman minuman = new Minuman(id, nama, harga, idStan, status);
                    mnmn.add(minuman);   
                    }
                }
                mkn = mknn;
                mnm = mnmn;
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(DisplaySWK.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        }
    }

    
    private void loadBookingMeja(){
        if(con != null){
            bm = new ArrayList<>();
            String kueri = "Select * from meja;";
            try{
                PreparedStatement ps = con.prepareStatement(kueri);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    int id = rs.getInt("ID_Meja");
                    int nomorMeja = rs.getInt("Nomor_Meja");
                    int jumlahKursi = rs.getInt("Jumlah_Kursi");
                    int status = rs.getInt("Status");
                    
                    Booking booking = new Booking(id, nomorMeja, jumlahKursi, status);
                    bm.add(booking);
                }
                rs.close();
                ps.close();
            }catch(SQLException ex){
                Logger.getLogger(PilihanMakan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void tampilKeranjang() {
        modelKeranjang.setRowCount(0);
        float harga = 0;
        for (Keranjang k : krnjg) {
            modelKeranjang.addRow(new Object[]{k.getIdStan(), k.getNamaMakanan(), k.getHarga(), k.getJumlahBeli(), k.getCatatan()});
            harga += k.getHarga() * k.getJumlahBeli();
        }
        tHarga = +harga;
        TFTotalHarga.setText(Float.toString(tHarga));
    }

    private void tampilMinuman() {
        modelMinum.setRowCount(0);
        for (Minuman mn : mnm) {
            modelMinum.addRow(new Object[]{mn.getIdStan(), mn.getID(), mn.getNama(), mn.getHarga(), mn.getStatus()});
        }       
    }

    private void tampilMakanan() {
        modelMakan.setRowCount(0);
        for (Makanan m : mkn) {
            modelMakan.addRow(new Object[]{m.getIdStan(), m.getID(), m.getNama(), m.getHarga(), m.getStatus()});
        }
    }

    private void tampilStan() {
        modelStan.setRowCount(0);
        for (Stan s : stn) {
            modelStan.addRow(new Object[]{s.getID(), s.getNomor(), s.getNama(), s.getStatus()});
        }
    }
    
    private void tampilBooking(){
        modelMeja.setRowCount(0);
        for(Booking b: bm){
            modelMeja.addRow(new Object [] {b.getNomorMeja(), b.getJumlahKursi(), b.getID(), b.getStatus()});
        }
    }

    /**
     * Creates new form DisplaySWK
     */
    public DisplaySWK() {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        con = Koneksi.bukaKoneksi();
        loadKolomStan();
        loadKolomMakan();
        loadKolomMinum();
        loadKolomBookingMeja();
        loadBookingMeja();
        loadKolomKeranjang();
        tampilBooking();
        RBJenisMakan.setActionCommand("pil1");
        buttonGroup.add(RBJenisMakan);
        RBJenisMinum.setActionCommand("pil2");
        buttonGroup.add(RBJenisMinum);
        TFKursi.setEditable(false);
        TFMeja.setEditable(false);
        TFNamaCustomer.setEditable(false);
        TFNoTelpCustomer.setEditable(false);
        TFNomorMeja.setEditable(false);
        reset();
    }

    private void reset() {
        loadStan();
        tampilStan();
        loadMenu(2);
        tampilMakanan();
        tampilMinuman();
    }

    private void cariStanByKeyword(String keyword) {
        if (con != null) {
            ArrayList<Stan> hasilPencarian = new ArrayList<>();
            String kueri = "Select * from stan where Nomor_Stan like ? or Nama_Stan like ?";
            try {
                PreparedStatement ps = con.prepareStatement(kueri);
                ps.setString(1, "%" + keyword + "%");
                ps.setString(2, "%" + keyword + "%");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("ID_Stan");
                    String nama = rs.getString("Nama_Stan");
                    int nomor = rs.getInt("Nomor_Stan");
                    int status = rs.getInt("status");
                    Stan stan = new Stan(id, nomor, nama, status);
                    hasilPencarian.add(stan);
                }
                rs.close();
                ps.close();

                // Update daftarBuku dengan hasil pencarian
                stn = hasilPencarian;
                // Tampilkan data hasil pencarian di tabel
                tampilStan();

            } catch (SQLException ex) {
                Logger.getLogger(DisplaySWK.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void cariMenuByKeyword(int btn, int metode, String keyword) {
        ArrayList<Minuman> Minum = new ArrayList<>();
        ArrayList<Makanan> Makan = new ArrayList<>();
        if (con != null) {
            if (metode == 2) {
                String kueri = "Select * from menu where Nama like ?";
                try {
                    PreparedStatement ps = con.prepareStatement(kueri);
                    ps.setString(1, "%" + keyword + "%");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        int id = rs.getInt("ID_Menu");
                        String nama = rs.getString("Nama");
                        int harga = rs.getInt("Harga");
                        int idStan = rs.getInt("ID_Stan");
                        int status = rs.getInt("status");
                        int type = rs.getInt("Type");
                        if (type == 2) {
                            Minuman minuman = new Minuman(id, nama, harga, idStan, status);
                            Minum.add(minuman);
                            System.out.println("1");
                        } else if (type == 1) {
                            Makanan makanan = new Makanan(id, nama, harga, idStan, status);
                            Makan.add(makanan);
                            System.out.println("2");
                        }
                    }
                    rs.close();
                    ps.close();
                    mnm = Minum;
                    mkn = Makan;
                    switch (btn) {
                        case 1 ->
                            tampilMakanan();
                        case 2 ->
                            tampilMinuman();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(DisplaySWK.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else if (metode == 1) {
                String kueri = "Select m.ID_Menu, m.Nama, m.Harga, m.status, m.ID_Stan, m.Type, s.Nomor_Stan from menu m INNER JOIN stan s ON s.ID_Stan = m.ID_Stan WHERE s.Nomor_Stan = " + keyword;
                try {
                    PreparedStatement ps = con.prepareStatement(kueri);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        int id = rs.getInt("ID_Menu");
                        String nama = rs.getString("Nama");
                        int harga = rs.getInt("Harga");
                        int idStan = rs.getInt("ID_Stan");
                        int status = rs.getInt("status");
                        int type = rs.getInt("Type");
                        Minuman minuman;
                        Makanan makanan;
                        if (type == 2) {
                            minuman = new Minuman(id, nama, harga, idStan, status);
                            Minum.add(minuman);
                        } else if (type == 1) {
                            makanan = new Makanan(id, nama, harga, idStan, status);
                            Makan.add(makanan);
                        }
                    }
                    rs.close();
                    ps.close();
                    mnm = Minum;
                    mkn = Makan;
                    tampilMinuman();
                    tampilMakanan();

                } catch (SQLException ex) {
                    Logger.getLogger(DisplaySWK.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    private void cekCustomer(){
    try{
        String nomorHandphone = customer.getNoTelp();
        String kueri = "SELECT No_Telp, ID_Customer FROM customer WHERE No_Telp = ?";
        PreparedStatement psSelect = con.prepareStatement(kueri);
        psSelect.setString(1, nomorHandphone);
        ResultSet rs = psSelect.executeQuery();
        int idCustomer = 0;
        
        if (rs.next()) {
        idCustomer = rs.getInt("ID_Customer");
            
        }
        rs.close();
        psSelect.close();
        
        if (idCustomer != 0){
            System.out.println("menambahkan id ke objek customer");
            customer.setId(idCustomer);
        } else {
            System.out.println("tambah user baru");
            kueri = "INSERT INTO `customer`(`Nama_Customer`, `No_Telp`) VALUES (?,?);";
            try{
            PreparedStatement ps = con.prepareStatement(kueri);
            ps.setString(1, customer.getNama());
            ps.setString(2, customer.getNoTelp());
            ps.executeUpdate();            
            ps.close();
            
            //Memanggil Funsinya sendiri
            cekCustomer();
        }catch (SQLException ex) {
            Logger.getLogger(DisplaySWK.class.getName()).log(Level.SEVERE, null, ex);
        }       
        }        
    }catch (SQLException e) {
            System.err.println("Error saat mendapatkan nomor urut terakhir: " + e.getMessage());
    }
    }
    
    
private String getNewIDTransaksi() {
    if (con != null) {
        int currentYear = LocalDate.now().getYear();
        String newID = currentYear + "_" + 1;
        String kueri = "SELECT COUNT(ID_Transakasi) AS Total_Transaksi,Tanggal_Pesanan FROM transaksi WHERE DATE_FORMAT(Tanggal_Pesanan, '%Y') = ?;";
        try {
            PreparedStatement ps = con.prepareStatement(kueri);
            ps.setInt(1, currentYear);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                int maxID = rs.getInt("Total_Transaksi");
                System.out.println(maxID);
                maxID += 1;
                newID = currentYear + "_" + maxID;
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newID;
    }
    
    return "1";
}

    
    private void buatTransaksi(){
        String kueri = "INSERT INTO transaksi(ID_Transakasi, Total_Belanja, Tanggal_Pesanan) VALUES (?,?,?)";
        LocalDate tanggalSaatIni = LocalDate.now();
        String tanggalString = tanggalSaatIni.toString();
        java.sql.Date sqlDate = java.sql.Date.valueOf(tanggalString);
        String id = getNewIDTransaksi();
        try {
            PreparedStatement ps = con.prepareStatement(kueri);
                ps.setString(1, id);
                ps.setFloat(2, Float.parseFloat(TFTotalHarga.getText()));
                ps.setDate(3, sqlDate);
                ps.executeUpdate();
            
            uploadTransaksi(id);
            System.out.println("Data berhasil diupload ke database.");

        } catch (SQLException e) {
            System.err.println("Error saat mengupload data ke database: " + e.getMessage());
        }
    }

    private void uploadTransaksi(String id) {
        ArrayList<Keranjang> newKrnjg = new ArrayList<>();
        String kueri = "INSERT INTO pesanan (ID_Transaksi, ID_Menu, Jumlah, ID_Meja, ID_Customer, Total_Harga, Jam_Pemesanan) VALUES (?, ?, ?, ?, ?, ?, ?)";
        LocalTime waktuFilter = LocalTime.now();
        cekCustomer();
        int idCustomer = customer.getId();
        System.out.println(idCustomer + "idCustomer");
        try {
            PreparedStatement ps = con.prepareStatement(kueri);
            int i = 0;
            for (Keranjang k : krnjg) {
                ps.setString(1, id);
                ps.setInt(2, k.getIdMakan());
                ps.setInt(3, k.getJumlahBeli());
                ps.setString(4, meja.getIdMeja());
                ps.setInt(5, idCustomer);
                ps.setFloat(6, k.getHarga());
                ps.setTime(7, java.sql.Time.valueOf(waktuFilter));

                ps.executeUpdate();
               i++;
            }
            if (i != 0){
            JOptionPane.showMessageDialog(this, "Mohon Menunggu Pesanan Anda");
            krnjg = newKrnjg;
            tampilKeranjang();
            }else{
                JOptionPane.showMessageDialog(this, "Maaf Terjadi Kesalahan Memesan Mohon Periksa Kembali Pesanan Anda");
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengupload data ke database: " + e.getMessage());
        }
    }
    
    private int radioButton(){
        ButtonModel command = buttonGroup.getSelection();
        int rtrn = 0;
        if (command != null){
            if("pil1".equals(command.getActionCommand())){
                rtrn = 1;
            } else if ("pil2".equals(command.getActionCommand())){
                rtrn = 2;
            }
        }
        return rtrn; 
    }

    private void editMenu(String tombol) {
        if (con != null) {
            String menuId = " ";
            int type = radioButton();
            int idStan = seller.getID(); 
            String namaMakanan = tfNamaMenu.getText();
            float hargaMakanan = Float.parseFloat(tfHargaMenu.getText());
            int statusMakanan = cbStatusMenu.getSelectedIndex();
            System.out.println(type);
            String kueri;
            if (tombol.equals("Ubah")){
                 kueri = "UPDATE `menu` SET `Type`=?,`Nama`=?,`Harga`=?,`Status`=?,`ID_Stan`=?, `ID_Menu`=? where ID_Menu = ?;";
                 
            }else{
                 kueri = "INSERT INTO menu ( Type, Nama, Harga, Status, ID_Stan) VALUES (?, ?, ?, ?, ?);";
            }   
            try {
                PreparedStatement ps = con.prepareStatement(kueri);
         
                ps.setInt(1, type);
                ps.setString(2, namaMakanan);
                ps.setFloat(3, hargaMakanan);
                ps.setInt(4, statusMakanan);
                ps.setInt(5, idStan);
                if (tombol.equals("Ubah")){
                ps.setInt(6, idMenu);
                ps.setInt(7,idMenu);
                }

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1) {
                    JOptionPane.showMessageDialog(this, "Data Berhasil Terupdate");
                    tfNamaMenu.setText("");
                    tfHargaMenu.setText("");
                    cbStatusMenu.setSelectedIndex(0);
                    btnSubmitMenu.setText("Submit");
                } else {
                    JOptionPane.showMessageDialog(this, "Insert Data Failed");
                }
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(DisplaySWK.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void fungsiEditMenuMakanan(){
      int barisTerpilih = TabelEditMakanan.getSelectedRow(); 

    if (barisTerpilih == -1) {
        JOptionPane.showMessageDialog(this, "Pilih Baris Untuk Di Edit");
        return;
    }
    String editIdMenu = TabelEditMakanan.getValueAt(barisTerpilih, 1).toString();
    String editNamaMakanan = TabelEditMakanan.getValueAt(barisTerpilih, 2).toString();
    String editHargaMakanan = TabelEditMakanan.getValueAt(barisTerpilih, 3).toString();
    String editStatusMakanan = TabelEditMakanan.getValueAt(barisTerpilih, 4).toString();

    idMenu = Integer.parseInt(editIdMenu);
    tfNamaMenu.setText(editNamaMakanan); 
    tfHargaMenu.setText(editHargaMakanan);
    cbStatusMenu.setSelectedItem(editStatusMakanan); 
        
    }
    
    private void fungsiEditMenuMinuman(){
    int barisTerpilih = TabelEditMinuman.getSelectedRow(); 

    if (barisTerpilih == -1) {
        JOptionPane.showMessageDialog(this, "Pilih Baris Untuk Di Edit");
        return;
    }
    String editIdMenu = TabelEditMinuman.getValueAt(barisTerpilih, 1).toString();
    String editNamaMinuman = TabelEditMinuman.getValueAt(barisTerpilih, 2).toString();
    String editHargaMinuman = TabelEditMinuman.getValueAt(barisTerpilih, 3).toString();
    String editStatusMinuman = TabelEditMinuman.getValueAt(barisTerpilih, 4).toString();

    idMenu = Integer.parseInt(editIdMenu);
    tfNamaMenu.setText(editNamaMinuman); 
    tfHargaMenu.setText(editHargaMinuman);
    cbStatusMenu.setSelectedItem(editStatusMinuman); 
        
    }

        private void login(String email, String password) {
        if(con != null){
            
            String kueri = "SELECT s.Email, s.ID_Seller, stn.ID_Stan, stn.Nama_Stan, stn.Nomor_Stan, stn.status FROM seller s INNER JOIN stan stn ON s.ID_Stan = stn.ID_Stan WHERE Email = ? AND Password = ? ;";
            try{
                PreparedStatement ps = con.prepareStatement(kueri);
                ps.setString(1, email);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                    int id = rs.getInt("ID_Stan");
                    String nama = rs.getString("Nama_Stan");
                    int noStan = rs.getInt("Nomor_Stan");
                    int status = rs.getInt("status");                   
                    seller = new Stan(id,  noStan, nama, status);
                    }
                rs.close();
                ps.close();
            }catch(SQLException ex){
                Logger.getLogger(loginPage.class.getName()).log(Level.SEVERE, null, ex);
                
                JOptionPane.showMessageDialog(this, "Email atau Password tidak tersedia atau salah");
            }
        }

    }
        
    private void statusStan(){
        if(con != null){
        int idStan = seller.getID();
        if ("Buka".equals(seller.getStatus())) {
        String kueri = "UPDATE `stan` SET `status`='1' WHERE ID_Stan = ?;";
                try{
                PreparedStatement ps = con.prepareStatement(kueri);
                ps.setInt(1, idStan);
                
                int rowsAffected = ps.executeUpdate();
                    if (rowsAffected == 1) {
                    JOptionPane.showMessageDialog(this, "Data Berhasil Terupdate");
                    seller.setStatus(0);
                    TBStatus.setBackground(Color.GREEN);
        TBStatus.setText("Buka");
                    } else {
                    JOptionPane.showMessageDialog(this, "Insert Data Failed");
                    }
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(DisplaySWK.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else{
        String kueri = "UPDATE `stan` SET `status`='0' WHERE ID_Stan = ?;";
                try{
                PreparedStatement ps = con.prepareStatement(kueri);
                ps.setInt(1, idStan);
                
                int rowsAffected = ps.executeUpdate();
                    if (rowsAffected == 1) {
                    JOptionPane.showMessageDialog(this, "Data Berhasil Terupdate");
                    seller.setStatus(1);
                    TBStatus.setBackground(Color.RED);
                    TBStatus.setText("Tutup");
                    } else {
                    JOptionPane.showMessageDialog(this, "Insert Data Failed");
                    }
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(DisplaySWK.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    }
    
    private void bookingMeja(int statusBooking){
        if(con != null){
            String kueri;
            int nomor = Integer.parseInt(meja.getNo());
            if (statusBooking == 1){
            kueri = "UPDATE `meja` SET `Status`='0' WHERE Nomor_Meja = ?;";
            }else{
                kueri = "UPDATE `meja` SET `Status`='1' WHERE Nomor_Meja = ?";
            }
            try {
                PreparedStatement ps = con.prepareStatement(kueri);
                ps.setInt(1, nomor);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1) {                   
                    JOptionPane.showMessageDialog(this, "Data Berhasil Terupdate");
                } else {
                    JOptionPane.showMessageDialog(this, "Insert Data Failed");
                }
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(DisplaySWK.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void updatePesanan() {
        // Timer untuk polling setiap 5 detik
        Timer timer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTransaksi(0);
            }
        });
        timer.start();
    }
    
    private void loadTransaksi(int tampilan){
        if(con != null){
            String kueri;
            if(tampilan == 0){
             kueri = "SELECT t.ID_Transaksi, t.Tanggal_Pesanan, c.Nama_Customer, m.ID_Stan FROM transaksi t INNER JOIN pesanan p ON p.ID_Transaksi = t.ID_Transaksi INNER JOIN menu m ON m.ID_Menu = p.ID_Menu INNER JOIN customer c ON c.ID_Customer = p.ID_Customer WHERE m.ID_Stan = ? AND p.status = 0 GROUP BY t.ID_Transaksi;" ;
            }else{
             kueri = "SELECT t.ID_Transaksi, t.Tanggal_Pesanan, c.Nama_Customer, m.ID_Stan FROM transaksi t INNER JOIN pesanan p ON p.ID_Transaksi = t.ID_Transaksi INNER JOIN menu m ON m.ID_Menu = p.ID_Menu INNER JOIN customer c ON c.ID_Customer = p.ID_Customer WHERE m.ID_Stan = ? AND p.status = 1 GROUP BY t.ID_Transaksi;" ;
            }
            try{
                PreparedStatement ps = con.prepareStatement(kueri);
                ps.setInt(1, seller.getID());
                ResultSet rs = ps.executeQuery();
                modelTransaksi.setRowCount(0);
                while(rs.next()){
                    String idTransaksi = rs.getString("ID_Transaksi");
                    String tanggalTransaksi = rs.getString("Tanggal_Pesanan");
                    String namaCustomer = rs.getString("Nama_Customer");
                                      
                   modelTransaksi.addRow(new Object []{idTransaksi, tanggalTransaksi, namaCustomer});
                }
            }catch (SQLException ex) {
                Logger.getLogger(DisplaySWK.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }

    private void updateTablePesanan(int tampilan, String idTransaksi) {
       
            if (con != null) {
                if(tampilan == 1){
                String kueri = "SELECT c.Nama_Customer, p.ID_Meja, m.Nama, p.Jumlah, p.Total_Harga, p.catatan , p.Jam_Pemesanan, p.status FROM pesanan p INNER JOIN menu m ON p.ID_Menu = m.ID_Menu INNER JOIN customer c ON p.ID_Customer = c.ID_Customer WHERE m.ID_Stan = ? AND p.status = 0 ORDER BY Jam_Pemesanan ASC;";
                try {
                PreparedStatement ps = con.prepareStatement(kueri);
                ps.setInt(1, seller.getID());
                ResultSet rs = ps.executeQuery();

                modelPesanan.setRowCount(0);

                while (rs.next()) {
                    String customer = rs.getString("Nama_Customer");
                    int idMeja = rs.getInt("ID_Meja");
                    String makanan = rs.getString("Nama");
                    int jumlah = rs.getInt("Jumlah");
                    int totalHarga = rs.getInt("Total_Harga");
                    Time jamPemesanan = rs.getTime("Jam_Pemesanan");
                    String catatan = rs.getString("Catatan");
                    int status = rs.getInt("status");

                    // Tambahkan data ke tabel pesanan pada GUI
                    modelPesanan.addRow(new Object[]{customer, idMeja, makanan, jumlah, totalHarga, catatan, jamPemesanan, status});
                }

                rs.close();
                ps.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(DisplaySWK.class.getName()).log(Level.SEVERE, null, ex);
        }
        }else{
              String kueri = "SELECT c.Nama_Customer, p.ID_Meja, m.Nama, p.Jumlah, p.Total_Harga, p.catatan , p.Jam_Pemesanan, p.status,p.ID_Transaksi FROM pesanan p INNER JOIN menu m ON p.ID_Menu = m.ID_Menu INNER JOIN customer c ON p.ID_Customer = c.ID_Customer WHERE m.ID_Stan = ? AND p.status = 0 AND p.ID_Transaksi = ? ORDER BY Jam_Pemesanan ASC;";
                try {
                PreparedStatement ps = con.prepareStatement(kueri);
                ps.setInt(1, seller.getID());
                ps.setString(2, idTransaksi);
                ResultSet rs = ps.executeQuery();

                modelPesanan.setRowCount(0);

                while (rs.next()) {
                    String customer = rs.getString("Nama_Customer");
                    int idMeja = rs.getInt("ID_Meja");
                    String makanan = rs.getString("Nama");
                    int jumlah = rs.getInt("Jumlah");
                    int totalHarga = rs.getInt("Total_Harga");
                    Time jamPemesanan = rs.getTime("Jam_Pemesanan");
                    String catatan = rs.getString("Catatan");
                    int status = rs.getInt("status");

                    // Tambahkan data ke tabel pesanan pada GUI
                    modelPesanan.addRow(new Object[]{customer, idMeja, makanan, jumlah, totalHarga, catatan, jamPemesanan, status});
                }

                rs.close();
                ps.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(DisplaySWK.class.getName()).log(Level.SEVERE, null, ex);
        }      
        }
    }
    }
    
    
    
    private void deleteMenu(int id){
        if(con != null){
            String kueri = "DELETE FROM menu WHERE ID_Menu = ?";
            try{
            PreparedStatement ps = con.prepareStatement(kueri);
            ps.setInt(1, id);
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Data berhasil dihapus dari database.");
            } else {
                System.out.println("Data tidak ditemukan atau gagal dihapus.");
            }
            ps.close();
            } catch (SQLException ex) {
            Logger.getLogger(DisplaySWK.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
     private static class Order{
        private String namaStan;
        private String namaPesanan;
        private int status;

        public String getNamaStan() {
            return namaStan;
        }

        public String getNamaPesanan() {
            return namaPesanan;
        }

        public int getStatus() {
            return status;
        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jTabbedPane = new javax.swing.JTabbedPane();
        PaneLogin = new javax.swing.JScrollPane();
        panelLogin = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        cbPilihan = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        btnLanjut = new javax.swing.JButton();
        PaneBookingMeja = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        jtBooking = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jPanel12 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        TFNama = new javax.swing.JTextField();
        TFNo = new javax.swing.JTextField();
        Text = new javax.swing.JLabel();
        TFKursi = new javax.swing.JTextField();
        TFMeja = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cbPesanan = new javax.swing.JComboBox<>();
        jLabel40 = new javax.swing.JLabel();
        TFNoMeja = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        PaneLoginSeller = new javax.swing.JScrollPane();
        jPanel10 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        btnAdmin = new javax.swing.JButton();
        TFEmail = new javax.swing.JTextField();
        TFPassword = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        PaneSeller = new javax.swing.JScrollPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        tfNamaMenu = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        tfHargaMenu = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        cbStatusMenu = new javax.swing.JComboBox<>();
        RBJenisMakan = new javax.swing.JRadioButton();
        RBJenisMinum = new javax.swing.JRadioButton();
        jLabel33 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        TabelEditMakanan = new javax.swing.JTable();
        btnEditMakanan = new javax.swing.JButton();
        btnHapusMakanan = new javax.swing.JButton();
        btnSubmitMenu = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        TabelEditMinuman = new javax.swing.JTable();
        jLabel34 = new javax.swing.JLabel();
        btnEditMinuman = new javax.swing.JButton();
        btnHapusMakanan1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tbPesanan = new javax.swing.JTable();
        jLabel35 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        TBStatus = new javax.swing.JToggleButton();
        txtNamaStan = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        TableTransaksi = new javax.swing.JTable();
        jLabel38 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jLabel41 = new javax.swing.JLabel();
        PanePesanMakan = new javax.swing.JScrollPane();
        panelPesanan = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelStan = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btnTampilkanMenu = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        TabelMenuMakanan = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TabelMenuMinuman = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        TFCatatanMakanan = new javax.swing.JTextField();
        ButtonKeranjangMakanan = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        TFCatatanMinuman = new javax.swing.JTextField();
        ButtonKeranjangMinuman = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        TFJumlahBeliMakan = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        TFJumlahBeliMinuman = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TabelKeranjang = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        ButtonHapusPesanan = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        TFNoTelpCustomer = new javax.swing.JTextField();
        TFNamaCustomer = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        TFNomorMeja = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        TFTotalHarga = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        TFCariStan = new javax.swing.JTextField();
        btnCariStan = new javax.swing.JButton();
        TFCariMakan = new javax.swing.JTextField();
        TFCariMinum = new javax.swing.JTextField();
        btnCariMakan = new javax.swing.JButton();
        btnCariMinum = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel4.setBackground(new java.awt.Color(10, 38, 71));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel7.setBackground(new java.awt.Color(0, 129, 138));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 0, 300, 30));

        panelLogin.setBackground(new java.awt.Color(10, 38, 71));

        jLabel16.setFont(new java.awt.Font("Trebuchet MS", 1, 48)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 153, 0));
        jLabel16.setText("DIGITAL SWK");

        cbPilihan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbPilihan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seller", "Customer" }));
        cbPilihan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPilihanActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Login Sebagai:");

        btnLanjut.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnLanjut.setText("Lanjutkan");
        btnLanjut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLanjutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelLoginLayout = new javax.swing.GroupLayout(panelLogin);
        panelLogin.setLayout(panelLoginLayout);
        panelLoginLayout.setHorizontalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLoginLayout.createSequentialGroup()
                        .addGap(437, 437, 437)
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbPilihan, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelLoginLayout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(btnLanjut))))
                    .addGroup(panelLoginLayout.createSequentialGroup()
                        .addGap(502, 502, 502)
                        .addComponent(jLabel16)))
                .addGap(0, 862, Short.MAX_VALUE))
        );
        panelLoginLayout.setVerticalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addGap(124, 124, 124)
                .addComponent(jLabel16)
                .addGap(67, 67, 67)
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(cbPilihan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addComponent(btnLanjut)
                .addContainerGap())
        );

        PaneLogin.setViewportView(panelLogin);

        jTabbedPane.addTab("tab4", PaneLogin);

        jPanel1.setBackground(new java.awt.Color(0, 129, 138));

        jButton1.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        jButton1.setText("Booking");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jtBooking.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "No. Meja", "Jumlah Kursi", "Status"
            }
        ));
        jScrollPane9.setViewportView(jtBooking);

        jLabel18.setFont(new java.awt.Font("Trebuchet MS", 1, 36)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(216, 146, 22));
        jLabel18.setText("DIGITAL SWK");

        jButton2.setBackground(new java.awt.Color(255, 102, 102));
        jButton2.setText("Back to Login Page");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Data Pemesan");

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        jLabel27.setBackground(new java.awt.Color(10, 38, 71));
        jLabel27.setForeground(new java.awt.Color(60, 63, 65));
        jLabel27.setText("Nama :");

        jLabel30.setBackground(new java.awt.Color(10, 38, 71));
        jLabel30.setForeground(new java.awt.Color(60, 63, 65));
        jLabel30.setText("No Tlp :");

        TFNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TFNamaActionPerformed(evt);
            }
        });

        TFNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TFNoActionPerformed(evt);
            }
        });

        Text.setBackground(new java.awt.Color(10, 38, 71));
        Text.setForeground(new java.awt.Color(60, 63, 65));
        Text.setText("Jum Kursi :");

        TFKursi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TFKursiActionPerformed(evt);
            }
        });

        TFMeja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TFMejaActionPerformed(evt);
            }
        });

        jLabel39.setBackground(new java.awt.Color(10, 38, 71));
        jLabel39.setForeground(new java.awt.Color(60, 63, 65));
        jLabel39.setText("ID Meja :");

        jLabel12.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 129, 138));
        jLabel12.setText("Order by:");

        cbPesanan.setBackground(new java.awt.Color(255, 204, 0));
        cbPesanan.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        cbPesanan.setForeground(new java.awt.Color(0, 0, 0));
        cbPesanan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "On Site", "Take Away" }));

        jLabel40.setBackground(new java.awt.Color(10, 38, 71));
        jLabel40.setForeground(new java.awt.Color(60, 63, 65));
        jLabel40.setText("No Meja :");

        TFNoMeja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TFNoMejaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Text)
                    .addComponent(jLabel30)
                    .addComponent(jLabel27)
                    .addComponent(jLabel39))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(TFNama, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                    .addComponent(TFNo)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(TFKursi, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbPesanan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(TFMeja, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel40)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TFNoMeja, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TFNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TFNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Text)
                    .addComponent(TFKursi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(cbPesanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TFMeja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39)
                    .addComponent(jLabel40)
                    .addComponent(TFNoMeja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("Booking Table");

        jButton4.setBackground(new java.awt.Color(255, 255, 204));
        jButton4.setFont(new java.awt.Font("Trebuchet MS", 0, 18)); // NOI18N
        jButton4.setText("Pesan Sekarang");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton9.setText("Reset");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 1271, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(527, 527, 527)
                        .addComponent(jLabel37))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(336, 336, 336)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jButton1)
                                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 556, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(193, 193, 193)
                                .addComponent(jLabel26))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jButton2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton9)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton4))
                                .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(373, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel37)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(69, 69, 69)
                .addComponent(jLabel26)
                .addGap(8, 8, 8)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton4)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(489, Short.MAX_VALUE))
        );

        PaneBookingMeja.setViewportView(jPanel1);

        jTabbedPane.addTab("tab4", PaneBookingMeja);

        jPanel10.setBackground(new java.awt.Color(10, 38, 71));

        jLabel21.setFont(new java.awt.Font("Trebuchet MS", 1, 36)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 153, 0));
        jLabel21.setText("DIGITAL SWK");

        btnLogin.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        btnLogin.setText("LOGIN");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        btnAdmin.setBackground(new java.awt.Color(102, 204, 0));
        btnAdmin.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        btnAdmin.setForeground(new java.awt.Color(10, 38, 71));
        btnAdmin.setText("Hubungi Admin");
        btnAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdminActionPerformed(evt);
            }
        });

        TFEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TFEmailActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Atau");

        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Email :");

        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Password:");

        jButton3.setBackground(new java.awt.Color(255, 102, 102));
        jButton3.setText("Back to Login Page");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel21)
                        .addGap(53, 53, 53))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                        .addGap(375, 375, 375)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton3)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel25)
                                    .addComponent(jLabel24))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel23)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnAdmin))
                                    .addComponent(TFEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TFPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(869, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(153, 153, 153)
                .addComponent(jLabel21)
                .addGap(19, 19, 19)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TFEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TFPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addGap(30, 30, 30)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogin)
                    .addComponent(jLabel23)
                    .addComponent(btnAdmin))
                .addGap(88, 88, 88)
                .addComponent(jButton3)
                .addContainerGap(668, Short.MAX_VALUE))
        );

        PaneLoginSeller.setViewportView(jPanel10);

        jTabbedPane.addTab("tab5", PaneLoginSeller);

        jPanel11.setBackground(new java.awt.Color(0, 129, 138));

        jLabel28.setFont(new java.awt.Font("Trebuchet MS", 1, 36)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(216, 146, 22));
        jLabel28.setText("DIGITAL SWK");

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tfNamaMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNamaMenuActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(10, 38, 71));
        jLabel29.setText("Nama Menu :");

        tfHargaMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfHargaMenuActionPerformed(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(10, 38, 71));
        jLabel31.setText("Harga :");

        jLabel32.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(10, 38, 71));
        jLabel32.setText("Status :");

        cbStatusMenu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Habis", "Tersedia" }));
        cbStatusMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbStatusMenuActionPerformed(evt);
            }
        });

        RBJenisMakan.setText("Makanan");
        RBJenisMakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RBJenisMakanActionPerformed(evt);
            }
        });

        RBJenisMinum.setText("Minuman");

        jLabel33.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(10, 38, 71));
        jLabel33.setText("Jenis :");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29)
                    .addComponent(jLabel32)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(RBJenisMakan)
                        .addGap(18, 18, 18)
                        .addComponent(RBJenisMinum))
                    .addComponent(cbStatusMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(tfNamaMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                        .addComponent(tfHargaMenu)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(tfNamaMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfHargaMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RBJenisMakan)
                    .addComponent(jLabel33)
                    .addComponent(RBJenisMinum))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbStatusMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32))
                .addGap(23, 23, 23))
        );

        jLabel36.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText("Edit Menu Makanan");

        TabelEditMakanan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID ", "Nama ", "Harga", "Status "
            }
        ));
        jScrollPane11.setViewportView(TabelEditMakanan);

        btnEditMakanan.setText("EDIT");
        btnEditMakanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditMakananActionPerformed(evt);
            }
        });

        btnHapusMakanan.setText("HAPUS");
        btnHapusMakanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusMakananActionPerformed(evt);
            }
        });

        btnSubmitMenu.setText("SUBMIT");
        btnSubmitMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitMenuActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Tabel Makanan");

        TabelEditMinuman.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID ", "Nama ", "Harga", "Status "
            }
        ));
        jScrollPane12.setViewportView(TabelEditMinuman);

        jLabel34.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("Tabel Minuman");

        btnEditMinuman.setText("EDIT");
        btnEditMinuman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditMinumanActionPerformed(evt);
            }
        });

        btnHapusMakanan1.setText("HAPUS");
        btnHapusMakanan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusMakanan1ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(10, 38, 71));

        tbPesanan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID Pesanan", "No Meja", "Makanan/Minuman", "Harga", "Jumlah Pesanan"
            }
        ));
        jScrollPane10.setViewportView(tbPesanan);

        jLabel35.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText("TABEL PESANAN");

        jButton10.setText("PROCESS");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 1132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 81, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(jLabel35))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(609, 609, 609)
                        .addComponent(jButton10)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton10)
                .addContainerGap(230, Short.MAX_VALUE))
        );

        jLabel19.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("SELAMAT DATANG ");

        jLabel20.setBackground(new java.awt.Color(10, 38, 71));
        jLabel20.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Status Stan:");

        TBStatus.setText("Buka");
        TBStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TBStatusActionPerformed(evt);
            }
        });

        txtNamaStan.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        txtNamaStan.setForeground(new java.awt.Color(255, 204, 0));

        jButton5.setText("RESET");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(255, 102, 102));
        jButton7.setText("Back to Login Page");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        TableTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(TableTransaksi);

        jLabel38.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("Pesanan");

        jButton11.setText("TAMPILKAN ");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setText("TAMPILKAN SEMUA");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("Tampilkan detail pesanan :");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 1334, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel38)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel11Layout.createSequentialGroup()
                                    .addComponent(jLabel19)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtNamaStan))
                                .addGroup(jPanel11Layout.createSequentialGroup()
                                    .addComponent(jLabel20)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(TBStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel36)
                                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                    .addComponent(jButton7)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnSubmitMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton5)))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel41)
                                .addGap(18, 18, 18)
                                .addComponent(jButton11)
                                .addGap(18, 18, 18)
                                .addComponent(jButton12)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                .addComponent(btnHapusMakanan1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEditMinuman, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                .addComponent(btnHapusMakanan, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEditMakanan, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22)
                            .addComponent(jLabel34))
                        .addGap(140, 140, 140))))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtNamaStan))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnHapusMakanan)
                            .addComponent(btnEditMakanan))
                        .addGap(11, 11, 11)
                        .addComponent(jLabel34))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(TBStatus))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel36)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSubmitMenu)
                            .addComponent(jButton5)
                            .addComponent(jButton7))
                        .addGap(0, 11, Short.MAX_VALUE)))
                .addGap(19, 19, 19)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHapusMakanan1)
                    .addComponent(btnEditMinuman)
                    .addComponent(jButton11)
                    .addComponent(jButton12)
                    .addComponent(jLabel41))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 1310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 340, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        PaneSeller.setViewportView(jPanel5);

        jTabbedPane.addTab("tab4", PaneSeller);

        panelPesanan.setBackground(new java.awt.Color(10, 38, 71));

        jLabel1.setFont(new java.awt.Font("Trebuchet MS", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(216, 146, 22));
        jLabel1.setText("DIGITAL SWK");

        TabelStan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Nomor", "Nama", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TabelStan);

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Stan/Warung SWK");

        btnTampilkanMenu.setText("Tampilkan Menu");
        btnTampilkanMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTampilkanMenuActionPerformed(evt);
            }
        });

        TabelMenuMakanan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Makanan", "Harga"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(TabelMenuMakanan);

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Menu Makanan");

        TabelMenuMinuman.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Minuman", "Harga"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(TabelMenuMinuman);

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Menu Minuman");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Input jumlah beli makanan : ");

        TFCatatanMakanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TFCatatanMakananActionPerformed(evt);
            }
        });

        ButtonKeranjangMakanan.setText("Masukan Ke Keranjang");
        ButtonKeranjangMakanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonKeranjangMakananActionPerformed(evt);
            }
        });

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Input jumlah beli minuman : ");

        ButtonKeranjangMinuman.setText("Masukan Ke Keranjang");
        ButtonKeranjangMinuman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonKeranjangMinumanActionPerformed(evt);
            }
        });

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Catatan Makanan : ");

        TFJumlahBeliMakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TFJumlahBeliMakanActionPerformed(evt);
            }
        });

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Catatan Minuman : ");

        jPanel3.setBackground(new java.awt.Color(0, 129, 138));

        TabelKeranjang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "No Stan", "Makanan/Minuman", "Harga", "Jumlah Beli"
            }
        ));
        jScrollPane4.setViewportView(TabelKeranjang);

        jLabel9.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("KERANJANG PESANAN");

        ButtonHapusPesanan.setText("Hapus");
        ButtonHapusPesanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonHapusPesananActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("PESANAN");

        TFNoTelpCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TFNoTelpCustomerActionPerformed(evt);
            }
        });

        TFNamaCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TFNamaCustomerActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Nama :");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Nomor Hp :");

        TFNomorMeja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TFNomorMejaActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Nomor Meja :");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Total Harga Pesanan :");

        TFTotalHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TFTotalHargaActionPerformed(evt);
            }
        });

        jButton6.setText("Bayar Sekarang");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(255, 102, 102));
        jButton8.setText("Back to Login Page");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(ButtonHapusPesanan)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 771, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 762, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                            .addComponent(jSeparator5))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton6)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel13)
                                        .addComponent(jLabel15)
                                        .addComponent(jLabel14))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(TFNoTelpCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                                        .addComponent(TFNamaCustomer)
                                        .addComponent(TFNomorMeja, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(TFTotalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jButton8)))
                        .addGap(63, 63, 63))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ButtonHapusPesanan)
                            .addComponent(jButton8)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TFNamaCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TFNoTelpCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TFNomorMeja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addGap(12, 12, 12)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(TFTotalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jButton6)))
                .addContainerGap(54, Short.MAX_VALUE))
        );

        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnCariStan.setText("Cari");
        btnCariStan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariStanActionPerformed(evt);
            }
        });

        btnCariMakan.setText("Cari");
        btnCariMakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariMakanActionPerformed(evt);
            }
        });

        btnCariMinum.setText("Cari");
        btnCariMinum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariMinumActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPesananLayout = new javax.swing.GroupLayout(panelPesanan);
        panelPesanan.setLayout(panelPesananLayout);
        panelPesananLayout.setHorizontalGroup(
            panelPesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPesananLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPesananLayout.createSequentialGroup()
                        .addGap(419, 419, 419)
                        .addGroup(panelPesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ButtonKeranjangMakanan)
                            .addComponent(TFJumlahBeliMakan, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelPesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelPesananLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(panelPesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel8))
                                .addGroup(panelPesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelPesananLayout.createSequentialGroup()
                                        .addGap(65, 65, 65)
                                        .addComponent(TFJumlahBeliMinuman, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelPesananLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(TFCatatanMinuman, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(panelPesananLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ButtonKeranjangMinuman))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPesananLayout.createSequentialGroup()
                        .addGap(106, 106, 106)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCariStan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TFCariStan, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1)
                    .addGroup(panelPesananLayout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 1255, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPesananLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnTampilkanMenu)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPesananLayout.createSequentialGroup()
                        .addGroup(panelPesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelPesananLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(306, 306, 306)
                                .addComponent(TFCariMakan, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCariMakan))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 619, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelPesananLayout.createSequentialGroup()
                                .addGroup(panelPesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TFCatatanMakanan, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelPesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPesananLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(238, 238, 238)
                                .addComponent(TFCariMinum, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCariMinum)))))
                .addGap(23, 23, 23))
            .addGroup(panelPesananLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelPesananLayout.setVerticalGroup(
            panelPesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPesananLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelPesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCariStan)
                    .addComponent(jLabel2)
                    .addComponent(TFCariStan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTampilkanMenu)
                    .addComponent(btnReset))
                .addGap(28, 28, 28)
                .addGroup(panelPesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelPesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(TFCariMakan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCariMinum)
                        .addComponent(TFCariMinum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCariMakan)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPesananLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelPesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TFCatatanMinuman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelPesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel7)
                                .addComponent(TFCatatanMakanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel8)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelPesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(TFJumlahBeliMakan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(TFJumlahBeliMinuman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelPesananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ButtonKeranjangMakanan)
                            .addComponent(ButtonKeranjangMinuman)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        PanePesanMakan.setViewportView(panelPesanan);

        jTabbedPane.addTab("tab4", PanePesanMakan);

        jPanel4.add(jTabbedPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1310, 990));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1310, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 999, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 1310, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 985, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCariMinumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariMinumActionPerformed
        // TODO add your handling code here:
        String keyword = TFCariMinum.getText().trim();
        if (keyword.length() == 0) {
            loadMenu(2);
            tampilMinuman();
        } else {
            cariMenuByKeyword(2, 2, keyword);
            tampilMinuman();
            System.out.println("dsafdf");
        }
    }//GEN-LAST:event_btnCariMinumActionPerformed

    private void btnCariMakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariMakanActionPerformed
        // TODO add your handling code here:
        String keyword = TFCariMakan.getText().trim();
        if (keyword.length() == 0) {
            loadMenu(2);
            tampilMakanan();
        } else {
            cariMenuByKeyword(1, 2, keyword);
            tampilMakanan();

        }

    }//GEN-LAST:event_btnCariMakanActionPerformed

    private void btnCariStanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariStanActionPerformed
        // TODO add your handling code here:
        String keyword = TFCariStan.getText().trim();
        if (keyword.length() == 0) {
            loadStan();
            tampilStan();
        } else {
            cariStanByKeyword(keyword);
            tampilStan();
        }
    }//GEN-LAST:event_btnCariStanActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_btnResetActionPerformed

    private void ButtonHapusPesananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonHapusPesananActionPerformed
        // TODO add your handling code here:
        int baris = TabelKeranjang.getSelectedRow();
        int pil = JOptionPane.showConfirmDialog(this, "Anda Yakin ingin Menghapus pesanan ini?", 
                "Perhatian", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
        
        if (pil == JOptionPane.OK_OPTION){
             krnjg.remove(baris);
             tampilKeranjang();
        }
    }//GEN-LAST:event_ButtonHapusPesananActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        buatTransaksi();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void TFNoTelpCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TFNoTelpCustomerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TFNoTelpCustomerActionPerformed

    private void TFNomorMejaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TFNomorMejaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TFNomorMejaActionPerformed

    private void TFTotalHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TFTotalHargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TFTotalHargaActionPerformed

    private void TFJumlahBeliMakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TFJumlahBeliMakanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TFJumlahBeliMakanActionPerformed

    private void ButtonKeranjangMinumanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonKeranjangMinumanActionPerformed
        // TODO add your handling code here:
        int barisTerpilih = TabelMenuMinuman.getSelectedRow();
        int idStan = Integer.parseInt(TabelMenuMinuman.getValueAt(barisTerpilih, 0).toString());
        int idMinuman = Integer.parseInt(TabelMenuMinuman.getValueAt(barisTerpilih, 1).toString());
        String nama = TabelMenuMinuman.getValueAt(barisTerpilih, 2).toString();
        int jumlah = Integer.parseInt(TFJumlahBeliMinuman.getText());
        float harga = Float.parseFloat(TabelMenuMinuman.getValueAt(barisTerpilih, 3).toString());
        String catatan = TFCatatanMinuman.getText();
        if (catatan.length() == 0) {
            catatan = " ";
        }

        Keranjang keranjang = new Keranjang(idStan, idMinuman, nama, jumlah, harga, catatan);
        krnjg.add(keranjang);
        tampilKeranjang();
        TFCatatanMinuman.setText("");
        TFJumlahBeliMinuman.setText("");
    }//GEN-LAST:event_ButtonKeranjangMinumanActionPerformed

    private void ButtonKeranjangMakananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonKeranjangMakananActionPerformed
        // TODO add your handling code here:
        LocalDate tanggalSaatIni = LocalDate.now();
        int barisTerpilih = TabelMenuMakanan.getSelectedRow();
        int idStan = Integer.parseInt(TabelMenuMakanan.getValueAt(barisTerpilih, 0).toString());
        int idMakanan = Integer.parseInt(TabelMenuMakanan.getValueAt(barisTerpilih, 1).toString());
        String nama = TabelMenuMakanan.getValueAt(barisTerpilih, 2).toString();
        int jumlah = Integer.parseInt(TFJumlahBeliMakan.getText());
        float harga = Float.parseFloat(TabelMenuMakanan.getValueAt(barisTerpilih, 3).toString());
        String catatan = TFCatatanMakanan.getText();
        if (catatan.length() == 0) {
            catatan = " ";
        }

        Keranjang keranjang = new Keranjang(idStan, idMakanan, nama, jumlah, harga, catatan);
        krnjg.add(keranjang);
        tampilKeranjang();
        TFCatatanMakanan.setText("");
        TFJumlahBeliMakan.setText("");
    }//GEN-LAST:event_ButtonKeranjangMakananActionPerformed

    private void TFCatatanMakananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TFCatatanMakananActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TFCatatanMakananActionPerformed

    private void btnTampilkanMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTampilkanMenuActionPerformed
        // TODO add your handling code here:
        int barisTerpilih = TabelStan.getSelectedRow();
        String keyword = modelStan.getValueAt(barisTerpilih, 1).toString();
        if (keyword.length() == 0) {
            loadMenu(2);
            tampilMakanan();
            tampilMinuman();
        } else {
            cariMenuByKeyword(3, 1, keyword);
        }

    }//GEN-LAST:event_btnTampilkanMenuActionPerformed

    private void TFNamaCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TFNamaCustomerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TFNamaCustomerActionPerformed

    private void btnLanjutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLanjutActionPerformed
        // TODO add your handling code here:
        String login = cbPilihan.getSelectedItem().toString();

        if (login.equals("Customer")) {
            JOptionPane.showMessageDialog(this, "Masuk Sebagai Customer");
            jTabbedPane.setSelectedIndex(1);
        } else if (login.equals("Seller")) {
            JOptionPane.showMessageDialog(this, "Masuk Sebagai Seller");
            jTabbedPane.setSelectedIndex(2);
        }
        
         
    }//GEN-LAST:event_btnLanjutActionPerformed

    private void cbPilihanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPilihanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbPilihanActionPerformed

    private void btnHapusMakanan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusMakanan1ActionPerformed
        // TODO add your handling code here:
        int barisTerpilih = TabelEditMinuman.getSelectedRow();
        int idMenu = Integer.parseInt(TabelEditMinuman.getValueAt(barisTerpilih, 1).toString());
        int pil = JOptionPane.showConfirmDialog(this, "Anda Yakin ingin Menghapus Menu ini?", 
                "Perhatian", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
        
        if (pil == JOptionPane.OK_OPTION){
             deleteMenu(idMenu);
             loadMenu(1);
            tampilMinuman();
        }
    }//GEN-LAST:event_btnHapusMakanan1ActionPerformed

    private void btnEditMinumanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditMinumanActionPerformed
        // TODO add your handling code here:
        RBJenisMinum.setSelected(true);
        fungsiEditMenuMinuman();
        btnSubmitMenu.setText("Ubah");
    }//GEN-LAST:event_btnEditMinumanActionPerformed

    private void TBStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TBStatusActionPerformed
        // TODO add your handling code here:
        statusStan();
        radioButton();
    }//GEN-LAST:event_TBStatusActionPerformed

    private void btnSubmitMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitMenuActionPerformed
        // TODO add your handling code here:
        String tombol = btnSubmitMenu.getText();
        editMenu(tombol);
        loadMenu(1);
        tampilMakanan();
        tampilMinuman();
    }//GEN-LAST:event_btnSubmitMenuActionPerformed

    private void btnHapusMakananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusMakananActionPerformed
        // TODO add your handling code here:
        int barisTerpilih = TabelEditMakanan.getSelectedRow();
        int idMenu = Integer.parseInt(TabelEditMakanan.getValueAt(barisTerpilih, 1).toString());
        int pil = JOptionPane.showConfirmDialog(this, "Anda Yakin ingin Menghapus Menu ini?", 
                "Perhatian", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
        
        if (pil == JOptionPane.OK_OPTION){
             deleteMenu(idMenu);
             loadMenu(1);
            tampilMakanan();;
        }       
    }//GEN-LAST:event_btnHapusMakananActionPerformed

    private void btnEditMakananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditMakananActionPerformed
        // TODO add your handling code here:
        RBJenisMakan.setSelected(true);
        fungsiEditMenuMakanan();
        btnSubmitMenu.setText("Ubah");
    }//GEN-LAST:event_btnEditMakananActionPerformed

    private void cbStatusMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbStatusMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbStatusMenuActionPerformed

    private void tfHargaMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfHargaMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfHargaMenuActionPerformed

    private void tfNamaMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNamaMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNamaMenuActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        // TODO add your handling code here:


        String email = TFEmail.getText();
        String pass = TFPassword.getText();
        login(email,pass);
        if (seller != null){
            txtNamaStan.setText(seller.getNama());
            jTabbedPane.setSelectedIndex(3);
            loadKolomPesanan();
            updatePesanan();
            loadMenu(1);
            tampilMakanan();
            tampilMinuman();
            statusStan();
            loadKolomTransaksi();
        }else{
            
        }
        
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdminActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAdminActionPerformed

    private void TFEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TFEmailActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_TFEmailActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        jTabbedPane.setSelectedIndex(0);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void RBJenisMakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RBJenisMakanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RBJenisMakanActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        jTabbedPane.setSelectedIndex(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
         int barisTerpilih = jtBooking.getSelectedRow();
         String idMeja = modelMeja.getValueAt(barisTerpilih, 2).toString();
         String no = modelMeja.getValueAt(barisTerpilih, 0).toString();
         String kursi = modelMeja.getValueAt(barisTerpilih, 1).toString();
         meja = new Meja(idMeja, no, kursi);
        TFMeja.setText(idMeja);
        TFNoMeja.setText(no);
        TFKursi.setText(kursi);
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void TFNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TFNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TFNoActionPerformed

    private void TFKursiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TFKursiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TFKursiActionPerformed

    private void TFMejaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TFMejaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TFMejaActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        String booking = cbPesanan.getSelectedItem().toString();
        String nama = TFNama.getText();
        String no =TFNo.getText();
        
        if(nama.trim().isEmpty() || no.trim().isEmpty() ){
            JOptionPane.showMessageDialog(this, "Mohon Untuk Mengisi Nama dan Nomor Telefon"); 
        }else{
            if (booking.equals("On Site")){              
                customer = new Customer(nama, no );
                bookingMeja(1);
                TFNamaCustomer.setText(nama);
                TFNoTelpCustomer.setText(no);
                TFNomorMeja.setText(TFMeja.getText());
                jTabbedPane.setSelectedIndex(4);
                
            }else if(booking.equals("Take Away")){
                customer = new Customer(nama, no);
                JOptionPane.showMessageDialog(this, "Silahkan memesan makanan");
                TFNamaCustomer.setText(nama);
                TFNoTelpCustomer.setText(no);
                jTabbedPane.setSelectedIndex(4);
            }
        }

        
        
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        tfNamaMenu.setText("");
        tfHargaMenu.setText("");
        btnSubmitMenu.setText("Submit");
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        jTabbedPane.setSelectedIndex(0);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        jTabbedPane.setSelectedIndex(0);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void TFNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TFNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TFNamaActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        TFNama.setText("");
        TFNo.setText("");
        TFKursi.setText("");
        TFMeja.setText("");
    }//GEN-LAST:event_jButton9ActionPerformed

    private void TFNoMejaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TFNoMejaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TFNoMejaActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        int barisTerpilih = TableTransaksi.getSelectedRow();
        String idTransaksi = TableTransaksi.getValueAt(barisTerpilih, 0).toString();
        updateTablePesanan(2,idTransaksi);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        updateTablePesanan(1," ");
    }//GEN-LAST:event_jButton12ActionPerformed


    
    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonHapusPesanan;
    private javax.swing.JButton ButtonKeranjangMakanan;
    private javax.swing.JButton ButtonKeranjangMinuman;
    private javax.swing.JScrollPane PaneBookingMeja;
    private javax.swing.JScrollPane PaneLogin;
    private javax.swing.JScrollPane PaneLoginSeller;
    private javax.swing.JScrollPane PanePesanMakan;
    private javax.swing.JScrollPane PaneSeller;
    private javax.swing.JRadioButton RBJenisMakan;
    private javax.swing.JRadioButton RBJenisMinum;
    private javax.swing.JToggleButton TBStatus;
    private javax.swing.JTextField TFCariMakan;
    private javax.swing.JTextField TFCariMinum;
    private javax.swing.JTextField TFCariStan;
    private javax.swing.JTextField TFCatatanMakanan;
    private javax.swing.JTextField TFCatatanMinuman;
    private javax.swing.JTextField TFEmail;
    private javax.swing.JTextField TFJumlahBeliMakan;
    private javax.swing.JTextField TFJumlahBeliMinuman;
    private javax.swing.JTextField TFKursi;
    private javax.swing.JTextField TFMeja;
    private javax.swing.JTextField TFNama;
    private javax.swing.JTextField TFNamaCustomer;
    private javax.swing.JTextField TFNo;
    private javax.swing.JTextField TFNoMeja;
    private javax.swing.JTextField TFNoTelpCustomer;
    private javax.swing.JTextField TFNomorMeja;
    private javax.swing.JTextField TFPassword;
    private javax.swing.JTextField TFTotalHarga;
    private javax.swing.JTable TabelEditMakanan;
    private javax.swing.JTable TabelEditMinuman;
    private javax.swing.JTable TabelKeranjang;
    private javax.swing.JTable TabelMenuMakanan;
    private javax.swing.JTable TabelMenuMinuman;
    private javax.swing.JTable TabelStan;
    private javax.swing.JTable TableTransaksi;
    private javax.swing.JLabel Text;
    private javax.swing.JButton btnAdmin;
    private javax.swing.JButton btnCariMakan;
    private javax.swing.JButton btnCariMinum;
    private javax.swing.JButton btnCariStan;
    private javax.swing.JButton btnEditMakanan;
    private javax.swing.JButton btnEditMinuman;
    private javax.swing.JButton btnHapusMakanan;
    private javax.swing.JButton btnHapusMakanan1;
    private javax.swing.JButton btnLanjut;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSubmitMenu;
    private javax.swing.JButton btnTampilkanMenu;
    private javax.swing.JComboBox<String> cbPesanan;
    private javax.swing.JComboBox<String> cbPilihan;
    private javax.swing.JComboBox<String> cbStatusMenu;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTable jtBooking;
    private javax.swing.JPanel panelLogin;
    private javax.swing.JPanel panelPesanan;
    private javax.swing.JTable tbPesanan;
    private javax.swing.JTextField tfHargaMenu;
    private javax.swing.JTextField tfNamaMenu;
    private javax.swing.JLabel txtNamaStan;
    // End of variables declaration//GEN-END:variables
}
