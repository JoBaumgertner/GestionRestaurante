package GestionMenu;

import Empleado.EmpleadoControlador;
import Empleado.EmpleadoRepositorio;
import Empleado.EmpleadoVista;
import Excepciones.ExcepcionDNIStringInvalido;
import Excepciones.ExcepcionEntradaInvalida;
import Excepciones.ExcepcionNombreInvalido;
import Plato.Colores.Colores;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuEmpleados {

    EmpleadoRepositorio empleadoRepositorio = new EmpleadoRepositorio();
    EmpleadoVista empleadoVista = new EmpleadoVista();
    MenuPrincipal menuPrincipal = new MenuPrincipal();


    EmpleadoControlador empleadoControlador = new EmpleadoControlador(empleadoVista, empleadoRepositorio);

    public void menuEmpleados() throws ExcepcionDNIStringInvalido, ExcepcionNombreInvalido, IOException {

        Scanner scanner = new Scanner(System.in);

        int op = -1;
        do {
            try {
                Colores.printInColor("========MENU GESTION EMPLEADOS========", Colores.BLUE);
                System.out.println("1. Agregar empleados");
                System.out.println("2. Eliminar empleados");
                System.out.println("3. Modificar empleados");
                System.out.println("4. Mostrar empleados");
                System.out.println("5. Mostrar un empleado");
                System.out.println("6. Volver al menu principal");
                System.out.println("7. Salir del sistema");
                Colores.printInColor("--------------------------------------", Colores.BLUE);

                op = scanner.nextInt(); // Leer la entrada como cadena
                switch (op) {
                    case 1:
                        empleadoControlador.agregarEmpleado();
                        break;
                    case 2:
                        empleadoControlador.eliminarEmpleado();
                        break;
                    case 3:
                        empleadoControlador.modificarEmpleado();
                        break;
                    case 4:
                        empleadoControlador.mostrarListaEmpleados();
                        break;
                    case 5:
                        empleadoControlador.mostrarUnEmpleado();
                        break;
                    case 6:
                        System.out.println("Volviendo al menu principal...");
                        break;
                    case 7:
                        System.out.println("Saliendo...");
                        System.exit(0);
                    default:
                        Colores.printInColor("Opción incorrecta, ingrese una opción valida", Colores.RED);
                }
            } catch (InputMismatchException e) {
                Colores.printInColor("Entrada inválida. Debe ingresar un número entero", Colores.RED);
                scanner.nextLine();
            }
        }
            while (op != 6 && op!=7) ;
        }
    }
