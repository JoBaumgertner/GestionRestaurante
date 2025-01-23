package Plato;

import Interfaces.IABM;
import Plato.Colores.Colores;
import Plato.Excepciones.ExcepPlatoExistente;
import Plato.Variedad.Variedad;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlatoRepositorio implements IABM<Plato> {

    private Gson gson = new Gson();
    private static final String FILE_COMIDAS = "src/main/resources/Platos.json";
    private Set<Plato> platoSet;

    //Constructor del Repositorio de Plato
    public PlatoRepositorio() {
        this.platoSet = new HashSet<>();
        loadFilePlatos();
    }

    //Deserializacion del archivo de Platos a la Coleccion HashSet
    private void loadFilePlatos() {
        try (Reader reader = new FileReader(FILE_COMIDAS)) {
            Type setType = new TypeToken<Set<Plato>>() {
            }.getType();
            platoSet = gson.fromJson(reader, setType);
            if (platoSet == null) platoSet = new HashSet<>();
        } catch (FileNotFoundException fnf) {
            platoSet = new HashSet<>();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    //Serializacion del HashSet al archivo
    private void saveFilePlatos() {
        try (Writer writer = new FileWriter(FILE_COMIDAS)) {
            gson.toJson(platoSet, writer);
        } catch (IOException ioE) {
            ioE.printStackTrace();
        }
    }

    //Agregar plato al archivo y HashSet
    @Override
    public void agregar(Plato comida) {
        this.platoSet.add(comida);
        saveFilePlatos();
    }

    //Eliminar plato del archivo y HashSet
    @Override
    public void eliminar(Plato comida) {
        this.platoSet.remove(comida);
        saveFilePlatos();
    }

    //Modificar y actualizar plato en el archivo y HashSet
    @Override
    public void modificar(Plato comida) {
        for (Plato plato : platoSet) {
            if (comida.getNombre().equals(plato.getNombre())) {
                plato.setPrecio(comida.getPrecio());
                plato.getVariedades().clear();
                plato.getVariedades().addAll(comida.getVariedades());
            }
        }
        saveFilePlatos();
    }

    public void comprobarExistenciaPlato(String nombre) throws ExcepPlatoExistente {
        for (Plato plato : platoSet) {
            if (nombre.equals(plato.getNombre())) {
                throw new ExcepPlatoExistente("Ya existen un plato cargado con ese nombre. Carga Cancelada");
            }
        }
        System.out.println("El nombre " + nombre + " esta disponible.");
    }

    //Recibe un string con el nombre del plato a buscar. Si lo encuentra devuelve el plato, caso contrario devuelve null
    public Plato buscarPlatoXnombre(String nombre) {
        for (Plato plato : platoSet) {
            if (plato.getNombre().equals(nombre)) {
                return plato;
            }
        }
        return null;
    }

    public void mostrarUnPlato(Plato plato) {
        System.out.println(plato.toString());
        if (plato.getVariedades() != null && !plato.getVariedades().isEmpty()) {
            List<Variedad> variedades = plato.getVariedades();
            for (int i = 0; i < variedades.size(); i++) {
                Variedad variedad = variedades.get(i);
                if (i == variedades.size() - 1) {
                    System.out.print("  - " + variedad.toString());
                } else {
                    System.out.println("  - " + variedad.toString());
                }
            }
        }
        System.out.println(); //salto de linea
    }

    //recibe un String con el tipo de categoria y carga una lista recorriendo el set la cual devuelve
    public List<Plato> enlistarXTipo(String tipo) {
        List<Plato> listaAuxiliar = new ArrayList<>();
        for (Plato plato : platoSet) {
            if (tipo.equals(plato.getTipo())) {
                if (plato.getVariedades().isEmpty()) {
                    listaAuxiliar.add(plato);
                } else {
                    for (Variedad variedad : plato.getVariedades()) {
                        String nombreCompuesto = String.format(plato.getNombre() + " " + variedad.getNombre());
                        Plato platoConVariedad = new Plato(tipo, nombreCompuesto, variedad.getPrecio());
                        listaAuxiliar.add(platoConVariedad);
                    }
                }
            }
        }
        return listaAuxiliar;
    }

    //crea una lista con el tipo ingresado, pero no considera las variedades de cada plato
    public List<Plato> enlistarXTipoSinVariedad(String tipo) {
        List<Plato> listaAuxiliar = new ArrayList<>();
        for (Plato plato : platoSet) {
            if (tipo.equals(plato.getTipo())) {
                listaAuxiliar.add(plato);
            }
        }
        return listaAuxiliar;
    }

    public void mostrarListaParaEliminar(List<Plato> listaPlatos) {
        int contadorDeIndices = 1;
        for (Plato plato : listaPlatos) {
            System.out.println(String.format("%d - %-20s", contadorDeIndices, plato.getNombre()));
            contadorDeIndices++;
        }
    }

    // Recibe un tipo por parámetro y crea dos listas, una con nombre y otra con precio. Complementa el nombre del plato que contiene variedad
    // Luego busca el nombre más largo y lo toma de referencia para acomodar el menu con un largo estandarizado.
    // Utilizado en la selección de plato para orden y eliminación por selección
    public void mostrarEnlistadoBonitoXtipoOld(String tipo) {
        List<String> nombre = new ArrayList<>();
        List<Double> precio = new ArrayList<>();
        for (Plato plato : platoSet) {
            if (tipo.equals(plato.getTipo())) {
                if (plato.getVariedades().isEmpty()) {
                    nombre.add(plato.getNombre());
                    precio.add(plato.getPrecio());
                } else {
                    for (Variedad variedad : plato.getVariedades()) {
                        String nombreCompuesto = String.format(plato.getNombre() + " " + variedad.getNombre());
                        nombre.add(nombreCompuesto);
                        precio.add(variedad.getPrecio());
                    }
                }
            }
        }
        // busca el plato con el nombre más largo
        int largoMaximoDelNombreDelPlato = 0;
        for (String plato : nombre) {
            if (plato.length() > largoMaximoDelNombreDelPlato) {
                largoMaximoDelNombreDelPlato = plato.length();
            }
        }
        // crea una variable de tipo int que contiene la cantidad total de índices
        int totalIndices = String.valueOf(nombre.size()).length();

        // Imprimir el menú formateado
        System.out.println("Menu de " + tipo);
        System.out.println("========================================");
        for (int i = 0; i < nombre.size(); i++) {
            System.out.printf("% " + totalIndices + "d. %-" + largoMaximoDelNombreDelPlato + "s  $%.2f%n", i + 1, nombre.get(i), precio.get(i));
        }
    }

    //Método para mostrar la carta completa accediendo desde el set. metodología similar a la utilizada en el mostrarEnlistadoBonitoXtipoOld
    // pero se modularizó las secciones del método mensionado. Se calcula por separado el nombre más largo del total de nombres en el set
    // y el total de índices. En diferentes métodos. Por último mostrarEnlistadoBonitoXtipoNew imprime el total del Set.

    public void mostrarCartaDePlatosCompleta() {
        Set<String> tiposYaMostrados = new HashSet<>();
        for (Plato plato : this.platoSet) {
            String tipoActual = plato.getTipo();
            if (!tiposYaMostrados.contains(tipoActual)) {
                tiposYaMostrados.add(tipoActual);
                mostrarEnlistadoBonitoXtipoNew(tipoActual);
            }
        }
    }

    public void mostrarEnlistadoBonitoXtipoNew(String tipo) {
        List<Plato> platosPorTipo = new ArrayList<>();
        for (Plato plato : platoSet) {
            if (tipo.equals(plato.getTipo())) {
                platosPorTipo.add(plato);
            }
        }

        // Imprimir el menú formateado
        System.out.println("Menu de " + tipo);
        System.out.println("====================================================");

        for (Plato plato : platosPorTipo) {
            //Imprime el plato principal
            System.out.println(plato.toString());

            //Imprime las variedades
            if (plato.getVariedades() != null && !plato.getVariedades().isEmpty()) {
                List<Variedad> variedades = plato.getVariedades();
                for (int i = 0; i < variedades.size(); i++) {
                    Variedad variedad = variedades.get(i);
                    if (i == variedades.size() - 1) {
                        System.out.print("  - " + variedad.toString());
                    } else {
                        System.out.println("  - " + variedad.toString());
                    }
                }
                System.out.println();//salto de linea
            }
        }
        System.out.println("====================================================");
    }

    //Si el plato contiene variedades imprime las variedades, caso contrario imprime los platos base con sus valores
    public void mostrarPlato(Plato plato) {
        if (plato.getVariedades().isEmpty()) {
            System.out.print(plato.toString());
        } else {
            System.out.println("-" + plato.getNombre());
            for (Variedad variedad : plato.getVariedades()) {
                System.out.println(variedad.toString());
            }
        }
    }

    // Se ingresa un valor para aumentar el precio total de los productos de manera porcentual
    public void aumentoPorcentualPrecio(double aumento) {
        if (aumento != 0) {
            aumento = aumento * 0.01;
            for (Plato plato : this.platoSet) {
                if (plato.getVariedades().isEmpty()) {
                    // Si el plato no tiene variedades, aumentar el precio del plato mismo
                    double valorAnterior = plato.getPrecio();
                    double nuevoValor = valorAnterior + (valorAnterior * aumento);
                    plato.setPrecio(nuevoValor);
                } else {
                    // Aumentar el precio de las variedades, si existen
                    for (Variedad variedad : plato.getVariedades()) {
                        double valorAnterior = variedad.getPrecio();
                        double nuevoValor = valorAnterior + (valorAnterior * aumento);
                        variedad.setPrecio(nuevoValor);
                    }
                }
            }
            saveFilePlatos();
        } else {
            Colores.printInColor("No se realizó el aumento de precios", Colores.RED);
        }
    }

    public void bajaPorcentualPrecio(double reduccion) {
        if (reduccion != 0) {
            reduccion = reduccion * 0.01;
            for (Plato plato : this.platoSet) {
                if (plato.getVariedades().isEmpty()) {
                    // Si el plato no tiene variedades, baja el precio del plato mismo
                    double valorAnterior = plato.getPrecio();
                    double nuevoValor = valorAnterior - (valorAnterior * reduccion);
                    plato.setPrecio(nuevoValor);
                } else {
                    // Aumentar el precio de las variedades, si existen
                    for (Variedad variedad : plato.getVariedades()) {
                        double valorAnterior = variedad.getPrecio();
                        double nuevoValor = valorAnterior - (valorAnterior * reduccion);
                        variedad.setPrecio(nuevoValor);
                    }
                }
            }
            saveFilePlatos();
        } else {
            Colores.printInColor("No se realizó la reducción de precios", Colores.RED);
        }
    }


}
