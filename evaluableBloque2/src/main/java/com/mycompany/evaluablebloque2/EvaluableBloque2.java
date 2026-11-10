/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.evaluablebloque2;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.SwingUtilities;

/**
 *
 * @author jalca
 */
public class EvaluableBloque2 {

    public static void main(String[] args) {
        FlatLightLaf.setup();
        
        //creamos una ventana en un hilo a parte
        
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}
