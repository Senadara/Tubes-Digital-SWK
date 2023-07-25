/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.tubesdigitalswk;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
    private ArrayList<Keranjang> krnjg = new ArrayList<>();
    

    private float tHarga = 0;

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
    private void loadMenuMakan(int metode) {
        if (con != null) {
            int idStn = 2001;
            ArrayList<Makanan> mknn = new ArrayList<>();
            ArrayList<Minuman> mnmn = new ArrayList<>();
            if (metode == 1){
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
        }
        }
    }

    private void loadMenuMakan() {
        if (con != null) {
            mkn = new ArrayList<>();
            String kueri = "Select * from menu WHERE Type = 1;";
            try {
                PreparedStatement ps = con.prepareStatement(kueri);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("ID_Menu");
                    String nama = rs.getString("Nama");
                    float harga = rs.getFloat("Harga");
                    int status = rs.getInt("status");
                    int idStan = rs.getInt("ID_Stan");

                    Makanan makanan = new Makanan(id, nama, harga, idStan, status);
                    mkn.add(makanan);
                }
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(DisplaySWK.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void loadMenuMinum() {
        if (con != null) {
            mnm = new ArrayList<>();
            String kueri = "Select * from menu WHERE Type = 2;";
            try {
                PreparedStatement ps = con.prepareStatement(kueri);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("ID_Menu");
                    String nama = rs.getString("Nama");
                    float harga = rs.getFloat("Harga");
                    int status = rs.getInt("status");
                    int idStan = rs.getInt("ID_Stan");

                    Minuman minuman = new Minuman(id, nama, harga, idStan, status);
                    mnm.add(minuman);
                }
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(DisplaySWK.class.getName()).log(Level.SEVERE, null, ex);
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

    /**
     * Creates new form DisplaySWK
     */
    public DisplaySWK() {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        loadKolomStan();
        loadKolomMakan();
        loadKolomMinum();
        loadKolomKeranjang();
        con = Koneksi.bukaKoneksi();
        reset();
    }

    private void reset() {
        loadStan();
        tampilStan();
        loadMenuMakan();
        tampilMakanan();
        loadMenuMinum();
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

    private int getIdTransaksi() {
        int nomorUrutTerakhir = 0;
        int tahunSaatIni = LocalDate.now().getYear();
        String kueri = "SELECT MAX(ID_Pesanan) FROM pesanan WHERE YEAR(tanggal_transaksi) = ?";
        try {
            PreparedStatement ps = con.prepareStatement(kueri);
            ps.setInt(1, tahunSaatIni);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nomorUrutTerakhir = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error saat mendapatkan nomor urut terakhir: " + e.getMessage());
        }
        return nomorUrutTerakhir;
    }

    // Metode untuk membuat ID transaksi berdasarkan tahun saat ini dan nomor urut terakhir
    public String buatIDTransaksi() {
        int nomorUrutTerakhir = getIdTransaksi();
        int tahunSaatIni = LocalDate.now().getYear();
        int nomorUrutBaru = nomorUrutTerakhir + 1;
        String idTransaksi = tahunSaatIni + "" + nomorUrutBaru;
        return idTransaksi;
    }

    public void uploadTransaksi() {
        String kueri = "INSERT INTO pesanan (ID_Pesanan, ID_Menu, Jumlah, ID_Meja, ID_Customer, Total_Belanja, Tanggal_Transaksi) VALUES (?, ?, ?, ?, ?, ?, ?)";
        LocalDate tanggalSaatIni = LocalDate.now();
        String tanggalString = tanggalSaatIni.toString();
        java.sql.Date sqlDate = java.sql.Date.valueOf(tanggalString);
        int id = Integer.parseInt(buatIDTransaksi());
        int idCustomer = 2001;
        try {
            PreparedStatement ps = con.prepareStatement(kueri);
            for (Keranjang k : krnjg) {
                ps.setInt(1, id);
                ps.setInt(2, k.getIdMakan());
                ps.setInt(3, k.getJumlahBeli());
                ps.setInt(4, Integer.parseInt(TFNomorMeja.getText()));
                ps.setInt(5, idCustomer);
                ps.setFloat(6, Float.parseFloat(TFTotalHarga.getText()));
                ps.setDate(7, sqlDate);

                ps.executeUpdate();
            }

            System.out.println("Data berhasil diupload ke database.");

        } catch (SQLException e) {
            System.err.println("Error saat mengupload data ke database: " + e.getMessage());
        }
    }

    private void editMenu(int idMakan, int type) {
        if (con != null) {
            int idStan = 2001; //nanti dirubah
            String namaMakanan = tfNamaMakanan.getText();
            float hargaMakanan = Float.parseFloat(tfHargaMakanan.getText());
            int statusMakanan = Integer.parseInt(cbStatusMakanan.getSelectedItem().toString());
            String kueri = "INSERT INTO menu (ID_Menu, Type, Nama, Harga, Status, ID_Stan) VALUES (?, ?, ?, ?, ?, ?)";
            try {
                PreparedStatement ps = con.prepareStatement(kueri);
                ps.setInt(1, idMakan);
                ps.setInt(2, type);
                ps.setString(3, namaMakanan);
                ps.setFloat(4, hargaMakanan);
                ps.setInt(5, statusMakanan);
                ps.setInt(6, idStan);

                //ps.setInt(5, idStan);
                int rowsAffected = ps.executeUpdate();

                if (rowsAffected == 1) {
                    JOptionPane.showMessageDialog(this, "Insert Data Successfully");
                    tfNamaMakanan.setText("");
                    tfHargaMakanan.setText("");
                    cbStatusMakanan.setSelectedIndex(0);
                } else {
                    JOptionPane.showMessageDialog(this, "Insert Data Failed");
                }

                ps.close();
                con.close(); // Close the connection after use.
            } catch (SQLException ex) {
                Logger.getLogger(DisplaySWK.class.getName()).log(Level.SEVERE, null, ex);
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

        jPanel4 = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        panelLogin = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        cbPilihan = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        btnLanjut = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
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
        TFNoTelp = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        TFNomorMeja = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        TFTotalHarga = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        TFCariStan = new javax.swing.JTextField();
        btnCariStan = new javax.swing.JButton();
        TFCariMakan = new javax.swing.JTextField();
        TFCariMinum = new javax.swing.JTextField();
        btnCariMakan = new javax.swing.JButton();
        btnCariMinum = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        tfNamaMakanan = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        tfHargaMakanan = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        cbStatusMakanan = new javax.swing.JComboBox<>();
        jLabel36 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        TabelEditMakanan = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        tfNamaStan = new javax.swing.JTextField();
        btnEditMakanan = new javax.swing.JButton();
        btnHapusMakanan = new javax.swing.JButton();
        btnUbahMakanan = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        toggleButtonStatus = new javax.swing.JToggleButton();
        jScrollPane12 = new javax.swing.JScrollPane();
        TabelEditMinuman = new javax.swing.JTable();
        jLabel34 = new javax.swing.JLabel();
        btnEditMinuman = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tbPesanan = new javax.swing.JTable();
        jLabel35 = new javax.swing.JLabel();
        tfCari = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel4.setBackground(new java.awt.Color(10, 38, 71));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
                .addContainerGap(490, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addGap(532, 532, 532))
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addGap(437, 437, 437)
                .addComponent(jLabel17)
                .addGap(18, 18, 18)
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbPilihan, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelLoginLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(btnLanjut)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelLoginLayout.setVerticalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addGap(121, 121, 121)
                .addComponent(jLabel16)
                .addGap(70, 70, 70)
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(cbPilihan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addComponent(btnLanjut)
                .addContainerGap(643, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("tab2", panelLogin);

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

        TFNoTelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TFNoTelpActionPerformed(evt);
            }
        });

        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
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
                                        .addComponent(TFNoTelp, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                                        .addComponent(jTextField7)
                                        .addComponent(TFNomorMeja, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(TFTotalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
                        .addComponent(ButtonHapusPesanan))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TFNoTelp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(panelPesananLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
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

        jScrollPane6.setViewportView(panelPesanan);

        jTabbedPane3.addTab("tab4", jScrollPane6);

        jPanel1.setBackground(new java.awt.Color(10, 38, 71));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel11.setBackground(new java.awt.Color(0, 129, 138));

        jLabel28.setFont(new java.awt.Font("Trebuchet MS", 1, 36)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(216, 146, 22));
        jLabel28.setText("DIGITAL SWK");

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tfNamaMakanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNamaMakananActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel29.setText("Nama Menu:");

        tfHargaMakanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfHargaMakananActionPerformed(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel31.setText("Harga:");

        jLabel32.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel32.setText("Status:");

        cbStatusMakanan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tersedia", "Habis" }));
        cbStatusMakanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbStatusMakananActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32))
                .addGap(25, 25, 25)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbStatusMakanan, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(tfNamaMakanan, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                        .addComponent(tfHargaMakanan)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfNamaMakanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addGap(8, 8, 8)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(tfHargaMakanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbStatusMakanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32))
                .addGap(35, 35, 35))
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

        jLabel19.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Nama Stan:");

        jLabel20.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Status Stan:");

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

        btnUbahMakanan.setText("UBAH");
        btnUbahMakanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahMakananActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Tabel Makanan");

        toggleButtonStatus.setText("Buka");
        toggleButtonStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleButtonStatusActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel36)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addGap(5, 5, 5)
                                .addComponent(tfNamaStan, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(62, 62, 62)
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(toggleButtonStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 184, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnUbahMakanan, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHapusMakanan, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(216, 216, 216)))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEditMakanan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel22)
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel34))
                    .addComponent(btnEditMinuman, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(137, 137, 137))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditMakanan)
                        .addGap(5, 5, 5)
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditMinuman))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel36)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfNamaStan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20)
                            .addComponent(toggleButtonStatus))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnUbahMakanan)
                            .addComponent(btnHapusMakanan))))
                .addContainerGap(121, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

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

        tfCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfCariActionPerformed(evt);
            }
        });

        btnCari.setText("Cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(407, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addGap(212, 212, 212)
                        .addComponent(tfCari, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCari))
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 622, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(274, 274, 274))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(tfCari, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCari))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(166, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(574, 574, 574)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(445, Short.MAX_VALUE)))
        );

        jTabbedPane3.addTab("tab3", jPanel5);

        jPanel4.add(jTabbedPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1310, 1030));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 1310, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 1032, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cbPilihanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPilihanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbPilihanActionPerformed

    private void btnLanjutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLanjutActionPerformed
        // TODO add your handling code here:
        String login = cbPilihan.getSelectedItem().toString();

        if (!login.equals("Seller")) {
            JOptionPane.showMessageDialog(this, "Masuk Sebagai Customer");
        } else if (login.equals("Customer")) {
            JOptionPane.showMessageDialog(this, "Masuk Sebagai Seller");
        }

    }//GEN-LAST:event_btnLanjutActionPerformed

    private void btnCariMinumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariMinumActionPerformed
        // TODO add your handling code here:
        String keyword = TFCariMinum.getText().trim();
        if (keyword.length() == 0) {
            loadMenuMinum();
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
            loadMenuMakan();
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
    }//GEN-LAST:event_ButtonHapusPesananActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        uploadTransaksi();
        System.out.println("berhasil upload transaksi");
    }//GEN-LAST:event_jButton6ActionPerformed

    private void TFNoTelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TFNoTelpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TFNoTelpActionPerformed

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
            loadMenuMakan();
            tampilMakanan();
            loadMenuMinum();
            tampilMinuman();
        } else {
            cariMenuByKeyword(3, 1, keyword);
        }

    }//GEN-LAST:event_btnTampilkanMenuActionPerformed

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void tfCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfCariActionPerformed

    private void tfNamaMakananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNamaMakananActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNamaMakananActionPerformed

    private void tfHargaMakananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfHargaMakananActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfHargaMakananActionPerformed

    private void toggleButtonStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleButtonStatusActionPerformed
        // TODO add your handling code here:
        loadMenuMakan(1);
        tampilMakanan();
        tampilMinuman();
    }//GEN-LAST:event_toggleButtonStatusActionPerformed

    private void btnUbahMakananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahMakananActionPerformed
        // TODO add your handling code here:
        editMenu(1, 2);

    }//GEN-LAST:event_btnUbahMakananActionPerformed

    private void btnHapusMakananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusMakananActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHapusMakananActionPerformed

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCariActionPerformed

    private void btnEditMakananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditMakananActionPerformed
        // TODO add your handling code here:
       try{
                PreparedStatement ps = con.prepareStatement("Select * from menu WHERE ID_Stan");
                ResultSet rs = ps.executeQuery();
                if(rs.next() == true){
                    tfNamaMakanan.setText(rs.getString(1));
                    tfHargaMakanan.setText(rs.getString(2));
                }else{
                    JOptionPane.showMessageDialog(this, "Data Tidak Di Temukan");
                }
                   
            } catch (SQLException ex) {
                Logger.getLogger(DisplaySWK.class.getName()).log(Level.SEVERE, null, ex);
            }
    }//GEN-LAST:event_btnEditMakananActionPerformed

    private void cbStatusMakananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbStatusMakananActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbStatusMakananActionPerformed

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
    private javax.swing.JTextField TFCatatanMakanan;
    private javax.swing.JTextField TFCatatanMinuman;
    private javax.swing.JTextField TFJumlahBeliMakan;
    private javax.swing.JTextField TFJumlahBeliMinuman;
    private javax.swing.JTextField TFNoTelp;
    private javax.swing.JTextField TFNomorMeja;
    private javax.swing.JTextField TFTotalHarga;
    private javax.swing.JTable TabelEditMakanan;
    private javax.swing.JTable TabelEditMinuman;
    private javax.swing.JTable TabelKeranjang;
    private javax.swing.JTable TabelMenuMakanan;
    private javax.swing.JTable TabelMenuMinuman;
    private javax.swing.JTable TabelStan;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnCariMakan;
    private javax.swing.JButton btnCariMinum;
    private javax.swing.JButton btnCariStan;
    private javax.swing.JButton btnEditMakanan;
    private javax.swing.JButton btnEditMinuman;
    private javax.swing.JButton btnHapusMakanan;
    private javax.swing.JButton btnLanjut;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnTampilkanMenu;
    private javax.swing.JButton btnUbahMakanan;
    private javax.swing.JComboBox<String> cbPilihan;
    private javax.swing.JComboBox<String> cbStatusMakanan;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JPanel panelLogin;
    private javax.swing.JPanel panelPesanan;
    private javax.swing.JTable tbPesanan;
    private javax.swing.JTextField tfCari;
    private javax.swing.JTextField tfHargaMakanan;
    private javax.swing.JTextField tfNamaMakanan;
    private javax.swing.JTextField tfNamaStan;
    private javax.swing.JToggleButton toggleButtonStatus;
    // End of variables declaration//GEN-END:variables
}
