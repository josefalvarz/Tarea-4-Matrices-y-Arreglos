/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ejercicio_matrices_y_arreglos;

import java.util.Scanner;
import java.util.Random;

public class Ejercicio_Matrices_y_Arreglos {

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        System.out.print("Bienvenido a mi programa");
        System.out.println("Elija una de las siguientes ocpiones: ");
        System.out.println("1.Battleship");
        int option = entrada.nextInt();

        switch (option) {
            case 1:
                System.out.println("Usted ha elegido la opción número 1.battleship");
                menuJuego();
                break;
        default:
            System.out.println("Vuelvalo a intentar");
    }

    }
    // Este es el metodo en el cual corre cuando se ha seleccionado la opcion 1.
    public static void menuJuego() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("Menu");
            System.out.println("1. Solitario");
            System.out.println("2. Dos jugadores");
            System.out.println("3. Salir");
            System.out.print("Elige una opción: ");

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("Bienvenido al Battleship");
                    System.out.println("El mar está vacío");

                    tablaDeJuego batallaNaval = new tablaDeJuego();

                    // En estas lineas de codigo esta llamando a los métodos de BatallaNaval
                    tablaDeJuego.crearMapa();
                    tablaDeJuego.expandirBarcosJugador();
                    tablaDeJuego.expandirBarcosMaquina();

                    // Batalla
                    do {
                        tablaDeJuego.batalla();
                    } while (tablaDeJuego.barcosdelJugador != 0 && tablaDeJuego.barcosdelComputadora != 0);

                    // Fin del juego
                    tablaDeJuego.gameOver();
                                break;
                case 2:
                        // Crear tableros para el jugador y para el tablero de la maquina
                    String[][] tableroJugador = new String[5][5];
                    String[][] tableroMaquina = new String[5][5];

                    // Esta creando la instancia del unJugador
                    unJugador juego = new unJugador(tableroJugador, tableroMaquina);

                    // Inicializa los tableros
                    juego.inicializarTablero(tableroJugador);
                    juego.inicializarTablero(tableroMaquina);

                    // Coloca los barcos del jugador y de la maquina
                    juego.ubicarBarcos(tableroJugador, 1);
                    juego.ubicarBarcosComputadora(tableroMaquina);

                    // Realizar los ataques hasta que se hundan todos los barcos de un jugador
                    int jugadorActual = 1;
                    while (true) {
                        if (jugadorActual == 1) {
                            juego.desarrollarAtaque(tableroJugador, tableroMaquina, jugadorActual);
                        } else {
                            juego.hacerAtaqueMaquina(tableroMaquina, tableroJugador);
                        }

                        // Imprime el tablero actual
                        juego.imprimirTablero(tableroJugador, "Tablero del Jugador");
                        juego.imprimirTablero(tableroMaquina, "Tablero del Bot");

                        // Verificar si alguien de los jugadores ha ganado
                        if (juego.ganador(tableroJugador, tableroMaquina)) {
                            break;
                        }

                        // Cambia de jugador
                        jugadorActual = 3 - jugadorActual;
                    }
                    break;
                case 3:
                    System.out.println("Ha salido, gracias por jugar");
                    break;
                default:
                    System.out.println("La opcion que ha seleccionado es invalida.");
            }
        } while (opcion != 0);

    
    }

}
