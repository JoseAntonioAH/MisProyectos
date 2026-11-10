import java.util.Scanner;

public class Vivienda extends Casilla {
    private double precio;
    private boolean disponibilidad;
    private Jugador propietario;
    private int eleccion = 0;

    public Vivienda(String nombre, double precio) {
        super(nombre);
        this.precio = precio;
        this.disponibilidad = true;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public Jugador getPropietario() {
        return propietario;
    }

    public void setPropietario(Jugador propietario) {
        this.propietario = propietario;
    }

    // METODO COMPRAR VIVIENDA
    // si esta disponible, se efetua la compra, si no lo esta se hace el pago al
    // propietario
    public void comprarVivienda(Jugador jugador) {
        Scanner sc = new Scanner(System.in);
        if (disponibilidad) {

            System.out.println("\n[1] Comprar vivienda\n[2] Salir sin comprar");

            while (this.eleccion != 2) {
            try {
                this.eleccion = Integer.parseInt(sc.nextLine());

                    if (this.eleccion == 1) {

                        if (jugador.getSaldo() - this.precio >= 0) {
                            jugador.setSaldo(jugador.getSaldo() - this.precio);
                            this.disponibilidad = false;
                            this.propietario = jugador;
                            System.out.println("<< Has comprado la vivienda >>");
                            System.out.println("Te quedan "+jugador.getSaldo()+" ME");
                            break;
                        } else {
                            System.out.println("No tienes dinero suficiente para comprar esta vivienda");
                            break;
                        }

                    } else if (this.eleccion == 2) {
                        System.out.println("Gracias por su visita");
                    } else {
                        System.out.println("Elemento del menú no permitido");
                    }
                } catch (NumberFormatException nfe) {
                    System.out.println("Error: El valor introducido no es un número");
                } catch (Exception e){
                    System.out.println("Error desconocido");
                }
            }
        } else {
            // si el jugador actual no es el propietario, se efectua el pago al propietario
            if (jugador != this.propietario) {
                System.out.println("No se puede comprar esta vivienda.\nPertenece a " + this.propietario.getFigura()
                        + "\nLe pagas " + (this.precio * 0.5) + " ME");
                jugador.setSaldo(jugador.getSaldo() - (this.precio * 0.5));
                this.propietario.setSaldo(this.propietario.getSaldo() + (this.precio * 0.5));
            } else {
                System.out.println("Esta vivienda es de tu propiedad");
            }
        }
    }

    @Override
    public String toString() {
        if (this.disponibilidad) {
            return "-------------------------------------------\n======= " + this.nombre + " =======\n| Precio: "
                    + this.precio + " ME\n| Está disponible\n-------------------------------------------";
        } else {
            return "-------------------------------------------\n======= " + this.nombre + " =======\n| Precio: "
                    + this.precio + "\n| No está disponible\n| Propietario: " + this.propietario.getFigura()
                    + "\n-------------------------------------------";
        }

    }

}
