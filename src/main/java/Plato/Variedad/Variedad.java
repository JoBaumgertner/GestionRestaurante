package Plato.Variedad;

public class Variedad {
    private String nombre;
    private double precio;

    public Variedad(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return String.format("  - %-32s $%.2f", nombre, precio);
    }
}
