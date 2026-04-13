package vista;

import dao.ProductoDAO;
import modelo.Producto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/*
 * =====================================================
 * CLASE: GestionProductos
 * =====================================================
 * ABSTRACCION:
 * Interfaz gráfica para gestionar productos del sistema.
 *
 * RESPONSABILIDAD:
 * - Mostrar productos
 * - Permitir CRUD de productos
 * - Mostrar dashboard
 * - Mostrar gráficos
 * - Volver al menú principal
 *
 * POO:
 * ✔ Encapsulamiento → atributos privados
 * ✔ Abstracción → uso del DAO para datos
 * ✔ Modularidad → métodos separados por función
 */
public class GestionProductos extends JFrame {

    /*
     * =====================================================
     * ATRIBUTOS PRIVADOS
     * =====================================================
     */
    private JTable tabla;
    private DefaultTableModel modelo;

    private JLabel lblNombre, lblPrecio, lblCantidad, lblSubtotal;
    private JLabel lblTotal, lblCantidadProductos;

    private JTextField txtBuscar;

    /*
     * Formato moneda RD$
     */
    private NumberFormat formato =
            NumberFormat.getCurrencyInstance(new Locale("es", "DO"));

    /*
     * =====================================================
     * CONSTRUCTOR
     * =====================================================
     */
    public GestionProductos() {

        setTitle("📦 Gestión de Productos PRO");
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(18, 18, 18));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        /*
         * =====================================================
         * BUSCADOR
         * =====================================================
         */
        JPanel top = new JPanel();
        top.setBackground(new Color(18, 18, 18));

        txtBuscar = new JTextField(20);
        JLabel lblBuscar = new JLabel("🔍 Buscar:");
        lblBuscar.setForeground(Color.WHITE);

        top.add(lblBuscar);
        top.add(txtBuscar);

        panel.add(top, BorderLayout.NORTH);

        /*
         * =====================================================
         * TABLA
         * =====================================================
         */
        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Marca");
        modelo.addColumn("Categoría");
        modelo.addColumn("Precio");
        modelo.addColumn("Cantidad");

        tabla = new JTable(modelo);
        tabla.setBackground(new Color(30, 30, 30));
        tabla.setForeground(Color.WHITE);
        tabla.setRowHeight(30);

        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);

        /*
         * =====================================================
         * DASHBOARD DERECHO
         * =====================================================
         */
        JPanel derecha = new JPanel(new GridLayout(8, 1, 10, 10));
        derecha.setBackground(new Color(28, 28, 28));

        lblNombre = crearLabel("Nombre:");
        lblPrecio = crearLabel("Precio:");
        lblCantidad = crearLabel("Cantidad:");
        lblSubtotal = crearLabel("Subtotal: RD$0");

        lblCantidadProductos = crearLabel("📦 Productos: 0");
        lblTotal = crearLabel("💰 Total: RD$0");

        derecha.add(lblNombre);
        derecha.add(lblPrecio);
        derecha.add(lblCantidad);
        derecha.add(lblSubtotal);
        derecha.add(lblCantidadProductos);
        derecha.add(lblTotal);

        panel.add(derecha, BorderLayout.EAST);

        /*
         * =====================================================
         * BOTONES
         * =====================================================
         */
        JPanel botones = new JPanel();

        JButton btnAgregar = crearBoton("➕ Agregar", new Color(0, 153, 51));
        JButton btnEditar = crearBoton("✏ Editar", new Color(255, 140, 0));
        JButton btnEliminar = crearBoton("🗑 Eliminar", new Color(200, 50, 50));
        JButton btnActualizar = crearBoton("🔄 Actualizar", new Color(0, 120, 215));
        JButton btnGrafico = crearBoton("📊 Gráfico", new Color(128, 0, 128));
        JButton btnDinero = crearBoton("💰 Dinero", new Color(0, 150, 0));
        JButton btnVolver = crearBoton("🔙 Volver", new Color(100, 100, 100));

        botones.add(btnAgregar);
        botones.add(btnEditar);
        botones.add(btnEliminar);
        botones.add(btnActualizar);
        botones.add(btnGrafico);
        botones.add(btnDinero);
        botones.add(btnVolver);

        panel.add(botones, BorderLayout.SOUTH);

        add(panel);

        /*
         * =====================================================
         * EVENTOS
         * =====================================================
         */
        btnAgregar.addActionListener(e -> agregarProducto());
        btnEditar.addActionListener(e -> abrirFormularioEdicion());
        btnEliminar.addActionListener(e -> eliminarProducto());
        btnActualizar.addActionListener(e -> cargarProductos());

        btnGrafico.addActionListener(e -> mostrarGrafico());
        btnDinero.addActionListener(e -> mostrarGraficoDinero());

        btnVolver.addActionListener(e -> {
            dispose();
            new MenuPrincipal("Usuario").setVisible(true);
        });

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                filtrar(txtBuscar.getText());
            }
        });

        tabla.getSelectionModel().addListSelectionListener(e -> mostrarDetalle());

        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    abrirFormularioEdicion();
                }
            }
        });

        /*
         * Carga inicial
         */
        cargarProductos();
    }

    /*
     * =====================================================
     * METODO: cargarProductos
     * =====================================================
     * FUNCION:
     * Carga los productos desde la BD a la tabla y
     * actualiza el dashboard.
     */
    private void cargarProductos() {

        modelo.setRowCount(0);
        List<Producto> lista = new ProductoDAO().listarProductos();

        double total = 0;

        for (Producto p : lista) {

            total += p.getPrecio() * p.getCantidad();

            modelo.addRow(new Object[]{
                    p.getId(),
                    p.getNombre(),
                    p.getMarca(),
                    p.getCategoria(),
                    formato.format(p.getPrecio()),
                    p.getCantidad()
            });
        }

        lblCantidadProductos.setText("📦 Productos: " + lista.size());
        lblTotal.setText("💰 Total: " + formato.format(total));
    }

    /*
     * =====================================================
     * METODO: filtrar
     * =====================================================
     * FUNCION:
     * Filtra productos por nombre.
     */
    private void filtrar(String txt) {

        modelo.setRowCount(0);

        for (Producto p : new ProductoDAO().listarProductos()) {

            if (p.getNombre().toLowerCase().contains(txt.toLowerCase())) {

                modelo.addRow(new Object[]{
                        p.getId(),
                        p.getNombre(),
                        p.getMarca(),
                        p.getCategoria(),
                        formato.format(p.getPrecio()),
                        p.getCantidad()
                });
            }
        }
    }

    /*
     * =====================================================
     * METODO: mostrarDetalle
     * =====================================================
     * FUNCION:
     * Muestra en el panel derecho el producto seleccionado.
     */
    private void mostrarDetalle() {

        int fila = tabla.getSelectedRow();

        if (fila != -1) {

            double precio = Double.parseDouble(
                    modelo.getValueAt(fila, 4).toString()
                            .replace("RD$", "")
                            .replace(",", "")
            );

            int cantidad = Integer.parseInt(modelo.getValueAt(fila, 5).toString());
            double subtotal = precio * cantidad;

            lblNombre.setText("Nombre: " + modelo.getValueAt(fila, 1));
            lblPrecio.setText("Precio: " + formato.format(precio));
            lblCantidad.setText("Cantidad: " + cantidad);
            lblSubtotal.setText("Subtotal: " + formato.format(subtotal));
        }
    }

    /*
     * =====================================================
     * METODO: agregarProducto
     * =====================================================
     * FUNCION:
     * Permite registrar un producto nuevo.
     */
    private void agregarProducto() {

        JTextField nombre = new JTextField();
        JTextField marca = new JTextField();
        JTextField categoria = new JTextField();
        JTextField precio = new JTextField();
        JTextField cantidad = new JTextField();

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Nombre:")); panel.add(nombre);
        panel.add(new JLabel("Marca:")); panel.add(marca);
        panel.add(new JLabel("Categoría:")); panel.add(categoria);
        panel.add(new JLabel("Precio:")); panel.add(precio);
        panel.add(new JLabel("Cantidad disponible:")); panel.add(cantidad);

        if (JOptionPane.showConfirmDialog(this, panel, "Agregar", 0) == 0) {

            try {
                Producto p = new Producto();

                p.setNombre(nombre.getText());
                p.setMarca(marca.getText());
                p.setCategoria(categoria.getText());
                p.setPrecio(Double.parseDouble(precio.getText()));
                p.setCantidad(Integer.parseInt(cantidad.getText()));

                if (new ProductoDAO().insertarProducto(p)) {
                    cargarProductos();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo agregar el producto");
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error en los datos ingresados");
            }
        }
    }

    /*
     * =====================================================
     * METODO: abrirFormularioEdicion
     * =====================================================
     * FUNCION:
     * Permite editar un producto seleccionado.
     */
    private void abrirFormularioEdicion() {

        int fila = tabla.getSelectedRow();
        if (fila == -1) return;

        int id = (int) modelo.getValueAt(fila, 0);

        JTextField nombre = new JTextField(modelo.getValueAt(fila, 1).toString());
        JTextField marca = new JTextField(modelo.getValueAt(fila, 2).toString());
        JTextField categoria = new JTextField(modelo.getValueAt(fila, 3).toString());
        JTextField precio = new JTextField(
                modelo.getValueAt(fila, 4).toString().replace("RD$", "").replace(",", "")
        );
        JTextField cantidad = new JTextField(modelo.getValueAt(fila, 5).toString());

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Nombre:")); panel.add(nombre);
        panel.add(new JLabel("Marca:")); panel.add(marca);
        panel.add(new JLabel("Categoría:")); panel.add(categoria);
        panel.add(new JLabel("Precio:")); panel.add(precio);
        panel.add(new JLabel("Cantidad:")); panel.add(cantidad);

        if (JOptionPane.showConfirmDialog(this, panel, "Editar", 0) == 0) {

            try {
                Producto p = new Producto();

                p.setId(id);
                p.setNombre(nombre.getText());
                p.setMarca(marca.getText());
                p.setCategoria(categoria.getText());
                p.setPrecio(Double.parseDouble(precio.getText()));
                p.setCantidad(Integer.parseInt(cantidad.getText()));

                if (new ProductoDAO().actualizarProducto(p)) {
                    cargarProductos();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo actualizar el producto");
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error en los datos ingresados");
            }
        }
    }

    /*
     * =====================================================
     * METODO: eliminarProducto
     * =====================================================
     * FUNCION:
     * Elimina el producto seleccionado.
     */
    private void eliminarProducto() {

        int fila = tabla.getSelectedRow();
        if (fila == -1) return;

        int id = (int) modelo.getValueAt(fila, 0);

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Desea eliminar este producto?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            new ProductoDAO().eliminarProducto(id);
            cargarProductos();
        }
    }

    /*
     * =====================================================
     * METODO: mostrarGrafico
     * =====================================================
     * FUNCION:
     * Muestra gráfico circular por cantidad.
     */
    private void mostrarGrafico() {

        JFrame ventana = new JFrame("📊 Cantidad PRO");
        ventana.setSize(650, 650);
        ventana.setLocationRelativeTo(null);
        ventana.setLayout(new BorderLayout());

        JButton volver = new JButton("Volver");
        volver.addActionListener(e -> ventana.dispose());
        ventana.add(volver, BorderLayout.SOUTH);

        ventana.add(new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                List<Producto> lista = new ProductoDAO().listarProductos();

                if (lista.isEmpty()) {
                    g2.drawString("No hay datos", 250, 300);
                    return;
                }

                int total = 0;
                for (Producto p : lista) total += p.getCantidad();

                int inicio = 0;
                int i = 0;

                Color[] colores = {
                        new Color(52,152,219),
                        new Color(46,204,113),
                        new Color(241,196,15),
                        new Color(231,76,60),
                        new Color(155,89,182),
                        new Color(26,188,156),
                        new Color(230,126,34)
                };

                int x = 150, y = 100, size = 300;

                for (Producto p : lista) {
                    int valor = p.getCantidad();
                    int angulo = (int) Math.round(valor * 360.0 / total);

                    g2.setColor(colores[i % colores.length]);
                    g2.fillArc(x, y, size, size, inicio, angulo);

                    inicio += angulo;
                    i++;
                }

                int ly = 450;
                i = 0;

                for (Producto p : lista) {
                    g2.setColor(colores[i % colores.length]);
                    g2.fillRect(80, ly, 20, 20);

                    g2.setColor(Color.BLACK);
                    g2.drawString(p.getNombre() + " (" + p.getCantidad() + ")", 110, ly + 15);

                    ly += 25;
                    i++;
                }
            }
        }, BorderLayout.CENTER);

        ventana.setVisible(true);
    }

    /*
     * =====================================================
     * METODO: mostrarGraficoDinero
     * =====================================================
     * FUNCION:
     * Muestra gráfico circular por valor monetario.
     */
    private void mostrarGraficoDinero() {

        JFrame ventana = new JFrame("💰 Dinero PRO");
        ventana.setSize(650, 650);
        ventana.setLocationRelativeTo(null);
        ventana.setLayout(new BorderLayout());

        JButton volver = new JButton("Volver");
        volver.addActionListener(e -> ventana.dispose());
        ventana.add(volver, BorderLayout.SOUTH);

        ventana.add(new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                List<Producto> lista = new ProductoDAO().listarProductos();

                if (lista.isEmpty()) {
                    g2.drawString("No hay datos", 250, 300);
                    return;
                }

                double total = 0;
                for (Producto p : lista) total += p.getPrecio() * p.getCantidad();

                int inicio = 0;
                int i = 0;

                Color[] colores = {
                        new Color(39,174,96),
                        new Color(41,128,185),
                        new Color(230,126,34),
                        new Color(192,57,43),
                        new Color(142,68,173),
                        new Color(22,160,133),
                        new Color(243,156,18)
                };

                int x = 150, y = 100, size = 300;

                for (Producto p : lista) {
                    double valor = p.getPrecio() * p.getCantidad();
                    int angulo = (int) Math.round(valor * 360.0 / total);

                    g2.setColor(colores[i % colores.length]);
                    g2.fillArc(x, y, size, size, inicio, angulo);

                    inicio += angulo;
                    i++;
                }

                int ly = 450;
                i = 0;

                for (Producto p : lista) {
                    double dinero = p.getPrecio() * p.getCantidad();

                    g2.setColor(colores[i % colores.length]);
                    g2.fillRect(80, ly, 20, 20);

                    g2.setColor(Color.BLACK);
                    g2.drawString(p.getNombre() + " (RD$" + (int) dinero + ")", 110, ly + 15);

                    ly += 25;
                    i++;
                }
            }
        }, BorderLayout.CENTER);

        ventana.setVisible(true);
    }

    /*
     * =====================================================
     * METODOS AUXILIARES
     * =====================================================
     */
    private JLabel crearLabel(String txt) {
        JLabel l = new JLabel(txt);
        l.setForeground(Color.WHITE);
        return l;
    }

    private JButton crearBoton(String txt, Color color) {
        JButton btn = new JButton(txt);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }
}