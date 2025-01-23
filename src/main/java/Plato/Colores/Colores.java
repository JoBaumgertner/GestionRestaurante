package Plato.Colores;

public class Colores {
    // Códigos de colores ANSI
    public static final String RESET = "\033[0m";  // Restablece el color predeterminado
    public static final String BLACK = "\033[0;30m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String WHITE = "\033[0;37m";

    // Fondos de color
    public static final String BLACK_BG = "\033[40m";
    public static final String RED_BG = "\033[41m";
    public static final String GREEN_BG = "\033[42m";
    public static final String YELLOW_BG = "\033[43m";
    public static final String BLUE_BG = "\033[44m";
    public static final String PURPLE_BG = "\033[45m";
    public static final String CYAN_BG = "\033[46m";
    public static final String WHITE_BG = "\033[47m";

    // Método para imprimir texto en color
    public static void printInColor(String text, String color) {
        System.out.println(color + text + RESET);
    }

    // Método para imprimir texto en color con fondo
    public static void printBackground(String text, String background) {
        System.out.println(background + text + RESET);
    }

}
