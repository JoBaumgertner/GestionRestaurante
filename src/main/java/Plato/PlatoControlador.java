package Plato;

import Plato.Colores.Colores;
import Plato.Excepciones.ExcepCargaNoRealizada;
import Plato.Excepciones.ExcepIngresoInvalido;
import Plato.Excepciones.ExcepPlatoExistente;
import Plato.Variedad.VariedadController;
import Plato.Variedad.VariedadVista;

import java.util.List;

public class PlatoControlador {
    PlatoRepositorio platoRepositorio;
    PlatoVista platoVista;

    public PlatoControlador(PlatoRepositorio platoRepositorio, PlatoVista platoVista) {
        this.platoRepositorio = platoRepositorio;
        this.platoVista = platoVista;
    }

    //Agregar Plato
    public void cargarPlatoEnSistema(PlatoRepositorio platoRepositorio, PlatoVista platoVista, VariedadVista varVista, VariedadController varController) throws RuntimeException {
//
        Plato nuevoPlato;
        try {
            String tipo = platoVista.menuTipoComida();
            platoRepositorio.mostrarEnlistadoBonitoXtipoNew(tipo);
            platoVista.mensajePersonalizado("Ingrese el nombre del plato: ");
            String nombre = varController.cargarNombre(varVista);
            platoRepositorio.comprobarExistenciaPlato(nombre);

            nuevoPlato = platoVista.nuevoPlato(tipo, nombre);
            platoVista.printearLineasSeparadoras();
            if (nuevoPlato != null) {
                boolean confirm = platoVista.metodoConfirmacion("¿Desea confirmar la carga?");
                if (confirm) {
                    platoRepositorio.agregar(nuevoPlato);
                    platoVista.mensajeCargaExitoFracaso(true);
                    platoVista.printearLineasSeparadoras();
                } else {
                    platoVista.mensajeCargaExitoFracaso(false);
                    platoVista.printearLineasSeparadoras();
                }
            }

        } catch (ExcepCargaNoRealizada | NumberFormatException e) {
            System.out.println(e.getMessage());
        } catch (ExcepPlatoExistente epe) {
            System.out.println(epe.getMessage());
            platoVista.printearLineasSeparadoras();
            System.out.println();
            platoVista.printearLineasSeparadoras();
        } catch (ExcepIngresoInvalido e) {
            //throw new RuntimeException(e);
            System.out.println(e.getMessage());
        }
    }

    //Eliminar Plato
    public void eliminarPlatosDelSistemaXNombre(PlatoRepositorio platoRepositorio, PlatoVista platoVista, VariedadController varController, VariedadVista varVista) {
        Plato buscado;

        try {
            platoVista.mensajePersonalizado("Ingrese el nombre del Plato a eliminar");
            String nombre = varController.cargarNombre(varVista);

            buscado = platoRepositorio.buscarPlatoXnombre(nombre);

            if (buscado != null) {
                boolean confirmEliminar = platoVista.metodoConfirmacion("¿Confirmar la eliminacion?");
                if (confirmEliminar) {
                    platoRepositorio.eliminar(buscado);
                    platoVista.mensajeEliminacionExitoFracaso(true);
                } else {
                    platoVista.mensajeEliminacionExitoFracaso(false);
                }
            } else {
                platoVista.mensajeEliminacionPlatoInexistente();
                platoVista.mensajeEliminacionExitoFracaso(false);
            }
        } catch (ExcepIngresoInvalido eii) {
            platoVista.mensajePersonalizado(eii.getMessage());
        }
    }

    public void eliminarPlatosDelSistemaXSeleccion(PlatoRepositorio platoRepositorio, PlatoVista platoVista) throws NumberFormatException {
        Plato buscado = null;
        int indiceSeleccionado;
        try {

            String tipo = platoVista.menuTipoComida();
            List<Plato> listaP = platoRepositorio.enlistarXTipoSinVariedad(tipo);
            do {

                platoRepositorio.mostrarListaParaEliminar(listaP);
                indiceSeleccionado = platoVista.obtenerIndiceSeleccionado(listaP);

                if (indiceSeleccionado == -1) {
                    Colores.printInColor("Selección cancelada", Colores.RED);
                    return;
                } else if (indiceSeleccionado >= 0 && indiceSeleccionado < listaP.size()) {
                    buscado = listaP.get(indiceSeleccionado);
                } else {
                    Colores.printInColor("Opción incorrecta, ingrese una opción valida", Colores.RED);
                }
            } while (indiceSeleccionado < 0 || indiceSeleccionado >= listaP.size());

            if (buscado != null) {
                boolean confirmEliminar = platoVista.metodoConfirmacion("¿Confirmar la eliminación?");
                if (confirmEliminar) {
                    platoRepositorio.eliminar(buscado);
                    platoVista.mensajeEliminacionExitoFracaso(true);
                } else {
                    platoVista.mensajeEliminacionExitoFracaso(false);
                }
            }
        } catch (NumberFormatException nfe) {
            platoVista.mensajePersonalizado(nfe.getMessage());
        }

    }

    //Actualizar Platos y sus variantes
    public void actualizarPlatoExistente(PlatoRepositorio platoRepositorio, PlatoVista platoVista, VariedadController varController, VariedadVista varVista) throws NumberFormatException {
        try {
            String tipo = platoVista.menuTipoComida();
            platoVista.printearLineasSeparadoras();
            platoRepositorio.mostrarEnlistadoBonitoXtipoNew(tipo);
            platoVista.printearLineasSeparadoras();
            String nombre;
            do {
                platoVista.mensajePersonalizado("Ingrese el nombre del plato a modificar: ");
                nombre = varController.cargarNombre(varVista);
            } while (nombre == null);

            Plato buscado = platoRepositorio.buscarPlatoXnombre(nombre);

            platoVista.printearLineasSeparadoras();
            if (buscado == null) {
                Colores.printInColor("Plato no encontrado", Colores.RED);
            } else {
                platoRepositorio.mostrarUnPlato(buscado);
                platoVista.printearLineasSeparadoras();
                Plato actualizado = platoVista.actualizarPlato(tipo, nombre, buscado.getVariedades());
                if (actualizado == null) {
                    platoVista.printearLineasSeparadoras();
                    throw new ExcepIngresoInvalido("No se realizaron modificaciones en el Plato.");
                }
                platoRepositorio.mostrarUnPlato(actualizado);
                platoVista.printearLineasSeparadoras();
                boolean confirmUpdate = platoVista.metodoConfirmacion("¿Confirmar la actualización?");
                if (confirmUpdate) {
                    platoRepositorio.modificar(actualizado);
                    platoVista.printearLineasSeparadoras();
                    platoVista.mensajeCargaExitoFracaso(true);
                } else {
                    platoVista.printearLineasSeparadoras();
                    platoVista.mensajeCargaExitoFracaso(false);
                }
            }
        } catch (NumberFormatException | ExcepIngresoInvalido nfe) {
            platoVista.printearLineasSeparadoras();
            platoVista.mensajePersonalizado(nfe.getMessage());
            platoVista.printearLineasSeparadoras();
        }
    }

    //Aumentar precio de manera porcentual
    public void aumentoPreciosPorcentualmente(PlatoRepositorio platoRepositorio, PlatoVista platoVista) {
        platoRepositorio.aumentoPorcentualPrecio(platoVista.ingresePorcentaje());
    }

    //Bajar precio de manera porcentual
    public void bajarPreciosPorcentualmente(PlatoRepositorio platoRepositorio, PlatoVista platoVista) {
        platoRepositorio.bajaPorcentualPrecio(platoVista.ingresePorcentajeBaja());
    }

    //Selección de Plato para Orden que devuelve el plato seleccionado
    public Plato seleccionPlatoParaOrden(PlatoRepositorio platoRepositorio, PlatoVista platoVista) throws NumberFormatException {

        try {
            String tipo = platoVista.menuTipoComida();
            List<Plato> listaP;
            if (tipo != null) {
                listaP = platoRepositorio.enlistarXTipo(tipo);
            } else {
                return null;
            }

            int indiceSeleccionado;
            platoRepositorio.mostrarEnlistadoBonitoXtipoOld(tipo);
            indiceSeleccionado = platoVista.obtenerIndiceSeleccionado(listaP);
            if (indiceSeleccionado == -1) {
                Colores.printInColor("Selección cancelada", Colores.RED);
                return null;
            } else if (indiceSeleccionado > listaP.size()) {
                try {
                    throw new ExcepIngresoInvalido("Opción incorrecta, ingrese una opción valida");
                } catch (ExcepIngresoInvalido e) {
                    throw new RuntimeException(e);
                }
            }
            return listaP.get(indiceSeleccionado);
        } catch (NumberFormatException nfe) {
            System.out.println(nfe.getMessage());
            return seleccionPlatoParaOrden(platoRepositorio, platoVista);
        }
    }

    //Mostrar Platos por categoria
    public void mostrarPlatosXTipo(PlatoRepositorio platoRepositorio, PlatoVista platoVista) {
        try {
            platoRepositorio.mostrarEnlistadoBonitoXtipoOld(platoVista.menuTipoComida());
        } catch (NumberFormatException nfe) {
            System.out.println(nfe.getMessage());
        }catch (NullPointerException nullPointerException){
            System.out.println("");
        }
    }

    //Mostrar Menu Completo
    public void mostrarMenuCompleto(PlatoRepositorio platoRepositorio) {
        platoRepositorio.mostrarCartaDePlatosCompleta();
    }

    //metodo pausar pantalla
    public static void pausarPantalla(PlatoVista platoVista) {
        platoVista.pausarPantalla();
    }

}
