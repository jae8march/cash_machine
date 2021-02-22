package com.company.app.dao.impl;
//TODO WORK
import com.company.app.dao.connection.FactoryDAO;
import com.company.app.dao.entity.Report;
import com.company.app.dao.entity.enumeration.ReportType;
import com.company.app.dao.interfaceDao.IDAO;
import com.company.app.dao.interfaceDao.IReportDAO;
import com.company.app.util.constant.Column;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO implements IReportDAO {
    private static final Logger LOG = Logger.getLogger(ReportDAO.class);
    private Connection connection;
    private static ReportDAO instance;

    /** Queries for working with MySQL*/
    private static final String SQL_CREATE_REPORT = "INSERT INTO report " +
            "(report_date, report_cash_before, report_cash_after, report_total, report_type) VALUES (?,?,?,?,?)";
    private static final String SQL_FIND_ALL = "SELECT * FROM report";
    private static final String SQL_DELETE_REPORT = "DELETE FROM report WHERE report_id=?";
    private static final String SQL_REPORT_DATA_TYPE = "SELECT * FROM report where DATE(?) and report_type=?";
    private static final String SQL_REPORT_DATA_TODAY = "SELECT * FROM checks WHERE DATE(?);";

    public static ReportDAO getInstance() {
        if (instance == null) {
            instance = new ReportDAO();
        }
        return instance;
    }

    /**
     * {@link IDAO#newObject(Object)}
     */
    @Override
    public boolean newObject(Report entity) {
        connection = FactoryDAO.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(SQL_CREATE_REPORT)){

            stmt.setTimestamp(1, entity.getDate());
            stmt.setDouble(2, entity.getBeforeCash());
            stmt.setDouble(3, entity.getNowCash());
            stmt.setDouble(4, entity.getTotalCash());
            stmt.setString(5, String.valueOf(entity.getReportType()));
            stmt.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            return false;
        }
        return true;
    }

    public List<Report> findAllReportsTypeByData(Timestamp date) {
        List<Report> reports = new ArrayList<>();
        connection = FactoryDAO.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(SQL_REPORT_DATA_TODAY)){
            stmt.setTimestamp(1, date);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reports.add(mapper(rs));
            }

            close(rs);
            connection.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return reports;
    }

    public List<Report> findAllReportsTypeByDataByType(Date date, String type) {
        List<Report> reports = new ArrayList<>();
        connection = FactoryDAO.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(SQL_REPORT_DATA_TYPE)){
            stmt.setDate(1, date);
            stmt.setString(2,type);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reports.add(mapper(rs));
            }

            close(rs);
            connection.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return reports;
    }

    public List<Report> findAll(){
        return mapper(SQL_FIND_ALL);
    }

    /**
     * {@link IDAO#update(Object)}
     */
    @Override
    public boolean update(Report entity) {
        return false;
    }

    /**
     * {@link IDAO#deleteObject(long)}
     */
    @Override
    public boolean deleteObject(long id) {
        connection = FactoryDAO.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(SQL_DELETE_REPORT)){
            stmt.setLong(1, id);

            stmt.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<Report> mapper(String sql){
        List<Report> reports = new ArrayList<>();
        connection = FactoryDAO.getConnection();
        try (Statement stmt = connection.createStatement()){

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                reports.add(mapper(rs));
            }

            close(rs);
            connection.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return reports;
    }

    /**
     * {@link IDAO#mapper(ResultSet)}
     */
    @Override
    public Report mapper(ResultSet resultSet) {
        Report report = new Report();
        try {
            report.setId(resultSet.getLong(Column.REPORT_ID));
            report.setReportType(ReportType.valueOf(resultSet.getString(Column.REPORT_TYPE)));
            report.setDate(resultSet.getTimestamp(Column.REPORT_DATA));
            report.setBeforeCash(resultSet.getDouble(Column.REPORT_BEFORE));
            report.setNowCash(resultSet.getDouble(Column.REPORT_AFTER));
            report.setTotalCash(resultSet.getDouble(Column.REPORT_TOTAL));
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return report;
    }

    /**
     * {@link AutoCloseable#close()}
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@link IDAO#close(ResultSet)}
     */
    @Override
    public void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                LOG.error(e.getMessage());
            }
        }
    }

    /**
     * {@link IDAO#close(PreparedStatement)}
     */
    @Override
    public void close(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                LOG.error(e.getMessage());
            }
        }
    }

    /**
     * {@link IDAO#close(Statement)}
     */
    @Override
    public void close(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                LOG.error(e.getMessage());
            }
        }
    }
}
