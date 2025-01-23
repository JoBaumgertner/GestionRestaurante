package Orden;

import Cliente.Cliente;
import Cliente.ClienteControlador;
import Cliente.ClienteRepositorio;
import Cliente.ClienteVista;
import Empleado.Empleado;
import Empleado.EmpleadoControlador;
import Empleado.EmpleadoRepositorio;
import Empleado.EmpleadoVista;
import Excepciones.*;
import Plato.Colores.Colores;
import Plato.Plato;
import Plato.PlatoControlador;
import Plato.PlatoRepositorio;
import Plato.PlatoVista;

import java.io.FileNotFoundException;
import java.util.*;


public class OrdenControlador {
    private OrdenRepositorio ordenRepositorio;
    private OrdenVista ordenVista;
    private EmpleadoRepositorio empleadoRepositorio;
    private EmpleadoVista empleadoVista;
    private ClienteRepositorio clienteRepositorio;
    private ClienteVista clienteVista;
    private PlatoRepositorio platoRepositorio;
    private PlatoVista platoVista;
    private PlatoControlador platoControlador;

    public OrdenControlador(OrdenRepositorio ordenRepositorio, OrdenVista ordenVista,
                            EmpleadoRepositorio empleadoRepositorio, EmpleadoVista empleadoVista,
                            ClienteRepositorio clienteRepositorio, ClienteVista clienteVista,
                            PlatoRepositorio platoRepositorio, PlatoVista platoVista, PlatoControlador platoControlador) {
        this.ordenRepositorio = ordenRepositorio;
        this.ordenVista = ordenVista;
        this.empleadoRepositorio = empleadoRepositorio;
        this.empleadoVista = empleadoVista;
        this.clienteRepositorio = clienteRepositorio;
        this.clienteVista = clienteVista;
        this.platoRepositorio = platoRepositorio;
        this.platoVista = platoVista;
        this.platoControlador = platoControlador;
    }


    public void crearOrden() throws ExcepcionClienteNoEncontrado, ExcepcionDNIStringInvalido, ExcepcionFormatoIncorrecto, FileNotFoundException {

        ordenRepositorio.cargarOrdenesDesdeArchivo();
        Integer idOrden;
        if (ordenRepositorio.obtenerMap().isEmpty() || ordenRepositorio.obtenerMap() == null) {
            idOrden = 0;
        } else {
            idOrden = ordenRepositorio.obtenerMap().lastKey();
        }

        ClienteControlador clienteControlador = new ClienteControlador(clienteVista, clienteRepositorio);
        clienteControlador.loadGestionCliente();
        Set<Cliente> setClientes = clienteRepositorio.getClienteSet();
        Integer clienteId;
        Cliente c = null;
        do {
            clienteVista.verTodosClientesVersionCorta(setClientes);
            clienteId = clienteVista.seleccId();
            try {
                c = clienteRepositorio.findCliente(clienteId, setClientes);
            } catch (ExcepcionClienteNoEncontrado excepcionClienteNoEncontrado) {
                System.out.println(excepcionClienteNoEncontrado.getMessage());

                System.out.println("Quiere agregar el cliente? s/n");
                Scanner scan = new Scanner(System.in);
                String agregar = scan.nextLine();
                if (agregar.equalsIgnoreCase("s")) {
                    clienteControlador.agregarClientes();
                    clienteControlador.Update();
                }
            }
        } while (c == null);


        EmpleadoControlador empleadoControlador = new EmpleadoControlador(empleadoVista, empleadoRepositorio);
        empleadoControlador.pasarAMemoria();
        Integer claveEmpleado;
        Empleado e;
        do {
            empleadoVista.mostrarListaEmpleadoVersionCorta(empleadoRepositorio.getListaEmpleados());
            claveEmpleado = empleadoVista.pedirClave();
            e = empleadoRepositorio.buscarEmpleado(claveEmpleado);
            if (e == null) {
                empleadoVista.mensajeErrorBusqueda();
            }
        } while (e == null);


        List<Plato> platos = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        String op;
        do {
            Plato p = platoControlador.seleccionPlatoParaOrden(platoRepositorio, platoVista);
            if (p != null) {
                platos.add(p);
            } else {
                Colores.printInColor("Plato no agregado", Colores.RED);
            }
            System.out.println("Ingrese 's' si quiere agregar otro plato: ");
            op = scan.nextLine();
        } while (op.equals("s"));

        if (!platos.isEmpty()) {
            Orden orden = new Orden(c, e);
            orden.setId(idOrden + 1);
            orden.setPlatoList(platos);

            ordenRepositorio.guardarOrden(orden);
            ordenRepositorio.guardarOrdenesEnArchivo();
            Colores.printInColor("Orden creada con éxito", Colores.GREEN);
        } else {
            Colores.printInColor("Orden cancelada, no se cargó ningún plato", Colores.RED);
        }

    }

    public void modificarOrden() throws ExcepcionEntradaInvalida {

        ordenRepositorio.cargarOrdenesDesdeArchivo();
        Scanner scan = new Scanner(System.in);

        try {
            mostrarTodasLasOrdenesPendientes();
            Integer idOrden = ordenVista.pedirIdOrdenModificar();
            Orden orden = ordenRepositorio.obtenerOrden(idOrden);
            if (orden.getEstado().equalsIgnoreCase("Pendiente")) {
                mostrarOrden(orden.getId());
                List<Plato> platos = new ArrayList<>(orden.getPlatoList());
                int opcion;

                do {
                    opcion = 0;
                    try {
                        System.out.println("Seleccione una opción: ");
                        System.out.println("1. Agregar un plato");
                        System.out.println("2. Quitar un plato");
                        System.out.println("3. Reemplazar un plato");
                        System.out.println("4. Marcar orden como Completada");
                        System.out.println("5. Finalizar modificaciones");
                        System.out.print("Opción: \n");
                        opcion = scan.nextInt();
                        scan.nextLine();  // capturar el salto de linea desp del nextInt
                        switch (opcion) {
                            case 1:
                                // Agregar un plato
                                Plato nuevoPlato = platoControlador.seleccionPlatoParaOrden(platoRepositorio, platoVista); // Método que selecciona y retorna un plato
                                if (nuevoPlato != null) {
                                    platos.add(nuevoPlato);
                                } else {
                                    Colores.printInColor("Plato no encontrado", Colores.RED);
                                }
                                break;
                            case 2:
                                // Quitar un plato
                                System.out.print("Ingrese el índice del plato a quitar (empezando desde 0): \n");
                                ordenVista.mostrarPlatosDeOrden(orden);
                                int indiceQuitar = scan.nextInt();
                                scan.nextLine();  // capturar el salto de linea desp del nextInt
                                if (indiceQuitar >= 0 && indiceQuitar < platos.size()) {
                                    platos.remove(indiceQuitar);
                                } else {
                                    Colores.printInColor("Indice inválido", Colores.RED);

                                }
                                break;
                            case 3:
                                // Reemplazar un plato
                                System.out.print("Ingrese el índice del plato a reemplazar (empezando desde 0): \n");
                                ordenVista.mostrarPlatosDeOrden(orden);
                                int indiceReemplazar = scan.nextInt();
                                scan.nextLine();  // capturar el salto de linea desp del nextInt
                                if (indiceReemplazar >= 0 && indiceReemplazar < platos.size()) {
                                    Plato platoReemplazo = platoControlador.seleccionPlatoParaOrden(platoRepositorio, platoVista);
                                    if (platoReemplazo != null) {
                                        platos.set(indiceReemplazar, platoReemplazo);
                                    } else {
                                        Colores.printInColor("Plato no encontrado", Colores.RED);
                                    }
                                } else {
                                    Colores.printInColor("Indice inválido", Colores.RED);
                                }
                                break;
                            case 4:
                                orden.setEstado("Completada");
                                Colores.printInColor("Orden marcada como Completada con éxito", Colores.GREEN);
                                break;
                            case 5:
                                // Finalizar modificaciones
                                opcion = -1;
                                break;
                            default:
                                Colores.printInColor("Opción incorrecta, ingrese una opción valida", Colores.RED);
                        }
                    } catch (InputMismatchException e) {
                        System.out.println(new ExcepcionEntradaInvalida("Entrada inválida. Debe ingresar un número.").getMessage());
                        scan.nextLine();
                    }
                } while (opcion != -1);
                // Guardar los cambios
                orden.setPlatoList(platos);
                ordenRepositorio.guardarOrden(orden);
                ordenRepositorio.guardarOrdenesEnArchivo();
                Colores.printInColor("Orden modificada con éxito", Colores.GREEN);
            } else {
                Colores.printInColor("La orden ingresada ya esta completada, no se puede modificar", Colores.RED);
            }
        } catch (ExcepcionOrdenNoEncontrada excepcionOrdenNoEncontrada) {
            System.out.println(excepcionOrdenNoEncontrada.getMessage());
        } catch (ExcepcionEntradaInvalida excepcionEntradaInvalida) {
            System.out.println(excepcionEntradaInvalida.getMessage());
        }
    }

    public void eliminarOrden() {
        try {
            ordenRepositorio.cargarOrdenesDesdeArchivo();

            Scanner scan = new Scanner(System.in);
            mostrarTodasLasOrdenesPendientes();
            Integer idOrdenEliminar = ordenVista.pedirIdOrdenEliminar();  // Limpiar el buffer

            Orden orden = ordenRepositorio.obtenerOrden(idOrdenEliminar);
            if (orden == null) {
                Colores.printInColor("Orden no encontrada", Colores.RED);
                return;
            }
            if (orden.getEstado().equalsIgnoreCase("Pendiente")) {
                // Confirmar la eliminación
                System.out.println("¿Está seguro de que desea eliminar la siguiente orden?");
                mostrarOrden(orden.getId());
                System.out.print("Ingrese 's' para confirmar, cualquier otra tecla para cancelar: ");
                String op = scan.nextLine();

                if (op.equalsIgnoreCase("s")) {
                    ordenRepositorio.eliminarOrden(idOrdenEliminar);
                    Colores.printInColor("Orden eliminada con éxito", Colores.GREEN);
                    ordenRepositorio.guardarOrdenesEnArchivo();
                } else {
                    Colores.printInColor("Eliminación cancelada", Colores.RED);
                }
            } else {
                Colores.printInColor("La orden ingresada ya esta completada, no se puede eliminar", Colores.RED);
            }
        } catch (ExcepcionOrdenNoEncontrada e) {
            System.out.println(e.getMessage());
        } catch (ExcepcionEntradaInvalida excepcionEntradaInvalida) {
            System.out.println(excepcionEntradaInvalida.getMessage());
        }
    }

    public void mostrarTodasLasOrdenes() {
        try {
            ordenRepositorio.cargarOrdenesDesdeArchivo();

            if (ordenRepositorio.obtenerMap().isEmpty()) {
                Colores.printInColor("No hay órdenes cargadas", Colores.RED);
                return;
            }

            for (Orden orden : ordenRepositorio.obtenerMap().values()) {
                mostrarOrden(orden.getId());
            }

        } catch (Exception e) {
            Colores.printInColor("Ocurrió un error al cargar las órdenes.", Colores.RED);
            e.printStackTrace();
        }
    }

    public void mostrarTodasLasOrdenesPendientes() {
        try {
            ordenRepositorio.cargarOrdenesDesdeArchivo();

            if (ordenRepositorio.obtenerMap().isEmpty()) {
                Colores.printInColor("No hay órdenes cargadas", Colores.RED);
                return;
            }

            for (Orden orden : ordenRepositorio.obtenerMap().values()) {
                if (orden.getEstado().equalsIgnoreCase("Pendiente")) {
                    mostrarOrden(orden.getId());
                }
            }

        } catch (Exception e) {
            Colores.printInColor("Ocurrió un error al cargar las órdenes.", Colores.RED);
            e.printStackTrace();
        }
    }

    public void mostrarTodasLasOrdenesCompletadas() {
        try {
            ordenRepositorio.cargarOrdenesDesdeArchivo();

            if (ordenRepositorio.obtenerMap().isEmpty()) {
                Colores.printInColor("No hay órdenes cargadas", Colores.RED);
                return;
            }

            for (Orden orden : ordenRepositorio.obtenerMap().values()) {
                if (orden.getEstado().equalsIgnoreCase("Completada")) {
                    mostrarOrden(orden.getId());
                }
            }

        } catch (Exception e) {
            Colores.printInColor("Ocurrió un error al cargar las órdenes.", Colores.RED);
            e.printStackTrace();
        }
    }

    public void mostrarOrden(Integer id) throws ExcepcionOrdenNoEncontrada {
        ordenRepositorio.cargarOrdenesDesdeArchivo();
        try {
            Orden orden = ordenRepositorio.obtenerOrden(id);
            ordenVista.mostrarUnaOrden(orden);
            System.out.println("------------------");
            System.out.println("Total  $ " + String.format("%.2f", ordenRepositorio.calcularTotalOrden(orden.getId())));
            System.out.println("------------------");
            System.out.println("------------------------------------------------");
        } catch (ExcepcionOrdenNoEncontrada e) {
            System.out.println(e.getMessage());
        }
    }


}
