package tresEnRaya;

import java.util.Scanner;

public class Main {

    public static Scanner src = new Scanner(System.in);

    public static int tamano = 3;
    public static int victoriasNecesarias = 1;
    public static String nombre1 = "Jugador 1";
    public static String nombre2 = "Jugador 2";
    public static int victoriasTotales1 = 0;
    public static int victoriasTotales2 = 0;

    public static int puntosJugador1 = 0;
    public static int puntosJugador2 = 0;

    public static char[][] tablero;
    public static boolean ganador = false;
    public static boolean empate = false;
    public static char turno = 'X';

    // Método principal que inicia el juego
    static void main(String[] args) throws InterruptedException {

        boolean enMenu = true;
        boolean salir = false;

        System.out.println("========================");
        System.out.println("=== TRES EN RAYA ===");
        System.out.println("========================");
        Thread.sleep(1000);

        while (!salir) {
            while (enMenu) {
                mostrarMenu();
                int opcion = src.nextInt();

                switch (opcion) {
                    case 1:
                        prepararPartida();
                        enMenu = true;
                        break;
                    case 2:
                        config();
                        break;
                    case 3:
                        System.out.println("Saliendo...");
                        Thread.sleep(1000);
                        mostrarEstadisticas();
                        enMenu = false;
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción no válida.");
                        break;
                }
                Thread.sleep(500);
            }
        }
        System.out.println("\nGracias por jugar!!!");
    }

    // Muestra el menú principal con las opciones disponibles
    public static void mostrarMenu() {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("1. Jugar Partida");
        System.out.println("2. Configuración");
        System.out.println("3. Salir");
        System.out.print("Elige una opción: ");
    }

    // Permite configurar el tamaño del tablero y las victorias necesarias
    public static void config() throws InterruptedException {
        System.out.println("\n=== CONFIGURACIÓN ===");

        System.out.print("Tamaño del Tablero (Actual: " + tamano + "): ");
        tamano = src.nextInt();

        System.out.print("Victorias para ganar (Actual: " + victoriasNecesarias + "): ");
        victoriasNecesarias = src.nextInt();

        System.out.println("Guardado.");
        Thread.sleep(1000);
    }

    // Muestra las estadísticas totales de victorias de cada jugador
    public static void mostrarEstadisticas() {
        System.out.println("\n=== HISTORIAL ===");
        System.out.println(nombre1 + ": " + victoriasTotales1 + " victorias.");
        System.out.println(nombre2 + ": " + victoriasTotales2 + " victorias.");
    }

    // Prepara y gestiona una partida
    public static void prepararPartida() throws InterruptedException {
        src.nextLine();

        System.out.println("\n=== NOMBRES ===");
        System.out.print("Nombre Jugador 1 (X): ");
        nombre1 = src.nextLine();

        System.out.print("Nombre Jugador 2 (O): ");
        nombre2 = src.nextLine();

        System.out.println("\nComienza la partida entre " + nombre1 + " y " + nombre2);
        System.out.println("Gana el primero en llegar a " + victoriasNecesarias + " victoria(s).");
        Thread.sleep(1500);

        puntosJugador1 = 0;
        puntosJugador2 = 0;

        boolean jugando = true;

        while (jugando) {
            jugarRonda();

            if (puntosJugador1 >= victoriasNecesarias) {
                System.out.println("\n¡" + nombre1 + " ha ganado la partida!");
                victoriasTotales1++;
                jugando = false;
            } else if (puntosJugador2 >= victoriasNecesarias) {
                System.out.println("\n¡" + nombre2 + " ha ganado la partida!");
                victoriasTotales2++;
                jugando = false;
            } else {
                System.out.println("\n=== Marcador ===");
                System.out.println(nombre1 + ": " + puntosJugador1 + " | " + nombre2 + ": " + puntosJugador2);
                System.out.println("Siguiente ronda...");
                Thread.sleep(2000);
            }
        }
    }

    // Ejecuta una ronda completa del juego hasta que haya ganador o empate
    public static void jugarRonda() throws InterruptedException {
        System.out.println("\n=== NUEVA RONDA ===");
        Thread.sleep(1000);

        tablero = new char[tamano][tamano];
        prepararTablero();
        sorteoTurno();

        ganador = false;
        empate = false;
        int casillasOcupadas = 0;
        int totalCasillas = tamano * tamano;

        while (!ganador && !empate) {
            dibujarTablero();

            if (turno == 'X') {
                System.out.println("Turno de: " + nombre1 + " (X)");
            } else {
                System.out.println("Turno de: " + nombre2 + " (O)");
            }

            hacerTurno();
            casillasOcupadas++;

            verificarGanador();

            if (ganador) {
                dibujarTablero();
                if (turno == 'X') {
                    System.out.println(nombre1 + " gana la ronda!");
                    puntosJugador1++;
                } else {
                    System.out.println(nombre2 + " gana la ronda!");
                    puntosJugador2++;
                }
                Thread.sleep(1000);
            } else {
                if (casillasOcupadas == totalCasillas) {
                    dibujarTablero();
                    System.out.println("Empate.");
                    empate = true;
                } else {
                    cambiarTurno();
                }
            }
        }
    }

    // Inicializa el tablero con guiones (casillas vacías)
    public static void prepararTablero() {
        for (int i = 0; i < tamano; i++) {
            for (int j = 0; j < tamano; j++) {
                tablero[i][j] = '-';
            }
        }
    }

    // Elige aleatoriamente quién comienza (X u O)
    public static void sorteoTurno() {
        if (Math.random() < 0.5) {
            turno = 'X';
        } else {
            turno = 'O';
        }
    }

    // Cambia el turno al otro jugador
    public static void cambiarTurno() {
        if (turno == 'X') {
            turno = 'O';
        } else {
            turno = 'X';
        }
    }

    // Dibuja el tablero actual en la consola
    public static void dibujarTablero() {
        System.out.println();
        for (int i = 0; i < tamano; i++) {
            for (int j = 0; j < tamano; j++) {
                System.out.print(" " + tablero[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Pide al jugador que introduzca sus coordenadas y valida la jugada
    public static void hacerTurno() throws InterruptedException {
        int fila;
        int columna;
        boolean valido = false;

        while (!valido) {
            System.out.print("Fila (1-" + tamano + "): ");
            fila = src.nextInt() - 1;

            System.out.print("Columna (1-" + tamano + "): ");
            columna = src.nextInt() - 1;

            if (fila >= 0 && fila < tamano && columna >= 0 && columna < tamano) {
                if (tablero[fila][columna] == '-') {
                    tablero[fila][columna] = turno;
                    valido = true;
                    System.out.println("Marcando casilla...");
                    Thread.sleep(500);
                } else {
                    System.out.println("Casilla ocupada.");
                }
            } else {
                System.out.println("Coordenadas no válidas.");
            }
        }
    }

    // Verifica si el jugador actual ha ganado (filas, columnas o diagonales)
    public static void verificarGanador() {
        // Verificar filas
        for (int i = 0; i < tamano; i++) {
            boolean filaCompleta = true;
            for (int j = 0; j < tamano; j++) {
                if (tablero[i][j] != turno) filaCompleta = false;
            }
            if (filaCompleta) ganador = true;
        }

        // Verificar columnas
        for (int j = 0; j < tamano; j++) {
            boolean colCompleta = true;
            for (int i = 0; i < tamano; i++) {
                if (tablero[i][j] != turno) colCompleta = false;
            }
            if (colCompleta) ganador = true;
        }

        // Verificar diagonal principal
        boolean d1 = true;
        for (int i = 0; i < tamano; i++) {
            if (tablero[i][i] != turno) d1 = false;
        }
        if (d1) ganador = true;

        // Verificar diagonal inversa
        boolean d2 = true;
        for (int i = 0; i < tamano; i++) {
            if (tablero[i][tamano - 1 - i] != turno) d2 = false;
        }
        if (d2) ganador = true;
    }
}