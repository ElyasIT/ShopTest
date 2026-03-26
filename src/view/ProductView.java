/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package view;

import main.Shop;
import model.Product;
import javax.swing.JOptionPane;
import model.Amount;

/**
 *
 * @author elyas
 */
public class ProductView extends javax.swing.JDialog {

    private Shop shop;
    private int option;

    public ProductView(java.awt.Frame parent, boolean modal, Shop shop, int option) {
        super(parent, modal);
        this.shop = shop;
        this.option = option;
        initComponents();

        // Para que no se encojan los cuadros de texto (Punto 186) [cite: 186]
        txtName.setColumns(15);
        txtStock.setColumns(15);
        txtPrice.setColumns(15);

        // Requisito 5.c: Añadir action event al botón OK y Cancel [cite: 187]
        btnOK.addActionListener(this::btnOKActionPerformed);
        btnCancel.addActionListener(this::btnCancelActionPerformed);

        // Lógica de visibilidad según los puntos 193 y 196 [cite: 193, 196]
        if (this.option == 3) { // Añadir Stock
            jLabel3.setVisible(false);
            txtPrice.setVisible(false);
        } else if (this.option == 9) { // Eliminar Producto
            jLabel2.setVisible(false);
            txtStock.setVisible(false);
            jLabel3.setVisible(false);
            txtPrice.setVisible(false);
        }

        this.pack(); // Ajusta al contenido visible [cite: 186]
        this.setLocationRelativeTo(parent);
    }

    private void configurarVentana() {
        switch (option) {
            case 2: // Añadir
                this.setTitle("Añadir Producto");
                break;
            case 3: // Stock [cite: 193]
                this.setTitle("Añadir Stock");
                jLabel3.setVisible(false);
                txtPrice.setVisible(false);
                break;
            case 9: // Eliminar [cite: 196]
                this.setTitle("Eliminar Producto");
                jLabel2.setVisible(false);
                txtStock.setVisible(false);
                jLabel3.setVisible(false);
                txtPrice.setVisible(false);
                break;
        }
        this.pack(); // Ajusta el tamaño final [cite: 186]
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnOK = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        txtName = new javax.swing.JTextField();
        txtStock = new javax.swing.JTextField();
        txtPrice = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Nombre producto:");

        jLabel2.setText("Stock producto:");

        jLabel3.setText("Precio producto:");

        btnOK.setText("OK");

        btnCancel.setText("Cancel");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                    .addComponent(txtStock)
                    .addComponent(txtName))
                .addContainerGap(93, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(86, 86, 86))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtStock;
    // End of variables declaration//GEN-END:variables

    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {
        String name = txtName.getText();
        model.Product p = shop.findProduct(name); // Buscamos el producto

        try {
            switch (option) {
                case 2: // Añadir producto [cite: 189]
                    if (p == null) {
                        double price = Double.parseDouble(txtPrice.getText());
                        int stock = Integer.parseInt(txtStock.getText());
                        shop.addProduct(new model.Product(name, new model.Amount(price), true, stock));
                        JOptionPane.showMessageDialog(this, "Producto añadido correctamente."); // [cite: 191]
                        this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Error: El producto ya existe.", "Error", JOptionPane.ERROR_MESSAGE); // [cite: 192]
                    }
                    break;
                case 3: // Añadir stock [cite: 193]
                    if (p != null) {
                        p.setStock(p.getStock() + Integer.parseInt(txtStock.getText())); // [cite: 194]
                        JOptionPane.showMessageDialog(this, "Stock actualizado.");
                        this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Error: El producto no existe.", "Error", JOptionPane.ERROR_MESSAGE); // [cite: 195]
                    }
                    break;
                case 9: // Eliminar producto [cite: 196]
                    if (p != null) {
                        shop.getInventory().remove(p); // [cite: 197]
                        JOptionPane.showMessageDialog(this, "Producto eliminado.");
                        this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Error: El producto no existe.", "Error", JOptionPane.ERROR_MESSAGE); // [cite: 198]
                    }
                    break;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Datos numéricos incorrectos.");
        }
    }

    // LÓGICA DEL BOTÓN CANCEL
    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose(); // Cierra la ventana y vuelve al menú
    }
}
