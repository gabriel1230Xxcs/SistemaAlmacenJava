package vista;

import dao.UsuarioDAO;
import modelo.Usuario;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

/*
 * =====================================================
 * CLASE: GestionUsuarios
 * =====================================================
 * ABSTRACCION:
 * Panel administrativo para gestionar usuarios.
 *
 * FUNCION:
 * - CRUD usuarios
 * - Búsqueda en tiempo real 🔍
 * - Dashboard dinámico 👤
 * - Navegación entre ventanas
 *
 * POO:
 * - Encapsulamiento → atributos privados
 * - Abstracción → uso de DAO
 * - Polimorfismo → uso de MenuPrincipal()
 */
public class GestionUsuarios extends JFrame {

    /*
     * ATRIBUTOS (ENCAPSULAMIENTO)
     */
    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextField txtBuscar;

    // Dashboard
    private JLabel lblTotalUsuarios;
    private JLabel lblUser, lblNombre, lblEmail, lblTelefono;

    public GestionUsuarios() {

        setTitle("Admin Panel - Usuarios");
        setSize(1050, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        /*
         * =====================================================
         * SIDEBAR (MENÚ LATERAL)
         * =====================================================
         */
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(24,24,24));
        sidebar.setPreferredSize(new Dimension(200, getHeight()));
        sidebar.setLayout(new GridLayout(6,1,10,10));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20,10,20,10));

        JButton btnUsuarios = crearBotonSidebar("👤 Usuarios");
        JButton btnProductos = crearBotonSidebar("📦 Productos");
        JButton btnVolver = crearBotonSidebar("⬅ Volver");

        sidebar.add(btnUsuarios);
        sidebar.add(btnProductos);
        sidebar.add(new JLabel());
        sidebar.add(btnVolver);

        add(sidebar, BorderLayout.WEST);

        /*
         * =====================================================
         * PANEL PRINCIPAL
         * =====================================================
         */
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(18,18,18));

        /*
         * HEADER + BUSCADOR 🔍
         */
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(28,28,28));
        header.setBorder(BorderFactory.createEmptyBorder(10,15,10,15));

        JLabel titulo = new JLabel("👥 Gestión de Usuarios");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));

        txtBuscar = new JTextField();
        txtBuscar.setPreferredSize(new Dimension(200,30));

        header.add(titulo, BorderLayout.WEST);
        header.add(txtBuscar, BorderLayout.EAST);

        main.add(header, BorderLayout.NORTH);

        /*
         * =====================================================
         * TABLA
         * =====================================================
         */
        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Usuario");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Telefono");
        modelo.addColumn("Email");

        tabla = new JTable(modelo);
        tabla.setRowHeight(30);
        tabla.setBackground(new Color(30,30,30));
        tabla.setForeground(Color.WHITE);
        tabla.setSelectionBackground(new Color(0,120,215));

        JScrollPane scroll = new JScrollPane(tabla);
        main.add(scroll, BorderLayout.CENTER);

        /*
         * =====================================================
         * DASHBOARD 👤
         * =====================================================
         */
        JPanel derecha = new JPanel(new GridLayout(6,1,10,10));
        derecha.setBackground(new Color(28,28,28));
        derecha.setPreferredSize(new Dimension(220,0));

        lblTotalUsuarios = crearLabel("👤 Usuarios: 0");

        lblUser = crearLabel("Usuario:");
        lblNombre = crearLabel("Nombre:");
        lblTelefono = crearLabel("Teléfono:");
        lblEmail = crearLabel("Email:");

        derecha.add(new JLabel("📊 Dashboard", SwingConstants.CENTER));
        derecha.add(lblTotalUsuarios);
        derecha.add(lblUser);
        derecha.add(lblNombre);
        derecha.add(lblTelefono);
        derecha.add(lblEmail);

        main.add(derecha, BorderLayout.EAST);

        /*
         * =====================================================
         * BOTONES
         * =====================================================
         */
        JPanel acciones = new JPanel();
        acciones.setBackground(new Color(18,18,18));

        JButton btnActualizar = crearBoton("🔄 Actualizar", new Color(0,120,215));
        JButton btnEditar = crearBoton("✏️ Editar", new Color(255,140,0));
        JButton btnEliminar = crearBoton("🗑️ Eliminar", new Color(200,50,50));

        acciones.add(btnActualizar);
        acciones.add(btnEditar);
        acciones.add(btnEliminar);

        main.add(acciones, BorderLayout.SOUTH);

        add(main, BorderLayout.CENTER);

        /*
         * =====================================================
         * EVENTOS
         * =====================================================
         */

        // 🔍 BUSCADOR
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                filtrarTabla(txtBuscar.getText());
            }
        });

        // 📊 DASHBOARD DINÁMICO
        tabla.getSelectionModel().addListSelectionListener(e -> {

            int fila = tabla.getSelectedRow();

            if (fila != -1) {
                lblUser.setText("Usuario: " + tabla.getValueAt(fila,1));
                lblNombre.setText("Nombre: " + tabla.getValueAt(fila,2));
                lblTelefono.setText("Teléfono: " + tabla.getValueAt(fila,4));
                lblEmail.setText("Email: " + tabla.getValueAt(fila,5));
            }
        });

        // 🔄 ACTUALIZAR
        btnActualizar.addActionListener(e -> cargarUsuarios());

        // 🗑 ELIMINAR
        btnEliminar.addActionListener(e -> eliminarUsuario());

        // ✏ EDITAR
        btnEditar.addActionListener(e -> editarUsuario());

        // 📦 IR A PRODUCTOS
        btnProductos.addActionListener(e -> {
            dispose();
            new GestionProductos().setVisible(true);
        });

        // 🔙 VOLVER (🔥 AQUÍ ESTABA EL PROBLEMA)
        btnVolver.addActionListener(e -> {
            dispose();
            new MenuPrincipal().setVisible(true); // ✔ FUNCIONA
        });

        cargarUsuarios();
    }

    /*
     * =====================================================
     * CARGAR USUARIOS
     * =====================================================
     */
    private void cargarUsuarios() {

        modelo.setRowCount(0);

        List<Usuario> lista = new UsuarioDAO().listarUsuarios();

        for (Usuario u : lista) {

            modelo.addRow(new Object[]{
                    u.getId(),
                    u.getUserName(),
                    u.getNombre(),
                    u.getApellido(),
                    u.getTelefono(),
                    u.getEmail()
            });
        }

        lblTotalUsuarios.setText("👤 Usuarios: " + lista.size());
    }

    /*
     * =====================================================
     * BUSCADOR 🔍
     * =====================================================
     */
    private void filtrarTabla(String texto) {

        TableRowSorter<DefaultTableModel> sorter =
                new TableRowSorter<>(modelo);

        tabla.setRowSorter(sorter);

        if (texto.trim().isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
        }
    }

    /*
     * =====================================================
     * ELIMINAR
     * =====================================================
     */
    private void eliminarUsuario() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario");
            return;
        }

        int id = (int) modelo.getValueAt(fila, 0);

        if (new UsuarioDAO().eliminarUsuario(id)) {

            JOptionPane.showMessageDialog(this, "Usuario eliminado");
            cargarUsuarios();
        }
    }

    /*
     * =====================================================
     * METODO: editarUsuario
     * =====================================================
     * Permite modificar los datos de un usuario seleccionado
     *
     * POO:
     * - Encapsulamiento: uso del objeto Usuario
     * - Abstracción: uso del DAO
     */
    private void editarUsuario() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario");
            return;
        }

        // Obtener datos actuales
        int id = (int) modelo.getValueAt(fila, 0);
        String user = modelo.getValueAt(fila, 1).toString();
        String nombre = modelo.getValueAt(fila, 2).toString();
        String apellido = modelo.getValueAt(fila, 3).toString();
        String telefono = modelo.getValueAt(fila, 4).toString();
        String email = modelo.getValueAt(fila, 5).toString();

        // Crear campos editables
        JTextField txtUser = new JTextField(user);
        JTextField txtNombre = new JTextField(nombre);
        JTextField txtApellido = new JTextField(apellido);
        JTextField txtTelefono = new JTextField(telefono);
        JTextField txtEmail = new JTextField(email);

        JPanel panel = new JPanel(new GridLayout(5,2));

        panel.add(new JLabel("Usuario:"));
        panel.add(txtUser);

        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);

        panel.add(new JLabel("Apellido:"));
        panel.add(txtApellido);

        panel.add(new JLabel("Teléfono:"));
        panel.add(txtTelefono);

        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);

        int opcion = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Editar Usuario",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (opcion == JOptionPane.OK_OPTION) {

            Usuario u = new Usuario();

            u.setId(id);
            u.setUserName(txtUser.getText());
            u.setNombre(txtNombre.getText());
            u.setApellido(txtApellido.getText());
            u.setTelefono(txtTelefono.getText());
            u.setEmail(txtEmail.getText());

            // 🔥 LLAMAR AL DAO (IMPORTANTE)
            if (new UsuarioDAO().actualizarUsuario(u)) {

                JOptionPane.showMessageDialog(this, "Usuario actualizado");

                cargarUsuarios();

            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar");
            }
        }
    }

    /*
     * =====================================================
     * ESTILOS
     * =====================================================
     */
    private JButton crearBoton(String txt, Color color) {
        JButton btn = new JButton(txt);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        return btn;
    }

    private JButton crearBotonSidebar(String txt) {
        JButton btn = new JButton(txt);
        btn.setBackground(new Color(40,40,40));
        btn.setForeground(Color.WHITE);
        return btn;
    }

    private JLabel crearLabel(String txt) {
        JLabel lbl = new JLabel(txt);
        lbl.setForeground(Color.WHITE);
        return lbl;
    }
}