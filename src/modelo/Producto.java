package modelo;

/*
 * =====================================================
 * CLASE: Producto
 * =====================================================
 * ABSTRACCION:
 * Representa un producto dentro del sistema de almacén.
 *
 * RESPONSABILIDAD:
 * - Almacenar los datos de un producto
 *
 * POO:
 * ✔ Encapsulamiento → atributos privados
 * ✔ Abstracción → representación del objeto producto
 * ✔ Polimorfismo → método toString
 */
public class Producto {

    /*
     * =====================================================
     * ATRIBUTOS PRIVADOS (ENCAPSULAMIENTO)
     * =====================================================
     */
    private int id;
    private String nombre;
    private String marca;       // 🔥 NUEVO
    private String categoria;   // 🔥 NUEVO
    private double precio;
    private int cantidad;

    /*
     * =====================================================
     * CONSTRUCTOR VACÍO
     * =====================================================
     */
    public Producto() {}

    /*
     * =====================================================
     * GETTERS Y SETTERS
     * =====================================================
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // =====================
    // NOMBRE
    // =====================
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // =====================
    // MARCA (NUEVO)
    // =====================
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    // =====================
    // CATEGORÍA (NUEVO)
    // =====================
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    // =====================
    // PRECIO
    // =====================
    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    // =====================
    // CANTIDAD
    // =====================
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /*
     * =====================================================
     * METODO toString (POLIMORFISMO)
     * =====================================================
     * Representación del objeto como texto
     */
    @Override
    public String toString() {
        return nombre + " - RD$" + precio;
    }
}