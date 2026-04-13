package modelo;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;

/*
 * =====================================================
 * CLASE: Seguridad
 * =====================================================
 * ABSTRACCION:
 * Esta clase centraliza toda la lógica de seguridad del sistema.
 *
 * RESPONSABILIDAD:
 * - Encriptar contraseñas (SHA-256)
 * - Validar contraseñas seguras
 * - Evitar uso de contraseñas débiles
 *
 * POO:
 * - Encapsulamiento: métodos internos controlados
 * - Abstracción: oculta la complejidad de seguridad
 * - Reutilización: se puede usar en login y registro
 */
public class Seguridad {

    /*
     * LISTA DE CONTRASEÑAS PROHIBIDAS
     */
    private static final List<String> clavesDebiles = Arrays.asList(
            "1234", "123456", "password", "admin", "12345"
    );

    /*
     * =====================================================
     * METODO: esPasswordSegura
     * =====================================================
     * Valida que la contraseña cumpla con requisitos de seguridad
     */
    public static boolean esPasswordSegura(String password) {

        if (password == null) return false;

        // Mínimo 8 caracteres
        if (password.length() < 8) return false;

        // Evitar contraseñas comunes
        if (clavesDebiles.contains(password.toLowerCase())) return false;

        boolean tieneMayuscula = false;
        boolean tieneNumero = false;
        boolean tieneSimbolo = false;

        /*
         * Recorremos cada carácter
         */
        for (char c : password.toCharArray()) {

            if (Character.isUpperCase(c)) tieneMayuscula = true;
            if (Character.isDigit(c)) tieneNumero = true;

            if ("@#$%&*!".indexOf(c) >= 0) {
                tieneSimbolo = true;
            }
        }

        return tieneMayuscula && tieneNumero && tieneSimbolo;
    }

    /*
     * =====================================================
     * METODO: encriptar
     * =====================================================
     * Convierte la contraseña en un hash SHA-256
     */
    public static String encriptar(String password) {

        try {

            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] hash = md.digest(password.getBytes());

            StringBuilder hex = new StringBuilder();

            for (byte b : hash) {

                String h = Integer.toHexString(0xff & b);

                if (h.length() == 1) hex.append('0');

                hex.append(h);
            }

            return hex.toString();

        } catch (Exception e) {

            throw new RuntimeException("Error al encriptar contraseña", e);
        }
    }

    /*
     * =====================================================
     * METODO: validarPasswordLogin
     * =====================================================
     * Compara contraseña ingresada con la almacenada
     */
    public static boolean validarPasswordLogin(String inputPassword, String passwordBD) {

        String hashInput = encriptar(inputPassword);

        return hashInput.equals(passwordBD);
    }

    /*
     * =====================================================
     * MENSAJE DE ERROR DE SEGURIDAD
     * =====================================================
     */
    public static String mensajeError() {
        return "La contraseña debe tener:\n" +
               "- Mínimo 8 caracteres\n" +
               "- 1 letra mayúscula\n" +
               "- 1 número\n" +
               "- 1 símbolo (@#$%)\n" +
               "- No usar claves débiles como 1234";
    }
}