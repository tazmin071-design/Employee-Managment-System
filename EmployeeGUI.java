import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Employee class
class Employee {
    private int id;
    private String name;
    private double salary;

    public Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getSalary() { return salary; }

    public void setName(String name) { this.name = name; }
    public void setSalary(double salary) { this.salary = salary; }
}

// Main GUI class
public class EmployeeGUI extends JFrame implements ActionListener {

    JTextField idField, nameField, salaryField;
    JTextArea outputArea;

    Employee[] list = new Employee[50];
    int count = 0;

    public EmployeeGUI() {

        setTitle("Employee Management");
        setSize(500, 400);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Input fields
        add(new JLabel("ID:"));
        idField = new JTextField(10);
        add(idField);

        add(new JLabel("Name:"));
        nameField = new JTextField(10);
        add(nameField);

        add(new JLabel("Salary:"));
        salaryField = new JTextField(10);
        add(salaryField);

        // Buttons with colors
        String[] btns = {"Add", "Search", "Update", "Delete", "Display"};

        for (String b : btns) {
            JButton btn = new JButton(b);

            // Assign colors
            if (b.equals("Add"))
                btn.setBackground(new Color(0, 153, 76));     // green
            else if (b.equals("Delete"))
                btn.setBackground(Color.RED);
            else if (b.equals("Update"))
                btn.setBackground(Color.ORANGE);
            else if (b.equals("Search"))
                btn.setBackground(Color.CYAN);
            else
                btn.setBackground(Color.LIGHT_GRAY);

            btn.setForeground(Color.BLACK);  // text color

            // Important for color visibility
            btn.setOpaque(true);
            btn.setBorderPainted(false);

            btn.addActionListener(this);
            add(btn);
        }

        // Output area
        outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea));

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        String cmd = e.getActionCommand();

        int id = 0;
        double salary = 0;
        String name = nameField.getText();

        try {
            if (!idField.getText().isEmpty())
                id = Integer.parseInt(idField.getText());

            if (!salaryField.getText().isEmpty())
                salary = Double.parseDouble(salaryField.getText());

        } catch (Exception ex) {
            outputArea.setText("Invalid Input!");
            return;
        }

        // ADD
        if (cmd.equals("Add")) {
            if (count < list.length) {
                list[count] = new Employee(id, name, salary);
                count++;
                outputArea.setText("Employee Added");
            } else {
                outputArea.setText("List Full");
            }
        }

        // SEARCH
        else if (cmd.equals("Search")) {
            boolean found = false;

            for (int i = 0; i < count; i++) {
                if (list[i].getId() == id) {
                    outputArea.setText("Found: " + list[i].getName()
                            + " | Salary: " + list[i].getSalary());
                    found = true;
                    break;
                }
            }

            if (!found)
                outputArea.setText("Employee Not Found");
        }

        // UPDATE
        else if (cmd.equals("Update")) {
            boolean found = false;

            for (int i = 0; i < count; i++) {
                if (list[i].getId() == id) {
                    list[i].setName(name);
                    list[i].setSalary(salary);
                    outputArea.setText("Employee Updated");
                    found = true;
                    break;
                }
            }

            if (!found)
                outputArea.setText("Employee Not Found");
        }

        // DELETE
        else if (cmd.equals("Delete")) {
            boolean found = false;

            for (int i = 0; i < count; i++) {
                if (list[i].getId() == id) {

                    for (int j = i; j < count - 1; j++) {
                        list[j] = list[j + 1];  // shift left
                    }

                    count--;
                    outputArea.setText("Employee Deleted");
                    found = true;
                    break;
                }
            }

            if (!found)
                outputArea.setText("Employee Not Found");
        }

        // DISPLAY
        else if (cmd.equals("Display")) {
            String data = "";

            for (int i = 0; i < count; i++) {
                data += "ID: " + list[i].getId() +
                        ", Name: " + list[i].getName() +
                        ", Salary: " + list[i].getSalary() + "\n";
            }

            if (data.isEmpty())
                outputArea.setText("No Employees Found");
            else
                outputArea.setText(data);
        }
    }

    public static void main(String[] args) {
        new EmployeeGUI();
    }
}