
package dao;

import database.DBConnection;
import model.Appointment;
import java.sql.*;
import java.util.*;

public class AppointmentDAO implements GenericDAO<Appointment> {

    @Override
    public void add(Appointment a) throws SQLException {
        String sql = "INSERT INTO appointments " +
                "(patient_id, doctor_id, appointment_date, appointment_time, status) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, a.getPatientId());
            p.setInt(2, a.getDoctorId());
            p.setDate(3, java.sql.Date.valueOf(a.getAppointmentDate()));
            p.setTime(4, Time.valueOf(a.getAppointmentTime()));
            p.setString(5, a.getStatus());
            p.executeUpdate();
        }
    }

    @Override
    public List<Appointment> getAll() throws SQLException {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM appointments";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet r = p.executeQuery()) {
            while (r.next()) {
                Appointment a = new Appointment(
                    r.getInt("appointment_id"),
                    r.getInt("patient_id"),
                    r.getInt("doctor_id"),
                    r.getDate("appointment_date").toLocalDate(),
                    r.getTime("appointment_time").toLocalTime()
                );
                a.setStatus(r.getString("status"));
                list.add(a);
            }
        }
        return list;
    }
}


