package com.company.app.dao.interfaceDao;
//TODO WORK
import com.company.app.dao.entity.Report;

import java.util.List;

public interface IReportDAO extends IDAO<Report>{
    List<Report> mapper(String sql);
}
