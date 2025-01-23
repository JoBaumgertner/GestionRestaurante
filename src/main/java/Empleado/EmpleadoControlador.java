package Empleado;

import Excepciones.ExcepcionDNIStringInvalido;
import Excepciones.ExcepcionNombreInvalido;
import Plato.Colores.Colores;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

public class EmpleadoControlador {

    private EmpleadoVista empleadoVista = new EmpleadoVista();
    private EmpleadoRepositorio empleadoRepositorio = new EmpleadoRepositorio();

    public EmpleadoControlador(EmpleadoVista empleadoVista, EmpleadoRepositorio empleadoRepositorio) {
        this.empleadoVista = empleadoVista;
        this.empleadoRepositorio = empleadoRepositorio;
    }

    Gson gson = new Gson();

    private static final String PATH = "src/main/resources/Empleados.json";

    public void pasarAarchivo() throws IOException {
        try (Writer writer = new FileWriter(PATH)) {
            gson.toJson(empleadoRepositorio.listaEmpleados, writer);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public void pasarAMemoria() throws FileNotFoundException {
        try (Reader reader = new FileReader(PATH)) {
            Type type = new TypeToken<TreeMap<Integer, Empleado>>() {
            }.getType();
            empleadoRepositorio.listaEmpleados = gson.fromJson(reader, type);
            if (empleadoRepositorio.listaEmpleados == null) {
                empleadoRepositorio.listaEmpleados = new TreeMap<Integer, Empleado>();
            }
        } catch (IOException io) {
            System.out.println(io.getMessage());

        }
    }

    public void agregarEmpleado() throws ExcepcionDNIStringInvalido, ExcepcionNombreInvalido, IOException {
        pasarAMemoria();
        Integer clave;
        Empleado empleado = empleadoVista.pedirUnEmpleado();
        Empleado empleadoEncontrado = empleadoRepositorio.buscarEmpleadoPorDNI(empleado.getId());
        if (empleadoEncontrado != null) {
            Colores.printInColor("Empleado ya cargado en el sistema", Colores.RED);

        } else if (!empleadoRepositorio.listaEmpleados.isEmpty()) {
            clave = empleadoRepositorio.listaEmpleados.lastKey();
            empleado.setIdEmpleado(clave + 1);
            empleadoRepositorio.agregar(empleado);
            Colores.printInColor("Empleado cargado con éxito", Colores.GREEN);

        } else {
            empleadoRepositorio.agregar(empleado);
            Colores.printInColor("Empleado cargado con éxito", Colores.GREEN);
        }
        pasarAarchivo();
    }

    public void eliminarEmpleado() throws IOException, ExcepcionDNIStringInvalido {
        pasarAMemoria();
        Integer clave = empleadoVista.pedirClave();
        Empleado empleado = empleadoRepositorio.buscarEmpleado(clave);
        if (empleado != null) {
            empleadoRepositorio.eliminar(empleado);
            Colores.printInColor("Empleado eliminado con éxito", Colores.GREEN);
        } else {
            empleadoVista.mensajeErrorBusqueda();
        }
        pasarAarchivo();
    }

    public void modificarEmpleado() throws IOException, ExcepcionDNIStringInvalido, ExcepcionNombreInvalido {
        pasarAMemoria();
        Empleado empleadoModificado;
        Integer clave = empleadoVista.pedirClave();
        Empleado empleado = empleadoRepositorio.buscarEmpleado(clave);
        if (empleado != null) {
            String modificacion = empleadoVista.pedirModificacion();
            if (modificacion.equalsIgnoreCase("nombre")) {
                String nombreNuevo = empleadoVista.pedirNombreParaModificar();
                empleadoModificado = new Empleado(nombreNuevo, empleado.getId(), empleado.getIdEmpleado(), empleado.getPuesto());
                empleadoRepositorio.agregar(empleadoModificado);
                Colores.printInColor("Empleado modificado con éxito", Colores.GREEN);
            } else if (modificacion.equalsIgnoreCase("puesto")) {
                String puestoNuevo = empleadoVista.elegirPuesto();
                empleadoModificado = new Empleado(empleado.getNombre(), empleado.getId(), empleado.getIdEmpleado(), puestoNuevo);
                empleadoRepositorio.agregar(empleadoModificado);
                Colores.printInColor("Empleado modificado con éxito", Colores.GREEN);
            }
        } else {
            empleadoVista.mensajeErrorBusqueda();
        }
        pasarAarchivo();
    }

    public void mostrarListaEmpleados() throws IOException {
        pasarAMemoria();
        Map<Integer, Empleado> lista = empleadoRepositorio.getListaEmpleados();
        empleadoVista.mostrarListaEmpleados(lista);
        pasarAarchivo();
    }

    public void mostrarUnEmpleado() throws IOException {
        pasarAMemoria();
        Integer clave = empleadoVista.pedirClave();
        Empleado empleado = empleadoRepositorio.buscarEmpleado(clave);
        if (empleado == null) {
            Colores.printInColor("Empleado no cargado en sistema", Colores.RED);
        } else {
            empleadoVista.mostrarEmpleado(empleado);
        }
        pasarAarchivo();

    }

}
