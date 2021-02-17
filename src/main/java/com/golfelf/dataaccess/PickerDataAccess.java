package com.golfelf.dataaccess;

import com.golfelf.drivingrange.Picker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.golfelf.util.DBConnectionManager;
import org.apache.log4j.Logger;

public class PickerDataAccess implements IPickerDataAccess{
    private Logger logger = Logger.getLogger("PickerDataAccess");

    @Override
    public void create(Picker picker) throws Exception {
        try {
            Connection conn = DBConnectionManager.dbConnection;
            String insertStatement = "INSERT INTO driving_range.picker(name, type, throughput)" +
                " VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(insertStatement);
            stmt.setString(1, picker.getName());
            stmt.setString(2, picker.getType());
            stmt.setInt(3, picker.getThroughput());
            stmt.executeUpdate();
        } catch (Exception e) {
            logger.warn(e.toString());
            throw e;
        }
    }

    @Override
    public Picker getPicker(int id) throws Exception {
        Picker result = null;
        try {
            Connection conn = DBConnectionManager.dbConnection;
            String query = "SELECT id, name, type, throughput FROM driving_range.picker" +
                    " WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            result = new Picker(rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getInt(4)
            );
        } catch (Exception e) {
            logger.warn(e.toString());
            throw e;
        } finally {
            return result;
        }
    }

    @Override
    public List<Picker> getAllPickers() throws Exception {
        List<Picker> result = new ArrayList<>();
        try {
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
        } catch (Exception e) {
            logger.warn(e.toString());
            throw e;
        } finally {
            return result;
        }
    }

    @Override
    public Picker updatePicker(Picker picker) throws Exception {
        Picker result = null;
        try {
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
            result = picker;
        } catch (Exception e) {
            logger.warn(e.toString());
            throw e;
        } finally {
            return result;
        }
    }

    @Override
    public void deletePicker(int id) throws Exception {
        try {
            Connection conn = DBConnectionManager.dbConnection;
            String deleteStatement = "DELETE FROM driving_range.picker " +
                    "WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(deleteStatement);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            logger.warn(e.toString());
            throw e;
        }
    }
}
