public class Jugador {
    private String figura;
    private double saldo;
    private int casillaX;
    private int casillaY;
    private int carcel;
    private boolean comenzado;

    public Jugador(String figura) {
        this.figura = figura;
        this.saldo = 100;
        this.casillaX = 0;
        this.casillaY = 0;
        this.carcel = 0;
        this.comenzado=false;
    }
    public String getFigura() {
        return figura;
    }
    public void setFigura(String figura) {
        this.figura = figura;
    }
    public double getSaldo() {
        return this.saldo;
    }
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    public int getCasillaX() {
        return this.casillaX;
    }
    public boolean isComenzado() {
        return comenzado;
    }
    public void setComenzado(boolean comenzado) {
        this.comenzado = comenzado;
    }
    public void setCasillaX(int casillaX) {
        this.casillaX = casillaX;
    }
    public int getCasillaY() {
        return this.casillaY;
    }
    public void setCasillaY(int casillaY) {
        this.casillaY = casillaY;
    }
    
    

    @Override
    public String toString() {
        return "Jugador [figura=" + figura + ", saldo=" + saldo +  ", casillaX=" + casillaX + ", casillaY=" + casillaY + "]";
    }
    public int getCarcel() {
        return carcel;
    }
    public void setCarcel(int carcel) {
        this.carcel = carcel;
    }

    
}
