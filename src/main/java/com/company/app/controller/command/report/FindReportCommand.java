package com.company.app.controller.command.report;

import com.company.app.controller.command.ICommand;
import com.company.app.dao.entity.Report;
import com.company.app.service.impl.ReportService;
import com.company.app.util.constant.Path;
import com.company.app.util.valid.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class FindReportCommand implements ICommand {
    private final ReportService reportService;

    public FindReportCommand(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * Find all report in the specified date and type.
     * @param request request to read the command from
     * @param response
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String date = request.getParameter("date");
        String type = request.getParameter("type");

        if(Validator.isValidNull(date) || Validator.isLegalDate(date)){
            request.setAttribute("incorrect_date", "error");
            forward(request, response, Path.C_LIST_REPORT);
        }

        List<Report> reports = reportService.getAllReportsTypeByDataByType(date,type);

        request.setAttribute("reports", reports);
        request.setAttribute("reportType", type);
        forward(request, response, Path.C_LIST_REPORT);
    }
}
