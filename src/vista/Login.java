package vista;

import dao.UsuarioDAO;
import modelo.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/*
 * =====================================================
 * CLASE: Login
 * =====================================================
 * ABSTRACCION:
 * Interfaz gráfica de autenticación del sistema.
 *
 * RESPONSABILIDAD:
 * - Capturar credenciales
 * - Validar datos
 * - Autenticar con DAO
 * - Navegar al menú
 *
 * POO:
 * ✔ Encapsulamiento
 * ✔ Abstracción
 */
public class Login extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JCheckBox chkMostrar;

    public Login() {

        setTitle("Sistema de Almacen");
        setSize(420, 420);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(18,18,18));

        JPanel card = new JPanel(null);
        card.setBounds(40, 30, 320, 330);
        card.setBackground(new Color(28,28,28));
        card.setBorder(new EmptyBorder(10,10,10,10));
        panel.add(card);

        JLabel titulo = new JLabel("INICIAR SESIÓN");
        titulo.setBounds(70, 15, 200, 30);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        card.add(titulo);

        JLabel lblUser = new JLabel("Usuario");
        lblUser.setBounds(40, 50, 100, 20);
        lblUser.setForeground(Color.GRAY);
        card.add(lblUser);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(40, 70, 240, 30);
        card.add(txtUsuario);

        JLabel lblPass = new JLabel("Contraseña");
        lblPass.setBounds(40, 110, 100, 20);
        lblPass.setForeground(Color.GRAY);
        card.add(lblPass);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(40, 130, 240, 30);
        card.add(txtPassword);

        chkMostrar = new JCheckBox("Mostrar contraseña");
        chkMostrar.setBounds(40, 165, 200, 20);
        chkMostrar.setForeground(Color.WHITE);
        chkMostrar.setBackground(new Color(28,28,28));
        card.add(chkMostrar);

        JButton btnLogin = new JButton("Entrar");
        btnLogin.setBounds(40, 190, 240, 35);
        estiloBoton(btnLogin, new Color(0,120,215));
        card.add(btnLogin);

        JButton btnRegistro = new JButton("Crear cuenta");
        btnRegistro.setBounds(40, 235, 240, 30);
        estiloBoton(btnRegistro, new Color(0,153,51));
        card.add(btnRegistro);

        JButton btnSalir = new JButton("✖ Cerrar aplicación");
        btnSalir.setBounds(40, 270, 240, 28);
        estiloBoton(btnSalir, new Color(200,50,50));
        card.add(btnSalir);

        add(panel);

        /*
         * EVENTOS
         */

        // Mostrar contraseña
        chkMostrar.addActionListener(e -> {
            txtPassword.setEchoChar(chkMostrar.isSelected() ? (char)0 : '*');
        });

        // LOGIN
        btnLogin.addActionListener(e -> autenticar());

        // REGISTRO
        btnRegistro.addActionListener(e -> new Registro().setVisible(true));

        // SALIR
        btnSalir.addActionListener(e -> {
            dispose();
            new PantallaCierre().setVisible(true);
        });

        getRootPane().setDefaultButton(btnLogin);
    }

    /*
     * =====================================================
     * METODO: autenticar
     * =====================================================
     */
    private void autenticar() {

        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        /*
         * =====================================================
         * VALIDACION DE CAMPOS (RÚBRICA)
         * =====================================================
         */
        if (usuario.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar su usuario y contraseña, si no está registrado debe registrarse",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            txtUsuario.requestFocus();
            return;
        }

        /*
         * =====================================================
         * AUTENTICACION (POO + DAO)
         * =====================================================
         */
        UsuarioDAO dao = new UsuarioDAO();
        Usuario user = dao.login(usuario, password);

        if (user != null) {

            JOptionPane.showMessageDialog(
                    this,
                    "✅ Bienvenido " + user.getNombre()
            );

            new MenuPrincipal(user.getNombre()).setVisible(true);
            dispose();

        } else {

            /*
             * ERROR DE LOGIN
             */
            JOptionPane.showMessageDialog(
                    this,
                    "❌ Usuario o contraseña incorrectos",
                    "Error de acceso",
                    JOptionPane.ERROR_MESSAGE
            );

            txtPassword.setText("");
            txtPassword.requestFocus();
        }
    }

    /*
     * ESTILO BOTON
     */
    private void estiloBoton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
    }

    /*
     * MAIN
     */
    public static void main(String[] args) {
        new Login().setVisible(true);
    }
}