
package dao;

import database.DBConnection;
import model.Bill;
import java.sql.*;
import java.util.*;

public class BillDAO implements GenericDAO<Bill> {

    @Override
    public void add(Bill bill) throws SQLException {
        String sql = "INSERT INTO bills " +
                "(patient_id, consultation_fee, medicine_fee, room_fee, bill_date) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, bill.getPatientId());
            p.setDouble(2, bill.getConsultationFee());
            p.setDouble(3, bill.getMedicineFee());
            p.setDouble(4, bill.getRoomFee());
            p.setDate(5, java.sql.Date.valueOf(bill.getBillDate()));
            p.executeUpdate();
        }
    }

    @Override
    public List<Bill> getAll() throws SQLException {
        List<Bill> list = new ArrayList<>();
        String sql = "SELECT * FROM bills";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet r = p.executeQuery()) {
            while (r.next()) {
                Bill bill = new Bill(
                    r.getInt("bill_id"),
                    r.getInt("patient_id"),
                    r.getDouble("consultation_fee"),
                    r.getDouble("medicine_fee"),
                    r.getDouble("room_fee")
                );
                list.add(bill);
            }
        }
        return list;
    }
}
