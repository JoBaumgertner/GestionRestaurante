package Plato.Variedad;

import Plato.Excepciones.ExcepIngresoInvalido;

import java.util.List;

public class VariedadController {

    VariedadVista vVista;
    VariedadRepositorio vRepositorio;

    public VariedadController(VariedadVista vVista, VariedadRepositorio vRepositorio) {
        this.vVista = vVista;
        this.vRepositorio = vRepositorio;
    }

    public List<Variedad> crearListaDeVariedades (VariedadVista vVista, VariedadRepositorio vRepositorio){
        do {
            Variedad variedad = vRepositorio.cargaDeVariedad(vVista.crearUnaVariedad());
            if (variedad != null) {
                vRepositorio.cargaListaVariedad(variedad);
                vVista.mensajeCargaCorrecta();
            } else {
                vVista.mensajeCargaIncorrecta();
            }
        }while (vVista.agregarMas());
        return vRepositorio.copiaListaVar();
    }

    public void limparListaVariedades (){
        vRepositorio.limpiarLista();
    }

    public double checkPrecio(String precio){
        return vVista.checkDouble(precio);
    }

    public double cargarPrecio(VariedadVista vVista){
        return vVista.ingresarPrecio();
    }

    public String cargarNombre(VariedadVista vVista) throws ExcepIngresoInvalido {
        return  vVista.ingresarNombre();
    }




}
