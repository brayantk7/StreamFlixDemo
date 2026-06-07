import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import models.User;
import models.Movie;
import core.DecisionTreeNode;
import core.CollaborativeFiltering;

/**
 * Graphical User Interface for the StreamFlix AI System.
 * Uses native Java Swing to provide a visual frontend.
 */
public class StreamFlixGUI extends JFrame {

    private JTextField txtName;
    private JTextField txtAge;
    private JComboBox<String> cbGenre;
    private JTextArea txtResult;

    // Database simulation
    private List<User> userBase;
    private List<Movie> catalog;
    private DecisionTreeNode rootAgeNode;

    public StreamFlixGUI() {
        initializeData();
        setupUI();
    }

    /**
     * Initializes the mock database and AI models.
     */
    private void initializeData() {
        User similarUser = new User("U002", "Carlos", 22, "Terror");
        userBase = Arrays.asList(similarUser);

        Movie m1 = new Movie("M1", "Kingdom", "Terror", 4.8);
        Movie m2 = new Movie("M2", "Stranger Things", "Ciencia Ficcion", 4.5);
        Movie m3 = new Movie("M3", "Toy Story", "Animacion", 4.9);
        catalog = Arrays.asList(m1, m2, m3);

        DecisionTreeNode leafKingdom = new DecisionTreeNode(m1);
        DecisionTreeNode leafStranger = new DecisionTreeNode(m2);
        DecisionTreeNode leafToyStory = new DecisionTreeNode(m3);

        DecisionTreeNode genreNode = new DecisionTreeNode("generoFavorito", "Terror", leafKingdom, leafStranger);
        rootAgeNode = new DecisionTreeNode("esMayorDeEdad", "true", genreNode, leafToyStory);
    }

    /**
     * Configures the visual components of the window.
     */
    private void setupUI() {
        setTitle("StreamFlix AI - Recomendaciones");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        JLabel lblHeader = new JLabel("StreamFlix AI", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 24));
        lblHeader.setForeground(Color.RED);
        lblHeader.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        JPanel pnlBackground = new JPanel(new BorderLayout());
        pnlBackground.setBackground(new Color(20, 20, 20)); // Dark theme
        pnlBackground.add(lblHeader, BorderLayout.NORTH);

        // Form Panel
        JPanel pnlForm = new JPanel(new GridLayout(4, 2, 10, 15));
        pnlForm.setBackground(new Color(20, 20, 20));
        pnlForm.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        pnlForm.add(createStyledLabel("Tu Nombre:"));
        txtName = new JTextField();
        pnlForm.add(txtName);

        pnlForm.add(createStyledLabel("Tu Edad:"));
        txtAge = new JTextField();
        pnlForm.add(txtAge);

        pnlForm.add(createStyledLabel("Género Favorito:"));
        String[] genres = {"Terror", "Ciencia Ficcion", "Animacion", "Accion"};
        cbGenre = new JComboBox<>(genres);
        pnlForm.add(cbGenre);

        JButton btnRecommend = new JButton("GENERAR RECOMENDACIÓN");
        btnRecommend.setBackground(Color.RED);
        btnRecommend.setForeground(Color.WHITE);
        btnRecommend.setFont(new Font("Arial", Font.BOLD, 14));
        btnRecommend.setFocusPainted(false);
        
        pnlBackground.add(pnlForm, BorderLayout.CENTER);

        // Results Panel
        JPanel pnlBottom = new JPanel(new BorderLayout());
        pnlBottom.setBackground(new Color(20, 20, 20));
        pnlBottom.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        pnlBottom.add(btnRecommend, BorderLayout.NORTH);

        txtResult = new JTextArea(8, 20);
        txtResult.setEditable(false);
        txtResult.setBackground(new Color(40, 40, 40));
        txtResult.setForeground(Color.WHITE);
        txtResult.setFont(new Font("Consolas", Font.PLAIN, 13));
        txtResult.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        pnlBottom.add(new JScrollPane(txtResult), BorderLayout.CENTER);
        pnlBackground.add(pnlBottom, BorderLayout.SOUTH);

        add(pnlBackground);

        // Button Action
        btnRecommend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processRecommendation();
            }
        });
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        return label;
    }

    /**
     * Executes the AI engines and updates the UI with the results.
     */
    private void processRecommendation() {
        try {
            String name = txtName.getText();
            int age = Integer.parseInt(txtAge.getText());
            String genre = (String) cbGenre.getSelectedItem();

            User liveUser = new User("U_LIVE", name, age, genre);

            long startTree = System.nanoTime();
            Movie treeRec = rootAgeNode.evaluar(liveUser);
            long endTree = System.nanoTime();

            CollaborativeFiltering cf = new CollaborativeFiltering();
            long startCF = System.nanoTime();
            Movie cfRec = cf.recomendarPorSimilitud(liveUser, userBase, catalog);
            long endCF = System.nanoTime();

            StringBuilder sb = new StringBuilder();
            sb.append("=== RESULTADOS DE IA ===\n\n");
            sb.append("Analizando perfil de: ").append(name).append("\n\n");
            
            sb.append("[1] Árbol de Decisión:\n");
            sb.append("  -> Película: ").append(treeRec.titulo).append("\n");
            sb.append("  -> Tiempo: ").append(String.format("%.4f", (endTree - startTree) / 1000000.0)).append(" ms\n\n");

            sb.append("[2] Filtrado Colaborativo:\n");
            sb.append("  -> Película: ").append(cfRec != null ? cfRec.titulo : "Sin coincidencias exactas").append("\n");
            sb.append("  -> Tiempo: ").append(String.format("%.4f", (endCF - startCF) / 1000000.0)).append(" ms");

            txtResult.setText(sb.toString());

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor ingresa una edad válida en números.", "Error de validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Set Look and Feel to make it look modern
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            StreamFlixGUI app = new StreamFlixGUI();
            app.setVisible(true);
        });
    }
}