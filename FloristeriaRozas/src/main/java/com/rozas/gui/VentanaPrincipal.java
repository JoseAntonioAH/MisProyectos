package com.rozas.gui;

import com.rozas.model.Encargo;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Desktop;
import java.io.*;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VentanaPrincipal extends JFrame {
    private DefaultListModel<Encargo> modelo = new DefaultListModel<>();
    private JList<Encargo> lista = new JList<>(modelo);
    private final String ARCHIVO_DATOS = "encargos.txt";

    public VentanaPrincipal() {
        setTitle("Floristeria Rozas - Gestion Integral");
        setSize(1150, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        cargarDatos();

        JPanel pnlHeader = new JPanel();
        pnlHeader.setBackground(new Color(171, 209, 255));
        JLabel titulo = new JLabel("FLORISTERIA ROZAS");
        titulo.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);
        pnlHeader.add(titulo);

        lista.setCellRenderer(new EncargoRenderer());
        lista.setFixedCellHeight(110);

        lista.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int index = lista.locationToIndex(evt.getPoint());
                if (index != -1 && evt.getX() < 40) {
                    Encargo enc = modelo.getElementAt(index);
                    enc.setSeleccionadoParaPDF(!enc.isSeleccionadoParaPDF());
                    lista.repaint();
                }
            }
        });

        JPanel pnlBotones = new JPanel(new GridLayout(6, 1, 10, 10));
        pnlBotones.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnAdd = new JButton("Nuevo Pedido");
        JButton btnEdit = new JButton("Modificar Pedido");
        JButton btnPay = new JButton("Cambiar Estado Pago");
        JButton btnMap = new JButton("Google Maps");
        JButton btnDel = new JButton("Eliminar");
        JButton btnPDF = new JButton("Generar PDF");

        btnAdd.addActionListener(e -> abrirFormulario(null));
        btnEdit.addActionListener(e -> {
            Encargo sel = lista.getSelectedValue();
            if (sel != null)
                abrirFormulario(sel);
        });
        btnPay.addActionListener(e -> {
            Encargo sel = lista.getSelectedValue();
            if (sel != null) {
                sel.setPagado(!sel.isPagado());
                lista.repaint();
                guardarDatos();
            }
        });
        btnDel.addActionListener(e -> {
            int i = lista.getSelectedIndex();
            if (i != -1) {
                modelo.remove(i);
                guardarDatos();
            }
        });
        btnMap.addActionListener(e -> {
            Encargo sel = lista.getSelectedValue();
            if (sel != null)
                abrirMaps(sel.getDireccion());
        });
        btnPDF.addActionListener(e -> generarReportePDF());

        pnlBotones.add(btnAdd);
        pnlBotones.add(btnEdit);
        pnlBotones.add(btnPay);
        pnlBotones.add(btnMap);
        pnlBotones.add(btnDel);
        pnlBotones.add(btnPDF);

        add(pnlHeader, BorderLayout.NORTH);
        add(new JScrollPane(lista), BorderLayout.CENTER);
        add(pnlBotones, BorderLayout.EAST);
    }

    private void generarReportePDF() {
    File carpeta = new File("Pedidos");
    if (!carpeta.exists()) {
        carpeta.mkdir();
    }

    String fecha = new SimpleDateFormat("dd-MM-yyyy_HH-mm").format(new Date());
    String nombreArchivo = "Pedido_" + fecha + ".pdf";
    
    File archivoPDF = new File(carpeta, nombreArchivo);

    Document doc = new Document(PageSize.A4);
    try {
        PdfWriter.getInstance(doc, new FileOutputStream(archivoPDF));
        doc.open();
        
        doc.add(new Paragraph("LISTA DE PEDIDOS"));
        doc.add(new Paragraph(" "));
        
        PdfPTable tabla = new PdfPTable(6);
        tabla.addCell("Cliente"); tabla.addCell("Telefono"); tabla.addCell("Direccion"); tabla.addCell("Dia/Hora");
        tabla.addCell("Pedido"); tabla.addCell("Pagado");

        for (int i = 0; i < modelo.size(); i++) {
            Encargo enc = modelo.get(i);
            if (enc.isSeleccionadoParaPDF()) {
                tabla.addCell(enc.getCliente());
                tabla.addCell(enc.getTelefono());
                tabla.addCell(enc.getDireccion());
                tabla.addCell(enc.getFechaHora());
                tabla.addCell(enc.getContenidoPedido());
                tabla.addCell(enc.isPagado() ? "SI" : "NO ("+enc.getPrecioTotal()+")");
            }
        }
        
        doc.add(tabla);
        doc.close();
        
        JOptionPane.showMessageDialog(this, "PDF guardado correctamente");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al generar PDF: " + ex.getMessage());
    }
}

    private void abrirFormulario(Encargo editar) {
        JPanel pnlForm = new JPanel(new GridLayout(0, 2, 10, 10));
        JTextField fNombre = new JTextField(editar != null ? editar.getCliente() : "");
        JTextField fTlf = new JTextField(editar != null ? editar.getTelefono() : "");
        JTextField fDir = new JTextField(editar != null ? editar.getDireccion() : "");
        JTextField fPrecio = new JTextField(editar != null ? String.valueOf(editar.getPrecioTotal()) : "0.00");
        JTextArea fContenido = new JTextArea(editar != null ? editar.getContenidoPedido() : "", 3, 20);

        JSpinner fFecha = new JSpinner(new SpinnerDateModel());
        fFecha.setEditor(new JSpinner.DateEditor(fFecha, "dd/MM/yyyy"));
        JSpinner fHora = new JSpinner(new SpinnerDateModel());
        fHora.setEditor(new JSpinner.DateEditor(fHora, "HH:mm"));

        pnlForm.add(new JLabel("Cliente:"));
        pnlForm.add(fNombre);
        pnlForm.add(new JLabel("Telefono:"));
        pnlForm.add(fTlf);
        pnlForm.add(new JLabel("Direccion:"));
        pnlForm.add(fDir);
        pnlForm.add(new JLabel("Fecha:"));
        pnlForm.add(fFecha);
        pnlForm.add(new JLabel("Hora:"));
        pnlForm.add(fHora);
        pnlForm.add(new JLabel("Precio Total:"));
        pnlForm.add(fPrecio);
        pnlForm.add(new JLabel("Contenido:"));
        pnlForm.add(new JScrollPane(fContenido));

        int res = JOptionPane.showConfirmDialog(this, pnlForm, "Gestionar Encargo", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String fechaHora = sdf.format(fFecha.getValue()).substring(0, 10) + " "
                    + sdf.format(fHora.getValue()).substring(11, 16);
            double precio = Double.parseDouble(fPrecio.getText());
            if (editar == null) {
                modelo.addElement(new Encargo(fNombre.getText(), fTlf.getText(), fechaHora, fDir.getText(),
                        "Personalizado", fContenido.getText(), false, "Domicilio", precio));
            } else {
                editar.setCliente(fNombre.getText());
                editar.setContenidoPedido(fContenido.getText());
                editar.setFechaHora(fechaHora);
                editar.setPrecioTotal(precio);
                lista.repaint();
            }
            guardarDatos();
        }
    }

    private void abrirMaps(String d) {
        try {
            Desktop.getDesktop()
                    .browse(new URI("https://www.google.com/maps/search/?api=1&query=" + d.replace(" ", "+")));
        } catch (Exception e) {
        }
    }

    private void guardarDatos() {
        try (PrintWriter w = new PrintWriter(new FileWriter(ARCHIVO_DATOS))) {
            for (int i = 0; i < modelo.size(); i++)
                w.println(modelo.getElementAt(i).toCSV());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarDatos() {
        File f = new File(ARCHIVO_DATOS);
        if (!f.exists())
            return;
        try (BufferedReader r = new BufferedReader(new FileReader(f))) {
            String l;
            while ((l = r.readLine()) != null) {
                Encargo e = Encargo.fromCSV(l);
                if (e != null)
                    modelo.addElement(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}