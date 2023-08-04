/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.tubesdigitalswk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author putra
 */
public class HistoriTransaksi extends javax.swing.JFrame {
    private DefaultTableModel model = new DefaultTableModel();
    private DefaultTableModel model1 = new DefaultTableModel();
    
    private Connection con;
    private int idStan;
    
    private void LoadKolomTransaksi(){
        model.addColumn("ID Transaksi");
        model.addColumn("Tanggal");
        model.addColumn("Customer");
        model.addColumn("Jumlah Harga");
        TabelTransaksi.setModel(model);
    }
    
    private void loadKolomDetail() {
        model1.addColumn("No Stan");
        model1.addColumn("Makanan/Minuman");
        model1.addColumn("Harga");
        model1.addColumn("Jumlah beli");
        model1.addColumn("Catatan");
        TabelDetailTransaksi.setModel(model1);
    }
    
    private void loadTransaksi(int methode, String tanggal){
       if(con != null){
           String kueri = "";
           if(methode == 1){
             kueri = "SELECT t.ID_Transaksi, t.Tanggal_Pesanan, c.Nama_Customer, m.ID_Stan, t.Total_Belanja FROM transaksi t INNER JOIN pesanan p ON p.ID_Transaksi = t.ID_Transaksi INNER JOIN menu m ON m.ID_Menu = p.ID_Menu INNER JOIN customer c ON c.ID_Customer = p.ID_Customer WHERE m.ID_Stan = ? AND p.status = 3 AND t.Tanggal_Pesanan= ? GROUP BY t.ID_Transaksi;";
           }else{
               kueri = "SELECT t.ID_Transaksi, t.Tanggal_Pesanan, c.Nama_Customer, m.ID_Stan, t.Total_Belanja FROM transaksi t INNER JOIN pesanan p ON p.ID_Transaksi = t.ID_Transaksi INNER JOIN menu m ON m.ID_Menu = p.ID_Menu INNER JOIN customer c ON c.ID_Customer = p.ID_Customer WHERE m.ID_Stan = ? AND p.status = 3 GROUP BY t.ID_Transaksi;";
           } 

             try{
                PreparedStatement ps = con.prepareStatement(kueri);
                ps.setInt(1, idStan );
                if(methode == 1){
                ps.setString(2, tanggal);
                }
                ResultSet rs = ps.executeQuery();
                model.setRowCount(0);
                while(rs.next()){
                    String idTransaksi = rs.getString("ID_Transaksi");
                    String tanggalTransaksi = rs.getString("Tanggal_Pesanan");
                    String namaCustomer = rs.getString("Nama_Customer");
                    String jumlahHarga = rs.getString("Total_Belanja");
                                      
                   model.addRow(new Object []{idTransaksi, tanggalTransaksi, namaCustomer, jumlahHarga});
                }
                
                rs.close();
                ps.close();
            }catch (SQLException ex) {
                Logger.getLogger(DisplaySWK.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }else{
            JOptionPane.showMessageDialog(this, "Koneksi Ke Database Gagal");
        }
    }
    
    private void TampilDetailPesanan(String idTransaksi){
        if(con!= null){
            String kueri = "SELECT c.Nama_Customer, p.ID_Meja, m.Nama, p.Jumlah, p.Total_Harga, p.catatan , p.Jam_Pemesanan, p.status, p.ID_Pesanan FROM pesanan p INNER JOIN menu m ON p.ID_Menu = m.ID_Menu INNER JOIN customer c ON p.ID_Customer = c.ID_Customer WHERE m.ID_Stan = ? AND p.ID_Transaksi = ? ORDER BY Jam_Pemesanan ASC;";
                try {
                PreparedStatement ps = con.prepareStatement(kueri);
                ps.setInt(1, idStan);
                ps.setString(2, idTransaksi);
                ResultSet rs = ps.executeQuery();

                model1.setRowCount(0);

                while (rs.next()) {
                    String customer = rs.getString("Nama_Customer");
                    int idMeja = rs.getInt("ID_Meja");
                    String makanan = rs.getString("Nama");
                    int jumlah = rs.getInt("Jumlah");
                    int totalHarga = rs.getInt("Total_Harga");
                    Time jamPemesanan = rs.getTime("Jam_Pemesanan");
                    String catatan = rs.getString("Catatan");
                    int status = rs.getInt("status");
                    String idPesanan = rs.getString("ID_Pesanan");

                    // Tambahkan data ke tabel pesanan pada GUI
                    model1.addRow(new Object[]{customer, idMeja, makanan, jumlah, totalHarga, catatan, jamPemesanan, status});
                                     
                }
                rs.close();
                ps.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(DisplaySWK.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
     
    private void setComboBox(){
        
        for (int i = 1; i <= 31; i++) {
            String item = "" + i;
            CBTanggal.addItem(item);
        }
            
        String[] months = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        };
        
        for(int i = 0; i < 12; i++){
            CBBulan.addItem(months[i]);
        }
        
         int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        for (int i = currentYear - 10; i <= currentYear; i++) {
            String item = ""+ i;
            CBTahun.addItem(item);
        }
    
    }
    /**
     * Creates new form HistoriTransaksi
     */
    public HistoriTransaksi(int idStan) {
        initComponents();
        this.idStan = idStan;
        con = Koneksi.bukaKoneksi();
        setComboBox();
        LoadKolomTransaksi();
        loadKolomDetail();
        loadTransaksi(2,"");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelTransaksi = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        CBTahun = new javax.swing.JComboBox<>();
        CBBulan = new javax.swing.JComboBox<>();
        CBTanggal = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        TabelDetailTransaksi = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);

        jPanel1.setBackground(new java.awt.Color(10, 38, 71));

        jLabel16.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 153, 0));
        jLabel16.setText("HISTORI TRANSAKSI");

        TabelTransaksi.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(TabelTransaksi);

        jButton1.setText("Cari");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        CBTanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CBTanggalActionPerformed(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Tanggal :");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Bulan :");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Tahun :");

        jButton2.setText("Tampilkan");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        TabelDetailTransaksi.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(TabelDetailTransaksi);

        jLabel17.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("DETAIL TRANSAKSI");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(222, 222, 222)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(173, 173, 173))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(CBTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(CBBulan, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(CBTahun, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton1))))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 845, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 845, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(681, 681, 681)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(CBTahun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CBTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(CBBulan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int tanggal = Integer.parseInt(CBTanggal.getSelectedItem().toString());
        int bulan   = CBBulan.getSelectedIndex() + 1;
        int tahun   = Integer.parseInt(CBTahun.getSelectedItem().toString());
        String date = "";
        if (bulan < 10){
        date = tahun + "-0" + bulan + "-" + tanggal;       
        }else{
            date = tahun + "-" + bulan + "-" + tanggal; 
        }
        System.out.println(date);
        loadTransaksi(1,date);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void CBTanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CBTanggalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CBTanggalActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        int barisTerpilih = TabelTransaksi.getSelectedRow();
        String idTransaksi = TabelTransaksi.getValueAt(barisTerpilih, 0).toString();
        TampilDetailPesanan(idTransaksi);
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CBBulan;
    private javax.swing.JComboBox<String> CBTahun;
    private javax.swing.JComboBox<String> CBTanggal;
    private javax.swing.JTable TabelDetailTransaksi;
    private javax.swing.JTable TabelTransaksi;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
