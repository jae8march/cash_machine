package com.company.app.dao.connection;

import com.company.app.dao.impl.CheckDAO;
import com.company.app.dao.impl.ReportDAO;
import com.company.app.dao.impl.UserDAO;
import com.company.app.dao.impl.ProductDAO;
import java.sql.Connection;

/**
 * Connection for DAO's and factory pattern for service.
 */
public class FactoryDAO {
    private static FactoryDAO instance;

    public static Connection getConnection() {
        return ConnectionPool.getConnection();
    }

    public static FactoryDAO getInstance(){
        if (instance==null)
            instance = new FactoryDAO();
        return instance;
    }

    /** Getters */
    public UserDAO getUserDAO() {
        return new UserDAO(getConnection());
    }

    public CheckDAO getCheckDAO() {
        return new CheckDAO(getConnection());
    }

    public ProductDAO getProductDAO() {
        return new ProductDAO(getConnection());
    }

    public ReportDAO getReportDAO() {
        return new ReportDAO(getConnection());
    }
}
