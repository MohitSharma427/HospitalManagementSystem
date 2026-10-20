
package dao;

import database.DBConnection;
import model.Doctor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO implements GenericDAO<Doctor> {

    @Override
    public void add(Doctor doctor) throws SQLException {

        String sql = "INSERT INTO doctors " +
                "(name, age, specialization, appointment_fee, available) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, doctor.getName());
            statement.setInt(2, doctor.getAge());
            statement.setString(3, doctor.getSpecialization());
            statement.setDouble(4, doctor.getAppointmentFee());
            statement.setBoolean(5, doctor.isAvailable());

            statement.executeUpdate();

            System.out.println("Doctor added successfully!");
        }
    }

    @Override
    public List<Doctor> getAll() throws SQLException {

        List<Doctor> doctors = new ArrayList<>();

        String sql = "SELECT * FROM doctors";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                Doctor doctor = new Doctor(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getInt("age"),
                        result.getString("specialization"),
                        result.getDouble("appointment_fee")
                );

                doctor.setAvailable(
                        result.getBoolean("available")
                );

                doctors.add(doctor);
            }
        }

        return doctors;
    }
}
