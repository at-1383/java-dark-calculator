import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdvancedCalculator extends JFrame implements ActionListener {

    private JTextField screen;
    private String input = "0";
    private double firstNumber = 0;
    private String operation = "";
    private boolean newInput = false;

    public AdvancedCalculator() {
        setTitle("Calculator");
        setSize(360, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        screen = new JTextField(input);
        screen.setFont(new Font("Arial", Font.BOLD, 26));
        screen.setHorizontalAlignment(JTextField.RIGHT);
        screen.setEditable(false);
        screen.setBackground(Color.BLACK);
        screen.setForeground(Color.WHITE);
        screen.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        JPanel panel = new JPanel(new GridLayout(5, 4, 8, 8));
        panel.setBackground(Color.BLACK);

        String[] keys = {
                "C", "√", "x²", "/",
                "7", "8", "9", "*",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "0", ".", "=", ""
        };

        for (String key : keys) {
            if (key.equals("")) {
                panel.add(new JLabel());
                continue;
            }

            JButton btn = new JButton(key);
            btn.setFont(new Font("Arial", Font.BOLD, 16));
            btn.setFocusPainted(false);
            btn.addActionListener(this);

            if (key.matches("[0-9.]")) {
                btn.setBackground(new Color(60, 60, 60));
                btn.setForeground(Color.WHITE);
            } else if (key.equals("=")) {
                btn.setBackground(new Color(255, 149, 0));
                btn.setForeground(Color.WHITE);
            } else {
                btn.setBackground(new Color(120, 120, 120));
                btn.setForeground(Color.BLACK);
            }

            panel.add(btn);
        }

        setLayout(new BorderLayout(10, 10));
        add(screen, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        getContentPane().setBackground(Color.BLACK);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.matches("[0-9]")) {
            if (newInput || input.equals("0")) {
                input = cmd;
                newInput = false;
            } else {
                input += cmd;
            }
        }
        else if (cmd.equals(".")) {
            if (!input.contains(".")) input += ".";
        }
        else if (cmd.equals("C")) {
            input = "0";
            firstNumber = 0;
            operation = "";
        }
        else if (cmd.equals("√")) {
            double val = Double.parseDouble(input);
            input = val >= 0 ? format(Math.sqrt(val)) : "Error";
        }
        else if (cmd.equals("x²")) {
            double val = Double.parseDouble(input);
            input = format(val * val);
        }
        else if (cmd.equals("=")) {
            calculate();
            operation = "";
        }
        else {
            firstNumber = Double.parseDouble(input);
            operation = cmd;
            newInput = true;
        }

        screen.setText(input);
    }

    private void calculate() {
        double secondNumber = Double.parseDouble(input);
        double result = 0;

        switch (operation) {
            case "+" -> result = firstNumber + secondNumber;
            case "-" -> result = firstNumber - secondNumber;
            case "*" -> result = firstNumber * secondNumber;
            case "/" -> {
                if (secondNumber == 0) {
                    input = "Error";
                    return;
                }
                result = firstNumber / secondNumber;
            }
        }

        input = format(result);
        newInput = true;
    }

    private String format(double num) {
        if (num == (long) num)
            return String.valueOf((long) num);
        return String.valueOf(num);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdvancedCalculator().setVisible(true));
    }
}
