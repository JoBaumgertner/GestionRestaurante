package Cliente;

import Excepciones.ExcepcionClienteNoEncontrado;
import Excepciones.ExcepcionFormatoIncorrecto;
import Plato.Colores.Colores;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;


public class ClienteControlador {

    private ClienteVista clienteVista = new ClienteVista();
    private ClienteRepositorio clienteRepositorio = new ClienteRepositorio();
    private Gson gson = new Gson();
    private static final String PATH = "src/main/resources/Clientes.json";
    private static int lastId = 0;


    public ClienteControlador(ClienteVista clienteVista, ClienteRepositorio clienteRepositorio) {
        this.clienteVista = clienteVista;
        this.clienteRepositorio = clienteRepositorio;
        this.loadGestionCliente();
    }

    public void loadGestionCliente() {
        try (Reader reader = new FileReader(PATH);) {
            Type type = new TypeToken<Set<Cliente>>() {
            }.getType();
            this.clienteRepositorio.clienteSet = gson.fromJson(reader, type);
            if (this.clienteRepositorio.clienteSet == null) {
                this.clienteRepositorio.clienteSet = new HashSet<>();
            } else {
                this.clienteRepositorio.clienteSet.forEach(cliente -> {
                    clienteRepositorio.addCliente(cliente);
                    lastId = cliente.getIdCliente();
                });

            }
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
    }

    public Set<Cliente> getClienteSet() {
        return this.clienteRepositorio.clienteSet;
    }

    public void Update() {
        try (Writer writer = new FileWriter(PATH);) {
            gson.toJson(this.clienteRepositorio.clienteSet, writer);
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
    }


    public ClienteVista getClienteVista() {
        return clienteVista;
    }

    public void setClienteVista(ClienteVista clienteVista) {
        this.clienteVista = clienteVista;
    }

    public ClienteRepositorio getClienteRepositorio() {
        return clienteRepositorio;
    }

    public void setClienteRepositorio(ClienteRepositorio clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
    }

    public void agregarClientes() {
        Cliente cliente = this.clienteVista.agregarVista();
        lastId++;
        cliente.setIdCliente(lastId);
        this.clienteRepositorio.addCliente(cliente);
        this.Update();
    }

    public void updateClientes() {
        Cliente cliente;
        Integer ok = 0;
        if (this.clienteRepositorio.getClienteSet().isEmpty()) {
            Colores.printInColor("No hay clientes cargados", Colores.RED);
        } else {
            do {
                try {
                    Integer id = this.clienteVista.seleccId();

                    cliente = this.clienteRepositorio.findCliente(id, this.clienteRepositorio.getClienteSet());
                    String phone = this.clienteVista.newPhone();
                    this.clienteRepositorio.updateCliente(cliente, phone);
                    Colores.printInColor("Datos modificados con éxito", Colores.GREEN);
                    ok = 1;
                    System.out.println(cliente.toString());
                } catch (ExcepcionClienteNoEncontrado excepcionClienteNoEncontrado) {
                    System.out.println(excepcionClienteNoEncontrado.getMessage());
                } catch (ExcepcionFormatoIncorrecto excepcionFormatoIncorrecto) {
                    System.out.println(excepcionFormatoIncorrecto.getMessage());
                }
            } while (ok == 0);
            this.Update();
        }
    }

    public void removeCliente() {
        Cliente cliente;
        Integer id;
        if (this.clienteRepositorio.getClienteSet().isEmpty()) {
            Colores.printInColor("No hay clientes cargados", Colores.RED);
        } else {
            do {
                try {

                    id = this.clienteVista.selecIdRemove();
                    cliente = this.clienteRepositorio.findCliente(id, this.clienteRepositorio.getClienteSet());
                    this.clienteRepositorio.removeCliente(cliente);
                    System.out.println("-------- Nueva lista de Clientes --------");
                    this.clienteVista.verTodosClientes(this.clienteRepositorio.getClienteSet());
                } catch (ExcepcionClienteNoEncontrado excepcionClienteNoEncontrado) {
                    System.out.println(excepcionClienteNoEncontrado.getMessage());
                    cliente = null;
                } catch (ExcepcionFormatoIncorrecto excepcionFormatoIncorrecto) {
                    System.out.println(excepcionFormatoIncorrecto.getMessage());
                    cliente = null;
                }
            } while (cliente == null);
            this.Update();
        }
    }

    public void viewClientes() {
        loadGestionCliente();
        if (this.clienteRepositorio.getClienteSet().isEmpty()) {
            Colores.printInColor("No hay clientes cargados", Colores.RED);
        } else {
            this.clienteVista.verTodosClientes(this.clienteRepositorio.getClienteSet());
        }
    }

    public void consultCliente() {
        loadGestionCliente();
        if (this.clienteRepositorio.getClienteSet().isEmpty()) {
            Colores.printInColor("No hay clientes cargados", Colores.RED);
        } else {
            try {
                this.clienteVista.verIdAndName(this.clienteRepositorio.getClienteSet());
                Integer id = this.clienteVista.consultarCliente();
                Cliente cliente = this.clienteRepositorio.findCliente(id, this.clienteRepositorio.getClienteSet());
                System.out.println("------------------------");
                System.out.println("Telefono: " + cliente.getTelefono());
                System.out.println("------------------------");
            } catch (ExcepcionClienteNoEncontrado excepcionClienteNoEncontrado) {
                System.out.println(excepcionClienteNoEncontrado.getMessage());
            }
        }
    }
}



