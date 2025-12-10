import java.awt.*;
import java.text.DecimalFormat;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class AcademicPerformanceGUIGradient extends JFrame {

    private JTextField nameField, subjectsField, marksField;
    private DefaultTableModel tableModel;
    private DecimalFormat df = new DecimalFormat("#.##");

    public AcademicPerformanceGUIGradient() {
        setTitle("Smart Academic Performance Evaluator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 620);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create gradient background panel (center content)
        GradientPanel center = new GradientPanel();
        center.setLayout(new BorderLayout(12, 12));
        center.setBorder(new EmptyBorder(12, 12, 12, 12));
        add(center, BorderLayout.CENTER);

        // Header (transparent so gradient shows)
        JLabel header = new JLabel("SMART ACADEMIC PERFORMANCE SYSTEM", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 26));
        header.setForeground(Color.WHITE);
        header.setBorder(new EmptyBorder(12, 0, 12, 0));
        center.add(header, BorderLayout.NORTH);

        // Left side input panel (transparent)
        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new GridBagLayout());
        left.setBorder(new EmptyBorder(8, 18, 8, 18));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Student Name:");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 0;
        left.add(nameLabel, gbc);

        nameField = new JTextField(18);
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 1;
        left.add(nameField, gbc);

        JLabel subjectsLabel = new JLabel("Number of Subjects:");
        subjectsLabel.setForeground(Color.WHITE);
        subjectsLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 2;
        left.add(subjectsLabel, gbc);

        subjectsField = new JTextField(6);
        subjectsField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 3;
        left.add(subjectsField, gbc);

        JLabel marksLabel = new JLabel("Marks (comma-separated):");
        marksLabel.setForeground(Color.WHITE);
        marksLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 4;
        left.add(marksLabel, gbc);

        marksField = new JTextField(18);
        marksField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 5;
        left.add(marksField, gbc);

        // Buttons (left bottom)
        JPanel leftButtons = new JPanel();
        leftButtons.setOpaque(false);
        leftButtons.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 0));
        JButton addBtn = new JButton("ADD STUDENT");
        JButton clearBtn = new JButton("CLEAR");
        JButton exitBtn = new JButton("EXIT");

        // Button styling
        addBtn.setBackground(new Color(0, 204, 153)); addBtn.setForeground(Color.WHITE);
        clearBtn.setBackground(new Color(255, 204, 0)); clearBtn.setForeground(Color.BLACK);
        exitBtn.setBackground(new Color(231, 76, 60)); exitBtn.setForeground(Color.WHITE);

        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        clearBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        exitBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));

        addBtn.setFocusPainted(false);
        clearBtn.setFocusPainted(false);
        exitBtn.setFocusPainted(false);

        leftButtons.add(addBtn);
        leftButtons.add(clearBtn);
        leftButtons.add(exitBtn);

        gbc.gridx = 0; gbc.gridy = 6;
        left.add(leftButtons, gbc);

        center.add(left, BorderLayout.WEST);

        // Right side: table in a panel with a titled border
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // Table model and JTable
        String[] cols = {"Name", "Obtained", "Out Of", "Average (%)", "Grade"};
        tableModel = new DefaultTableModel(cols, 0) {
            // prevent editing cells
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        table.setRowHeight(28);
        table.setForeground(Color.WHITE);
        table.setBackground(new Color(5, 31, 64)); // dark row color
        table.setGridColor(new Color(44, 62, 80));
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(false);

        // Header style
        JTableHeader headerComp = table.getTableHeader();
        headerComp.setBackground(new Color(10, 63, 116));
        headerComp.setForeground(Color.WHITE);
        headerComp.setFont(new Font("Segoe UI", Font.BOLD, 15));
        headerComp.setReorderingAllowed(false);

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setOpaque(false);
        tableScroll.getViewport().setOpaque(false);
        tableScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(10, 63, 116), 2),
                "Student Results",
                0, 0, new Font("Segoe UI", Font.BOLD, 14),
                Color.WHITE
        ));

        rightPanel.add(tableScroll, BorderLayout.CENTER);
        center.add(rightPanel, BorderLayout.CENTER);

        // Action listeners

        addBtn.addActionListener(e -> addStudentAction());
        clearBtn.addActionListener(e -> {
            // Only clear input fields, leave table data intact
            nameField.setText("");
            subjectsField.setText("");
            marksField.setText("");
        });
        exitBtn.addActionListener(e -> System.exit(0));

        // keyboard: press Enter in marks field as Add
        marksField.addActionListener(e -> addStudentAction());
    }

    // Method to add student after validation
    private void addStudentAction() {
        String name = nameField.getText().trim();
        String subjectsText = subjectsField.getText().trim();
        String marksRaw = marksField.getText().trim();

        if (name.isEmpty() || subjectsText.isEmpty() || marksRaw.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill Name, Number of Subjects and Marks.",
                    "Missing Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int numSubjects;
        try {
            numSubjects = Integer.parseInt(subjectsText);
            if (numSubjects <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Number of subjects must be a positive integer.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] parts = marksRaw.split(",");
        if (parts.length != numSubjects) {
            JOptionPane.showMessageDialog(this,
                    "Number of marks entered (" + parts.length + ") does not match number of subjects (" + numSubjects + ").",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double total = 0.0;
        for (int i = 0; i < parts.length; i++) {
            String s = parts[i].trim();
            try {
                double mark = Double.parseDouble(s);
                if (mark < 0 || mark > 100) {
                    JOptionPane.showMessageDialog(this,
                            "Marks must be between 0 and 100. Invalid mark: " + mark,
                            "Invalid Marks", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                total += mark;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Please enter valid numeric marks (comma-separated). Error at: '" + s + "'",
                        "Invalid Marks", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        double outOf = numSubjects * 100.0;
        double average = (total / outOf) * 100.0; // percentage out of 100
        // grade based on percentage
        char grade;
        if (average >= 90) grade = 'A';
        else if (average >= 80) grade = 'B';
        else if (average >= 70) grade = 'C';
        else if (average >= 60) grade = 'D';
        else grade = 'F';

        // Format numbers
        String obtainedStr = String.valueOf((int) total);
        String outOfStr = String.valueOf((int) outOf);
        String avgStr = new DecimalFormat("#.##").format(average);

        // Add to table
        tableModel.addRow(new Object[]{name, obtainedStr, outOfStr, avgStr, grade});

        // Optionally clear inputs (you asked to leave this behavior as-is)
        nameField.setText("");
        subjectsField.setText("");
        marksField.setText("");
    }

    // Custom panel that paints a vertical gradient background
    private static class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // create a smooth vertical gradient (deep navy -> royal blue)
            Graphics2D g2d = (Graphics2D) g.create();
            int w = getWidth();
            int h = getHeight();
            Color top = new Color(0, 20, 48);      // deep navy
            Color bottom = new Color(0, 90, 160);  // royal blue
            GradientPaint gp = new GradientPaint(0, 0, top, 0, h, bottom);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, w, h);
            g2d.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AcademicPerformanceGUIGradient app = new AcademicPerformanceGUIGradient();
            app.setVisible(true);
            });
}
}
