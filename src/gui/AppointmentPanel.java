
package gui;

import dao.AppointmentDAO;
import dao.PatientDAO;
import dao.DoctorDAO;
import model.Appointment;
import model.Patient;
import model.Doctor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AppointmentPanel extends JPanel {

    private final JTextField patientIdField = new JTextField();
    private final JTextField doctorIdField = new JTextField();
    private final JTextField dateField = new JTextField("2026-10-20");
    private final JTextField timeField = new JTextField("10:00");

    private final DefaultTableModel tableModel =
            new DefaultTableModel(
                    new String[]{"Appointment ID", "Patient ID",
                            "Doctor ID", "Date", "Time", "Status"}, 0
            );

    private final AppointmentDAO appointmentDAO = new AppointmentDAO();
    private final PatientDAO patientDAO = new PatientDAO();
    private final DoctorDAO doctorDAO = new DoctorDAO();

    public AppointmentPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));

        form.add(new JLabel("Patient ID:"));
        form.add(patientIdField);

        form.add(new JLabel("Doctor ID:"));
        form.add(doctorIdField);

        form.add(new JLabel("Date (YYYY-MM-DD):"));
        form.add(dateField);

        form.add(new JLabel("Time (HH:MM):"));
        form.add(timeField);

        JButton bookButton = new JButton("Book Appointment");
        JButton refreshButton = new JButton("Refresh");

        form.add(bookButton);
        form.add(refreshButton);

        add(form, BorderLayout.NORTH);
        add(new JScrollPane(new JTable(tableModel)), BorderLayout.CENTER);

        bookButton.addActionListener(e -> bookAppointment());
        refreshButton.addActionListener(e -> loadAppointments());

        loadAppointments();
    }

    private void bookAppointment() {
        try {
            int patientId = Integer.parseInt(patientIdField.getText().trim());
            int doctorId = Integer.parseInt(doctorIdField.getText().trim());

            List<Patient> patients = patientDAO.getAll();
            List<Doctor> doctors = doctorDAO.getAll();

            boolean patientExists = patients.stream()
                    .anyMatch(p -> p.getId() == patientId);

            boolean doctorExists = doctors.stream()
                    .anyMatch(d -> d.getId() == doctorId);

            if (!patientExists || !doctorExists) {
                JOptionPane.showMessageDialog(
                        this, "Enter an existing patient ID and doctor ID."
                );
                return;
            }

            LocalDate date = LocalDate.parse(dateField.getText().trim());
            LocalTime time = LocalTime.parse(timeField.getText().trim());

            if (date.isBefore(LocalDate.now())) {
                JOptionPane.showMessageDialog(
                        this, "Choose today or a future date."
                );
                return;
            }

            Appointment appointment = new Appointment(
                    0, patientId, doctorId, date, time
            );

            appointmentDAO.add(appointment);

            JOptionPane.showMessageDialog(
                    this, "Appointment booked successfully!"
            );

            loadAppointments();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this, "Could not book appointment: " + ex.getMessage()
            );
        }
    }

    private void loadAppointments() {
        try {
            tableModel.setRowCount(0);

            for (Appointment a : appointmentDAO.getAll()) {
                tableModel.addRow(new Object[]{
                        a.getAppointmentId(),
                        a.getPatientId(),
                        a.getDoctorId(),
                        a.getAppointmentDate(),
                        a.getAppointmentTime(),
                        a.getStatus()
                });
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this, "Could not load appointments: " + ex.getMessage()
            );
        }
    }
}
