package Cliente;

import Excepciones.ExcepcionCamposVacios;
import Persona.Persona;

import java.util.Objects;

public class Cliente extends Persona implements Comparable<Cliente> {

    private static int secCliente = 0;
    private Integer idCliente;
    private String telefono;


    public Cliente(String nombre, String id, String telefono) throws ExcepcionCamposVacios {
        super(nombre, id);
        this.idCliente = ++secCliente;
        this.telefono = telefono;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public static int getSecCliente() {
        return secCliente;
    }


    public Integer getIdCliente() {
        return idCliente;
    }


    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    @Override
    public String toString() {
        return super.toString()+" - Id:" + idCliente +
                " - Telefono:" + telefono ;
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Cliente)) return false;
        if (!super.equals(object)) return false;
        Cliente cliente = (Cliente) object;


        return  Objects.equals(cliente.id, cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), telefono);
    }

    @Override
    public int compareTo(Cliente o) {
        return this.getId().compareTo(o.getId());
    }
}
