package com.mycompany.evaluablebloque2;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.miginfocom.swing.MigLayout;

public class VentanaPrincipal extends JFrame {

    // variables que se utilizan
    private boolean oscuro = false;
    Datos listaDatos = new Datos();

    // actions

    // el action guardar abre un seleector de archivo en modo "save", crea el
    // archivo y escribe los toString de todos los objetos del arraylist
    private Action actionGuardar = new AbstractAction("Guardar") {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser selector = new JFileChooser();
            selector.setFileFilter(new FileNameExtensionFilter("Ficheros de texto (txt)", "txt"));

            if (selector.showSaveDialog(VentanaPrincipal.this) == JFileChooser.APPROVE_OPTION) {
                try {
                    File archivo = selector.getSelectedFile();

                    if (!archivo.getName().toLowerCase().endsWith(".txt")) {
                        archivo = new File(archivo.getAbsolutePath() + ".txt");
                    }

                    Files.writeString(archivo.toPath(), listaDatos.getLista());

                    JOptionPane.showMessageDialog(VentanaPrincipal.this, "Archivo guardado correctamente");

                } catch (IOException ioe) {
                    JOptionPane.showMessageDialog(VentanaPrincipal.this, "No se ha podido guardar el fichero", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    };

    // el action Abrir abre un selector de archivo en modo "show", lee el archivo,
    // lo separa por lineas y crea un objeto con cada linea. Cada objeto se añade al
    // arraylist
    private Action actionAbrir = new AbstractAction("Abrir") {
        @Override
        public void actionPerformed(ActionEvent e) {
            listaDatos.limpiarLista();
            JFileChooser selector = new JFileChooser();
            selector.setFileFilter(new FileNameExtensionFilter("Ficheros de texto (txt)", "txt"));

            if (selector.showOpenDialog(VentanaPrincipal.this) == JFileChooser.APPROVE_OPTION) {
                try {
                    String archivo = Files.readString(selector.getSelectedFile().toPath());
                    String[] lineas = archivo.split("\n");
                    for (int i = 0; i < lineas.length; i++) {

                        String[] atributos = lineas[i].split("\\|");
                        String cat = atributos[0];
                        double imp = Double.parseDouble(atributos[1]);
                        Date fecha = new SimpleDateFormat("dd/MM/yyyy").parse(atributos[2]);
                        String com = atributos[3];
                        listaDatos.anadir(cat, imp, fecha, com);
                    }

                    actualizarGrafico();
                    actualizarHistorial();
                    labelRegistros.setText(listaDatos.comprobarTamano() + " registros");
                    labelDinero.setText(listaDatos.getTotal() + " euros");

                } catch (IOException ioe) {
                    JOptionPane.showMessageDialog(VentanaPrincipal.this, "No se ha podido abrir el fichero", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } catch (ParseException pe) {
                    JOptionPane.showMessageDialog(VentanaPrincipal.this, "Error en la lectura de la fecha", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        }
    };

    // el action nuevo borra el arraylist, el historial, el grafico y el formulario
    private Action actionNuevo = new AbstractAction("Nuevo") {
        @Override
        public void actionPerformed(ActionEvent e) {
            listaDatos.limpiarLista();
            actualizarHistorial();
            actualizarGrafico();
            borrarFormulario();
            labelRegistros.setText(listaDatos.comprobarTamano() + " registros");
            labelDinero.setText(listaDatos.getTotal() + " euros");
        }
    };

    // el action cambiar tema cambia entre el modo claro y oscuro
    private Action actionCambiarTema = new AbstractAction("Cambiar tema") {
        @Override
        public void actionPerformed(ActionEvent e) {
            cambiarTema();
        }
    };

    private Action actionInforme = new AbstractAction("Generar informe") {
    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(VentanaPrincipal.this, "En proceso...");
    }
};

    // creacion de los elementos de la ventana

    // menuBar y sus elementos
    private JMenuBar menuBar = new JMenuBar();

    private JMenu archivoMenu = new JMenu("Archivo");
    private JMenuItem nuevoItem = new JMenuItem(actionNuevo);
    private JMenuItem abrirItem = new JMenuItem(actionAbrir);
    private JMenuItem guardarItem = new JMenuItem(actionGuardar);
    private JMenuItem informeItem = new JMenuItem(actionInforme);
    private JMenuItem salirItem = new JMenuItem("Salir");

    private JMenu verMenu = new JMenu("Ver");
    private JMenuItem cambiarTemaItem = new JMenuItem(actionCambiarTema);

    // toolbar y sus elementos (north)
    private JToolBar toolBar = new JToolBar();
    private JButton nuevoToolButton = new JButton(actionNuevo);
    private JButton abrirToolButton = new JButton(actionAbrir);
    private JButton guardarToolButton = new JButton(actionGuardar);
    private JButton informeToolButton = new JButton(actionInforme);

    // panel principal de la ventana. Se usa un borderlayout
    JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));

    // panel del formulario (west). Usa un miglayout
    JPanel panelFormulario = new JPanel(new MigLayout("insets 10, wrap 2, gap 8", "[left][grow, fill]",
            "[grow]push[grow]push[grow]push[grow]push[grow]"));

    JLabel labelCategoria = new JLabel("Categoria");
    JComboBox<String> comboCategoria = new JComboBox<>(
            new String[] { "Alimentacion", "Ocio", "Transporte", "Vivienda", "Salud" });

    JLabel labelImporte = new JLabel("Importe");
    JTextField textFieldImporte = new JTextField();

    JLabel labelFecha = new JLabel("Fecha");
    JDateChooser dateFecha = new JDateChooser();

    JLabel labelComentario = new JLabel("Comentario");
    JTextField textFieldComentario = new JTextField();

    JButton buttonGuardar = new JButton("Guardar Datos");

    // panel central (center). usa un border layout
    JPanel panelCentral = new JPanel(new BorderLayout());

    // el panel de la leyenda del grafico (north), usa un miglayout
    JPanel panelLeyenda = new JPanel(new MigLayout("insets 10, wrap 5, gap 8",
            "[grow, fill, center][grow, fill, center][grow, fill, center][grow, fill, center][grow, fill, center]",
            ""));
    JLabel labelAlimentacion = new JLabel("Alimentacion");
    JLabel labelOcio = new JLabel("Ocio");
    JLabel labelTransporte = new JLabel("Transporte");
    JLabel labelVivienda = new JLabel("Vivienda");
    JLabel labelSalud = new JLabel("Salud");

    // panel del grafico (center). Se le pasa el mismo arraylist sobre el que se
    // hacen los cambios
    PanelGrafico panelGrafico = new PanelGrafico(listaDatos);

    // panel histrorial (east). Tiene un miglayout
    JPanel panelHistorial = new JPanel(new MigLayout("insets 4 12 4 8, wrap 2, width 150", "[][]", "[][grow, fill]"));
    JLabel labelHistorial = new JLabel("Historial");
    JTextArea textAreaHistorial = new JTextArea();
    JScrollPane scrollHistorial = new JScrollPane(textAreaHistorial);

    // panel inferior (south). Usa un miglayout
    JPanel panelInferior = new JPanel(new MigLayout("insets 4 12 4 8", "[left][]", ""));
    JLabel labelRegistros = new JLabel("0 registros");
    JLabel labelDinero = new JLabel("0 euros");

    // contructor de la ventana principal
    public VentanaPrincipal() {

        // titulo de la ventana
        super("Control de gastos");

        // cuando se cierre la ventana se para el programa
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // propiedades de los elementos de la ventana

        // se añaden los atajos de teclado a los elementos del menu
        nuevoItem.setAccelerator(KeyStroke.getKeyStroke("control N"));
        abrirItem.setAccelerator(KeyStroke.getKeyStroke("control A"));
        guardarItem.setAccelerator(KeyStroke.getKeyStroke("control G"));
        salirItem.setAccelerator(KeyStroke.getKeyStroke("control S"));
        cambiarTemaItem.setAccelerator(KeyStroke.getKeyStroke("control T"));
        informeItem.setAccelerator(KeyStroke.getKeyStroke("control I"));


        // el metodo dispose() es para cerrar el programa. No hay que crearlo, solo
        // llamarlo
        salirItem.addActionListener(e -> dispose());

        // propiedades del historial
        textAreaHistorial.setLineWrap(true);
        textAreaHistorial.setWrapStyleWord(true);
        textAreaHistorial.setEditable(false);

        // propiedades del selector de fecha
        dateFecha.setDateFormatString("dd/MM/yyyy");

        // propiedades de los label de la leyenda del grafico
        labelAlimentacion.setBackground(Colores.rojo);
        labelAlimentacion.setForeground(Colores.blanco);
        labelAlimentacion.setOpaque(true);
        labelAlimentacion.setHorizontalAlignment(JLabel.CENTER);

        labelOcio.setBackground(Colores.azul);
        labelOcio.setForeground(Colores.blanco);
        labelOcio.setOpaque(true);
        labelOcio.setHorizontalAlignment(JLabel.CENTER);

        labelTransporte.setBackground(Colores.rosa);
        labelTransporte.setForeground(Colores.blanco);
        labelTransporte.setOpaque(true);
        labelTransporte.setHorizontalAlignment(JLabel.CENTER);

        labelVivienda.setBackground(Colores.verde);
        labelVivienda.setForeground(Colores.blanco);
        labelVivienda.setOpaque(true);
        labelVivienda.setHorizontalAlignment(JLabel.CENTER);

        labelSalud.setBackground(Colores.naranja);
        labelSalud.setForeground(Colores.blanco);
        labelSalud.setOpaque(true);
        labelSalud.setHorizontalAlignment(JLabel.CENTER);

        // se añaden los elementos del menu a su apartado
        archivoMenu.add(nuevoItem);
        archivoMenu.add(abrirItem);
        archivoMenu.add(guardarItem);
        archivoMenu.add(informeItem);
        archivoMenu.addSeparator();
        archivoMenu.add(salirItem);

        verMenu.add(cambiarTemaItem);

        // se añaden los apartados del menu al menu
        menuBar.add(archivoMenu);
        menuBar.add(verMenu);

        // se establece el menubar de la ventana
        setJMenuBar(menuBar);

        // se añaden los elementos de la barra de tareas
        toolBar.add(nuevoToolButton);
        toolBar.add(abrirToolButton);
        toolBar.add(guardarToolButton);
        toolBar.add(informeToolButton);

        // se añade la barra de tareas en la posicion norte
        panelPrincipal.add(toolBar, BorderLayout.NORTH);

        // propiedades del formulario y añadimos elementos
        panelFormulario.setBorder(BorderFactory.createLineBorder(Colores.grisOscuro));
        panelFormulario.setBackground(Colores.blanco);
        panelFormulario.add(labelCategoria);
        panelFormulario.add(comboCategoria);

        panelFormulario.add(labelImporte);
        panelFormulario.add(textFieldImporte);

        panelFormulario.add(labelFecha);
        panelFormulario.add(dateFecha);

        panelFormulario.add(labelComentario);
        panelFormulario.add(textFieldComentario);

        panelFormulario.add(buttonGuardar, "span 2, center");

        // se coloca el formulario en la zona oeste
        panelPrincipal.add(panelFormulario, BorderLayout.WEST);

        // propiedades del panel central y se añaden sus elementos
        // panel de la leyenda
        panelLeyenda.setBorder(BorderFactory.createLineBorder(Colores.grisOscuro));
        panelLeyenda.setBackground(Colores.blanco);
        panelLeyenda.add(labelAlimentacion);
        panelLeyenda.add(labelOcio);
        panelLeyenda.add(labelTransporte);
        panelLeyenda.add(labelVivienda);
        panelLeyenda.add(labelSalud);

        // panel del grafico
        panelGrafico.setBorder(BorderFactory.createLineBorder(Colores.grisOscuro));
        panelGrafico.setBackground(Colores.blanco);

        // se añaden los paneles al panel central
        panelCentral.add(panelLeyenda, BorderLayout.NORTH);
        panelCentral.add(panelGrafico, BorderLayout.CENTER);

        // se añade el panel central a la ventana
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);

        // propiedades del panel historial
        panelHistorial.setBorder(BorderFactory.createLineBorder(Colores.grisOscuro));
        panelHistorial.setBackground(Colores.blanco);

        // se añaden sus elementos
        panelHistorial.add(labelHistorial, "span 2, center");
        panelHistorial.add(scrollHistorial, "span 2, growx, pushx");
        // se añade el panel al este de la ventana
        panelPrincipal.add(panelHistorial, BorderLayout.EAST);

        // propiedades del panel inferior
        panelInferior.setBorder(BorderFactory.createLineBorder(Colores.grisOscuro));
        panelInferior.setBackground(Colores.blanco);

        // se añaden sus elementos
        panelInferior.add(labelRegistros);
        panelInferior.add(labelDinero);

        // se añade el panel al sur de la ventana
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        // se añade el panel principal a la ventana
        add(panelPrincipal);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        // se añaden los eventos de los botones
        buttonGuardar.addActionListener(e -> guardarRegistro());

        // se establecen los valores del estado del archivo. Si no hay atos, es 0
        labelRegistros.setText(listaDatos.comprobarTamano() + " registros");
        labelDinero.setText(listaDatos.getTotal() + " euros");

    }


    //metodo para guardar los regitros del formulario
    private void guardarRegistro() {

        //guardo los valores del formulario en variables
        String categoria = comboCategoria.getSelectedItem().toString();
        double importe = Double.parseDouble(textFieldImporte.getText());
        Date fecha = dateFecha.getDate();
        String comentario = textFieldComentario.getText();

        //añado un nuevo objeto al arraylist
        listaDatos.anadir(categoria, importe, fecha, comentario);

        //actualizo el historial, el grafico y pongo el formulario en blanco
        actualizarHistorial();
        actualizarGrafico();
        borrarFormulario();

        //se actualizan los label de estado
        labelRegistros.setText(listaDatos.comprobarTamano() + " registros");
        labelDinero.setText(listaDatos.getTotal() + " euros");


        //mensaje de que ha ido todo bien
        JOptionPane.showMessageDialog(this, "Registro guardado correctamente");
    }

    //metodo para dejar el formulario en blanco
    public void borrarFormulario() {
        comboCategoria.setSelectedIndex(0);
        textFieldImporte.setText("");
        dateFecha.setDate(null);
        textFieldComentario.setText("");
    }

    //metodo para actualizar el historial

    //se va por los elementos del arraylist, se pasa la fecha a un string y se guarda toda la linea en un string
    private void actualizarHistorial() {
        String registros = "";
        Date fecha;
        String fechaTexto;
        for (int i = 0; i < listaDatos.comprobarTamano(); i++) {
            fecha = listaDatos.getRegistro(i).getFecha();
            fechaTexto = new SimpleDateFormat("dd/MM/yyyy").format(fecha);
            registros = registros + "\n" + fechaTexto + "\n" + listaDatos.getRegistro(i).getCategoria() + " \n "
                    + listaDatos.getRegistro(i).getImporte() + "\n"+listaDatos.getRegistro(i).getComentario()+"\n---------------";

        }

        //se pone el string guardado en el textArea del historial
        textAreaHistorial.setText(registros);

    }

    //metodo para actualizar el grafico cuando hay un cambio
    private void actualizarGrafico() {
        panelCentral.repaint();
    }

    //metodo para cambiar entre el modo claro y oscuro
    //como el flatlaf no cambiaba los paneles, hay que hacer el cambio de color aparte
    private void cambiarTema() {
        if (oscuro) {
            FlatDarkLaf.setup();
            panelFormulario.setBackground(Colores.negro);
            panelInferior.setBackground(Colores.negro);
            panelHistorial.setBackground(Colores.negro);
            panelLeyenda.setBackground(Colores.negro);
            panelGrafico.setBackground(Colores.grisOscuro);
        } else {
            FlatLightLaf.setup();
            panelFormulario.setBackground(Colores.blanco);
            panelInferior.setBackground(Colores.blanco);
            panelHistorial.setBackground(Colores.blanco);
            panelLeyenda.setBackground(Colores.blanco);
            panelGrafico.setBackground(Colores.blanco);
        }

        oscuro = !oscuro;

        FlatLaf.updateUI();
    }

}