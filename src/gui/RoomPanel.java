
package gui;

import dao.RoomDAO;
import model.Room;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RoomPanel extends JPanel {

    private final JTextField numberField = new JTextField();
    private final JTextField typeField = new JTextField();
    private final JTextField priceField = new JTextField();

    private final DefaultTableModel tableModel =
            new DefaultTableModel(
                    new String[]{"Room ID", "Room Number", "Type",
                            "Price Per Day", "Available"}, 0
            );

    private final RoomDAO roomDAO = new RoomDAO();

    public RoomPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));

        form.add(new JLabel("Room Number:"));
        form.add(numberField);

        form.add(new JLabel("Room Type:"));
        form.add(typeField);

        form.add(new JLabel("Price Per Day:"));
        form.add(priceField);

        JButton addButton = new JButton("Add Room");
        JButton refreshButton = new JButton("Refresh");

        form.add(addButton);
        form.add(refreshButton);

        JTable table = new JTable(tableModel);

        add(form, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        addButton.addActionListener(e -> addRoom());
        refreshButton.addActionListener(e -> loadRooms());

        loadRooms();
    }

    private void addRoom() {
        try {
            int number = Integer.parseInt(numberField.getText().trim());
            String type = typeField.getText().trim();
            double price = Double.parseDouble(priceField.getText().trim());

            if (number <= 0 || type.isEmpty() || price < 0) {
                JOptionPane.showMessageDialog(
                        this, "Enter valid room details."
                );
                return;
            }

            Room room = new Room(0, number, type, price);
            roomDAO.add(room);

            JOptionPane.showMessageDialog(
                    this, "Room added successfully!"
            );

            numberField.setText("");
            typeField.setText("");
            priceField.setText("");

            loadRooms();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this, "Room number and price must be numbers."
            );
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this, "Could not add room: " + ex.getMessage()
            );
        }
    }

    private void loadRooms() {
        try {
            tableModel.setRowCount(0);

            List<Room> rooms = roomDAO.getAll();

            for (Room room : rooms) {
                tableModel.addRow(new Object[]{
                        room.getRoomId(),
                        room.getRoomNumber(),
                        room.getRoomType(),
                        room.getPricePerDay(),
                        room.isAvailable() ? "Yes" : "No"
                });
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this, "Could not load rooms: " + ex.getMessage()
            );
        }
    }
}
