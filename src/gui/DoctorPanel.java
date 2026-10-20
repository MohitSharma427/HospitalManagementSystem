
package gui;

import dao.DoctorDAO;
import model.Doctor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DoctorPanel extends JPanel {

    private final JTextField nameField = new JTextField(15);
    private final JTextField ageField = new JTextField(5);
    private final JTextField specializationField = new JTextField(15);
    private final JTextField feeField = new JTextField(8);

    private final DefaultTableModel tableModel =
            new DefaultTableModel(
                    new String[]{"ID", "Name", "Age", "Specialization", "Fee"},
                    0
            );

    private final DoctorDAO doctorDAO = new DoctorDAO();

    public DoctorPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));

        form.add(new JLabel("Doctor Name:"));
        form.add(nameField);

        form.add(new JLabel("Age:"));
        form.add(ageField);

        form.add(new JLabel("Specialization:"));
        form.add(specializationField);

        form.add(new JLabel("Consultation Fee:"));
        form.add(feeField);

        JButton addButton = new JButton("Add Doctor");
        JButton refreshButton = new JButton("Refresh");

        form.add(addButton);
        form.add(refreshButton);

        JTable table = new JTable(tableModel);

        add(form, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        addButton.addActionListener(e -> addDoctor());
        refreshButton.addActionListener(e -> loadDoctors());

        loadDoctors();
    }

    private void addDoctor() {
        try {
            String name = nameField.getText().trim();
            String specialization = specializationField.getText().trim();

            if (name.isEmpty() || specialization.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this, "Enter the name and specialization."
                );
                return;
            }

            int age = Integer.parseInt(ageField.getText().trim());
            double fee = Double.parseDouble(feeField.getText().trim());

            if (age <= 0 || age > 120 || fee < 0) {
                JOptionPane.showMessageDialog(
                        this, "Enter a valid age and fee."
                );
                return;
            }

            Doctor doctor = new Doctor(
                    0, name, age, specialization, fee
            );

            doctorDAO.add(doctor);

            JOptionPane.showMessageDialog(
                    this, "Doctor added successfully!"
            );

            nameField.setText("");
            ageField.setText("");
            specializationField.setText("");
            feeField.setText("");

            loadDoctors();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this, "Age and fee must be valid numbers."
            );
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this, "Could not add doctor: " + ex.getMessage()
            );
        }
    }

    private void loadDoctors() {
        try {
            tableModel.setRowCount(0);

            List<Doctor> doctors = doctorDAO.getAll();

            for (Doctor d : doctors) {
                tableModel.addRow(new Object[]{
                        d.getId(),
                        d.getName(),
                        d.getAge(),
                        d.getSpecialization(),
                        d.getAppointmentFee()
                });
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this, "Could not load doctors: " + ex.getMessage()
            );
        }
    }
}
