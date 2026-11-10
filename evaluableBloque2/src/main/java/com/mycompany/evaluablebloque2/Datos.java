/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.evaluablebloque2;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author jalca
 */

//clase del almacen de registros en un arraylist
public class Datos {
    private ArrayList<Registro> lista = new ArrayList<>();
    double total = 0;
    
    //metodo para añadir objetos al arraylist
    public void anadir(String cat, double imp, Date fec, String com){
        lista.add(new Registro(cat, imp, fec, com));
    }
    
    //metodo para ver cuantos elementos hay en el arraylist
    public int comprobarTamano(){
        return this.lista.size();
    }
    
    //metodo para ver cual es el total de los importes del arraylist
    public double getTotal(){
        total=0;
        for (int i = 0; i < lista.size(); i++) {
            total+=lista.get(i).getImporte();
            
        }
        
        return total;
    }

    //metodo para obtener la lista completa del arraylist
    public String getLista(){
        String listaTexto = "";
        for (int i = 0; i < lista.size(); i++) {
            listaTexto = listaTexto + lista.get(i).toString() + "\n";
        }
        return listaTexto;
    }

    //metodo para dejar el arraylist vacío
    public void limpiarLista(){
        this.lista.clear();
    }

    //metodo para obtener uno de los elementos del arraylist
    public Registro getRegistro(int i){
        return lista.get(i);
    }
}
