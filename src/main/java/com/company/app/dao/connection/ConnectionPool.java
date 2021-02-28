package com.company.app.dao.connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Connection pool.
 */
public class ConnectionPool {
    private ConnectionPool(){}

    /**
     * Method for connecting.
     * @return connection
     */
    public static Connection getConnection(){
        Context context;
        Connection connection = null;
        try {
            context = new InitialContext();
            DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/project");
            connection = ds.getConnection();
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
