package com.company.app.dao.interfaceDao;

import com.company.app.dao.entity.Report;

import java.sql.Date;
import java.util.List;

/**
 * Interface for DAO, report entity.
 */
public interface IReportDAO extends IDAO<Report>{
    List<Report> findAllReportsTypeByDataByType(Date date, String type);
}
