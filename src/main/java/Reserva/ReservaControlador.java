package Reserva;

import Cliente.Cliente;
import Cliente.ClienteControlador;
import Cliente.ClienteRepositorio;
import Cliente.ClienteVista;
import Excepciones.ExcepcionClienteNoEncontrado;
import MesasReservadas.MesasReservadas;
import MesasReservadas.MesasReservadasRepositorio;
import Plato.Colores.Colores;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ReservaControlador {
    private ReservaRepositorio reservaRepositorio;
    private ReservaVista reservaVista;
    private ClienteVista clienteVista;
    private ClienteRepositorio clienteRepositorio;
    private MesasReservadasRepositorio mesasReservadasRepositorio;
    private ClienteControlador clienteControlador;

    public ReservaControlador(ReservaRepositorio reservaRepositorio, ReservaVista reservaVista,
                              ClienteVista clienteVista, ClienteRepositorio clienteRepositorio,
                              MesasReservadasRepositorio mesasReservadasRepositorio, ClienteControlador clienteControlador) {
        this.reservaRepositorio = reservaRepositorio;
        this.reservaVista = reservaVista;
        this.clienteVista = clienteVista;
        this.clienteRepositorio = clienteRepositorio;
        this.mesasReservadasRepositorio = mesasReservadasRepositorio;
        this.clienteControlador = clienteControlador;
    }

    public void agregarReserva() throws ExcepcionClienteNoEncontrado {
        clienteControlador.loadGestionCliente();
        reservaRepositorio.cargarReserva();
        Reserva reserva = reservaVista.pedirFecha();
        LocalDate fecha = reserva.getFecha();

        Reserva reservaExistente = reservaRepositorio.buscarReserva(fecha);
        Integer cantPersonas = reservaVista.pedirCantidadPersonas();

        Cliente cliente = null;
        do {
            clienteVista.verTodosClientesVersionCorta(clienteRepositorio.getClienteSet());

            Integer id = clienteVista.seleccId();
            try {
                cliente = clienteRepositorio.findCliente(id, clienteRepositorio.getClienteSet());
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
        } while (cliente == null);

        MesasReservadas nuevaMesaReservada = new MesasReservadas(cliente, cantPersonas);
        if (reservaExistente == null) {
            List<MesasReservadas> mesasReservadas = new ArrayList<>();
            mesasReservadas.add(nuevaMesaReservada);
            Reserva nuevaReserva = new Reserva(fecha, mesasReservadas);
            reservaRepositorio.agregar(nuevaReserva);
            Colores.printInColor("Reserva cargada con éxito", Colores.GREEN);
        } else if (mesasReservadasRepositorio.buscarIndice(reservaExistente, cliente.getIdCliente())) {
            Colores.printInColor("El cliente " + cliente.getNombre() + " ya tiene una mesa reservada en esa fecha", Colores.RED);
            return;
        } else {
            reservaExistente.getMesasReservadas().add(nuevaMesaReservada);
            Colores.printInColor("Reserva cargada con éxito", Colores.GREEN);
        }
        reservaRepositorio.guardarReserva();

    }

    public void eliminarMesaReserva() throws ExcepcionClienteNoEncontrado {
        reservaRepositorio.cargarReserva();
        LocalDate fecha = reservaVista.buscarFechaReserva();
        Reserva reserva = reservaRepositorio.buscarReserva(fecha);

        if (reserva == null) {
            Colores.printInColor("No hay reservas cargadas para la fecha " + fecha, Colores.RED);
            return;
        }

        clienteControlador.viewClientes();
        Integer id = clienteVista.seleccId();
        MesasReservadas mesasReservadasAEliminar = mesasReservadasRepositorio.buscarMesasReservadas(reserva.getMesasReservadas(), id);

        if (mesasReservadasAEliminar == null) {
            Colores.printInColor("El cliente " + clienteRepositorio.findCliente(id, clienteRepositorio.getClienteSet()).getNombre() + " no tiene reserva en la fecha " + fecha, Colores.RED);

        } else {
            reserva.getMesasReservadas().remove(mesasReservadasAEliminar);
            reservaRepositorio.guardarReserva();
            Colores.printInColor("Reserva eliminada con éxito", Colores.GREEN);

        }
    }

    public void modificarReserva() throws ExcepcionClienteNoEncontrado {
        reservaRepositorio.cargarReserva();
        LocalDate fecha = reservaVista.buscarFechaReserva();
        Reserva reserva = reservaRepositorio.buscarReserva(fecha);

        if (reserva == null) {
            Colores.printInColor("No hay reservas cargadas para la fecha " + fecha, Colores.RED);
            return;
        }

        clienteVista.verTodosClientesVersionCorta(clienteRepositorio.getClienteSet());
        Integer id = clienteVista.seleccId();
        MesasReservadas mesasReservadasAModificar = mesasReservadasRepositorio.buscarMesasReservadas(reserva.getMesasReservadas(), id);
        if (mesasReservadasAModificar == null) {
            Colores.printInColor("El cliente " + clienteRepositorio.findCliente(id, clienteRepositorio.getClienteSet()).getNombre() + " no tiene reserva en la fecha " + fecha, Colores.RED);
        } else {
            String opcion = reservaVista.preguntarQueModificar();

            if (opcion.equalsIgnoreCase("fecha")) {
                LocalDate nuevaFecha = reservaVista.solicitarNuevaFecha(reservaRepositorio.todasLasReservas());
                while (nuevaFecha.isEqual(fecha)) {
                    Colores.printInColor("La fecha ingresada es la misma. Ingrese una nueva", Colores.RED);
                    nuevaFecha = reservaVista.solicitarNuevaFecha(reservaRepositorio.todasLasReservas());
                }

                reserva.getMesasReservadas().remove(mesasReservadasAModificar);

                if (reserva.getMesasReservadas().isEmpty()) {
                    reservaRepositorio.eliminar(reserva);
                }

                Reserva nuevaReserva = reservaRepositorio.buscarReserva(nuevaFecha);
                if (nuevaReserva == null) {
                    nuevaReserva = new Reserva(nuevaFecha, new ArrayList<>());
                    reservaRepositorio.agregar(nuevaReserva);
                }

                nuevaReserva.getMesasReservadas().add(mesasReservadasAModificar);
                reservaRepositorio.guardarReserva();
                Colores.printInColor("Fecha de reserva modificada con éxito", Colores.GREEN);

            } else if (opcion.equalsIgnoreCase("cantPersonas")) {
                Integer cantPersonas = reservaVista.modificarCantPersonas();
                mesasReservadasAModificar.setCantPersonas(cantPersonas);
                reservaRepositorio.guardarReserva();
                Colores.printInColor("Cantidad de personas modificada con éxito", Colores.GREEN);
            }
        }
    }

    public void mostrarReservasConArreglo() {
        reservaRepositorio.cargarReserva();
        List<Reserva> reservas = reservaRepositorio.todasLasReservas();

        List<Reserva> reservasConMesas = new ArrayList<>();
        for (Reserva reserva : reservas) {
            if (!reserva.getMesasReservadas().isEmpty()) {
                reservasConMesas.add(reserva);
            }
        }

        Collections.sort(reservasConMesas, (reserva1, reserva2) -> reserva1.getFecha().compareTo(reserva2.getFecha()));

        reservaVista.mostrarReservaConArreglo(reservasConMesas);
    }

    public void mostrarReservaPorFecha() {
        reservaRepositorio.cargarReserva();
        LocalDate fecha = reservaVista.buscarFechaReserva();
        List<Reserva> reservasPorFecha = new ArrayList<>();

        for (Reserva reserva : reservaRepositorio.todasLasReservas()) {
            if (reserva.getFecha().equals(fecha)) {
                reservasPorFecha.add(reserva);
            }
        }

        if (reservasPorFecha.isEmpty()) {
            Colores.printInColor("No hay reservas cargadas para la fecha " + fecha, Colores.RED);
        } else {
            reservaVista.mostrarReservaConArreglo(reservasPorFecha);
        }
    }

}
