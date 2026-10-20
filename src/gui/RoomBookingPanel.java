
package gui;

import dao.RoomBookingDAO;
import dao.PatientDAO;
import dao.RoomDAO;
import model.RoomBooking;
import model.Patient;
import model.Room;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class RoomBookingPanel extends JPanel {

    private final JTextField patientIdField = new JTextField();
    private final JTextField roomIdField = new JTextField();
    private final JTextField daysField = new JTextField();
    private final JTextField dateField =
            new JTextField(LocalDate.now().toString());

    private final DefaultTableModel tableModel =
            new DefaultTableModel(
                    new String[]{"Booking ID", "Patient ID", "Room ID",
                            "Days", "Check-in Date", "Status"}, 0
            );

    private final RoomBookingDAO bookingDAO = new RoomBookingDAO();
    private final PatientDAO patientDAO = new PatientDAO();
    private final RoomDAO roomDAO = new RoomDAO();

    public RoomBookingPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));

        form.add(new JLabel("Patient ID:"));
        form.add(patientIdField);

        form.add(new JLabel("Room ID:"));
        form.add(roomIdField);

        form.add(new JLabel("Number of Days:"));
        form.add(daysField);

        form.add(new JLabel("Check-in Date (YYYY-MM-DD):"));
        form.add(dateField);

        JButton bookButton = new JButton("Book Room");
        JButton refreshButton = new JButton("Refresh");

        form.add(bookButton);
        form.add(refreshButton);

        add(form, BorderLayout.NORTH);
        add(new JScrollPane(new JTable(tableModel)), BorderLayout.CENTER);

        bookButton.addActionListener(e -> bookRoom());
        refreshButton.addActionListener(e -> loadBookings());

        loadBookings();
    }

    private void bookRoom() {
        try {
            int patientId = Integer.parseInt(patientIdField.getText().trim());
            int roomId = Integer.parseInt(roomIdField.getText().trim());
            int days = Integer.parseInt(daysField.getText().trim());
            LocalDate date = LocalDate.parse(dateField.getText().trim());

            if (days <= 0) {
                JOptionPane.showMessageDialog(
                        this, "Number of days must be greater than zero."
                );
                return;
            }

            boolean patientExists = patientDAO.getAll().stream()
                    .anyMatch(p -> p.getId() == patientId);

            Room room = roomDAO.getAll().stream()
                    .filter(r -> r.getRoomId() == roomId)
                    .findFirst()
                    .orElse(null);

            if (!patientExists || room == null || !room.isAvailable()) {
                JOptionPane.showMessageDialog(
                        this, "Check the patient ID and room availability."
                );
                return;
            }

            RoomBooking booking = new RoomBooking(
                    0, patientId, roomId, days, date
            );

            bookingDAO.add(booking);

            JOptionPane.showMessageDialog(
                    this, "Room booking saved successfully!"
            );

            loadBookings();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this, "Could not book room: " + ex.getMessage()
            );
        }
    }

    private void loadBookings() {
        try {
            tableModel.setRowCount(0);

            List<RoomBooking> bookings = bookingDAO.getAll();

            for (RoomBooking b : bookings) {
                tableModel.addRow(new Object[]{
                        b.getBookingId(),
                        b.getPatientId(),
                        b.getRoomId(),
                        b.getDays(),
                        b.getCheckInDate(),
                        b.getStatus()
                });
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this, "Could not load bookings: " + ex.getMessage()
            );
        }
    }
}
