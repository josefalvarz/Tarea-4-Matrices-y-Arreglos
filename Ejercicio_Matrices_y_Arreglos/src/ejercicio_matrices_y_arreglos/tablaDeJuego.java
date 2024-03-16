/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejercicio_matrices_y_arreglos;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class tablaDeJuego {
//Son public static porque permite que las variables y métodos sean accesibles y utilizables desde cualquier parte del programa, incluso fuera de la clase donde están definidos.
    static int tamanoTabla = 5;//Define el tamaño del tablero del juego. 
    public static int filas = 6;//Representa el numero de filas 
    public static int columnas = 5;// Representa el numero de columnas
    public static int barcosdelJugador;
    public static int barcosdelComputadora;
    public static String[][] tabla = new String[filas][columnas];// Representa el tablero del juego
    public static int[][] intentosErrados = new int[filas][columnas];// Representa una matriz de los intentos fallidos 
    public static Random random = new Random();
    public static void main(String[] args) {
        System.out.println("Bienvenido al Battleship");
        System.out.println("El mar está vacío");

        //  Crea el mapa oceánico
        crearMapa();

        //  Despliega los barcos del jugador
        expandirBarcosJugador();

        // Despliega los barcos de la computadora
        expandirBarcosMaquina();

        // Batalla
        do {
            batalla();
        } while (barcosdelJugador != 0 && barcosdelComputadora != 0);

        // Fin del juego
        gameOver();
    }

    public static void crearMapa() {
        System.out.print("  ");
        for (int i = 0; i < columnas; i++)
            System.out.print(i);
        System.out.println();

        for (int i = 0; i < tabla.length; i++) {
            for (int j = 0; j < tabla[i].length; j++) {
                tabla[i][j] = " ";
                if (j == 0)
                    System.out.print(i + "[" + tabla[i][j]);
                else if (j == tabla[i].length - 1)
                    System.out.print(tabla[i][j] + "]" + i);
                else
                    System.out.print(tabla[i][j]);
            }
            System.out.println();
        }

        System.out.print("  ");
        for (int i = 0; i < columnas; i++)
            System.out.print(i);
        System.out.println();
    }

    public static void expandirBarcosJugador() {
        Scanner input = new Scanner(System.in);

        System.out.println("Despliega tus barcos:");
        // Despliega cinco barcos para el jugador
        barcosdelJugador = 3;
        for (int i = 1; i <= barcosdelJugador; ) {
            System.out.print("Ingresa la coordenada 0 para tu " + i + " barco: ");
            int x = input.nextInt();
            System.out.print("Ingresa la coordenada 1 para tu " + i + " barco: ");
            int y = input.nextInt();

            if ((x >= 0 && x < filas) && (y >= 0 && y < columnas) && (tabla[x][y].equals(" "))) {
                tabla[x][y] = "$";
                i++;
            } else if ((x >= 0 && x < filas) && (y >= 0 && y < columnas) && tabla[x][y].equals("$"))
                System.out.println("No puedes colocar dos o más barcos en la misma ubicación");
            else if ((x < 0 || x >= filas) || (y < 0 || y >= columnas))
                System.out.println("No puedes colocar barcos fuera de la cuadrícula " + filas + " por " + columnas);
        }
        imprimirMapa();
    }

    public static void expandirBarcosMaquina() {
        System.out.println("La computadora está desplegando sus barcos");
        // Despliega cinco barcos para la computadora
        barcosdelComputadora = 3;
        for (int i = 1; i <= barcosdelComputadora; ) {
            int x = random.nextInt(filas);
            int y = random.nextInt(columnas);

            if ((x >= 0 && x < filas) && (y >= 0 && y < columnas) && (tabla[x][y].equals(" "))) {
                tabla[x][y] = "x";
                System.out.println(i + ". El barco ha sido desplegado");
                i++;
            }
        }
        imprimirMapa();
    }

    public static void batalla() {
        turnoJugador();
        turnoMaquina();

        imprimirMapa();

        System.out.println();
        System.out.println("Tus barcos: " + barcosdelJugador + " | Barcos de la computadora: " + barcosdelComputadora);
        System.out.println();
    }

    public static void turnoJugador() {
        System.out.println("Es su turno");
        int x = -1, y = -1;
        do {
            Scanner input = new Scanner(System.in);
            System.out.print("Ingresa la coordenada 0: ");
            x = input.nextInt();
            System.out.print("Ingresa la coordenada 1: ");
            y = input.nextInt();

            if ((x >= 0 && x < filas) && (y >= 0 && y < columnas)) 
            {
                if (tabla[x][y].equals("x")) {
                    System.out.println("Bomba al agua.");
                    tabla[x][y] = "!";
                    --barcosdelComputadora;
                } else if (tabla[x][y].equals("$")) {
                    System.out.println("Uy un barco ha sido danado :(");
                    tabla[x][y] = "x";
                    --barcosdelJugador;
                    ++barcosdelComputadora;
                } else if (tabla[x][y].equals(" ")) {
                    System.out.println("Lo siento, fallo");
                    tabla[x][y] = "-";
                }
            } else if ((x < 0 || x >= filas) || (y < 0 || y >= columnas))  
                System.out.println("No puede colocar barcos fuera de la casilla " + filas + " por " + columnas);
        } while ((x < 0 || x >= filas) || (y < 0 || y >= columnas));  
    }

    public static void turnoMaquina() {
        System.out.println("Es el turno de la computadora");
        // Adivina las coordenadas
        int x = -1, y = -1;
        do {
            x = random.nextInt(filas);
            y = random.nextInt(columnas);

            if ((x >= 0 && x < filas) && (y >= 0 && y < columnas)) 
            {
                if (tabla[x][y].equals("$")) {
                    System.out.println("Uy un barco ha sido danado.");
                    tabla[x][y] = "x";
                    --barcosdelJugador;
                    ++barcosdelComputadora;
                } else if (tabla[x][y].equals("x")) {
                    System.out.println("Uy un barco ha sido danado.");
                    tabla[x][y] = "!";
                } else if (tabla[x][y].equals(" ")) {
                    System.out.println("La maquina falló");
                    // Guardando conjeturas fallidas para la computadora
                    if (intentosErrados[x][y] != 1)
                        intentosErrados[x][y] = 1;
                }
            }
        } while ((x < 0 || x >= filas) || (y < 0 || y >= columnas));  // Sigue repitiendo hasta la condicion válida
    }

    public static void gameOver() {
        System.out.println("Tus barcos: " + barcosdelJugador + " | Barcos de la computadora: " + barcosdelComputadora);
        if (barcosdelJugador > 0 && barcosdelComputadora <= 0)
            System.out.println("Gano la batalla :)");
        else
            System.out.println("Perdio la batalla");
        System.out.println();
    }

    public static void imprimirMapa() {
        System.out.println();
        System.out.print("  ");
        for (int i = 0; i < columnas; i++)
            System.out.print(i);
        System.out.println();

        for (int x = 0; x < tabla.length; x++) {
            System.out.print(x + "[");

            for (int y = 0; y < tabla[x].length; y++) {
                System.out.print(tabla[x][y]);
            }

            System.out.println("[" + x);
        }

        System.out.print("  ");
        for (int i = 0; i < columnas; i++)
            System.out.print(i);
        System.out.println();
    }

}
