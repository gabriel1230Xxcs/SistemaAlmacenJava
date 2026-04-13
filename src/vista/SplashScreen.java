package vista;

import javax.swing.*;
import java.awt.*;

/*
 * =====================================================
 * CLASE: SplashScreen
 * =====================================================
 * ABSTRACCION:
 * Pantalla de carga inicial del sistema.
 *
 * FUNCION:
 * - Simular carga del sistema
 * - Mostrar progreso visual
 * - Redirigir al login o menú
 *
 * POO:
 * - Encapsulamiento: atributos privados
 * - Abstracción: lógica de carga separada
 */
public class SplashScreen extends JFrame {

    private JProgressBar barra;
    private JLabel lblEstado;

    public SplashScreen() {

        setUndecorated(true);
        setSize(500, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(18,18,18));

        JLabel titulo = new JLabel("🚀 SISTEMA DE ALMACÉN", SwingConstants.CENTER);
        titulo.setBounds(50, 60, 400, 40);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));

        lblEstado = new JLabel("Iniciando...");
        lblEstado.setBounds(150, 120, 250, 20);
        lblEstado.setForeground(Color.LIGHT_GRAY);

        barra = new JProgressBar();
        barra.setBounds(100, 160, 300, 25);
        barra.setForeground(new Color(0,120,215));

        panel.add(titulo);
        panel.add(lblEstado);
        panel.add(barra);

        add(panel);

        iniciarCarga();
    }

    private void iniciarCarga() {

        new Thread(() -> {

            try {
                for (int i = 0; i <= 100; i++) {

                    barra.setValue(i);

                    if (i < 30) lblEstado.setText("Cargando módulos...");
                    else if (i < 60) lblEstado.setText("Conectando BD...");
                    else if (i < 90) lblEstado.setText("Iniciando interfaz...");
                    else lblEstado.setText("Listo...");

                    Thread.sleep(40);
                }

                dispose();

                // 🔥 AQUÍ DECIDES A DÓNDE IR
                new Login().setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();
    }
}