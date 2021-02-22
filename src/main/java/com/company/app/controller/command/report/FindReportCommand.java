package com.company.app.controller.command.report;

import com.company.app.controller.command.ICommand;
import com.company.app.dao.entity.Report;
import com.company.app.service.impl.ReportService;
import com.company.app.util.constant.Path;
import com.company.app.util.valid.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.List;

public class FindReportCommand implements ICommand {
    private ReportService reportService;

    public FindReportCommand(ReportService reportService) {
        this.reportService = reportService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String date = request.getParameter("date");
        String type = request.getParameter("type");
        if(Validator.isValidNull(date) || Validator.isLegalDate(date)){
            return Path.CHIEF_CASHIER;
        }

        List<Report> reports = reportService.getAllReportsTypeByDataByType(date,type);

        request.setAttribute("reports", reports);
        request.setAttribute("reportType", type);

        return Path.REPORT;
    }
}
