
package gui;

import dao.PatientDAO;
import model.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PatientPanel extends JPanel {

    private final JTextField nameField = new JTextField(15);
    private final JTextField ageField = new JTextField(5);
    private final JTextField diseaseField = new JTextField(15);
    private final JTextField phoneField = new JTextField(15);

    private final DefaultTableModel tableModel =
            new DefaultTableModel(
                    new String[]{"ID", "Name", "Age", "Disease", "Phone"}, 0
            );

    private final PatientDAO patientDAO = new PatientDAO();

    public PatientPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));

        form.add(new JLabel("Patient Name:"));
        form.add(nameField);

        form.add(new JLabel("Age:"));
        form.add(ageField);

        form.add(new JLabel("Disease:"));
        form.add(diseaseField);

        form.add(new JLabel("Phone:"));
        form.add(phoneField);

        JButton addButton = new JButton("Register Patient");
        JButton refreshButton = new JButton("Refresh");

        form.add(addButton);
        form.add(refreshButton);

        JTable table = new JTable(tableModel);

        add(form, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        addButton.addActionListener(e -> addPatient());
        refreshButton.addActionListener(e -> loadPatients());

        loadPatients();
    }

    private void addPatient() {
        try {
            String name = nameField.getText().trim();
            String disease = diseaseField.getText().trim();
            String phone = phoneField.getText().trim();

            if (name.isEmpty() || disease.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this, "Please fill in all fields."
                );
                return;
            }

            int age = Integer.parseInt(ageField.getText().trim());

            if (age <= 0 || age > 120) {
                JOptionPane.showMessageDialog(
                        this, "Enter a valid age."
                );
                return;
            }

            Patient patient = new Patient(
                    0, name, age, disease, phone
            );

            patientDAO.add(patient);

            JOptionPane.showMessageDialog(
                    this, "Patient registered successfully!"
            );

            nameField.setText("");
            ageField.setText("");
            diseaseField.setText("");
            phoneField.setText("");

            loadPatients();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this, "Age must be a valid number."
            );
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this, "Could not register patient: " + ex.getMessage()
            );
        }
    }

    private void loadPatients() {
        try {
            tableModel.setRowCount(0);

            List<Patient> patients = patientDAO.getAll();

            for (Patient p : patients) {
                tableModel.addRow(new Object[]{
                        p.getId(),
                        p.getName(),
                        p.getAge(),
                        p.getDisease(),
                        p.getPhone()
                });
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this, "Could not load patients: " + ex.getMessage()
            );
        }
    }
}
