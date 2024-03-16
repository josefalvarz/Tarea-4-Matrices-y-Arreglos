/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejercicio_matrices_y_arreglos;
import java.util.Random;
import java.util.Scanner;
/**
 *
 * @author KARIM ALVAREZ
 */
public class unJugador {
    public static final int tamanoTabla = 5;
    public String[][] tableroJugador;
    public String[][] tableroMaquina;
    
    /* NO es correcto usar static ya que la declaración su propósito es crear instancias 
    de la clase y no pertenecen a la clase en sí.*/
    
    public unJugador(String[][] tableroJugador, String[][] tableroBot) { 
        // Asigna los valores pasados ​​como parámetros a las variables de la clase unJugador.
        this.tableroJugador = tableroJugador;
        this.tableroMaquina = tableroBot;
    }


    public void inicializarTablero(String[][] tablero) {
        for (int i = 0; i < tamanoTabla; i++) {
            for (int j = 0; j < tamanoTabla; j++) {
                tablero[i][j] = "_";
            }
        }
    }

    public void ubicarBarcos(String[][] tablero, int jugador) {
        Scanner entrada = new Scanner(System.in);
        System.out.println("Jugador " + jugador + ", coloque sus barcos.");

        for (int i = 0; i < 3; i++) {
            int fila, columna;

            do {
                System.out.println("La posicion del barco es: " + (i + 1) + ":");
                System.out.print("Ingrese las coordenas como se le pide el formato (fila,columna): ");
                String posiciones = entrada.nextLine();
                String[] split = posiciones.split(",");

                if (split.length != 2 || !esEntero(split[0]) || !esEntero(split[1])) {
                    System.out.println("El Formato de coordenadas que ingreso es invalido. Intente de nuevo.");
                    continue;
                }

                fila = Integer.parseInt(split[0]) - 1;
                columna = Integer.parseInt(split[1]) - 1;

                if (fila < 0 || fila >= tamanoTabla || columna < 0 || columna >= tamanoTabla) {
                    System.out.println("Ha ingresado un valor incorrecto.");
                    continue;
                }

                if (!tablero[fila][columna].equals("_")) {
                    System.out.println("Esa posicion ya esta ocupada por un barco. Inténtalo de nuevo.");
                } else {
                    tablero[fila][columna] = "B";
                    break;
                }
            } while (true);
        }
    }

    public void ubicarBarcosComputadora(String[][] tablero) {
        Random rand = new Random();
        int numeroBarcos = 3;

        while (numeroBarcos > 0) {
            int fila = rand.nextInt(tamanoTabla);
            int columna = rand.nextInt(tamanoTabla);

            if (tablero[fila][columna].equals("_")) {
                tablero[fila][columna] = "B";
                numeroBarcos--;
            }
        }
    }

    public boolean esEntero(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    public int hacerAtaqueMaquina(String[][] tableroAtacante, String[][] tableroAtacado) {
        int partesDestruidas = 0;
        Random rand = new Random();

        System.out.println("Turno de la maquina");
        int fila, columna;

        do {
            fila = rand.nextInt(tamanoTabla);
            columna = rand.nextInt(tamanoTabla);
        } while (!tableroAtacante[fila][columna].equals("-") && !tableroAtacante[fila][columna].equals("_"));

        if (tableroAtacado[fila][columna].equals("B")) {
            System.out.println("¡Uy, un barco ha sido dañado!");
            partesDestruidas++;
            tableroAtacante[fila][columna] = "X";
            tableroAtacado[fila][columna] = "X";
        } else if (tableroAtacado[fila][columna].equals("X")) {
            System.out.println("La maquina ya ha atacado esa coordenada. Es el Turno del Jugador.");
        } else {
            System.out.println("La maquina ha fallado el ataque. Turno del Jugador.");
            tableroAtacante[fila][columna] = "-";
            tableroAtacado[fila][columna] = "X";
        }

        return partesDestruidas;
    }

    public void imprimirTablero(String[][] tablero, String titulo) {
        System.out.println(titulo);
        for (int i = 0; i < tamanoTabla; i++) {
            for (int j = 0; j < tamanoTabla; j++) {
                System.out.print(tablero[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int desarrollarAtaque(String[][] tableroAtacante, String[][] tableroAtacado, int jugador) {
        int partesDevastadas = 0;
        Scanner entrada = new Scanner(System.in);

        System.out.println("Es el turno del Jugador " + jugador);
        int fila, columna;

        do {
            System.out.print("Ingrese las coordenas como se le pide el formato (fila,columna): ");
            String[] posiciones = entrada.nextLine().split(",");

            if (posiciones.length != 2 || !esEntero(posiciones[0]) || !esEntero(posiciones[1])) {
                System.out.println("El formato de coordenadas es incorrecto.");
                continue;
            }

            fila = Integer.parseInt(posiciones[0]) - 1;
            columna = Integer.parseInt(posiciones[1]) - 1;

            if (fila < 0 || fila >= tamanoTabla || columna < 0 || columna >= tamanoTabla) {
                System.out.println("Las coordenadas son invalidas porque estan fuera de rango.");
                continue;
            }

            if (tableroAtacado[fila][columna].equals("B")) {
                System.out.println("¡Uy, un barco ha sido dañado!");
                partesDevastadas++;
                tableroAtacante[fila][columna] = "X";
                tableroAtacado[fila][columna] = "X";
            } else if (tableroAtacado[fila][columna].equals("X")) {
                System.out.println("Ya ha atacado esas coordenadas. Inténtalo de nuevo.");
            } else {
                System.out.println("Siga intentando");
                tableroAtacante[fila][columna] = "-";
                tableroAtacado[fila][columna] = "X";
            }
            break;
        } while (true);

        return partesDevastadas;
    } 
    
    public boolean ganador(String[][] tableroJugador, String[][] tableromaquina) {
        if (barcosHundidos(tableroJugador)) {
            System.out.println("¡La maquina gano! Todos sus barcos fueron destruidos.");
            return true;
        } else if (barcosHundidos(tableromaquina)) {
            System.out.println("¡Ha ganado! Todos los barcos de la maquina fueron destruidos.");
            return true;
        }
        return false;
    }

    public boolean barcosHundidos(String[][] tablero) {
        for (int i = 0; i < tamanoTabla; i++) {
            for (int j = 0; j < tamanoTabla; j++) {
                if (tablero[i][j].equals("B")) {
                    return false;
                }
            }
        }
        return true;
    }
    
}
