package vista;

import javax.swing.*;
import java.awt.*;

/*
 * =====================================================
 * CLASE: PantallaCierre
 * =====================================================
 * ABSTRACCION:
 * Pantalla final al cerrar la aplicación.
 *
 * FUNCION:
 * - Mostrar animación de salida
 * - Finalizar programa
 *
 * POO:
 * - Encapsulamiento
 * - Separación de lógica de cierre
 */
public class PantallaCierre extends JFrame {

    private JLabel lblEstado;

    public PantallaCierre() {

        setUndecorated(true);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(18,18,18));

        JLabel titulo = new JLabel("👋 Cerrando sistema...", SwingConstants.CENTER);
        titulo.setBounds(50, 40, 300, 30);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));

        lblEstado = new JLabel("Guardando datos...");
        lblEstado.setBounds(120, 90, 200, 20);
        lblEstado.setForeground(Color.LIGHT_GRAY);

        panel.add(titulo);
        panel.add(lblEstado);

        add(panel);

        iniciarCierre();
    }

    private void iniciarCierre() {

        new Thread(() -> {

            try {

                Thread.sleep(800);
                lblEstado.setText("Cerrando módulos...");

                Thread.sleep(800);
                lblEstado.setText("Hasta luego 👋");

                Thread.sleep(800);

                System.exit(0);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();
    }
}