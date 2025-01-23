package GestionMenu;

import Excepciones.ExcepcionClienteNoEncontrado;
import Excepciones.ExcepcionFormatoIncorrecto;
import Cliente.ClienteRepositorio;
import Cliente.ClienteVista;
import Empleado.EmpleadoRepositorio;
import Empleado.EmpleadoVista;
import Excepciones.ExcepcionDNIStringInvalido;
import Excepciones.ExcepcionEntradaInvalida;
import Excepciones.ExcepcionNombreInvalido;
import Excepciones.ExcepcionOrdenNoEncontrada;
import Orden.OrdenControlador;
import Orden.OrdenRepositorio;
import Orden.OrdenVista;
import Plato.Colores.Colores;
import Plato.PlatoControlador;
import Plato.PlatoRepositorio;
import Plato.PlatoVista;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuOrdenes {

    OrdenRepositorio ordenRepositorio = new OrdenRepositorio();
    OrdenVista ordenVista = new OrdenVista();
    EmpleadoRepositorio empleadoRepositorio = new EmpleadoRepositorio();
    EmpleadoVista empleadoVista = new EmpleadoVista();
    ClienteRepositorio clienteRepositorio = new ClienteRepositorio();
    ClienteVista clienteVista = new ClienteVista();
    PlatoRepositorio platoRepositorio = new PlatoRepositorio();
    PlatoVista platoVista = new PlatoVista();
    PlatoControlador platoControlador = new PlatoControlador(platoRepositorio, platoVista);

    OrdenControlador ordenControlador = new OrdenControlador(ordenRepositorio, ordenVista, empleadoRepositorio, empleadoVista, clienteRepositorio, clienteVista, platoRepositorio, platoVista, platoControlador);
    public void menuOrdenes() throws ExcepcionDNIStringInvalido, ExcepcionClienteNoEncontrado, ExcepcionOrdenNoEncontrada, ExcepcionFormatoIncorrecto, ExcepcionEntradaInvalida, ExcepcionNombreInvalido, IOException {

        Scanner scanner = new Scanner(System.in);

        int op = -1;
        do {
            try {
                Colores.printInColor("==========MENU ORDENES==========", Colores.BLUE);
                System.out.println("1. Agregar orden");
                System.out.println("2. Eliminar orden");
                System.out.println("3. Modificar orden");
                System.out.println("4. Listar todas las ordenes");
                System.out.println("5. Listar ordenes pendientes");
                System.out.println("6. Mostrar orden por ID");
                System.out.println("7. Volver al menu principal");
                System.out.println("8. Salir del sistema");
                Colores.printInColor("-------------------------------", Colores.BLUE);

                op = scanner.nextInt(); // Leer la entrada
                switch (op) {
                    case 1:
                        ordenControlador.crearOrden();
                        break;
                    case 2:
                        ordenControlador.eliminarOrden();
                        break;
                    case 3:
                        ordenControlador.modificarOrden();
                        break;
                    case 4:
                        ordenControlador.mostrarTodasLasOrdenes();
                        break;
                    case 5:
                        ordenControlador.mostrarTodasLasOrdenesPendientes();
                        break;
                    case 6:
                        try {
                            ordenControlador.mostrarOrden(ordenVista.pedirIdOrden());
                        } catch (ExcepcionEntradaInvalida e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 7:
                        System.out.println("Volviendo al menu principal...");
                        break;
                    case 8:
                        System.out.println("Saliendo...");
                        System.exit(0);
                        break;
                    default:
                        Colores.printInColor("Opción incorrecta, ingrese una opción valida", Colores.RED);
                }
            }catch (InputMismatchException e) {
                Colores.printInColor("Entrada inválida. Debe ingresar un número entero", Colores.RED);
                scanner.nextLine();
            }
        } while (op != 7 && op != 8);
    }
}