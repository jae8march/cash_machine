package com.company.app.service.impl;
//TODO WORK
import com.company.app.dao.entity.Report;
import com.company.app.dao.impl.ReportDAO;
import com.company.app.dao.connection.FactoryDAO;
import com.company.app.service.IReportService;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class ReportService implements IReportService {
    private static final Logger LOG = Logger.getLogger(ReportService.class);
    static FactoryDAO factory = FactoryDAO.getInstance();

    public boolean create(Report report){
        try (ReportDAO reportDAO = factory.getReportDAO()) {
            return reportDAO.newObject(report);
        } catch (Exception e) {
            LOG.error(e);
            return false;
        }
    }

    public List<Report> getAllReportsTypeByData(Timestamp date) {
        List<Report> reports = new ArrayList<>();
        try (ReportDAO reportDao = factory.getReportDAO()) {
            reports = reportDao.findAllReportsTypeByData(date);
        } catch (Exception e) {
            LOG.error(e);
        }
        return reports;
    }

    public List<Report> getAllReportsTypeByDataByType(String date, String type) {
        List<Report> reports = new ArrayList<>();
        try (ReportDAO reportDao = factory.getReportDAO()) {
            reports = reportDao.findAllReportsTypeByDataByType(Date.valueOf(date), type);
        } catch (Exception e) {
            LOG.error(e);
        }
        return reports;
    }

    @Override
    public List<Report> getAll() {
        List<Report> reports = new ArrayList<>();
        try (ReportDAO reportDao = factory.getReportDAO()) {
            reports = reportDao.findAll();
        } catch (Exception e) {
            LOG.error(e);
        }
        return reports;
    }

    @Override
    public boolean delete(long id) {
        try (ReportDAO reportDao = factory.getReportDAO()) {
            reportDao.deleteObject(id);
        } catch (Exception e) {
            LOG.error(e);
            return false;
        }
        return true;
    }
}
