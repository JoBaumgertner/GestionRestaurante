package Reserva;

import Excepciones.ExcepcionEntradaInvalida;
import Excepciones.ExcepcionReservaCamposVacios;
import Excepciones.ExcepcionReservaCaracterInvalido;
import Excepciones.ExcepcionReservaValorNegativo;
import MesasReservadas.MesasReservadas;
import Plato.Colores.Colores;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ReservaVista {
    private static final int totalMesas = 30;
    private static Scanner scanner = new Scanner(System.in);
    private static LocalDate hoy = LocalDate.now();

    public Reserva pedirFecha() {
        LocalDate fecha = null;
        while (true) {
            try {
                System.out.println("Ingrese fecha de la reserva (formato dd/MM/yyyy): ");
                String input = scanner.nextLine().trim();
                validarFecha(input);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                fecha = LocalDate.parse(input, formatter);
                validarFechaHoy(fecha);
                break;
            } catch (DateTimeParseException e) {
                String mensaje = traducirMensajeFechaInvalida(e.getMessage());
                System.out.println(mensaje);
            } catch (ExcepcionReservaCamposVacios | ExcepcionReservaCaracterInvalido e) {
                System.out.println(e.getMessage());
            }
        }
        return new Reserva(fecha);
    }


    private String traducirMensajeFechaInvalida(String mensajeOriginal) {
        if (mensajeOriginal.contains("could not be parsed")) {
            if (mensajeOriginal.contains("Invalid value for DayOfMonth")) {
                return "Error al analizar la fecha: valor de día del mes inválido (valores válidos 1 - 28/31).";
            } else if (mensajeOriginal.contains("Invalid value for MonthOfYear")) {
                return "Error al analizar la fecha: valor de mes del año inválido (valores válidos 1 - 12).";
            } else if (mensajeOriginal.contains("Invalid value for Year")) {
                return "Error al analizar la fecha: valor de año inválido.";
            } else {
                return "Error al analizar la fecha: formato inválido.";
            }
        }
        return "Error al analizar la fecha.";
    }

    public Integer pedirCantidadPersonas() {
        Integer cantPersonas = null;
        while (true) {
            try {
                System.out.println("Ingrese Cantidad de personas: ");
                cantPersonas = scanner.nextInt();
                scanner.nextLine();
                validarCantPersonas(cantPersonas);
                break;
            } catch (NumberFormatException | ExcepcionReservaCamposVacios | ExcepcionReservaCaracterInvalido |
                     ExcepcionReservaValorNegativo e) {
                System.out.println(e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println(new ExcepcionEntradaInvalida("Entrada inválida. Debe ingresar un número.").getMessage());
                scanner.nextLine();
            }
        }
        return cantPersonas;
    }

    public Integer modificarCantPersonas() {
        Integer cantPersonas = null;
        while (true) {
            try {
                System.out.println("Ingrese cantidad de personas: ");
                cantPersonas = scanner.nextInt();
                scanner.nextLine();
                validarCantPersonas(cantPersonas);
                break;
            } catch (NumberFormatException | ExcepcionReservaCamposVacios | ExcepcionReservaCaracterInvalido |
                     ExcepcionReservaValorNegativo e) {
                System.out.println(e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println(new ExcepcionEntradaInvalida("Entrada inválida. Debe ingresar un número.").getMessage());
                scanner.nextLine();
            }
        }
        return cantPersonas;
    }

    public String preguntarQueModificar() {
        String opcion = null;
        while (true) {
            try {
                System.out.println("Que desea modificar: ");
                System.out.println("1. Fecha");
                System.out.println("2. Cantidad de Personas ");
                Integer seleccion = scanner.nextInt();
                scanner.nextLine();

                if (seleccion == null) {
                    throw new ExcepcionReservaCamposVacios("El campo no puede estar vacío.");
                }

                if (seleccion == 1) {
                    opcion = "fecha";
                } else if (seleccion == 2) {
                    opcion = "cantPersonas";
                } else {
                    Colores.printInColor("Opción incorrecta, ingrese una opción valida", Colores.RED);
                    continue;
                }
                break;
            } catch (NumberFormatException | ExcepcionReservaCamposVacios e) {
                Colores.printInColor("Error: la entrada no es un número válido. Intente nuevamente", Colores.RED);
            } catch (InputMismatchException e) {
                Colores.printInColor("Entrada inválida. Debe ingresar un número entero", Colores.RED);
                scanner.nextLine();
            }
        }
        return opcion;
    }

    public LocalDate buscarFechaReserva() {
        LocalDate fecha = null;
        while (true) {
            try {
                System.out.println("Ingrese fecha de la reserva (formato dd/MM/yyyy): ");
                String input = scanner.nextLine().trim();
                validarFecha(input);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                fecha = LocalDate.parse(input, formatter);
                validarFechaHoy(fecha);
                break;
            } catch (DateTimeParseException | ExcepcionReservaCamposVacios | ExcepcionReservaCaracterInvalido e) {
                System.out.println(e.getMessage());
            }
        }
        return fecha;
    }

    public LocalDate solicitarNuevaFecha(List<Reserva> reservas) throws DateTimeParseException {
        LocalDate fecha = null;
        while (true) {
            try {
                mostrarDisponibilidad(reservas);

                System.out.println("\nIngrese fecha de la nueva reserva (formato dd/MM/yyyy): ");
                String input = scanner.nextLine().trim();
                validarFecha(input);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                fecha = LocalDate.parse(input, formatter);
                validarFechaHoy(fecha);
                validarFechaDentroDe15Dias(fecha);

                if (fechaReservada(fecha, reservas)) {
                    System.out.println("La fecha ingresada ya tiene una reserva completa.");
                    continue;
                }

                break;
            } catch (DateTimeParseException | ExcepcionReservaCamposVacios | ExcepcionReservaCaracterInvalido e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        return fecha;
    }

    public void mostrarDisponibilidad(List<Reserva> reservas) {
        LocalDate fechaInicio = hoy;
        LocalDate fechaFin = hoy.plusDays(15);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (LocalDate fechas = fechaInicio; !fechas.isAfter(fechaFin); fechas = fechas.plusDays(1)) {
            int mesasDisponibles = totalMesas;
            for (Reserva reserva : reservas) {
                if (reserva.getFecha().equals(fechas)) {
                    mesasDisponibles -= reserva.getMesasReservadas().size();
                }
            }
            System.out.println(fechas.format(formatter) + " - Disponibilidad " + mesasDisponibles + " mesas");
        }
    }

    public boolean fechaReservada(LocalDate fecha, List<Reserva> reservas) {
        for (Reserva reserva : reservas) {
            if (reserva.getFecha().equals(fecha)) {
                if (reserva.getMesasReservadas().size() >= totalMesas) {
                    return true;
                }
            }
        }
        return false;
    }


    public void mostrarReservaConArreglo(List<Reserva> reservas) {
        for (Reserva reserva : reservas) {
            //System.out.println("Fecha: " + reserva.getFecha());
            System.out.println("------------Mesas Reservadas - "+ "Fecha: " + reserva.getFecha() +"------------");
            for (MesasReservadas mesas : reserva.getMesasReservadas()) {
                System.out.println("Cliente: " + mesas.getCliente().getNombre());
                System.out.println("Cantidad de Personas: " + mesas.getCantPersonas());
                System.out.println("\n");
            }
        }
    }

    public void mensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public static boolean esNumero(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void validarFecha(String input) throws ExcepcionReservaCamposVacios {
        if (input == null || input.isEmpty()) {
            throw new ExcepcionReservaCamposVacios("El campo no puede estar vacío.");
        }
    }

    public void validarFechaHoy(LocalDate fecha) throws ExcepcionReservaCaracterInvalido {
        if (fecha.isBefore(hoy)) {
            throw new ExcepcionReservaCaracterInvalido("La fecha ingresada no puede ser anterior a la fecha de hoy.");
        }
    }

    public void validarFechaDentroDe15Dias(LocalDate fecha) throws ExcepcionReservaCaracterInvalido {
        if (fecha.isAfter(hoy.plusDays(15))) {
            throw new ExcepcionReservaCaracterInvalido("La fecha ingresada debe estar dentro de los próximos 15 días.");
        }
    }

    public void validarCantPersonas(Integer cantPersonas) throws ExcepcionReservaCamposVacios, ExcepcionReservaCaracterInvalido, ExcepcionReservaValorNegativo {
        if (cantPersonas == null) {
            throw new ExcepcionReservaCamposVacios("El campo no puede estar vacío.");
        }
        if (!esNumero(String.valueOf(cantPersonas)) || cantPersonas >= 20) {
            throw new ExcepcionReservaCaracterInvalido("La entrada no es un número válido.");
        }
        if (cantPersonas <= 0) {
            throw new ExcepcionReservaValorNegativo("El valor no puede ser negativo.");
        }
    }
}
