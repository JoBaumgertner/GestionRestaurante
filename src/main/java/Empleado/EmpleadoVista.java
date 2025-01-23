package Empleado;

import Excepciones.ExcepcionDNIStringInvalido;
import Excepciones.ExcepcionEntradaInvalida;
import Excepciones.ExcepcionNombreInvalido;
import Plato.Colores.Colores;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class EmpleadoVista {

    public String elegirPuesto() {
        Scanner scan = new Scanner(System.in);
        int op = -1;
        String nombre = null;
        do {
            try {
                System.out.println("Ingrese una opcion para el puesto:");
                System.out.println("1. Gerente general");
                System.out.println("2. Anfitrión");
                System.out.println("3. Cajero");
                System.out.println("4. Mesero");

                op = scan.nextInt();

                switch (op) {
                    case 1:
                        nombre = "Gerente general";
                        break;
                    case 2:
                        nombre = "Anfitrión";
                        break;
                    case 3:
                        nombre = "Cajero";
                        break;
                    case 4:
                        nombre = "Mesero";
                        break;
                    default:
                        Colores.printInColor("Opción incorrecta, ingrese una opción valida", Colores.RED);

                }
            } catch (InputMismatchException e) {
                System.out.println(new ExcepcionEntradaInvalida("Entrada inválida. Debe ingresar un número.").getMessage());
                scan.nextLine();
            }
        } while (op != 1 && op != 2 && op != 3 && op != 4);
        return nombre;
    }


    public Empleado pedirUnEmpleado() throws ExcepcionNombreInvalido, ExcepcionDNIStringInvalido {

        Scanner scan = new Scanner(System.in);
        String nombre = null;
        String DNI = null;
        boolean flag = false;
        while (!flag) {
            try {
                System.out.println("Ingrese el nombre y apellido del empleado:");
                nombre = scan.nextLine();

                if (nombre.trim().isEmpty() || !nombre.matches("[a-zA-Z ]+")) {
                    throw new ExcepcionNombreInvalido("El nombre debe contener letras y no estar vacío");
                }
                System.out.println("Ingrese el DNI del empleado:");
                DNI = scan.nextLine();

                if (DNI.isEmpty() || !DNI.matches("\\d{7,8}")) {
                    throw new ExcepcionDNIStringInvalido("El DNI debe contener números y ser de 7 u 8 dígitos");
                }
                flag = true;

            } catch (ExcepcionNombreInvalido excepcionNombreInvalido) {
                System.out.println(excepcionNombreInvalido.getMessage());

            } catch (ExcepcionDNIStringInvalido excepcionDNIStringInvalido) {
                System.out.println(excepcionDNIStringInvalido.getMessage());
            }
        }
        String puesto = elegirPuesto();

        Empleado empleado = new Empleado(nombre, DNI, puesto);

        return empleado;
    }

    public Integer pedirClave() {
        Integer id = null;
        Scanner scan = new Scanner(System.in);
        boolean op = false;
        while (!op) {
            System.out.println("Ingrese el ID del empleado:");
            try {
                id = scan.nextInt();
                op = true; // para cortar el while
            } catch (InputMismatchException e) {
                Colores.printInColor("Error: el id debe ser un numero entero", Colores.RED);
                scan.next();
            }
        }

        return id;
    }

    public String pedirModificacion() {
        Scanner scan = new Scanner(System.in);
        int op = -1;
        String nombre = null;
        do {
            try {
                System.out.println("Ingrese una opcion para modificar:");
                System.out.println("1. Nombre y apellido");
                System.out.println("2. Puesto");


                op = scan.nextInt();

                switch (op) {
                    case 1:
                        nombre = "nombre";
                        break;
                    case 2:
                        nombre = "puesto";
                        break;
                    default:
                        Colores.printInColor("Opción incorrecta, ingrese una opción valida", Colores.RED);

                }
            } catch (InputMismatchException e) {
                System.out.println(new ExcepcionEntradaInvalida("Entrada inválida. Debe ingresar un número.").getMessage());
                scan.nextLine();
            }
        } while (op != 1 && op != 2);
        return nombre;
    }

    public String pedirNombreParaModificar() throws ExcepcionNombreInvalido {
        boolean flag = false;
        Scanner scan = new Scanner(System.in);
        String nombre = null;
        while (!flag) {
            System.out.println("Ingrese el nombre y apellido del empleado:");
            try {
                nombre = scan.nextLine();
                if (nombre.trim().isEmpty() || !nombre.matches("[a-zA-Z ]+")) {
                    throw new ExcepcionNombreInvalido("El nombre debe contener letras y no estar vacío");
                }
                flag = true;
            } catch (ExcepcionNombreInvalido e) {
                System.out.println(e.getMessage());

            }
        }
        return nombre;
    }

    public void mensajeErrorBusqueda() {
        Colores.printInColor("Empleado no encontrado", Colores.RED);
    }

    public void mostrarEmpleado(Empleado e) {
        System.out.println("--------------------------------------------------------");
        System.out.println("DNI: " + e.getId());
        System.out.println("Nombre y Apellido: " + e.getNombre());
        System.out.println("Id empleado: " + e.getIdEmpleado());
        System.out.println("Puesto: " + e.getPuesto());
        System.out.println("--------------------------------------------------------");
    }

    public void mostrarListaEmpleados(Map<Integer, Empleado> mapaEmpleados) {
        if (mapaEmpleados.isEmpty()) {
            Colores.printInColor("No hay empleados cargados", Colores.RED);
        } else {
            for (Map.Entry<Integer, Empleado> entry : mapaEmpleados.entrySet()) {
                Empleado empleado = entry.getValue();
                System.out.println("=====================================================");
                mostrarEmpleado(empleado);
            }
        }
    }

    public void mostrarEmpleadoVersionCorta(Empleado e) {
        System.out.println("ID: " + e.getIdEmpleado() + " - " + e.getNombre());
    }

    public void mostrarListaEmpleadoVersionCorta(Map<Integer, Empleado> mapaEmpleados) {
        if (mapaEmpleados.isEmpty()) {
            Colores.printInColor("No hay empleados cargados", Colores.RED);
        } else {
            System.out.println("------------ Empleados cargados ------------");
            for (Map.Entry<Integer, Empleado> entry : mapaEmpleados.entrySet()) {
                Empleado empleado = entry.getValue();
                mostrarEmpleadoVersionCorta(empleado);
            }
            System.out.println("\n");
        }

    }


}
