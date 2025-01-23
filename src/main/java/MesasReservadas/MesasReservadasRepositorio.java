package MesasReservadas;

import Interfaces.IABM;
import Reserva.Reserva;

import java.util.ArrayList;
import java.util.List;

public class MesasReservadasRepositorio implements IABM<MesasReservadas> {

    List<MesasReservadas> mesasReservadas = new ArrayList<MesasReservadas>();


    public List<MesasReservadas> getMesasReservadas() {
        return mesasReservadas;
    }

    public void setMesasReservadas(List<MesasReservadas> mesasReservadas) {
        this.mesasReservadas = mesasReservadas;
    }

    @Override
    public void agregar(MesasReservadas obj) {
        mesasReservadas.add(obj);
    }

    @Override
    public void eliminar(MesasReservadas obj) {
        mesasReservadas.remove(obj);

    }

    @Override
    public void modificar(MesasReservadas obj) {
        mesasReservadas.add(obj);
    }

    public boolean buscarIndice(Reserva reserva, Integer id) {
        boolean opcion = false;
        int i;
        List<MesasReservadas> listamesas = reserva.getMesasReservadas();
        for (i = 0; i < listamesas.size(); i++) {
            if (listamesas.get(i).getCliente().getIdCliente() == id) {
                opcion = true;

            }
        }
        return opcion;
    }

    public MesasReservadas buscarMesasReservadas(List<MesasReservadas> mesasReservadas, Integer id) {
        for (MesasReservadas mesaReservada : mesasReservadas) {
            if (mesaReservada.getCliente().getIdCliente().equals(id)) {
                return mesaReservada;
            }
        }
        return null;
    }

    public MesasReservadas buscarMesaReservadaPorIndice(Integer id) {
        MesasReservadas mesa = null;
        for (MesasReservadas mesas : mesasReservadas) {
            if (mesas.getCliente().getIdCliente() == id) {
                mesa = mesas;
            }
        }
        return mesa;
    }

    public List<MesasReservadas> todasLasMesasReservadas() {
        return mesasReservadas;
    }
}