package Plato.Variedad;

import Plato.Excepciones.ExcepIngresoInvalido;

import java.util.ArrayList;
import java.util.List;

public class VariedadRepositorio {
    private List<Variedad> listaVar;

    public Variedad cargaDeVariedad(Variedad variedad) {
        if (variedad != null) return variedad;
        else{
            try {
                throw new ExcepIngresoInvalido("Carga Incorrecta");
            } catch (ExcepIngresoInvalido e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    public VariedadRepositorio() {
        this.listaVar = new ArrayList<>();
    }

    public void cargaListaVariedad(Variedad variedad){
        listaVar.add(variedad);
    }
    public List<Variedad> copiaListaVar(){
        return this.listaVar;
    }
    public void limpiarLista(){
        this.listaVar.clear();
    }



}
