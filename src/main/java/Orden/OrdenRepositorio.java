package Orden;

import Excepciones.ExcepcionOrdenNoEncontrada;
import Plato.Plato;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

public class OrdenRepositorio {
    private TreeMap<Integer, Orden> ordenes = new TreeMap<>();


    private static final String PATH_Ordenes = "src/main/resources/Ordenes.json";
    private Gson gson = new Gson();


    public void guardarOrden(Orden orden) {
        ordenes.put(orden.getId(), orden);
    }

    public Orden obtenerOrden(Integer id) throws ExcepcionOrdenNoEncontrada {
        if(ordenes.get(id)== null) {
            throw new ExcepcionOrdenNoEncontrada("No se encontro ninguna orden con el ID: " + id);
        }
        return ordenes.get(id);
    }

    public void eliminarOrden(Integer id) {
        ordenes.remove(id);
    }

    public void actualizarOrden(Orden orden) {
        ordenes.put(orden.getId(), orden);
    }

    public void guardarOrdenesEnArchivo() {
        try (FileWriter writer = new FileWriter(PATH_Ordenes)) {
            gson.toJson(ordenes, writer);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public void cargarOrdenesDesdeArchivo() {
        try (FileReader reader = new FileReader(PATH_Ordenes)) {
            Type type = new TypeToken<TreeMap<Integer, Orden>>() {}.getType();
            ordenes = gson.fromJson(reader, type);
            if(ordenes == null){
                ordenes = new TreeMap<>();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            ordenes = new TreeMap<>();
        }
    }

    public TreeMap<Integer, Orden> obtenerMap(){
        return ordenes;
    }

    public double calcularTotalOrden(Integer id) throws ExcepcionOrdenNoEncontrada {
        Orden orden = obtenerOrden(id);
        double total = 0.0;
        for (Plato plato : orden.getPlatoList()) {
            total += plato.getPrecio();
        }
        return total;
    }
}
