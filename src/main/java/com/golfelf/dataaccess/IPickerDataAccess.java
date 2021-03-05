/*
 * Copyright (c) 2021. Nikhila (Nikki) Suneel. All Rights Reserved.
 */

package com.golfelf.dataaccess;

import com.golfelf.drivingrange.Picker;

import java.sql.SQLException;
import java.util.List;

public interface IPickerDataAccess {
    void create(Picker picker) throws IllegalArgumentException, SQLException;

    Picker getPicker(int id) throws IllegalArgumentException, SQLException;

    Picker getPickerByName(String name) throws IllegalArgumentException, SQLException;

    List<Picker> getAllPickers() throws SQLException;

    Picker updatePicker(Picker picker) throws IllegalArgumentException, SQLException;

    void deletePicker(int id) throws SQLException;
}
