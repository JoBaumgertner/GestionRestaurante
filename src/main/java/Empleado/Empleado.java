package Empleado;

import Persona.Persona;

public class Empleado extends Persona {
    private static int secEmpleado = 0;
    private Integer idEmpleado;
    private String puesto;

    public Empleado(String nombre, String id, String puesto) {
        super(nombre, id);
        this.idEmpleado = ++secEmpleado;
        this.puesto = puesto;
    }

    public Empleado(String nombre, String id, Integer idEmpleado, String puesto) {
        super(nombre, id);
        this.idEmpleado = idEmpleado;
        this.puesto = puesto;
    }

    public static int getSecEmpleado() {
        return secEmpleado;
    }

    public static void setSecEmpleado(int secEmpleado) {
        Empleado.secEmpleado = secEmpleado;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }
}
