/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.tubesdigitalswk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author putra
 */
public class ProsesPesanan extends javax.swing.JFrame {
    DefaultTableModel model;
    Connection con;
    
    
    private void loadTabel(ArrayList<StatusCooking> cooking){
        model = (DefaultTableModel) TabelCheckList.getModel();
        for (StatusCooking sc : cooking) {
            System.out.println(sc.getID());
        model.addRow(new Object[]{sc.getID(), sc.getNamaCustomer(), sc.getNamaMakanan(), sc.getJumlah(), sc.getCatatan(), sc.getStatus()});
        }
    }
    
    private void updateData(int opsi) {
        if(con != null){
        
        try {
            String updateQuery = "UPDATE pesanan SET status = ? WHERE ID_Pesanan = ?";
            PreparedStatement ps = con.prepareStatement(updateQuery);
            if(opsi != 2){
                for (int i = 0; i < model.getRowCount(); i++) {
                long id = Long.parseLong(model.getValueAt(i, 0).toString());
                ps.setInt(1, opsi);
                ps.setLong(2, id);

                ps.addBatch();
            }
            ps.executeBatch();
            ps.close();
            }else{
            for (int i = 0; i < model.getRowCount(); i++) {
                boolean status = (boolean) model.getValueAt(i, 5);
                long id = Long.parseLong(model.getValueAt(i, 0).toString());
                int checkbox = 1;
                if (status){
                    checkbox = 2;
                }
                ps.setInt(1, checkbox);
                ps.setLong(2, id);

                ps.addBatch();
            }
            ps.executeBatch();
            ps.close();
            }
        
            JOptionPane.showMessageDialog(this, "Terimakasih Data Berhasil Terupdate");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
        }
    }

    /**
     * Creates new form ProsesPesanan
     */
    public ProsesPesanan(ArrayList<StatusCooking> cooking) {
        initComponents();
        loadTabel(cooking);
        con = Koneksi.bukaKoneksi();
        updateData(1);
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

        jPanel1.setBackground(new java.awt.Color(0, 129, 138));

        TabelCheckList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Pesanan", "Nama Pemesan", "Nama Makanan", "Jumlah Pesanan", "Catatan", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
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
            TabelCheckList.getColumnModel().getColumn(5).setPreferredWidth(20);
            TabelCheckList.getColumnModel().getColumn(5).setMaxWidth(20);
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
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 642, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(271, 271, 271)
                        .addComponent(jLabel28)))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel28)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(54, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
        updateData(2);
    }//GEN-LAST:event_jButton1ActionPerformed

    
    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TabelCheckList;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
