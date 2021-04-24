package ru.vsu.telecom.data.dbloader;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Burdyug Pavel
 */
public interface ConnectionBuilder {
    Connection getConnection() throws SQLException;
}
