package conexion;

import java.sql.Connection;
import java.sql.DriverManager;

/*
 * ================================
 * CLASE: Conexion
 * ================================
 * ABSTRACCION:
 * Gestiona la conexión a la base de datos remota.
 */
public class Conexion {

    /*
     * =====================================================
     * CONEXION A BASE DE DATOS REMOTA (PROFESOR)
     * =====================================================
     */
    private static final String URL =
        "jdbc:mysql://almacenitla-db-itla-3837.e.aivencloud.com:25037/almacenitlafinal?useSSL=true&requireSSL=true&serverTimezone=UTC";

    private static final String USER = "avnadmin";
    private static final String PASSWORD = "";

    /*
     * =====================================================
     * METODO: conectar
     * =====================================================
     */
    public static Connection conectar() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("✅ Conexion remota exitosa");

            return conn;

        } catch (Exception e) {

            System.out.println("❌ Error de conexion: " + e.getMessage());

            return null;
        }
    }
}