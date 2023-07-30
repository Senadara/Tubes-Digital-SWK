/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.tubesdigitalswk;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

public class OrderFrame extends JFrame {

    private Connection con;
    private DefaultTableModel modelOrder = new DefaultTableModel();
    String idTransaksi;
  
    private void loadKolomOrder() {
        modelOrder.addColumn("Stan");
        modelOrder.addColumn("Pesanan");
        modelOrder.addColumn("Status");
        TabelStatusPesanan.setModel(modelOrder);
    }
    
    public void updatePesanan() {
        // Timer untuk polling setiap 5 detik
        Timer timer = new Timer(7000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadOrderDetail();
            }
        });
        timer.start();
    }

    private void loadOrderDetail() {
        if (con != null) {
            String kueri = "SELECT p.ID_Transaksi, p.status, p.Jumlah, p.Total_Harga, s.Nama_Stan, c.Nama_Customer, m.Nama FROM pesanan p INNER JOIN customer c ON p.ID_Customer = c.ID_Customer INNER JOIN menu m ON p.ID_Menu = m.ID_Menu INNER JOIN stan s ON m.ID_Stan = s.ID_Stan WHERE ID_Transaksi = ?";
            try {
                PreparedStatement ps = con.prepareStatement(kueri);
                ps.setString(1, idTransaksi);
                ResultSet rs = ps.executeQuery();
                modelOrder.setRowCount(0);

                while (rs.next()) {
                    String namaStan = rs.getString("Nama_Stan");
                    String namaPesanan = rs.getString("Nama");
                    int status = rs.getInt("status");
                    String stts = "";
                    switch(status){
                        case 0 -> stts = "Menunggu Konfirmasi Seller";
                        case 1 -> stts = "Pesanan Anda Sedang di Prosses";
                        case 2 -> stts = "Pesanan Anda Sudah Siap";                       
                    }
                    modelOrder.addRow(new Object[]{namaStan, namaPesanan, stts});

                    
                }
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(OrderFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void selesaiMakan(){
        if(con != null){
            boolean checkbox = btnSelesaiMakan.isSelected();
            if(checkbox){
            String kueri = "UPDATE pesanan p INNER JOIN meja m ON p.ID_Meja = m.ID_Meja SET p.status = '3', m.Status = '1' WHERE p.ID_Transaksi = ? ;";
            try{
            PreparedStatement ps = con.prepareStatement(kueri);
            ps.setString(1, idTransaksi);
            int rowsUpdated = ps.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Terimakasih Sudah Makan di DIGITAL SWK :)");
                    System.exit(0);
                }
            ps.close();
            
            } catch (SQLException ex) {
                Logger.getLogger(OrderFrame.class.getName()).log(Level.SEVERE, null, ex);
            }            
            }else{
                JOptionPane.showMessageDialog(this, "Mohon untuk Memeriksa dan Mengisi CheckBox");
            }
        }
    }
     

      public OrderFrame(String idTransaksi, String nama, String noHp, String noMeja, String harga) {
        initComponents();
        
        this.idTransaksi = idTransaksi;
        loadKolomOrder();
        con = Koneksi.bukaKoneksi();
        setLocationRelativeTo(null);
        updatePesanan(); 
        tfNamaCustomer.setText(nama);
        tfNomorCustomer.setText(noHp);
        tfNomorMejaCustomer.setText(noMeja);
        tfJumlahPembayaran.setText(harga);
       
        tfNamaCustomer. setEditable(false);
        tfNomorCustomer. setEditable(false);
        tfNomorMejaCustomer. setEditable(false);
        tfJumlahPembayaran. setEditable(false);
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelStatusPesanan = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        tfNamaCustomer = new javax.swing.JTextField();
        tfNomorCustomer = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tfNomorMejaCustomer = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tfJumlahPembayaran = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnKeluar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        btnSelesaiMakan = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();

        jPanel1.setBackground(new java.awt.Color(0, 129, 138));

        TabelStatusPesanan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Stan", "Pesanan", "Status"
            }
        ));
        TabelStatusPesanan.setSelectionBackground(new java.awt.Color(216, 146, 22));
        jScrollPane1.setViewportView(TabelStatusPesanan);

        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(216, 146, 22));
        jLabel2.setText("DETAIL PESANAN");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(10, 38, 71));
        jLabel3.setText("No Hp :");

        jLabel1.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(10, 38, 71));
        jLabel1.setText("Nama :");

        tfNamaCustomer.setEditable(false);
        tfNamaCustomer.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(216, 146, 22)));

        tfNomorCustomer.setEditable(false);
        tfNomorCustomer.setForeground(new java.awt.Color(216, 146, 22));
        tfNomorCustomer.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(216, 146, 22)));
        tfNomorCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNomorCustomerActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(10, 38, 71));
        jLabel4.setText("No Meja :");

        tfNomorMejaCustomer.setEditable(false);
        tfNomorMejaCustomer.setForeground(new java.awt.Color(216, 146, 22));
        tfNomorMejaCustomer.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(216, 146, 22)));
        tfNomorMejaCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNomorMejaCustomerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfNamaCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfNomorCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfNomorMejaCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(67, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tfNamaCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tfNomorCustomer))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tfNomorMejaCustomer))
                .addGap(23, 23, 23))
        );

        jLabel5.setFont(new java.awt.Font("Trebuchet MS", 1, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 204, 51));
        jLabel5.setText("Jumlah Pembayaran :");

        jLabel6.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("STATUS PESANAN");

        tfJumlahPembayaran.setEditable(false);
        tfJumlahPembayaran.setForeground(new java.awt.Color(216, 146, 22));
        tfJumlahPembayaran.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(216, 146, 22)));
        tfJumlahPembayaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfJumlahPembayaranActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("DATA CUSTOMER");

        btnKeluar.setBackground(new java.awt.Color(255, 102, 102));
        btnKeluar.setText("KELUAR DARI APLIKASI");
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });

        jLabel8.setText("SUDAH SELESAI MAKAN ?");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(287, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(284, 284, 284))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(297, 297, 297))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfJumlahPembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 718, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel7)))
                        .addGap(110, 110, 110)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSelesaiMakan))
                            .addComponent(btnKeluar)))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tfJumlahPembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSelesaiMakan, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnKeluar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfNomorCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNomorCustomerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNomorCustomerActionPerformed

    private void tfNomorMejaCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNomorMejaCustomerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNomorMejaCustomerActionPerformed

    private void tfJumlahPembayaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfJumlahPembayaranActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfJumlahPembayaranActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        // TODO add your handling code here:
        selesaiMakan();
        
    }//GEN-LAST:event_btnKeluarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TabelStatusPesanan;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JCheckBox btnSelesaiMakan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField tfJumlahPembayaran;
    private javax.swing.JTextField tfNamaCustomer;
    private javax.swing.JTextField tfNomorCustomer;
    private javax.swing.JTextField tfNomorMejaCustomer;
    // End of variables declaration//GEN-END:variables
}
