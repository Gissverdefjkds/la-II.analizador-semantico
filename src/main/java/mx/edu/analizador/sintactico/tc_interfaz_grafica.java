package mx.edu.analizador.sintactico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
 
public class tc_interfaz_grafica extends JFrame {
 
    private JComboBox<String> comboArchivos;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextArea areaCodigo;
 
    public tc_interfaz_grafica() {
        setTitle("Analizador Léxico - Tabla de Contenido");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
 
    // comboBox para seleccionar el archivo
        String[] opciones = {"1 - Variable no declarada", 
        "2 - Variable fuera de alcance (scope)",
        "3 - Asignación de tipos incompatibles",
        "4 - Argumentos incorrectos en una función",
        "5 - Tipos incorrectos en una función",
        "6 - Función no declarada",
        "7 - Uso incorrecto de return",
        "8 - Uso incorrecto de break",
        "9 - Uso incorrecto de continue",
        "10 - Conversión de tipos de control",
        "11 - Operación entre tipos incompatibles",
        "12 - Miembro inexistente de una clase",
        "13 - Llamada incorrecta a un método",
        "14 - Objeto de tipo incorrecto",
        "16 - Redefinición / conflicto de identificadores",
        "17 - Referencia incorrecta",
        "18 - Uso incorrecto de const",
        "19 - Modificar una expresión no modificable",
        "20 - Acceso a arreglo con tipo incorrecto"
    };

        comboArchivos = new JComboBox<>(opciones);
        comboArchivos.setPreferredSize(new Dimension(380, 28));
        comboArchivos.setMaximumRowCount(12);

        //actualizar tabal y codigo
        comboArchivos.addActionListener(e ->
            mostrarArchivo((String) comboArchivos.getSelectedItem())
        );


        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        panelSuperior.add(comboArchivos);
        add(panelSuperior, BorderLayout.NORTH);
 
        // Tabla
        String[] columnas = {"Lexema", "Tipo", "Línea"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // solo lectura
            }
        };

        tabla = new JTable(modelo);
        tabla.setRowHeight(22);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(60);  

        JScrollPane scrollTabla = new JScrollPane(tabla);

        // Área de código
        areaCodigo = new JTextArea();
        areaCodigo.setEditable(false);
        areaCodigo.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollCodigo = new JScrollPane(areaCodigo);

        // Divisor horizontal
        JSplitPane divisor = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollTabla, scrollCodigo);
        divisor.setDividerLocation(250);
        add(divisor, BorderLayout.CENTER);
    }

    // Método para mostrar el archivo seleccionado
    private void mostrarArchivo(String opcion) {
        modelo.setRowCount(0); // Limpiar la tabla
        areaCodigo.setText(""); // Limpiar el área de código

        }
      public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new tc_interfaz_grafica().setVisible(true);
        }  
    );
    }
}
    