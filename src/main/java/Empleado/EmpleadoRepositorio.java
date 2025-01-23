package Empleado;

import Interfaces.IABM;


import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class EmpleadoRepositorio implements IABM<Empleado> {

    TreeMap<Integer,Empleado> listaEmpleados;

    public EmpleadoRepositorio() {
        this.listaEmpleados = new TreeMap<Integer, Empleado>();
    }

    public Map<Integer,Empleado> getListaEmpleados() {
        return listaEmpleados;
    }

    @Override
    public void agregar(Empleado empleado) {
        this.listaEmpleados.put(empleado.getIdEmpleado(),empleado);
    }

    @Override
    public void eliminar(Empleado empleado) {
        this.listaEmpleados.remove(empleado.getIdEmpleado());
    }

    @Override
    public void modificar(Empleado empleado) {

    }

    public Empleado buscarEmpleado(Integer clave){
        Empleado empleado = null;
        for (Map.Entry<Integer,Empleado> entry : listaEmpleados.entrySet()){
            if(entry.getValue().getIdEmpleado() == clave){
                empleado = entry.getValue();
            }
        }
        return empleado;
    }
    public Empleado buscarEmpleadoPorDNI(String DNI){
        Empleado empleado = null;
        for (Map.Entry<Integer,Empleado> entry : listaEmpleados.entrySet()){
            if(entry.getValue().getId().equals(DNI)){
                empleado = entry.getValue();
            }
        }
        return empleado;
    }

}
