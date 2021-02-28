package com.company.app.dao.impl;

import com.company.app.dao.entity.Report;
import com.company.app.dao.entity.enumeration.ReportType;
import com.company.app.dao.interfaceDao.IDAO;
import com.company.app.dao.interfaceDao.IReportDAO;
import com.company.app.util.constant.Column;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Extending class ReportDAO for working with a table 'report'.
 */
public class ReportDAO implements IReportDAO {
    private static final Logger LOG = Logger.getLogger(ReportDAO.class);
    private Connection connection;

    /** Queries for working with MySQL*/
    private static final String SQL_CREATE_REPORT = "INSERT INTO report " +
            "(report_type, report_date, report_cash_before, report_cash_after, report_total) VALUES (?,?,?,?,?)";
    private static final String SQL_DELETE_REPORT = "DELETE FROM report WHERE report_id=?";
    private static final String SQL_REPORT_DATA_TYPE = "SELECT * FROM report where DATE(report_date) = ? AND report_type=?";
    private static final String SQL_REPORT_DATA_TODAY = "SELECT count(*) AS count FROM report WHERE DATE(report_date) = ? AND report_type='Z'";

    public ReportDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * {@link IDAO#newObject(Object)}
     */
    @Override
    public boolean newObject(Report entity) {
        try (PreparedStatement stmt = connection.prepareStatement(SQL_CREATE_REPORT)){

            stmt.setString(1, String.valueOf(entity.getReportType()));
            stmt.setTimestamp(2, entity.getDate());
            stmt.setDouble(3, entity.getBeforeCash());
            stmt.setDouble(4, entity.getNowCash());
            stmt.setDouble(5, entity.getTotalCash());

            stmt.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            return false;
        }
        return true;
    }

    public long findAllZReportsTypeByData(Date date) {
        try (PreparedStatement stmt = connection.prepareStatement(SQL_REPORT_DATA_TODAY)){
            stmt.setDate(1, date);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getLong("count");
            }

            close(rs);
            connection.close();
        } catch (SQLException e) {
            return -1;
        }
        return 0;
    }

    @Override
    public List<Report> findAllReportsTypeByDataByType(Date date, String type) {
        List<Report> reports = new ArrayList<>();
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
            LOG.error(e.getMessage());
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
