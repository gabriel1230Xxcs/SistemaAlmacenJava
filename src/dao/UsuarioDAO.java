package dao;

import conexion.Conexion;
import modelo.Usuario;
import modelo.Seguridad;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * =====================================================
 * CLASE: UsuarioDAO
 * =====================================================
 * ABSTRACCION:
 * Encapsula el acceso a la base de datos para la entidad Usuario.
 *
 * FUNCION:
 * - Registrar usuarios
 * - Validar login
 * - Listar usuarios
 * - Eliminar usuarios
 * - Actualizar usuarios
 *
 * POO:
 * - Uso de objetos Usuario
 * - Separacion de responsabilidades (DAO)
 */
public class UsuarioDAO {

    /*
     * =====================================================
     * METODO: registrarUsuario
     * =====================================================
     */
    public boolean registrarUsuario(Usuario user) {

        String sql = "INSERT INTO usuarios(userName,nombre,apellido,telefono,email,password) VALUES (?,?,?,?,?,?)";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUserName());
            ps.setString(2, user.getNombre());
            ps.setString(3, user.getApellido());
            ps.setString(4, user.getTelefono());
            ps.setString(5, user.getEmail());

            // Encriptar contraseña
            ps.setString(6, Seguridad.encriptar(user.getPassword()));

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Error al registrar: " + e.getMessage());
            return false;
        }
    }

    /*
     * =====================================================
     * METODO: login
     * =====================================================
     */
    public Usuario login(String userName, String password) {

        String sql = "SELECT * FROM usuarios WHERE userName=? AND password=?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userName);
            ps.setString(2, Seguridad.encriptar(password));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Usuario u = new Usuario();

                u.setId(rs.getInt("idUser"));
                u.setUserName(rs.getString("userName"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setTelefono(rs.getString("telefono"));
                u.setEmail(rs.getString("email"));

                return u;
            }

        } catch (Exception e) {
            System.out.println("Error login: " + e.getMessage());
        }

        return null;
    }

    /*
     * =====================================================
     * METODO: listarUsuarios
     * =====================================================
     */
    public List<Usuario> listarUsuarios() {

        List<Usuario> lista = new ArrayList<>();

        String sql = "SELECT * FROM usuarios";

        try (Connection conn = Conexion.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                Usuario u = new Usuario();

                u.setId(rs.getInt("idUser"));
                u.setUserName(rs.getString("userName"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setTelefono(rs.getString("telefono"));
                u.setEmail(rs.getString("email"));

                lista.add(u);
            }

        } catch (Exception e) {
            System.out.println("Error al listar: " + e.getMessage());
        }

        return lista;
    }
    
    /*
     * =====================================================
     * METODO: actualizarUsuario
     * =====================================================
     * Actualiza un usuario en la base de datos
     */
    public boolean actualizarUsuario(Usuario u) {

        String sql = "UPDATE usuarios SET userName=?, nombre=?, apellido=?, telefono=?, email=? WHERE idUser=?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getUserName());
            ps.setString(2, u.getNombre());
            ps.setString(3, u.getApellido());
            ps.setString(4, u.getTelefono());
            ps.setString(5, u.getEmail());
            ps.setInt(6, u.getId());

            ps.executeUpdate();

            return true;

        } catch (Exception e) {
            System.out.println("Error actualizar: " + e.getMessage());
            return false;
        }
    }
    
    

    /*
     * =====================================================
     * METODO: eliminarUsuario
     * =====================================================
     */
    public boolean eliminarUsuario(int id) {

        String sql = "DELETE FROM usuarios WHERE idUser = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

            return true;

        } catch (Exception e) {
            System.out.println("Error al eliminar: " + e.getMessage());
            return false;
        }
    }

    /*
     * =====================================================
     * METODO: actualizarUsuario
     * =====================================================
     * Actualiza todos los datos del usuario
     */
    public boolean actualizarUsuario(int id, String user,
                                     String nombre, String apellido,
                                     String telefono, String email) {

        String sql = "UPDATE usuarios SET userName=?, nombre=?, apellido=?, telefono=?, email=? WHERE idUser=?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user);
            ps.setString(2, nombre);
            ps.setString(3, apellido);
            ps.setString(4, telefono);
            ps.setString(5, email);
            ps.setInt(6, id);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Error actualizar: " + e.getMessage());
            return false;
        }
    }
}