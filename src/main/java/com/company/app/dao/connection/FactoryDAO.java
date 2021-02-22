package com.company.app.dao.connection;

import com.company.app.dao.impl.CheckDAO;
import com.company.app.dao.impl.ReportDAO;
import com.company.app.dao.impl.UserDAO;
import com.company.app.dao.impl.ProductDAO;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connection for DAO's and factory pattern for service.
 */
public class FactoryDAO {
    private static final Logger LOG = Logger.getLogger(FactoryDAO.class);
    private static String URL = "jdbc:mysql://localhost:3306/cash_machine?autoReconnect=true&useSSL=false";
    private static String USER = "root";
    private static String PASSWORD = "root12345";
    private static FactoryDAO instance;

    public static synchronized FactoryDAO getInstance() {
        if (instance == null) {
            instance = new FactoryDAO();
        }
        return instance;
    }

    /**
     * Default method method for connecting.
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /** Getters */
    public UserDAO getUserDAO() {
        return UserDAO.getInstance();
    }

    public CheckDAO getCheckDAO() {
        return CheckDAO.getInstance();
    }

    public ProductDAO getProductDAO() {
        return ProductDAO.getInstance();
    }

    public ReportDAO getReportDAO() {
        return ReportDAO.getInstance();
    }

    private FactoryDAO() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            LOG.error("Cannot find class driver:" + e);
        }
    }
}
