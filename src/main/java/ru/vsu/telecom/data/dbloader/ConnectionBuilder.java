package ru.vsu.telecom.data.dbloader;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * interface to get connection
 * @author Burdyug Pavel
 */
public interface ConnectionBuilder {
    /**
     * Returns db connection
     * @return db connection
     * @throws SQLException when connection not received
     */
    Connection getConnection() throws SQLException;
}
