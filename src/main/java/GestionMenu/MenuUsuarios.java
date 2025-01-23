package GestionMenu;

import Excepciones.ExcepcionCamposVacios;
import Excepciones.ExcepcionEntradaInvalida;
import Excepciones.ExcepcionNombreNumerico;
import Login.LoginController;
import Login.LoginRepository;
import Login.LoginView;
import Plato.Colores.Colores;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuUsuarios {

    LoginView loginView = new LoginView();
    LoginRepository loginRepository = new LoginRepository();

    LoginController loginController = new LoginController(loginRepository,loginView);

    public void menuUsuarios(){

        Scanner scanner = new Scanner(System.in);

        int op = -1;
        do {
            try {
                Colores.printInColor("==========MENU USUARIOS==========", Colores.BLUE);
                System.out.println("1. Agregar usuario");
                System.out.println("2. Eliminar usuario");
                System.out.println("3. Listar usuario");
                System.out.println("6. Volver al menu principal");
                System.out.println("7. Salir del sistema");
                Colores.printInColor("-------------------------------", Colores.BLUE);

                op = scanner.nextInt(); // Leer la entrada
                switch (op) {
                    case 1:
                        loginController.addUser();
                        break;
                    case 2:
                        loginController.eliminarUsuario();
                        break;
                    case 3:
                        loginController.mostrarUsuarios();
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
            } catch (ExcepcionCamposVacios e) {
                throw new RuntimeException(e);
            } catch (ExcepcionNombreNumerico e) {
                throw new RuntimeException(e);
            }
        } while (op != 6 && op != 7);
    }

}

