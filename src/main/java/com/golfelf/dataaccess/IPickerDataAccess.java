package com.golfelf.dataaccess;

import com.golfelf.drivingrange.Picker;

import java.sql.SQLException;
import java.util.List;

public interface IPickerDataAccess {
    void create(Picker picker) throws Exception;

    Picker getPicker(int id) throws Exception;

    List<Picker> getAllPickers() throws Exception;

    Picker updatePicker(Picker picker) throws Exception;

    void deletePicker(int id) throws Exception;
}
