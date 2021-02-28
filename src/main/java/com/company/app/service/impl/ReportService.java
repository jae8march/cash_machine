package com.company.app.service.impl;

import com.company.app.dao.entity.Report;
import com.company.app.dao.impl.ReportDAO;
import com.company.app.dao.connection.FactoryDAO;
import com.company.app.service.IReportService;
import com.company.app.service.IService;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

/**
 * Service to work with dao, report.
 */
public class ReportService implements IReportService {
    private static final Logger LOG = Logger.getLogger(ReportService.class);
    static FactoryDAO factory = FactoryDAO.getInstance();

    /**
     * {@link IService#create(Object)} 
     */
    @Override
    public boolean create(Report entity){
        try (ReportDAO reportDAO = factory.getReportDAO()) {
            return reportDAO.newObject(entity);
        } catch (Exception e) {
            LOG.error(e);
            return false;
        }
    }

    public long getZReportsTypeByData(Date date) {
        try (ReportDAO reportDao = factory.getReportDAO()) {
            return reportDao.findAllZReportsTypeByData(date);
        } catch (Exception e) {
            LOG.error(e);
            return 0;
        }
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

    /**
     * {@link IService#delete(long)}
     */
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
