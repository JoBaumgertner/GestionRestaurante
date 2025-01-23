package Persona;

import java.util.Objects;

public abstract class Persona {
    protected String nombre;
    protected String id;


    public Persona(String nombre, String id) {
        this.nombre = nombre;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
}
    @Override
    public String toString() {
        return "Nombre:" + nombre +
                " - Dni:" + id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Persona persona = (Persona) object;
        return Objects.equals(id, persona.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash( id);
    }
}
