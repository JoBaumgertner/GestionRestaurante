package GestionMenu;


import Excepciones.*;
import Cliente.ClienteControlador;
import Cliente.ClienteRepositorio;
import Cliente.ClienteVista;
import MesasReservadas.MesasReservadasRepositorio;
import Plato.Colores.Colores;
import Reserva.ReservaControlador;
import Reserva.ReservaRepositorio;
import Reserva.ReservaVista;


import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;


public class MenuReservas {
    ReservaVista reservaVista = new ReservaVista();
    ReservaRepositorio reservaRepositorio = new ReservaRepositorio();
    ClienteRepositorio clienteRepositorio = new ClienteRepositorio();
    ClienteVista clienteVista = new ClienteVista();
    MesasReservadasRepositorio  mesasReservadasRepositorio = new MesasReservadasRepositorio();
    ClienteControlador clienteControlador = new ClienteControlador(clienteVista,clienteRepositorio);
    ReservaControlador reservaControlador = new ReservaControlador(reservaRepositorio,reservaVista,clienteVista,clienteRepositorio,mesasReservadasRepositorio,clienteControlador);

    public static Scanner scanner = new Scanner(System.in);

    public void menuReservas() throws ExcepcionClienteNoEncontrado, ExcepcionDNIStringInvalido, ExcepcionNombreInvalido, IOException, ExcepcionFormatoIncorrecto {
        int op = 0;
        do {
            try {
                Colores.printInColor("==========MENU RESERVAS==========", Colores.BLUE);
                System.out.println(". Elija una opción: ");
                System.out.println("1. Agregar reserva.");
                System.out.println("2. Eliminar reserva.");
                System.out.println("3. Modificar reserva.");
                System.out.println("4. Mostrar todas reservas.");
                System.out.println("5. Mostrar reservas por fecha.");
                System.out.println("6. Volver al menu principal");
                System.out.println("7. Salir del sistema");
                Colores.printInColor("-------------------------------", Colores.BLUE);

                op = scanner.nextInt();


                switch (op) {
                    case 1:
                        reservaControlador.agregarReserva();
                        break;
                    case 2:
                        reservaControlador.eliminarMesaReserva();
                        break;
                    case 3:
                        reservaControlador.modificarReserva();
                        break;
                    case 4:
                        reservaControlador.mostrarReservasConArreglo();
                        break;
                    case 5:
                        reservaControlador.mostrarReservaPorFecha();
                        break;
                    case 6:
                        System.out.println("Volviendo al menu principal...");
                        break;
                    case 7:
                        System.out.println("Saliendo...");
                        System.exit(0);
                        break;
                    default:
                        Colores.printInColor("Opción incorrecta, ingrese una opción valida", Colores.RED);
                }
            }catch (InputMismatchException e) {
                Colores.printInColor("Entrada inválida. Debe ingresar un número entero", Colores.RED);
                scanner.nextLine();
            }catch (ExcepcionClienteNoEncontrado excepcionClienteNoEncontrado){
                System.out.println(excepcionClienteNoEncontrado.getMessage());
                scanner.nextLine();
            }
        }while (op != 6 && op != 7);
    }
}