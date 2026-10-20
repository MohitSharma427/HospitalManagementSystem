
package dao;

import database.DBConnection;
import model.Room;
import java.sql.*;
import java.util.*;

public class RoomDAO implements GenericDAO<Room> {

    @Override
    public void add(Room room) throws SQLException {
        String sql = "INSERT INTO rooms " +
                "(room_number, room_type, price_per_day, available) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, room.getRoomNumber());
            p.setString(2, room.getRoomType());
            p.setDouble(3, room.getPricePerDay());
            p.setBoolean(4, room.isAvailable());
            p.executeUpdate();
        }
    }

    @Override
    public List<Room> getAll() throws SQLException {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT * FROM rooms";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet r = p.executeQuery()) {
            while (r.next()) {
                Room room = new Room(
                    r.getInt("room_id"),
                    r.getInt("room_number"),
                    r.getString("room_type"),
                    r.getDouble("price_per_day")
                );
                room.setAvailable(r.getBoolean("available"));
                list.add(room);
            }
        }
        return list;
    }
}
