package dao;

import conexion.Conexion;
import modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * =====================================================
 * CLASE: ProductoDAO
 * =====================================================
 * ABSTRACCION:
 * Clase encargada de manejar las operaciones CRUD
 * de la entidad Producto en la base de datos.
 *
 * RESPONSABILIDAD:
 * - Insertar productos
 * - Listar productos
 * - Actualizar productos
 * - Eliminar productos
 *
 * POO:
 * - Encapsulamiento: la lógica SQL queda dentro del DAO
 * - Abstracción: la vista usa métodos simples sin ver SQL
 */
public class ProductoDAO {

    /*
     * =====================================================
     * METODO: insertarProducto
     * =====================================================
     * FUNCION:
     * Inserta un nuevo producto en la base de datos remota.
     */
    public boolean insertarProducto(Producto p) {

        String sql = "INSERT INTO productos(nombre, marca, categoria, precio, cantidad_disponible) VALUES (?,?,?,?,?)";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getMarca());
            ps.setString(3, p.getCategoria());
            ps.setDouble(4, p.getPrecio());
            ps.setInt(5, p.getCantidad());

            ps.executeUpdate();

            System.out.println("✅ Producto insertado");
            return true;

        } catch (Exception e) {
            System.out.println("❌ ERROR insertar: " + e.getMessage());
            return false;
        }
    }

    /*
     * =====================================================
     * METODO: listarProductos
     * =====================================================
     * FUNCION:
     * Obtiene todos los productos registrados en la BD.
     */
    public List<Producto> listarProductos() {

        List<Producto> lista = new ArrayList<>();

        String sql = "SELECT * FROM productos";

        try (Connection conn = Conexion.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                Producto p = new Producto();

                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setMarca(rs.getString("marca"));
                p.setCategoria(rs.getString("categoria"));
                p.setPrecio(rs.getDouble("precio"));
                p.setCantidad(rs.getInt("cantidad_disponible"));

                lista.add(p);
            }

        } catch (Exception e) {
            System.out.println("❌ ERROR listar: " + e.getMessage());
        }

        return lista;
    }

    /*
     * =====================================================
     * METODO: actualizarProducto
     * =====================================================
     * FUNCION:
     * Actualiza un producto existente por su ID.
     */
    public boolean actualizarProducto(Producto p) {

        String sql = "UPDATE productos SET nombre=?, marca=?, categoria=?, precio=?, cantidad_disponible=? WHERE id=?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getMarca());
            ps.setString(3, p.getCategoria());
            ps.setDouble(4, p.getPrecio());
            ps.setInt(5, p.getCantidad());
            ps.setInt(6, p.getId());

            ps.executeUpdate();

            System.out.println("✅ Producto actualizado");
            return true;

        } catch (Exception e) {
            System.out.println("❌ ERROR actualizar: " + e.getMessage());
            return false;
        }
    }

    /*
     * =====================================================
     * METODO: eliminarProducto
     * =====================================================
     * FUNCION:
     * Elimina un producto por su ID.
     */
    public boolean eliminarProducto(int id) {

        String sql = "DELETE FROM productos WHERE id=?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

            System.out.println("✅ Producto eliminado");
            return true;

        } catch (Exception e) {
            System.out.println("❌ ERROR eliminar: " + e.getMessage());
            return false;
        }
    }
}