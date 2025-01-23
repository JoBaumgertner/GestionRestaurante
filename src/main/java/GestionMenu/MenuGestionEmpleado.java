package GestionMenu;

import Excepciones.*;
import Plato.Colores.Colores;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuGestionEmpleado {
    public void menuGestionEmpleado() throws ExcepcionDNIStringInvalido, ExcepcionNombreInvalido, IOException, ExcepcionReservaCamposVacios, ExcepcionReservaNoEncontrada, ExcepcionClienteNoEncontrado, ExcepcionReservaCaracterInvalido, ExcepcionReservaValorNegativo, ExcepcionFormatoIncorrecto {

        MenuReservas menuReservas = new MenuReservas();
        MenuOrdenes menuOrdenes = new MenuOrdenes();

        Colores.printInColor("========BIENVENIDOS A ´INTERFAZ DE SABORES´========", Colores.YELLOW);
        System.out.println("Que desea hacer?");

        Scanner scanner = new Scanner(System.in);

        int op = -1;
        do {
            try{
                System.out.println("1. Reservas");
                System.out.println("2. Ordenes");
                System.out.println("3. Volver al menu Login");
                System.out.println("0. Salir");

                System.out.println("Ingrese una opción");
                op = scanner.nextInt(); // Leer la entrada como cadena
                switch (op) {
                    case 1:
                        menuReservas.menuReservas();
                        break;
                    case 2:
                        menuOrdenes.menuOrdenes();
                        break;
                    case 3:
                        break;
                    case 0:
                        System.out.println("Saliendo...");
                        System.exit(0);
                        break;
                    default:
                        Colores.printInColor("Opción incorrecta, ingrese una opción valida", Colores.RED);
                }

            } catch (InputMismatchException e) {
                Colores.printInColor("Entrada inválida. Debe ingresar un número entero", Colores.RED);
                scanner.nextLine();
            }  catch (ExcepcionEntradaInvalida e) {
                System.out.println(e.getMessage());
            } catch (ExcepcionOrdenNoEncontrada excepcionOrdenNoEncontrada) {
                System.out.println(excepcionOrdenNoEncontrada.getMessage());
            }
        } while (op!=0 && op!= 3);
    }

}
