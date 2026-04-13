package modelo;

/*
 * =====================================================
 * CLASE: Usuario
 * =====================================================
 * ABSTRACCION:
 * Representa un usuario del sistema.
 *
 * RESPONSABILIDAD:
 * - Almacenar datos del usuario
 * - Proveer acceso controlado a los atributos
 *
 * POO:
 * - Encapsulamiento: atributos privados
 * - Uso de getters y setters
 * - Representación de entidad (modelo)
 */
public class Usuario {

    /*
     * =====================================================
     * ATRIBUTOS (ENCAPSULAMIENTO)
     * =====================================================
     * Se declaran privados para proteger los datos
     */
    private int id;
    private String userName;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private String password;

    /*
     * =====================================================
     * CONSTRUCTOR VACIO
     * =====================================================
     * Permite crear objetos sin inicializar valores
     */
    public Usuario() {
    }

    /*
     * =====================================================
     * CONSTRUCTOR COMPLETO
     * =====================================================
     * Permite crear un usuario con todos sus datos
     */
    public Usuario(int id, String userName, String nombre,
                   String apellido, String telefono,
                   String email, String password) {

        this.id = id;
        this.userName = userName;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
    }

    /*
     * =====================================================
     * GETTERS Y SETTERS
     * =====================================================
     * Permiten acceder y modificar los datos de forma segura
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /*
     * SEGURIDAD:
     * La contraseña no debería mostrarse directamente
     */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*
     * =====================================================
     * METODO toString (POLIMORFISMO)
     * =====================================================
     * Representa el objeto como texto
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}