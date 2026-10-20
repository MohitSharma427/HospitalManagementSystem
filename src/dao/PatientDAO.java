
package dao;

import database.DBConnection;
import model.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO implements GenericDAO<Patient> {

    @Override
    public void add(Patient patient) throws SQLException {

        String sql = "INSERT INTO patients " +
                "(name, age, disease, phone) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, patient.getName());
            statement.setInt(2, patient.getAge());
            statement.setString(3, patient.getDisease());
            statement.setString(4, patient.getPhone());

            statement.executeUpdate();

            System.out.println("Patient registered successfully!");
        }
    }

    @Override
    public List<Patient> getAll() throws SQLException {

        List<Patient> patients = new ArrayList<>();

        String sql = "SELECT * FROM patients";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                Patient patient = new Patient(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getInt("age"),
                        result.getString("disease"),
                        result.getString("phone")
                );

                patients.add(patient);
            }
        }

        return patients;
    }
}
