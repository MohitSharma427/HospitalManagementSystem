
package gui;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(cardLayout);

    public DashboardFrame() {
        setTitle("Hospital Management System");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(29, 42, 65));
        sidebar.setPreferredSize(new Dimension(230, 700));
        sidebar.setLayout(new GridLayout(9, 1, 5, 5));

        JLabel title = new JLabel("  HOSPITAL SYSTEM");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        sidebar.add(title);

        addButton(sidebar, "Dashboard", "Dashboard");
        addButton(sidebar, "Doctors", "Doctors");
        addButton(sidebar, "Patients", "Patients");
        addButton(sidebar, "Appointments", "Appointments");
        addButton(sidebar, "Rooms", "Rooms");
        addButton(sidebar, "Room Bookings", "Room Bookings");
        addButton(sidebar, "Billing", "Billing");
        addButton(sidebar, "Exit", "Exit");

        contentPanel.add(createPage(
                "Hospital Management Dashboard",
                "Welcome! Manage your hospital operations from one place."
        ), "Dashboard");

        contentPanel.add(new DoctorPanel(), "Doctors");
        contentPanel.add(new PatientPanel(), "Patients");

        contentPanel.add(new AppointmentPanel(), "Appointments");
        contentPanel.add(new RoomPanel(), "Rooms");
        contentPanel.add(new RoomBookingPanel(), "Room Bookings");
        contentPanel.add(new BillingPanel(), "Billing");
        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        getContentPane().setBackground(Color.WHITE);
    }

    private void addButton(JPanel sidebar, String text, String page) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 15));
        button.setFocusPainted(false);
        button.setBackground(new Color(43, 58, 84));
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);

        button.addActionListener(e -> {
            if (page.equals("Exit")) {
                dispose();
            } else {
                cardLayout.show(contentPanel, page);
            }
        });

        sidebar.add(button);
    }

    private JPanel createPage(String heading, String description) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 247, 250));

        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setBackground(Color.WHITE);
        box.setBorder(BorderFactory.createEmptyBorder(35, 40, 35, 40));

        JLabel headingLabel = new JLabel(heading);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 27));
        headingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descriptionLabel = new JLabel(description);
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        box.add(headingLabel);
        box.add(Box.createVerticalStrut(20));
        box.add(descriptionLabel);

        panel.add(box);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DashboardFrame().setVisible(true);
        });
    }
}
