package com.selrvk.inventory;

import java.sql.*;
import java.io.*;

public class DatabaseManager {

    private final String dbURL = "inventorydb.db";

    public Connection connect() throws SQLException{

        return DriverManager.getConnection(dbURL);

    }

}
