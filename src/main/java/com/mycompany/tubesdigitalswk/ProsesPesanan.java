/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.tubesdigitalswk;

/**
 *
 * @author putra
 */
public class ProsesPesanan extends javax.swing.JFrame {

    /**
     * Creates new form ProsesPesanan
     */
    public ProsesPesanan() {
        initComponents();
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
        TabelCheckList = new javax.swing.JTable();
        jLabel28 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 129, 138));

        TabelCheckList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nama Pemesan", "Nama Makanan", "Jumlah Pesanan", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TabelCheckList);
        if (TabelCheckList.getColumnModel().getColumnCount() > 0) {
            TabelCheckList.getColumnModel().getColumn(3).setPreferredWidth(20);
            TabelCheckList.getColumnModel().getColumn(3).setMaxWidth(20);
        }

        jLabel28.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(216, 146, 22));
        jLabel28.setText("SIAPKAN PESANAN");

        jButton1.setText("ANTARKAN PESANAN");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton1)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(52, 52, 52)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(1, 1, 1)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(172, 172, 172)
                        .addComponent(jLabel28)))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(54, Short.MAX_VALUE))
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
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ProsesPesanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProsesPesanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProsesPesanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProsesPesanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ProsesPesanan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TabelCheckList;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
