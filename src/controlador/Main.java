package controlador;

import conexion.Conexion;
import vista.SplashScreen; // 🔥 IMPORTANTE: importar la pantalla de carga

/*
 * ==========================================
 * CLASE PRINCIPAL DEL SISTEMA
 * ==========================================
 * ABSTRACCION:
 * Esta clase representa el punto de inicio del sistema.
 * Desde aqui se ejecuta el programa.
 */
public class Main {

    /*
     * METODO PRINCIPAL:
     * Es el punto de entrada del programa en Java.
     */
    public static void main(String[] args) {

        /*
         * ABSTRACCION:
         * Utilizamos la clase Conexion sin preocuparnos
         * por como se realiza internamente la conexion.
         */
        Conexion.conectar();

        /*
         * Este metodo prueba si la conexion a la base de datos funciona correctamente.
         */

        /*
         * =====================================================
         * INICIO DEL SISTEMA (SPLASH SCREEN 🚀)
         * =====================================================
         * Se muestra una pantalla de carga antes de iniciar
         * la aplicación principal.
         *
         * POO:
         * - Encapsulamiento: SplashScreen maneja su lógica interna
         * - Abstracción: solo llamamos la vista
         */
        new SplashScreen().setVisible(true);
    }
}