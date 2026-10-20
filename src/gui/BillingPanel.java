
package gui;

import dao.BillDAO;
import dao.PatientDAO;
import model.Bill;
import model.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BillingPanel extends JPanel {

    private final JTextField patientIdField = new JTextField();
    private final JTextField consultationField = new JTextField();
    private final JTextField medicineField = new JTextField();
    private final JTextField roomField = new JTextField();

    private final DefaultTableModel tableModel =
            new DefaultTableModel(
                    new String[]{"Bill ID", "Patient ID",
                            "Consultation", "Medicine", "Room", "Total", "Date"},
                    0
            );

    private final BillDAO billDAO = new BillDAO();
    private final PatientDAO patientDAO = new PatientDAO();

    public BillingPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));

        form.add(new JLabel("Patient ID:"));
        form.add(patientIdField);

        form.add(new JLabel("Consultation Fee:"));
        form.add(consultationField);

        form.add(new JLabel("Medicine Fee:"));
        form.add(medicineField);

        form.add(new JLabel("Room Fee:"));
        form.add(roomField);

        JButton generateButton = new JButton("Generate Bill");
        JButton refreshButton = new JButton("Refresh");

        form.add(generateButton);
        form.add(refreshButton);

        add(form, BorderLayout.NORTH);
        add(new JScrollPane(new JTable(tableModel)), BorderLayout.CENTER);

        generateButton.addActionListener(e -> generateBill());
        refreshButton.addActionListener(e -> loadBills());

        loadBills();
    }

    private void generateBill() {
        try {
            int patientId = Integer.parseInt(
                    patientIdField.getText().trim());

            double consultation = Double.parseDouble(
                    consultationField.getText().trim());

            double medicine = Double.parseDouble(
                    medicineField.getText().trim());

            double room = Double.parseDouble(
                    roomField.getText().trim());

            if (consultation < 0 || medicine < 0 || room < 0) {
                JOptionPane.showMessageDialog(
                        this, "Fees cannot be negative.");
                return;
            }

            List<Patient> patients = patientDAO.getAll();

            boolean patientExists = patients.stream()
                    .anyMatch(p -> p.getId() == patientId);

            if (!patientExists) {
                JOptionPane.showMessageDialog(
                        this, "Enter an existing patient ID.");
                return;
            }

            Bill bill = new Bill(
                    0, patientId, consultation, medicine, room);

            billDAO.add(bill);

            JOptionPane.showMessageDialog(
                    this,
                    String.format("Bill generated successfully!%nTotal: ₹%.2f",
                            bill.calculateTotal()));

            patientIdField.setText("");
            consultationField.setText("");
            medicineField.setText("");
            roomField.setText("");

            loadBills();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this, "Enter valid numeric values.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this, "Could not generate bill: " + ex.getMessage());
        }
    }

    private void loadBills() {
        try {
            tableModel.setRowCount(0);

            List<Bill> bills = billDAO.getAll();

            for (Bill bill : bills) {
                tableModel.addRow(new Object[]{
                        bill.getBillId(),
                        bill.getPatientId(),
                        bill.getConsultationFee(),
                        bill.getMedicineFee(),
                        bill.getRoomFee(),
                        bill.calculateTotal(),
                        bill.getBillDate()
                });
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this, "Could not load bills: " + ex.getMessage());
        }
    }
}
