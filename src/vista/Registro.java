package vista;

import dao.UsuarioDAO;
import modelo.Usuario;
import modelo.Seguridad; // 🔥 IMPORTANTE

import javax.swing.*;
import java.awt.*;

/*
 * =====================================================
 * CLASE: Registro
 * =====================================================
 * ABSTRACCION:
 * Interfaz para registrar usuarios con seguridad avanzada.
 *
 * POO:
 * - Encapsulamiento
 * - Abstracción (DAO)
 * - Validación de negocio
 */
public class Registro extends JFrame {

    private JTextField txtUser, txtNombre, txtApellido, txtTelefono, txtEmail;
    private JPasswordField txtPassword, txtConfirmar;
    private JCheckBox chkMostrar;
    private JButton btnRegistrar, btnLimpiar, btnCerrar;

    public Registro() {

        setTitle("Registro de Usuario");
        setSize(460, 470);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(24, 24, 24));

        JLabel titulo = new JLabel("CREAR CUENTA");
        titulo.setBounds(150, 20, 200, 30);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        panel.add(titulo);

        txtUser = crearCampo(panel, "Usuario:", 80);
        txtNombre = crearCampo(panel, "Nombre:", 120);
        txtApellido = crearCampo(panel, "Apellido:", 160);
        txtTelefono = crearCampo(panel, "Teléfono:", 200);
        txtEmail = crearCampo(panel, "Email:", 240);

        txtPassword = crearPassword(panel, "Contraseña:", 280);
        txtConfirmar = crearPassword(panel, "Confirmar:", 320);

        chkMostrar = new JCheckBox("Mostrar contraseñas");
        chkMostrar.setBounds(150, 350, 200, 20);
        chkMostrar.setForeground(Color.WHITE);
        chkMostrar.setBackground(new Color(24,24,24));
        panel.add(chkMostrar);

        btnRegistrar = new JButton("Registrar");
        estiloBoton(btnRegistrar, new Color(0,153,51));
        btnRegistrar.setBounds(40, 390, 120, 35);

        btnLimpiar = new JButton("Limpiar");
        estiloBoton(btnLimpiar, new Color(0,120,215));
        btnLimpiar.setBounds(170, 390, 120, 35);

        btnCerrar = new JButton("Cerrar");
        estiloBoton(btnCerrar, new Color(200,50,50));
        btnCerrar.setBounds(300, 390, 100, 35);

        panel.add(btnRegistrar);
        panel.add(btnLimpiar);
        panel.add(btnCerrar);

        add(panel);

        chkMostrar.addActionListener(e -> togglePassword());
        btnRegistrar.addActionListener(e -> registrar());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnCerrar.addActionListener(e -> dispose());
    }

    private JTextField crearCampo(JPanel panel, String texto, int y) {
        JLabel lbl = new JLabel(texto);
        lbl.setBounds(40, y, 100, 25);
        lbl.setForeground(Color.WHITE);
        panel.add(lbl);

        JTextField txt = new JTextField();
        txt.setBounds(150, y, 240, 28);
        panel.add(txt);

        return txt;
    }

    private JPasswordField crearPassword(JPanel panel, String texto, int y) {
        JLabel lbl = new JLabel(texto);
        lbl.setBounds(40, y, 100, 25);
        lbl.setForeground(Color.WHITE);
        panel.add(lbl);

        JPasswordField txt = new JPasswordField();
        txt.setBounds(150, y, 240, 28);
        panel.add(txt);

        return txt;
    }

    private void estiloBoton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
    }

    private void togglePassword() {
        char echo = chkMostrar.isSelected() ? (char) 0 : '*';
        txtPassword.setEchoChar(echo);
        txtConfirmar.setEchoChar(echo);
    }

    private void limpiarCampos() {
        txtUser.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
        txtConfirmar.setText("");
    }

    /*
     * =====================================================
     * REGISTRO CON SEGURIDAD 🔐
     * =====================================================
     */
    private void registrar() {

        String user = txtUser.getText().trim();
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();
        String pass = new String(txtPassword.getPassword()).trim();
        String conf = new String(txtConfirmar.getPassword()).trim();

        /*
         * VALIDACION BASICA
         */
        /*
         * =====================================================
         * VALIDACION DE CAMPOS (MEJORADA)
         * =====================================================
         */
        if (user.isEmpty()) {
            mostrar("Debe ingresar el usuario", "Campo requerido", JOptionPane.WARNING_MESSAGE);
            txtUser.requestFocus();
            return;
        }

        if (nombre.isEmpty()) {
            mostrar("Debe ingresar el nombre", "Campo requerido", JOptionPane.WARNING_MESSAGE);
            txtNombre.requestFocus();
            return;
        }

        if (apellido.isEmpty()) {
            mostrar("Debe ingresar el apellido", "Campo requerido", JOptionPane.WARNING_MESSAGE);
            txtApellido.requestFocus();
            return;
        }

        if (telefono.isEmpty()) {
            mostrar("Debe ingresar el teléfono", "Campo requerido", JOptionPane.WARNING_MESSAGE);
            txtTelefono.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            mostrar("Debe ingresar el email", "Campo requerido", JOptionPane.WARNING_MESSAGE);
            txtEmail.requestFocus();
            return;
        }

        if (pass.isEmpty()) {
            mostrar("Debe ingresar la contraseña", "Campo requerido", JOptionPane.WARNING_MESSAGE);
            txtPassword.requestFocus();
            return;
        }

        if (conf.isEmpty()) {
            mostrar("Debe confirmar la contraseña", "Campo requerido", JOptionPane.WARNING_MESSAGE);
            txtConfirmar.requestFocus();
            return;
        }

        /*
         * 🔥 VALIDACION DE SEGURIDAD (NUEVO)
         */
        if (!Seguridad.esPasswordSegura(pass)) {

            JOptionPane.showMessageDialog(this,
                    Seguridad.mensajeError(),
                    "Contraseña insegura",
                    JOptionPane.WARNING_MESSAGE);

            return;
        }

        /*
         * VALIDAR CONFIRMACION
         */
        
        /*
         * =====================================================
         * VALIDACION DE CONTRASEÑA (VISUAL PRO)
         * =====================================================
         */
        
        if (!pass.equals(conf)) {

            // 🔴 Bordes rojos
            txtPassword.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            txtConfirmar.setBorder(BorderFactory.createLineBorder(Color.RED, 2));

            JOptionPane.showMessageDialog(
                    this,
                    "❌ Las contraseñas no coinciden",
                    "Error de contraseña",
                    JOptionPane.ERROR_MESSAGE
            );

            txtConfirmar.setText("");
            txtConfirmar.requestFocus();
            return;
        }

        // ✅ AQUÍ VA (FUERA DEL IF 🔥)
        txtPassword.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        txtConfirmar.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        /*
         * CREACION DEL OBJETO
         */
        Usuario u = new Usuario();
        u.setUserName(user);
        u.setNombre(nombre);
        u.setApellido(apellido);
        u.setTelefono(telefono);
        u.setEmail(email);
        u.setPassword(pass);

        UsuarioDAO dao = new UsuarioDAO();

        if (dao.registrarUsuario(u)) {

            mostrar("Usuario registrado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            dispose();

        } else {

            mostrar("Error al registrar usuario", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrar(String msg, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, msg, titulo, tipo);
    }
}