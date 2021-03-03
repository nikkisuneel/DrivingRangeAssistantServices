package com.golfelf.dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.golfelf.drivingrange.Picker;
import com.golfelf.util.DBConnectionManager;

public class PickerSQLDataAccess implements IPickerDataAccess{

    @Override
    public void create(Picker picker) throws IllegalArgumentException, SQLException {
        if (picker == null) {
            throw new IllegalArgumentException("picker cannot be null");
        }

        Connection conn = DBConnectionManager.dbConnection;
        String insertStatement = "INSERT INTO driving_range.picker(name, type, throughput)" +
            " VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(insertStatement);
        stmt.setString(1, picker.getName());
        stmt.setString(2, picker.getType());
        stmt.setInt(3, picker.getThroughput());
        stmt.executeUpdate();
    }

    @Override
    public Picker getPicker(int id) throws SQLException {
        Picker result = new Picker();

        Connection conn = DBConnectionManager.dbConnection;
        String query = "SELECT id, name, type, throughput FROM driving_range.picker" +
                " WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            result.setId(rs.getInt(1));
            result.setName(rs.getString(2));
            result.setType(rs.getString(3));
            result.setThroughput(rs.getInt(4));
        }

        return result;
    }

    @Override
    public Picker getPickerByName(String name) throws IllegalArgumentException, SQLException {
        Picker result = new Picker();

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name cannot be null or empty");
        }

        Connection conn = DBConnectionManager.dbConnection;
        String query = "SELECT id, name, type, throughput FROM driving_range.picker" +
                " WHERE name = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, name);
        ResultSet rs = stmt.executeQuery();
        int count = 0;
        while (rs.next()) {
            count++;
            result.setId(rs.getInt(1));
            result.setName(rs.getString(2));
            result.setType(rs.getString(3));
            result.setThroughput(rs.getInt(4));
        }

        if (count > 1) {
            throw new RuntimeException("Got more than one record for " + name);
        }

        return result;
    }

    @Override
    public List<Picker> getAllPickers() throws SQLException {
        List<Picker> result = new ArrayList<>();

        Connection conn = DBConnectionManager.dbConnection;
        String query = "SELECT id, name, type, throughput FROM driving_range.picker";
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()) {
            Picker p = new Picker(rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getInt(4)
            );
            result.add(p);
        }
        return result;
    }

    @Override
    public Picker updatePicker(Picker picker) throws SQLException {
        Connection conn = DBConnectionManager.dbConnection;
        String updateStatement = "UPDATE driving_range.picker " +
                " SET name = ?, type = ?, throughput = ? " +
                "WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(updateStatement);
        stmt.setString(1, picker.getName());
        stmt.setString(2, picker.getType());
        stmt.setInt(3, picker.getThroughput());
        stmt.setInt(4, picker.getId());
        stmt.executeUpdate();

        return picker;
    }

    @Override
    public void deletePicker(int id) throws SQLException {
        Connection conn = DBConnectionManager.dbConnection;
        String deleteStatement = "DELETE FROM driving_range.picker " +
                "WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(deleteStatement);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }
}
