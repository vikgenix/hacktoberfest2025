/**
 * Heat Index Calculator
 * Created by: KartikeyaNainkhwal  
 * For: Hacktoberfest 2025 - Issue #164
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class HeatIndexCalculator extends JFrame implements ActionListener {
    
    private JTextField temperatureField;
    private JTextField humidityField;
    private JLabel resultLabel;
    private JLabel warningLabel;
    private JComboBox<String> tempUnitCombo;
    private JButton calculateButton;
    private JButton clearButton;
    private JTextArea historyArea;
    private DecimalFormat df = new DecimalFormat("#.##");
    
    private final Color BACKGROUND_COLOR = new Color(240, 248, 255);
    private final Color PANEL_COLOR = new Color(255, 255, 255);
    private final Color BUTTON_COLOR = new Color(70, 130, 180);
    private final Color TEXT_COLOR = new Color(25, 25, 112);
    private final Color WARNING_COLOR = new Color(220, 20, 60);
    private final Color SAFE_COLOR = new Color(34, 139, 34);
    
    public HeatIndexCalculator() {
        initializeGUI();
    }
    
    private void initializeGUI() {
        setTitle("Heat Index Calculator - by KartikeyaNainkhwal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND_COLOR);
        
        createMainPanel();
        createHistoryPanel();
        createInfoPanel();
        
        pack();
        setLocationRelativeTo(null);
        setResizable(true);
    }
    
    private void createMainPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(PANEL_COLOR);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Heat Index Calculator"),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Temperature input
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(new JLabel("Temperature:"), gbc);
        
        gbc.gridx = 1;
        temperatureField = new JTextField(10);
        temperatureField.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(temperatureField, gbc);
        
        gbc.gridx = 2;
        tempUnitCombo = new JComboBox<>(new String[]{"°F (Fahrenheit)", "°C (Celsius)"});
        tempUnitCombo.setSelectedIndex(0);
        mainPanel.add(tempUnitCombo, gbc);
        
        // Humidity input
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Relative Humidity (%):"), gbc);
        
        gbc.gridx = 1;
        humidityField = new JTextField(10);
        humidityField.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(humidityField, gbc);
        
        // Buttons
        gbc.gridx = 0; gbc.gridy = 2;
        calculateButton = createStyledButton("Calculate Heat Index");
        mainPanel.add(calculateButton, gbc);
        
        gbc.gridx = 1;
        clearButton = createStyledButton("Clear");
        mainPanel.add(clearButton, gbc);
        
        // Result display
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        resultLabel = new JLabel("Enter temperature and humidity to calculate heat index", JLabel.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
        resultLabel.setForeground(TEXT_COLOR);
        resultLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            new EmptyBorder(15, 10, 15, 10)
        ));
        mainPanel.add(resultLabel, gbc);
        
        // Warning label
        gbc.gridy = 4;
        warningLabel = new JLabel("", JLabel.CENTER);
        warningLabel.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(warningLabel, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Add Enter key listener
        temperatureField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    calculateHeatIndex();
                }
            }
        });
        
        humidityField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    calculateHeatIndex();
                }
            }
        });
    }
    
    private void createHistoryPanel() {
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBorder(BorderFactory.createTitledBorder("Calculation History"));
        historyPanel.setPreferredSize(new Dimension(300, 200));
        
        historyArea = new JTextArea(10, 25);
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        historyArea.setBackground(new Color(248, 248, 255));
        
        JScrollPane scrollPane = new JScrollPane(historyArea);
        historyPanel.add(scrollPane, BorderLayout.CENTER);
        
        JButton clearHistoryBtn = createStyledButton("Clear History");
        clearHistoryBtn.addActionListener(e -> {
            historyArea.setText("");
        });
        historyPanel.add(clearHistoryBtn, BorderLayout.SOUTH);
        
        add(historyPanel, BorderLayout.EAST);
    }
    
    private void createInfoPanel() {
        JPanel infoPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        infoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        infoPanel.setBackground(BACKGROUND_COLOR);
        
        infoPanel.add(createInfoCard("Caution", "80-90°F", new Color(255, 255, 0)));
        infoPanel.add(createInfoCard("Extreme Caution", "90-105°F", new Color(255, 165, 0)));
        infoPanel.add(createInfoCard("Danger", "105-130°F", new Color(255, 69, 0)));
        infoPanel.add(createInfoCard("Extreme Danger", "130°F+", new Color(139, 0, 0)));
        
        add(infoPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createInfoCard(String title, String range, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            new EmptyBorder(5, 5, 5, 5)
        ));
        
        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        titleLabel.setForeground(color);
        
        JLabel rangeLabel = new JLabel(range, JLabel.CENTER);
        rangeLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        
        card.add(titleLabel, BorderLayout.CENTER);
        card.add(rangeLabel, BorderLayout.SOUTH);
        
        return card;
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(BUTTON_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BUTTON_COLOR.darker()),
            new EmptyBorder(8, 15, 8, 15)
        ));
        button.addActionListener(this);
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(BUTTON_COLOR.brighter());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(BUTTON_COLOR);
            }
        });
        
        return button;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == calculateButton) {
            calculateHeatIndex();
        } else if (e.getSource() == clearButton) {
            clearFields();
        }
    }
    
    private void calculateHeatIndex() {
        try {
            String tempText = temperatureField.getText().trim();
            String humidityText = humidityField.getText().trim();
            
            if (tempText.isEmpty() || humidityText.isEmpty()) {
                showError("Please enter both temperature and humidity values.");
                return;
            }
            
            double temperature = Double.parseDouble(tempText);
            double humidity = Double.parseDouble(humidityText);
            
            // Validate inputs
            if (humidity < 0 || humidity > 100) {
                showError("Humidity must be between 0 and 100 percent.");
                return;
            }
            
            // Convert Celsius to Fahrenheit if needed
            boolean isCelsius = tempUnitCombo.getSelectedIndex() == 1;
            double tempF = temperature;
            if (isCelsius) {
                tempF = (temperature * 9.0 / 5.0) + 32;
                if (temperature < 26.7) { // 80°F
                    showError("Heat index calculation requires temperature ≥ 80°F (26.7°C).");
                    return;
                }
            } else {
                if (temperature < 80) {
                    showError("Heat index calculation requires temperature ≥ 80°F (26.7°C).");
                    return;
                }
            }
            
            // Calculate heat index using Rothfusz equation
            double heatIndex = calculateHeatIndexValue(tempF, humidity);
            
            // Display results
            displayResults(temperature, humidity, heatIndex, isCelsius);
            
            // Add to history
            addToHistory(temperature, humidity, heatIndex, isCelsius);
            
        } catch (NumberFormatException ex) {
            showError("Please enter valid numeric values.");
        } catch (Exception ex) {
            showError("An error occurred: " + ex.getMessage());
        }
    }
    
    private double calculateHeatIndexValue(double tempF, double humidity) {
        // Rothfusz regression equation for heat index
        double T = tempF;
        double RH = humidity;
        
        // Simple approximation for temperatures close to 80°F
        if (T < 80) {
            return T; // Just return the temperature
        }
        
        // Full Rothfusz equation
        double HI = -42.379 + 
                   2.04901523 * T + 
                   10.14333127 * RH - 
                   0.22475541 * T * RH - 
                   6.83783e-3 * T * T - 
                   5.481717e-2 * RH * RH + 
                   1.22874e-3 * T * T * RH + 
                   8.5282e-4 * T * RH * RH - 
                   1.99e-6 * T * T * RH * RH;
        
        // Adjustments for specific conditions
        if (RH < 13 && T >= 80 && T <= 112) {
            double adjustment = ((13 - RH) / 4) * Math.sqrt((17 - Math.abs(T - 95)) / 17);
            HI -= adjustment;
        } else if (RH > 85 && T >= 80 && T <= 87) {
            double adjustment = ((RH - 85) / 10) * ((87 - T) / 5);
            HI += adjustment;
        }
        
        return HI;
    }
    
    private void displayResults(double temp, double humidity, double heatIndex, boolean isCelsius) {
        String tempUnit = isCelsius ? "°C" : "°F";
        String hiUnit = "°F";
        
        // Convert heat index to Celsius if needed
        double displayHI = heatIndex;
        if (isCelsius) {
            displayHI = (heatIndex - 32) * 5.0 / 9.0;
            hiUnit = "°C";
        }
        
        String result = String.format("Heat Index: %s %s", df.format(displayHI), hiUnit);
        resultLabel.setText(result);
        resultLabel.setForeground(TEXT_COLOR);
        
        // Determine warning level and color
        String warning = getWarningLevel(heatIndex);
        warningLabel.setText(warning);
        
        if (warning.contains("Extreme Danger")) {
            warningLabel.setForeground(new Color(139, 0, 0));
        } else if (warning.contains("Danger")) {
            warningLabel.setForeground(new Color(255, 69, 0));
        } else if (warning.contains("Extreme Caution")) {
            warningLabel.setForeground(new Color(255, 140, 0));
        } else if (warning.contains("Caution")) {
            warningLabel.setForeground(new Color(255, 215, 0));
        } else {
            warningLabel.setForeground(SAFE_COLOR);
        }
    }
    
    private String getWarningLevel(double heatIndexF) {
        if (heatIndexF >= 130) {
            return "⚠️ EXTREME DANGER - Heat stroke highly likely!";
        } else if (heatIndexF >= 105) {
            return "⚠️ DANGER - Heat exhaustion and cramps likely!";
        } else if (heatIndexF >= 90) {
            return "⚠️ EXTREME CAUTION - Heat exhaustion possible!";
        } else if (heatIndexF >= 80) {
            return "⚠️ CAUTION - Fatigue possible with activity!";
        } else {
            return "✅ Safe conditions";
        }
    }
    
    private void addToHistory(double temp, double humidity, double heatIndex, boolean isCelsius) {
        String tempUnit = isCelsius ? "°C" : "°F";
        String hiUnit = "°F";
        double displayHI = heatIndex;
        
        if (isCelsius) {
            displayHI = (heatIndex - 32) * 5.0 / 9.0;
            hiUnit = "°C";
        }
        
        String entry = String.format("%s %s, %s%% RH → %s %s\n", 
            df.format(temp), tempUnit, 
            df.format(humidity), 
            df.format(displayHI), hiUnit);
        
        historyArea.append(entry);
        historyArea.setCaretPosition(historyArea.getDocument().getLength());
    }
    
    private void clearFields() {
        temperatureField.setText("");
        humidityField.setText("");
        resultLabel.setText("Enter temperature and humidity to calculate heat index");
        resultLabel.setForeground(TEXT_COLOR);
        warningLabel.setText("");
        temperatureField.requestFocus();
    }
    
    private void showError(String message) {
        resultLabel.setText("Error: " + message);
        resultLabel.setForeground(WARNING_COLOR);
        warningLabel.setText("");
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Could not set system look and feel");
        }
        
        SwingUtilities.invokeLater(() -> {
            new HeatIndexCalculator().setVisible(true);
        });
    }
}
