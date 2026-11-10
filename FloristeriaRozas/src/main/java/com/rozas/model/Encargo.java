package com.rozas.model;

public class Encargo {
    private String cliente, telefono, fechaHora, direccion, tipo, metodoEntrega, contenidoPedido;
    private boolean pagado;
    private double precioTotal;
    private boolean seleccionadoParaPDF = false;

    public Encargo(String cliente, String telefono, String fechaHora, String direccion, String tipo, 
                String contenidoPedido, boolean pagado, String metodoEntrega, double precioTotal) {
        this.cliente = cliente;
        this.telefono = telefono;
        this.fechaHora = fechaHora;
        this.direccion = direccion;
        this.tipo = tipo;
        this.contenidoPedido = contenidoPedido;
        this.pagado = pagado;
        this.metodoEntrega = metodoEntrega;
        this.precioTotal = precioTotal;
    }

    public boolean isSeleccionadoParaPDF() { return seleccionadoParaPDF; }
    public void setSeleccionadoParaPDF(boolean s) { this.seleccionadoParaPDF = s; }

    public String toCSV() {
        return String.join("|", cliente, telefono, fechaHora, direccion, tipo, contenidoPedido, 
                        String.valueOf(pagado), metodoEntrega, String.valueOf(precioTotal));
    }

    public static Encargo fromCSV(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 9) return null;
        return new Encargo(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], 
                            Boolean.parseBoolean(parts[6]), parts[7], Double.parseDouble(parts[8]));
    }

    public String getCliente() { return cliente; }
    public void setCliente(String c) { cliente = c; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String t) { telefono = t; }
    public String getFechaHora() { return fechaHora; }
    public void setFechaHora(String f) { fechaHora = f; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String d) { direccion = d; }
    public String getContenidoPedido() { return contenidoPedido; }
    public void setContenidoPedido(String cp) { contenidoPedido = cp; }
    public double getPrecioTotal() { return precioTotal; }
    public void setPrecioTotal(double p) { precioTotal = p; }
    public boolean isPagado() { return pagado; }
    public void setPagado(boolean p) { pagado = p; }
}