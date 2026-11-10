public class Carcel extends Casilla{

    public Carcel(String nombre) {
        super(nombre);
    }

    public void enviarCarcel(Jugador jugador){
        jugador.setCarcel(1);
    }

    @Override
    public String toString() {
        return "======= "+this.nombre+
        " =======\nTe han pillado los locales por hacer \nuna banana a una abuela (pobretica)\nDebes pasar una ronda en la cárcel\n-------------------------------------------";
    }

    
    
}
