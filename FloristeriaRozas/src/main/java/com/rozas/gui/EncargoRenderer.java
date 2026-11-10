package com.rozas.gui;

import com.rozas.model.Encargo;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;

public class EncargoRenderer extends JPanel implements ListCellRenderer<Encargo> {
    private JCheckBox chkSeleccionar = new JCheckBox();
    private JLabel lblNombre = new JLabel();
    private JLabel lblDatosContacto = new JLabel();
    private JLabel lblInfo = new JLabel();
    private JLabel lblPrecio = new JLabel();
    
    private final Border borderSeleccionado = BorderFactory.createLineBorder(new Color(135, 206, 235), 3);
    private final Border borderNormal = BorderFactory.createEmptyBorder(5, 10, 5, 10);

    public EncargoRenderer() {
        setLayout(new BorderLayout(15, 0));
        add(chkSeleccionar, BorderLayout.WEST);
        
        // Estilos
        lblNombre.setFont(new Font("Arial", Font.BOLD, 15));
        lblDatosContacto.setFont(new Font("Arial", Font.ITALIC, 12));
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 12));
        lblPrecio.setFont(new Font("Arial", Font.BOLD, 18));
        lblPrecio.setForeground(new Color(0, 0, 0));
        
        JPanel pnlC = new JPanel(new GridLayout(3, 1));
        pnlC.setOpaque(false);
        pnlC.add(lblNombre);
        pnlC.add(lblDatosContacto);
        pnlC.add(lblInfo);
        
        add(pnlC, BorderLayout.CENTER);
        add(lblPrecio, BorderLayout.EAST);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Encargo> list, Encargo e, int index, boolean isSelected, boolean cellHasFocus) {
        chkSeleccionar.setSelected(e.isSeleccionadoParaPDF());
        lblNombre.setText(e.getCliente() + "  |  " + e.getFechaHora());
        lblDatosContacto.setText("Telefono: " + e.getTelefono() + "  -  Direccion: " + e.getDireccion());
        lblInfo.setText("Pedido: " + e.getContenidoPedido() + "  [" + (e.isPagado() ? "PAGADO" : "PENDIENTE") + "]");
        lblPrecio.setText(String.format("%.2f" + "\u20AC", e.getPrecioTotal()));
        
        setBackground(e.isPagado() ? new Color(171, 209, 255) : new Color(255, 255, 255));
        chkSeleccionar.setBackground(getBackground());
        setBorder(isSelected ? borderSeleccionado : borderNormal);
        setOpaque(true);
        return this;
    }
}