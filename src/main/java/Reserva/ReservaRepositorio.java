package Reserva;

import Interfaces.IABM;
import MesasReservadas.MesasReservadas;
import MesasReservadas.MesasReservadasRepositorio;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class ReservaRepositorio implements IABM<Reserva> {
    private List<Reserva> reservas = new LinkedList<>();
    private MesasReservadasRepositorio mesasReservadasRepositorio = new MesasReservadasRepositorio();
    private static final String PATH_Reservas = "src/main/resources/Reservas.json";
    private Gson gson = new Gson();


    public ReservaRepositorio() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        gson = gsonBuilder.create();
    }


    @Override
    public void agregar(Reserva obj) {
        reservas.add(obj);
    }


    @Override
    public void eliminar(Reserva obj) {
        reservas.remove(obj);
    }


    @Override
    public void modificar(Reserva obj) {
        for (int i = 0; i < reservas.size(); i++) {
            if (reservas.get(i).getFecha().equals(obj.getFecha())) {
                reservas.set(i, obj);
                break;
            }
        }
    }


    public LocalDate buscarFecha(LocalDate fecha){
            for (Reserva reserva : reservas) {
                if (reserva.getFecha().equals(fecha)) {
                    return reserva.getFecha();
                }
            }
        return fecha;
    }


    public Reserva buscarReserva(LocalDate fecha){
        for (Reserva reserva : reservas) {
            if (reserva.getFecha().equals(fecha)) {
                return reserva;
            }
        }
        return null;
    }


    public void guardarReserva(){
        try (FileWriter writer = new FileWriter(PATH_Reservas)) {
            gson.toJson(reservas, writer);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public void cargarReserva(){
        try (FileReader reader = new FileReader(PATH_Reservas)) {
            Type type = new TypeToken<List<Reserva>>() {}.getType();
            List<Reserva> reservaList = gson.fromJson(reader, type);
            if (reservaList != null) {
                reservas = reservaList;
            } else {
                reservas = new LinkedList<>();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public List<Reserva> todasLasReservas() {
        return reservas;
    }


    public List<MesasReservadas> todasLasMesasReservadas() {
        return mesasReservadasRepositorio.todasLasMesasReservadas();
    }
}