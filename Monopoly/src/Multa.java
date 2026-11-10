public class Multa extends Casilla{

    public Multa(String nombre) {
        super(nombre);
    }
    
    public void darMulta(Jugador jugador){
        jugador.setSaldo(jugador.getSaldo()-20);
    }
    @Override
    public String toString() {
        return "======= "+this.nombre+" =======";
    }
}
