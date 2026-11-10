public abstract class Casilla {
    protected String nombre;

    public Casilla(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Casilla [nombre=" + nombre + "]";
    }

    
    
}
