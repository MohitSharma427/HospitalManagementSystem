
package dao;

import database.DBConnection;
import model.RoomBooking;
import java.sql.*;
import java.util.*;

public class RoomBookingDAO implements GenericDAO<RoomBooking> {

    @Override
    public void add(RoomBooking b) throws SQLException {
        String sql = "INSERT INTO room_bookings " +
                "(patient_id, room_id, days, check_in_date, status) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, b.getPatientId());
            p.setInt(2, b.getRoomId());
            p.setInt(3, b.getDays());
            p.setDate(4, java.sql.Date.valueOf(b.getCheckInDate()));
            p.setString(5, b.getStatus());
            p.executeUpdate();
        }
    }

    @Override
    public List<RoomBooking> getAll() throws SQLException {
        List<RoomBooking> list = new ArrayList<>();
        String sql = "SELECT * FROM room_bookings";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet r = p.executeQuery()) {
            while (r.next()) {
                RoomBooking b = new RoomBooking(
                    r.getInt("booking_id"),
                    r.getInt("patient_id"),
                    r.getInt("room_id"),
                    r.getInt("days"),
                    r.getDate("check_in_date").toLocalDate()
                );
                b.setStatus(r.getString("status"));
                list.add(b);
            }
        }
        return list;
    }
}
