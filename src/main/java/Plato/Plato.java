package Plato;
import Plato.Variedad.Variedad;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Plato {
    private String tipo;
    private String nombre;
    private double precio;
    private List<Variedad> variedades;


    public Plato(String tipo, String nombre, List<Variedad> variedades) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.precio = 0.0;
        this.variedades = variedades;
    }
    public Plato(String tipo, String nombre, double precio) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.precio = precio;
        this.variedades = Collections.emptyList();
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getTipo() {
        return tipo;
    }

    public List<Variedad> getVariedades() {
        return variedades;
    }

    // Modifique el toString para darle un mejor formato a la impresión de un plato solo utilizando StringBuilder
    // construyendo el String dependiendo se tiene variedad o no el plato.
    @Override
    public String toString() {
        if (variedades == null || variedades.isEmpty()) {
            return String.format("%-40s $%.2f", nombre, precio);
        } else {
            return nombre; // Para el encabezado del plato con variedades
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Plato plato = (Plato) object;
        return Objects.equals(nombre, plato.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }


}
