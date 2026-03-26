/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package view;

import java.text.NumberFormat;
import java.util.Locale;
import main.Shop;

/**
 *
 * @author elyas
 */
public class CashView extends javax.swing.JDialog {

    private Shop shop;

    /**
     * Constructor para inicializar la vista con los datos de la tienda.
     * @param parent Ventana principal.
     * @param modal Bloquea la ventana principal mientras esta esté abierta.
     * @param shop Instancia de la tienda para obtener el saldo.
     */
    public CashView(java.awt.Frame parent, boolean modal, Shop shop) {
    super(parent, modal);
    this.shop = shop;
    initComponents();
    
    this.setTitle("Caja");
    this.setLocationRelativeTo(parent);
    
    // CARGA DEL DINERO DESDE SHOP
    if (this.shop != null && this.shop.getCash() != null) {
        double saldo = this.shop.getCash().getValue();
        
        // Creamos un formateador de moneda para España
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "ES"));
        
        // Esto pondrá automáticamente "100,00 ?" sin errores de símbolos
        this.txtCash.setText(formatoMoneda.format(saldo)); 
    }
    
    this.txtCash.setEditable(false); 
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtCash = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Dinero en caja:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(137, 137, 137)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(160, Short.MAX_VALUE)
                .addComponent(txtCash, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCash, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(147, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField txtCash;
    // End of variables declaration//GEN-END:variables
}
