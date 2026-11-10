public class Premio extends Casilla{

    public Premio(String nombre) {
        super(nombre);
    }

    public void darPremio(Jugador jugador){
        jugador.setSaldo(jugador.getSaldo()+10);
    }
    
    @Override
    public String toString() {
        return "======= "+this.nombre+" =======";
    }
}
