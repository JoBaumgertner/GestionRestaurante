package GestionMenu;

import Excepciones.ExcepcionClienteNoEncontrado;
import Excepciones.ExcepcionFormatoIncorrecto;
import Cliente.ClienteControlador;
import Cliente.ClienteRepositorio;
import Cliente.ClienteVista;
import Excepciones.ExcepcionDNIStringInvalido;
import Excepciones.ExcepcionNombreInvalido;
import Excepciones.ExcepcionReservaCamposVacios;
import Excepciones.ExcepcionReservaCaracterInvalido;
import Excepciones.ExcepcionReservaNoEncontrada;
import Excepciones.ExcepcionReservaValorNegativo;
import Plato.Colores.Colores;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuClientes {


     ClienteRepositorio clienteRepositorio = new ClienteRepositorio();
     ClienteVista clienteVista = new ClienteVista();
    ClienteControlador clienteControlador = new ClienteControlador(clienteVista,clienteRepositorio);
    MenuPrincipal menuPrincipal = new MenuPrincipal();


    public MenuClientes(ClienteControlador clienteControlador) {

        this.clienteControlador = clienteControlador;
    }

    public MenuClientes() {
    }

    private Integer seguirOSalir() throws ExcepcionFormatoIncorrecto{
        Scanner scanner = new Scanner(System.in);
        Integer num = null;
        try {
            System.out.println("¿Desea realizar otra acción?");
            System.out.println("1. Sí");
            System.out.println("0. No");
            num = scanner.nextInt();
            String numStr = String.valueOf(num);
            if (!clienteVista.esNumero(numStr)) {
                throw new ExcepcionFormatoIncorrecto("Entrada inválida. Solo se aceptan números. Verifique y vuelva a intentar");
            }
        } catch (InputMismatchException e) {
            throw new ExcepcionFormatoIncorrecto("Entrada inválida. Debe ingresar un número entero. Verifique y vuelva a intentar");
        }
        return num;
    }
    public void menuCliente() throws ExcepcionDNIStringInvalido, ExcepcionNombreInvalido, IOException, ExcepcionFormatoIncorrecto {
        Integer ok = -1;
        Scanner scanner = new Scanner(System.in);

        do {
            try {
                Colores.printInColor("========MENU GESTION CLIENTES========", Colores.BLUE);
                System.out.println(". Elija una opción: ");
                System.out.println("1. Agregar cliente");
                System.out.println("2. Eliminar cliente");
                System.out.println("3. Actualizar teléfono de cliente");
                System.out.println("4. Consultar teléfono de cliente");
                System.out.println("5. Ver todos los clientes");
                System.out.println("6. Volver al menú principal");
                System.out.println("7. Salir del sistema");
                Colores.printInColor("-------------------------------------", Colores.BLUE);
                ok = scanner.nextInt();

                switch (ok) {
                    case 1:
                        clienteControlador.agregarClientes();
                        break;
                    case 2:
                        clienteControlador.removeCliente();
                        break;
                    case 3:
                        clienteControlador.updateClientes();
                        break;
                    case 4:
                        clienteControlador.consultCliente();
                        break;
                    case 5:
                        clienteControlador.viewClientes();
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

            } catch (InputMismatchException e) {
                Colores.printInColor("Entrada inválida. Debe ingresar un número entero", Colores.RED);
                scanner.nextLine();
            }
        } while (ok != 6 && ok != 7);

    }

}
