/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.evaluablebloque2;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author jalca
 */
public class Registro {

    //atributos de la clase
    private String categoria;
    private double importe;
    private Date fecha;
    private String comentario;
    
    //constructor
    public Registro(String cat, double imp, Date fec, String com){
        this.categoria = cat;
        this.importe = imp;
        this.fecha = fec;
        this.comentario = com;
    }

    //getters y setters
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    //toString
    @Override
    public String toString() {
        String fechaTexto = new SimpleDateFormat("dd/MM/yyyy").format(this.fecha);
        return categoria+"|"+importe+"|"+fechaTexto+"|"+comentario;
    }
    
    
}
