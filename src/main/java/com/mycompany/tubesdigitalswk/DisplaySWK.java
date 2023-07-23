/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.tubesdigitalswk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author putra
 */
public class DisplaySWK extends javax.swing.JFrame {
    
    private Connection con;
    private DefaultTableModel modelStan = new DefaultTableModel();
    private DefaultTableModel modelMakan = new DefaultTableModel();
    private DefaultTableModel modelMinum = new DefaultTableModel();
    private DefaultTableModel modelKeranjang = new DefaultTableModel();
    private DefaultTableModel modelMeja = new DefaultTableModel();
    private ArrayList<Stan> stn = new ArrayList<>();
    private ArrayList<Makanan> mkn = new ArrayList<>(); 
    private ArrayList<Minuman> mnm = new ArrayList<>();
    
    void loadKolomStan(){
        modelStan.addColumn("Nomor Stan");
        modelStan.addColumn("Nama Stan");
        modelStan.addColumn("Status");
        TabelStan.setModel(modelStan);
    }
    
    void loadKolomMakan(){
        modelMakan.addColumn("Nama Makanan");
        modelMakan.addColumn("Harga");
        modelMakan.addColumn("Status");
        TabelMenuMakanan.setModel(modelMakan);
    }
    
     void loadKolomMinum(){
        modelMinum.addColumn("Nama Minuman");
        modelMinum.addColumn("Harga");
        modelMinum.addColumn("Status");;
        TabelMenuMinuman.setModel(modelMinum);
    }
     
    void loadKolomKeranjang(){
        modelKeranjang.addColumn("No Stan");
        modelKeranjang.addColumn("Makanan/Minuman");
        modelKeranjang.addColumn("Harga");
        modelKeranjang.addColumn("Jumlah beli");
        modelKeranjang.addColumn("Catatan");
    }
        
    
    private void loadStan(){
        if(con != null){
            stn = new ArrayList<>();
            String kueri = "Select * from stan;";
            try{
                PreparedStatement ps = con.prepareStatement(kueri);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    int id = rs.getInt("ID_Stan");
                    int nomor = rs.getInt("Nomor_Stan");
                    String nama = rs.getString("Nama_Stan");
                    int status = rs.getInt("status");
                    
                    Stan stan = new Stan(id, nomor, nama, status);
                    stn.add(stan);
                }
                rs.close();
                ps.close();
            }catch(SQLException ex){
                Logger.getLogger(DisplaySWK.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void loadMenuMakan(){
        if(con != null){
            mkn = new ArrayList<>();
            String kueri = "Select * from makanan;";
            try{
                PreparedStatement ps = con.prepareStatement(kueri);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    int id = rs.getInt("ID_Makanan");
                    String nama = rs.getString("Nama_Makanan");
                    float harga = rs.getFloat("Harga_Makanan");
                    int status = rs.getInt("status");
                    int idStan = rs.getInt("ID_Stan");
                    
                    Makanan makanan = new Makanan(id, nama, harga, idStan, status);
                    mkn.add(makanan);
                }
                rs.close();
                ps.close();
            }catch(SQLException ex){
                Logger.getLogger(DisplaySWK.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
     private void loadMenuMinum(){
        if(con != null){
            mkn = new ArrayList<>();
            String kueri = "Select * from minuman;";
            try{
                PreparedStatement ps = con.prepareStatement(kueri);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    int id = rs.getInt("ID_Minuman");
                    String nama = rs.getString("Nama_Minuman");
                    float harga = rs.getFloat("Harga_Minuman");
                    int status = rs.getInt("status");
                    int idStan = rs.getInt("ID_Stan");
                    
                    Minuman minuman = new Minuman(id, nama, harga, idStan, status);
                    mnm.add(minuman);
                }
                rs.close();
                ps.close();
            }catch(SQLException ex){
                Logger.getLogger(DisplaySWK.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
     
    private void loadKeranjang(){
        if(con != null){
            stn = new ArrayList<>();
            String kueri = "Select * from stan;";
            try{
                PreparedStatement ps = con.prepareStatement(kueri);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    int id = rs.getInt("ID_Stan");
                    int nomor = rs.getInt("Nomor_Stan");
                    String nama = rs.getString("Nama_Stan");
                    int status = rs.getInt("status");
                    
                    Stan stan = new Stan(id, nomor, nama, status);
                    stn.add(stan);
                }
                rs.close();
                ps.close();
            }catch(SQLException ex){
                Logger.getLogger(DisplaySWK.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    void tambahKeranjang(){
        
    }
     
    private void tampilMinuman(){
        modelMinum.setRowCount(0);
        for(Minuman mn: mnm){
            modelMinum.addRow(new Object [] {mn.getNama(), mn.getHarga(),mn.getStatus()});
        }
    }
    
    
    private void tampilMakanan(){
        modelMakan.setRowCount(0);
        for(Makanan m: mkn){
            modelMakan.addRow(new Object [] {m.getNama(), m.getHarga(), m.getStatus()});
        }
    }
    
    private void tampilStan(){
        modelStan.setRowCount(0);
        for(Stan s: stn){
            modelStan.addRow(new Object [] {s.getNomor(),s.getNama(),s.getStatus()});
        }
    }
    

    /**
     * Creates new form DisplaySWK
     */
    public DisplaySWK() {
        initComponents();
        loadKolomStan();
        loadKolomMakan();
        loadKolomMinum();
        con = Koneksi.bukaKoneksi();
        reset();
    }
    
    private void reset(){    
        loadStan();
        tampilStan();
        loadMenuMakan();
        tampilMakanan();
        loadMenuMinum();
        tampilMinuman();
    }
    
    void cariStanByKeyword(String keyword){
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
    
    void cariMinumByKeyword(int metode ,String keyword){
        if (con != null) {   
        ArrayList<Minuman> hasilPencarian = new ArrayList<>();
        String kueri; 
        if (metode ==  2){
        kueri = "Select * from minuman where Nama_Minuman like ?";
        try {
            PreparedStatement ps = con.prepareStatement(kueri);
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID_Minuman");
                String nama = rs.getString("Nama_Minuman");
                int harga = rs.getInt("Harga_Minuman");
                int idStan = rs.getInt("ID_Stan");
                int status = rs.getInt("status");
                Minuman minuman = new Minuman(id, nama, harga, idStan, status);
                hasilPencarian.add(minuman);
            }
            rs.close();
            ps.close();

            // Update daftarBuku dengan hasil pencarian
            mnm = hasilPencarian;
            // Tampilkan data hasil pencarian di tabel
            tampilMinuman();

        } catch (SQLException ex) {
            Logger.getLogger(DisplaySWK.class.getName()).log(Level.SEVERE, null, ex);
        }
        }else{
            kueri = "Select m.ID_Minuman, m.Nama_Minuman, m.Harga_Minuman, m.status, m.ID_Stan, s.Nomor_Stan from minuman m INNER JOIN stan s ON s.ID_Stan = m.ID_Stan WHERE s.Nomor_Stan = " + keyword ;
        try {
            PreparedStatement ps = con.prepareStatement(kueri);
            ResultSet rs = ps.executeQuery();
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID_Minuman");
                String nama = rs.getString("Nama_Minuman");
                int harga = rs.getInt("Harga_Minuman");
                int idStan = rs.getInt("ID_Stan");
                int status = rs.getInt("status");
                Minuman minuman = new Minuman(id, nama, harga, idStan, status);
                hasilPencarian.add(minuman);
            }
            rs.close();
            ps.close();

            // Update daftarBuku dengan hasil pencarian
            mnm = hasilPencarian;
            // Tampilkan data hasil pencarian di tabel
            tampilMinuman();

        } catch (SQLException ex) {
            Logger.getLogger(DisplaySWK.class.getName()).log(Level.SEVERE, null, ex);
        }
        }           
    }
    }
    
    void cariMakanByKeyword(int metode ,String keyword){
        if (con != null) {   
        if (metode ==  2){
            System.out.println("anjing");
        ArrayList<Makanan> hasilPencarian = new ArrayList<>();
        String kueri = "Select * from makanan where Nama_Makanan like ?";
        try {
            PreparedStatement ps = con.prepareStatement(kueri);
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID_Makanan");
                String nama = rs.getString("Nama_Makanan");
                int harga = rs.getInt("Harga_Makanan");
                int idStan = rs.getInt("ID_Stan");
                int status = rs.getInt("status");
                Makanan makanan = new Makanan(id, nama, harga, idStan, status);
                hasilPencarian.add(makanan);
            }
            rs.close();
            ps.close();

            // Update daftarBuku dengan hasil pencarian
            mkn = hasilPencarian;
            // Tampilkan data hasil pencarian di tabel
            tampilMakanan();

        } catch (SQLException ex) {
            Logger.getLogger(DisplaySWK.class.getName()).log(Level.SEVERE, null, ex);
        }
        }else{
        ArrayList<Makanan> hasilPencarian = new ArrayList<>();
        String kueri = "Select m.ID_Makanan, m.Nama_Makanan, m.Harga_Makanan, m.status, m.ID_Stan, s.Nomor_Stan from makanan m INNER JOIN stan s ON s.ID_Stan = m.ID_Stan WHERE s.Nomor_Stan = " + keyword ;
        try {
            PreparedStatement ps = con.prepareStatement(kueri);
            ResultSet rs = ps.executeQuery();
            rs = ps.executeQuery();
             System.out.println("berhasil tampil makan");
       
            while (rs.next()) {
                int id = rs.getInt("ID_Makanan");
                String nama = rs.getString("Nama_Makanan");
                int harga = rs.getInt("Harga_Makanan");
                int idStan = rs.getInt("ID_Stan");
                int status = rs.getInt("status");
                Makanan makanan = new Makanan(id, nama, harga, idStan, status);
                hasilPencarian.add(makanan);
            }
            rs.close();
            ps.close();

            // Update daftarBuku dengan hasil pencarian
            mkn = hasilPencarian;
            // Tampilkan data hasil pencarian di tabel
            tampilMakanan();
        }catch (SQLException ex) {
            Logger.getLogger(DisplaySWK.class.getName()).log(Level.SEVERE, null, ex);
        }
        }        
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
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
        TFPesanMakanan = new javax.swing.JTextField();
        ButtonKeranjangMakanan = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        TFPesanMinuman = new javax.swing.JTextField();
        ButtonKeranjangMinuman = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        TFJumlahBeliMakan = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        TFJumlahBeliMinuman = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jTextField6 = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TabelKeranjang = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        ButtonHapusPesanan = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jSeparator3 = new javax.swing.JSeparator();
        jButton5 = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        TFCariStan = new javax.swing.JTextField();
        btnCariStan = new javax.swing.JButton();
        TFCariMakan = new javax.swing.JTextField();
        TFCariMinum = new javax.swing.JTextField();
        btnCariMakan = new javax.swing.JButton();
        btnCariMinum = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1324, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1088, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab1", jPanel4);

        jPanel1.setBackground(new java.awt.Color(10, 38, 71));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));

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

        TFPesanMakanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TFPesanMakananActionPerformed(evt);
            }
        });

        ButtonKeranjangMakanan.setText("Masukan Ke Keranjang");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Input jumlah beli minuman : ");

        ButtonKeranjangMinuman.setText("Masukan Ke Keranjang");

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Catatan Makanan : ");

        TFJumlahBeliMakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TFJumlahBeliMakanActionPerformed(evt);
            }
        });

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Catatan Minuman : ");

        jPanel2.setBackground(new java.awt.Color(27, 36, 48));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Total Harga Pesanan :");

        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(216, 146, 22));
        jLabel12.setText("Pesanan");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Nomor Meja :");

        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Nomor Hp :");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Nama : ");

        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });

        jButton6.setText("Bayar Sekarang");

        jToggleButton1.setText("Takeaway");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel13))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jTextField6, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(12, 12, 12))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(183, 183, 183)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jToggleButton1)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(120, 120, 120)
                                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(28, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(36, 36, 36)
                    .addComponent(jLabel15)
                    .addContainerGap(394, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 226, Short.MAX_VALUE)
                .addComponent(jToggleButton1)
                .addGap(18, 18, 18)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton6)
                .addGap(81, 81, 81))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(86, 86, 86)
                    .addComponent(jLabel15)
                    .addContainerGap(520, Short.MAX_VALUE)))
        );

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
        jLabel11.setText("CEK KETERSEDIAAN MEJA");

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "No Meja", "Jumlah Kursi", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(jTable4);

        jButton5.setText("Booking");

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
                        .addGap(427, 427, 427)
                        .addComponent(jButton5))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator3)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(230, 230, 230)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonHapusPesanan)
                    .addComponent(jButton5))
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(ButtonKeranjangMakanan))
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(jLabel5)
                                                .addGap(18, 18, 18)
                                                .addComponent(TFJumlahBeliMakan))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel7)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(TFPesanMakanan, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 9, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(TFCariMakan, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnCariMakan)))
                                .addGap(39, 39, 39)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(TFCariMinum, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnCariMinum))
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ButtonKeranjangMinuman, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(18, 18, 18)
                                        .addComponent(TFJumlahBeliMinuman, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(TFPesanMinuman)))
                                .addGap(25, 25, 25))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(btnTampilkanMenu)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 773, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCariStan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TFCariStan, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jSeparator1)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(604, 604, 604)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnCariStan)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnTampilkanMenu)
                                    .addComponent(btnReset))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(TFCariMakan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TFCariMinum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnCariMakan)
                                    .addComponent(btnCariMinum))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(TFPesanMakanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8)
                                    .addComponent(TFPesanMinuman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(TFJumlahBeliMakan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)
                                    .addComponent(TFJumlahBeliMinuman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(ButtonKeranjangMakanan)
                                    .addComponent(ButtonKeranjangMinuman))
                                .addGap(15, 15, 15))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(TFCariStan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("tab1", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1324, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 6, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1119, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1119, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane1))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnTampilkanMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTampilkanMenuActionPerformed
        // TODO add your handling code here:
        int barisTerpilih = TabelStan.getSelectedRow();
        String keyword = modelStan.getValueAt(barisTerpilih, 0).toString();
        if(keyword.length() == 0){
            loadMenuMakan();
            tampilMakanan();
            loadMenuMinum();
            tampilMinuman();
        }else{
            cariMakanByKeyword( 1 ,keyword);
            cariMinumByKeyword( 1 ,keyword);
            tampilMakanan();
            tampilMinuman();
        }
        System.out.println(keyword);
        
        
        
        
    }//GEN-LAST:event_btnTampilkanMenuActionPerformed

    private void TFPesanMakananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TFPesanMakananActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TFPesanMakananActionPerformed

    private void TFJumlahBeliMakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TFJumlahBeliMakanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TFJumlahBeliMakanActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void ButtonHapusPesananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonHapusPesananActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ButtonHapusPesananActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnCariStanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariStanActionPerformed
        // TODO add your handling code here:
        String keyword = TFCariStan.getText().trim();
        if(keyword.length() == 0){
            loadStan();
            tampilStan();
        }else{
            cariStanByKeyword(keyword);
            tampilStan();
        }         
    }//GEN-LAST:event_btnCariStanActionPerformed

    private void btnCariMakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariMakanActionPerformed
        // TODO add your handling code here:
        String keyword = TFCariMakan.getText().trim();
        if(keyword.length() == 0){
            loadMenuMakan();
            tampilMakanan();
        }else{
            cariMakanByKeyword( 2 ,keyword);
            tampilMakanan();
           
        }         
    
    }//GEN-LAST:event_btnCariMakanActionPerformed

    private void btnCariMinumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariMinumActionPerformed
        // TODO add your handling code here:
        String keyword = TFCariMinum.getText().trim();
        if(keyword.length() == 0){
            loadMenuMinum();
            tampilMinuman();
        }else{
            cariMinumByKeyword( 2 ,keyword);
            tampilMinuman();
            System.out.println("dsafdf");
        }
    }//GEN-LAST:event_btnCariMinumActionPerformed

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonHapusPesanan;
    private javax.swing.JButton ButtonKeranjangMakanan;
    private javax.swing.JButton ButtonKeranjangMinuman;
    private javax.swing.JTextField TFCariMakan;
    private javax.swing.JTextField TFCariMinum;
    private javax.swing.JTextField TFCariStan;
    private javax.swing.JTextField TFJumlahBeliMakan;
    private javax.swing.JTextField TFJumlahBeliMinuman;
    private javax.swing.JTextField TFPesanMakanan;
    private javax.swing.JTextField TFPesanMinuman;
    private javax.swing.JTable TabelKeranjang;
    private javax.swing.JTable TabelMenuMakanan;
    private javax.swing.JTable TabelMenuMinuman;
    private javax.swing.JTable TabelStan;
    private javax.swing.JButton btnCariMakan;
    private javax.swing.JButton btnCariMinum;
    private javax.swing.JButton btnCariStan;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnTampilkanMenu;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JToggleButton jToggleButton1;
    // End of variables declaration//GEN-END:variables
}
