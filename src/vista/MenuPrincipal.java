package vista;

import javax.swing.*;
import java.awt.*;
import vista.PantallaCierre; // 🔥 IMPORTANTE

/*
 * =====================================================
 * CLASE: MenuPrincipal
 * =====================================================
 * ABSTRACCION:
 * Representa el panel principal del sistema (Dashboard).
 *
 * RESPONSABILIDAD:
 * - Mostrar las opciones principales del sistema
 * - Permitir la navegación entre módulos
 *
 * POO:
 * ✔ Encapsulamiento → atributo privado nombreUsuario
 * ✔ Abstracción → interacción con otras vistas
 * ✔ Polimorfismo → sobrecarga de constructores
 */
public class MenuPrincipal extends JFrame {

    /*
     * =====================================================
     * ATRIBUTO PRIVADO (ENCAPSULAMIENTO)
     * =====================================================
     */
    private String nombreUsuario;

    /*
     * =====================================================
     * CONSTRUCTOR VACÍO (POLIMORFISMO 🔥)
     * =====================================================
     */
    public MenuPrincipal() {
        this("Usuario");
    }

    /*
     * =====================================================
     * CONSTRUCTOR CON PARÁMETRO
     * =====================================================
     */
    public MenuPrincipal(String nombre) {

        this.nombreUsuario = nombre;

        setTitle("Sistema de Almacen - Dashboard");
        setSize(600, 450);
        setLocationRelativeTo(null);

        // 🔥 CORRECTO (NO CIERRA TODA LA APP)
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        /*
         * =====================================================
         * PANEL PRINCIPAL
         * =====================================================
         */
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(18,18,18));

        /*
         * CONTENEDOR CENTRAL
         */
        JPanel card = new JPanel(null);
        card.setBounds(70, 40, 460, 320);
        card.setBackground(new Color(28,28,28));
        panel.add(card);

        /*
         * TITULO
         */
        JLabel titulo = new JLabel("SISTEMA DE ALMACEN");
        titulo.setBounds(110, 20, 300, 30);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        card.add(titulo);

        /*
         * BIENVENIDA
         */
        JLabel bienvenida = new JLabel("Bienvenido, " + nombreUsuario);
        bienvenida.setBounds(150, 50, 250, 20);
        bienvenida.setForeground(Color.LIGHT_GRAY);
        card.add(bienvenida);

        /*
         * BOTONES
         */
        JButton btnUsuarios = crearCard("Usuarios", "👤", 80);
        JButton btnProductos = crearCard("Productos", "📦", 260);

        card.add(btnUsuarios);
        card.add(btnProductos);

        /*
         * BOTON SALIR
         */
        JButton btnSalir = new JButton("✖ Cerrar Sesión");
        btnSalir.setBounds(160, 260, 150, 35);
        btnSalir.setBackground(new Color(200,50,50));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);
        btnSalir.setFont(new Font("Segoe UI", Font.BOLD, 12));
        card.add(btnSalir);

        add(panel);

        /*
         * =====================================================
         * EVENTOS
         * =====================================================
         */

        // Usuarios
        btnUsuarios.addActionListener(e -> {
            dispose();
            new GestionUsuarios().setVisible(true);
        });

        // Productos
        btnProductos.addActionListener(e -> {
            dispose();
            new GestionProductos().setVisible(true);
        });

        /*
         * =====================================================
         * 🔥 CIERRE PROFESIONAL (AQUÍ ESTÁ EL CAMBIO)
         * =====================================================
         */
        btnSalir.addActionListener(e -> {

            dispose(); // cerrar menú

            new Login().setVisible(true); // vuelve al login

        });

        /*
         * EFECTOS
         */
        efectoHover(btnUsuarios);
        efectoHover(btnProductos);
        efectoHover(btnSalir);
    }

    /*
     * =====================================================
     * CREAR BOTONES
     * =====================================================
     */
    private JButton crearCard(String texto, String emoji, int x) {

        JButton btn = new JButton(
            "<html><center style='font-size:40px'>" + emoji +
            "<br><span style='font-size:14px'>" + texto + "</span></center></html>"
        );

        btn.setBounds(x, 90, 150, 130);
        btn.setBackground(new Color(220,220,220));
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);

        return btn;
    }

    /*
     * =====================================================
     * EFECTO HOVER
     * =====================================================
     */
    private void efectoHover(JButton btn) {

        btn.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(180,180,180));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(220,220,220));
            }
        });
    }
}