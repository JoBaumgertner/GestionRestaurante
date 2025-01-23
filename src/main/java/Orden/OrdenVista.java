package Orden;

import Excepciones.ExcepcionEntradaInvalida;
import Plato.Plato;

import java.util.InputMismatchException;
import java.util.Scanner;

public class OrdenVista {
    public void mostrarUnaOrden(Orden orden) {
        System.out.println("ID: " + orden.getId());
        System.out.println("Cliente: " + orden.getCliente().getNombre());
        System.out.println("Empleado: " + orden.getEmpleado().getNombre());
        System.out.println("Estado: "+ orden.getEstado());
        System.out.println("Platos:");
        mostrarPlatosDeOrden(orden);
    }

    public void mostrarPlatosDeOrden(Orden orden) {
        int i = 0;
        for (Plato p : orden.getPlatoList()) {
            System.out.println(i + " - " + p.getNombre() + ": $ " + String.format("%.2f", p.getPrecio()));
            i++;
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public Integer pedirIdOrden() throws ExcepcionEntradaInvalida {
        Scanner scan = new Scanner(System.in);
        System.out.print("Ingrese el ID de la orden buscada: ");
        try {
            return scan.nextInt();
        } catch (InputMismatchException e) {
            scan.nextLine(); // Limpiar el buffer
            throw new ExcepcionEntradaInvalida("Entrada inválida. Debe ingresar un número.");
        }
    }

    public Integer pedirIdOrdenModificar() throws ExcepcionEntradaInvalida {
        Scanner scan = new Scanner(System.in);
        System.out.print("Ingrese el ID de la orden a modificar: ");
        try {
            return scan.nextInt();
        } catch (InputMismatchException e) {
            scan.nextLine(); // Limpiar el buffer
            throw new ExcepcionEntradaInvalida("Entrada inválida. Debe ingresar un número.");
        }
    }

    public Integer pedirIdOrdenEliminar() throws ExcepcionEntradaInvalida {
        Scanner scan = new Scanner(System.in);
        System.out.print("Ingrese el ID de la orden a eliminar: ");
        try {
            return scan.nextInt();
        } catch (InputMismatchException e) {
            scan.nextLine(); // Limpiar el buffer
            throw new ExcepcionEntradaInvalida("Entrada inválida. Debe ingresar un número.");
        }
    }
}