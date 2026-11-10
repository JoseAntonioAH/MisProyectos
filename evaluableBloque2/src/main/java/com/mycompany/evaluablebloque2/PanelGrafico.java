package com.mycompany.evaluablebloque2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JPanel;

/**
 *
 * @author jalca
 */
public class PanelGrafico extends JPanel {

    // variable con el arraylist que se le pasa al constructor
    // es el mismo sobre el que hace las acciones la ventanaPrincipal
    Datos listaDatos;

    public PanelGrafico(Datos listaDatos) {
        this.listaDatos = listaDatos;
    }

    @Override
    // se inicializa el "pintor" de java
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // limpia el fondo
        Graphics2D g2 = (Graphics2D) g; // versión moderna con más opciones

        // creo una matriz para guardar los importes de cada dia y categoria
        // filas: 7 días (0=lunes, 1=martes, 2=miercoles, 3=jueves, 4=viernes, 5=sabado,
        // 6=domingo)
        // columnas: 5 categorias (0=alimentacion, 1=ocio, 2=transporte, 3=vivienda,
        // 4=salud)
        int[][] importes = new int[7][5];

        // meto los importes de cada dia en la matriz. Si en un dia hay varios importes,
        // se suman

        // bucle que recorre el arraylist
        for (int i = 0; i < listaDatos.comprobarTamano(); i++) {

            // obtengo la fecha del registro
            Date fecha = listaDatos.getRegistro(i).getFecha();

            // obtengo el dia de la semana de la fecha
            String fechaDia = new SimpleDateFormat("EEEE").format(fecha);

            // obtengo la categoria del registro
            String categoria = listaDatos.getRegistro(i).getCategoria();

            // obtengo el importe del registro
            int importe = (int) listaDatos.getRegistro(i).getImporte();

            // segun el dia y la categoria, obtengo su fila / columna de la matriz
            int numDia;
            if (fechaDia.equalsIgnoreCase("lunes")) {
                numDia = 0;
            } else if (fechaDia.equalsIgnoreCase("martes")) {
                numDia = 1;
            } else if (fechaDia.equalsIgnoreCase("miércoles") || fechaDia.equalsIgnoreCase("miercoles")) {
                numDia = 2;
            } else if (fechaDia.equalsIgnoreCase("jueves")) {
                numDia = 3;
            } else if (fechaDia.equalsIgnoreCase("viernes")) {
                numDia = 4;
            } else if (fechaDia.equalsIgnoreCase("sábado") || fechaDia.equalsIgnoreCase("sabado")) {
                numDia = 5;
            } else if (fechaDia.equalsIgnoreCase("domingo")) {
                numDia = 6;
            } else {
                numDia = -1;
            }

            int numCat;
            if (categoria.equals("Alimentacion")) {
                numCat = 0;
            } else if (categoria.equals("Ocio")) {
                numCat = 1;
            } else if (categoria.equals("Transporte")) {
                numCat = 2;
            } else if (categoria.equals("Vivienda")) {
                numCat = 3;
            } else if (categoria.equals("Salud")) {
                numCat = 4;
            } else {
                numCat = -1;
            }

            // guardo el importe en su posicion de la matriz. Si ya hay uno, se suman
            importes[numDia][numCat] += importe;
        }

        // posicion de cada dia de la semana en x

        //los numeros raros son porcentajes de la ventana, para que al hacerla mas pequeña, se modifique el grafico y los elementos
        double[] posX = { (0.2396 * getWidth()), (0.3570 * getWidth()), (0.4743 * getWidth()), (0.5917 * getWidth()),
                (0.7090 * getWidth()), (0.8264 * getWidth()), (0.9438 * getWidth()) };

        // color de linea de cada categoria
        Color[] coloresCategorias = { Colores.rojo, Colores.azul, Colores.rosa, Colores.verde, Colores.naranja };

        // la anchura base es 818
        // la altura base es 648

        

        // dibujamos las lineas

        g2.setStroke(new BasicStroke(4));

        // bucle que se repite 5 veces, una por categoria
        for (int i = 0; i < 5; i++) {

            // establezco el color de la categoria que toque
            g2.setColor(coloresCategorias[i]);

            // linea del lunes, empieza en 0,0
            int y = 500 - (8 * importes[0][i]);
            g2.drawLine((int) (0.1222 * getWidth()), 500, (int) posX[0], y);

            // bucle que se repite para cada dia de la semana (0-6)
            for (int j = 0; j < 6; j++) {

                // la formula 500-(8*x) me la ha dado gemini, es para obtener el pixel del ejeY que corresponde a cada valor del grafico
                // para que el valor del grafico salga en el 10(importe 10€), debe ponerse en el pixel 420.

                // obtengo el importe del dia en el que estoy y el siguiente, para saber donde
                // empieza y acaba la linea
                int y1 = 500 - (8 * importes[j][i]);
                int y2 = 500 - (8 * importes[j + 1][i]);

                // dibujo la linea, desde la posicion del dia en el que estoy y su importe,
                // hasta la posicion del siguiente dia y su importe/posicionY
                g2.drawLine((int) posX[j], y1, (int) posX[j + 1], y2);
            }
        }

        // Añado los textos de los ejes
        g2.setColor(Colores.negro);
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        g2.drawString("L", (int) (0.9490 * posX[0]), 530);
        g2.drawString("M", (int) (0.9658 * posX[1]), 530);
        g2.drawString("X", (int) (0.9742 * posX[2]), 530);
        g2.drawString("J", (int) (0.9793 * posX[3]), 530);
        g2.drawString("V", (int) (0.9828 * posX[4]), 530);
        g2.drawString("S", (int) (0.9852 * posX[5]), 530);
        g2.drawString("D", (int) (0.9870 * posX[6]), 530);

        g2.drawString(" 5", (int) (0.0611 * getWidth()), 470);
        g2.drawString("10", (int) (0.0611 * getWidth()), 430);
        g2.drawString("15", (int) (0.0611 * getWidth()), 390);
        g2.drawString("20", (int) (0.0611 * getWidth()), 350);
        g2.drawString("25", (int) (0.0611 * getWidth()), 310);
        g2.drawString("30", (int) (0.0611 * getWidth()), 270);
        g2.drawString("35", (int) (0.0611 * getWidth()), 230);
        g2.drawString("40", (int) (0.0611 * getWidth()), 190);
        g2.drawString("45", (int) (0.0611 * getWidth()), 150);
        g2.drawString("50", (int) (0.0611 * getWidth()), 110);

        // dibujo las lineas del grafico
        // lo ultimo para que se vean las lineas negras del grafico por encima de los colores
        g2.setColor(Colores.negro);
        g2.drawLine((int) (0.1222 * getWidth()), 500, (int) (0.9438 * getWidth()), 500);
        g2.drawLine((int) (0.1222 * getWidth()), 100, (int) (0.1222 * getWidth()), 500);
    }

}