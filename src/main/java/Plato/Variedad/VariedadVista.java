package Plato.Variedad;
import Plato.Colores.Colores;
import Plato.Excepciones.ExcepIngresoInvalido;

import java.util.Scanner;

public class VariedadVista {
    private static final Scanner scanner = new Scanner(System.in);

    //Crea una variedad dentro de un Plato
    public Variedad crearUnaVariedad() {
        try {
            System.out.println("-Ingrese el nombre de la variedad:");
            String nombreVariedad = ingresarNombre();
            lineaSeparador();
            double precioVariedad = ingresarPrecio();
            lineaSeparador();
            if (precioVariedad != 0.0) return new Variedad(nombreVariedad, precioVariedad);
            else {
                Colores.printInColor("Los datos ingresados no fueron cargados", Colores.RED);
                lineaSeparador();
                return null;
            }
        }catch (ExcepIngresoInvalido eii){
            System.out.println(eii.getMessage());
        }
        return null;
    }

    // Modulo de ingreso de nombre con comprobacion para evitar ingresos de caracteres invalidos
    public String ingresarNombre() throws ExcepIngresoInvalido {
        try {
            String nombre = scanner.nextLine();
            nombre = primeraMayuscula(nombre);
            if (nombre.trim().isEmpty() || !nombre.matches("[a-zA-ZáéíóúüÁÉÍÓÚÜ ]+")) {
                throw new ExcepIngresoInvalido("Ha ingresado Numeros o Caracteres invalidos en el nombre.\nEl nombre del plato solo debe contener letras.");
            } else return nombre;
        }catch (IllegalArgumentException iae){
            System.out.println(iae.getMessage());
            return ingresarNombre();
        }
    }

    //Módulo recibe el precio y realiza comprobaciones
    public double ingresarPrecio() {
        boolean validarPrecio = false;
        double precioVariedad = 0.0;
        int intentos = 2;

        while (!validarPrecio && intentos > 0){
            try {
                System.out.println("Ingrese el precio de la variedad (Formato X.XX): $");
                precioVariedad = checkDouble(scanner.nextLine());
                validarPrecio = true;
            }catch (NumberFormatException nfe){
                System.out.println(nfe.getMessage());
                intentos--;
            }
        }
        if(!validarPrecio) System.out.println("Se agotaron los intentos. El precio no fue cargado.");
        return precioVariedad;
    }

    //Metodo que corrobora que el ingreso del precio sea de tipo double
    public double checkDouble(String precioString) throws NumberFormatException {
            try {
                return Double.parseDouble(precioString);
            }catch (NumberFormatException nfe){
                throw new NumberFormatException("El formato ingresado es Invalido.");
            }
    }

    public void mensajeCargaIncorrecta(){
        Colores.printInColor("No se realizó la carga", Colores.RED);
        lineaSeparador();
    }
    public void mensajeCargaCorrecta(){
        Colores.printInColor("Se realizó la carga con éxito", Colores.GREEN);
        lineaSeparador();
    }
    public boolean agregarMas(){
        System.out.println("¿Desea agregar otra variedad? (s/n):");
        String respuesta = scanner.nextLine().trim().toLowerCase();
        lineaSeparador();
        if (respuesta.equals("s")) {
            return true;
        } else if (respuesta.equals("n")) {
            return false;
        } else {
            lineaSeparador();
            Colores.printInColor("Opción inválida. Por favor, ingrese 's' para sí o 'n' para no.", Colores.RED);
            return agregarMas();
        }
    }

    //Se verifica si el nombre es nulo o vacío y se lanza una excepción en ese caso.
    //Se convierte el nombre a minúsculas y se divide en palabras usando split("\\s+") para considerar uno o más espacios.
    //Se itera sobre cada palabra, se convierte la primera letra a mayúscula, se concatena el resto de la palabra en minúsculas y se añade un espacio.
    //Se devuelve el resultado como una cadena
    public static String primeraMayuscula(String nombre) throws IllegalArgumentException {
        if (nombre == null || nombre.trim().isEmpty()) throw new IllegalArgumentException("El nombre no puede ser nulo o vacío.");
        String[] palabras = nombre.trim().toLowerCase().split("\\s+");
        StringBuilder nombreModificado = new StringBuilder();
        for (String palabra : palabras) {
            if (!palabra.isEmpty()) {
                nombreModificado.append(Character.toUpperCase(palabra.charAt(0)))
                        .append(palabra.substring(1))
                        .append(" ");
            }
        }
        return nombreModificado.toString().trim();
    }


    public void lineaSeparador(){
        System.out.println("------------------------------------------------------");
    }

}
