import Cliente.ClienteControlador;
import Cliente.ClienteRepositorio;
import Cliente.ClienteVista;
import Empleado.EmpleadoControlador;
import Empleado.EmpleadoRepositorio;
import Empleado.EmpleadoVista;
import Excepciones.*;
import GestionMenu.MenuLogin;
import GestionMenu.MenuPrincipal;
import Login.LoginController;
import Login.LoginRepository;
import Login.LoginView;
import MesasReservadas.MesasReservadasRepositorio;
import Orden.OrdenControlador;
import Orden.OrdenRepositorio;
import Orden.OrdenVista;
import Plato.Plato;
import Plato.PlatoControlador;
import Plato.PlatoRepositorio;
import Plato.PlatoVista;
import Reserva.ReservaControlador;
import Reserva.ReservaRepositorio;
import Reserva.ReservaVista;

import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException, ExcepcionDNIStringInvalido, ExcepcionNombreInvalido, ExcepcionClienteNoEncontrado, ExcepcionFormatoIncorrecto, ExcepcionCamposVacios, ExcepcionReservaCamposVacios, ExcepcionReservaNoEncontrada, ExcepcionReservaCaracterInvalido, ExcepcionNombreNumerico, ExcepcionReservaValorNegativo {
        ClienteVista clienteVista = new ClienteVista();
        ClienteRepositorio clienteRepositorio = new ClienteRepositorio();
        ClienteControlador clienteControlador = new ClienteControlador(clienteVista, clienteRepositorio);

        EmpleadoVista empleadoVista = new EmpleadoVista();
        EmpleadoRepositorio empleadoRepositorio = new EmpleadoRepositorio();
        EmpleadoControlador empleadoControlador = new EmpleadoControlador(empleadoVista, empleadoRepositorio);

        PlatoVista platoVista = new PlatoVista();
        PlatoRepositorio platoRepositorio = new PlatoRepositorio();
        PlatoControlador platoControlador = new PlatoControlador(platoRepositorio, platoVista);

        OrdenVista ordenVista = new OrdenVista();
        OrdenRepositorio ordenRepositorio = new OrdenRepositorio();
        OrdenControlador ordenControlador = new OrdenControlador(ordenRepositorio, ordenVista, empleadoRepositorio, empleadoVista, clienteRepositorio, clienteVista, platoRepositorio, platoVista,platoControlador);

        ReservaRepositorio reservaRepositorio = new ReservaRepositorio();
        ReservaVista reservaVista = new ReservaVista();
        MesasReservadasRepositorio mesasReservadasRepositorio = new MesasReservadasRepositorio();
        ReservaControlador reservaControlador = new ReservaControlador(reservaRepositorio, reservaVista, clienteVista, clienteRepositorio, mesasReservadasRepositorio, clienteControlador);

        LoginView loginVista = new LoginView();
        LoginRepository loginRepositorio = new LoginRepository();
        LoginController loginControlador = new LoginController(loginRepositorio, loginVista);


        clienteControlador.loadGestionCliente();
        empleadoControlador.pasarAMemoria();
        ordenRepositorio.cargarOrdenesDesdeArchivo();
        reservaRepositorio.cargarReserva();

        MenuLogin menuLogin = new MenuLogin(loginControlador);
        menuLogin.menu();






    }
}
