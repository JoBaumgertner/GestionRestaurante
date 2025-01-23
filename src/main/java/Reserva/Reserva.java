package Reserva;

import MesasReservadas.MesasReservadas;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Reserva {
    private LocalDate fecha;
    private List<MesasReservadas> mesasReservadas = new ArrayList<MesasReservadas>();

    public Reserva(LocalDate fecha, List<MesasReservadas> mesasReservadas) {
        this.fecha = fecha;
        this.mesasReservadas = mesasReservadas;
    }

    public Reserva(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public List<MesasReservadas> getMesasReservadas() {
        return mesasReservadas;
    }

    public void setMesasReservadas(List<MesasReservadas> mesasReservadas) {
        this.mesasReservadas = mesasReservadas;
    }

    @Override
    public String toString() {
        return "Reserva: " +
                "fecha: " + fecha +
                ", Mesas Reservadas: " + mesasReservadas;
    }
}
