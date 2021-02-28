package com.company.app.controller.command.report;

import com.company.app.controller.command.ICommand;
import com.company.app.dao.entity.Report;
import com.company.app.dao.entity.enumeration.ReportType;
import com.company.app.service.impl.CheckService;
import com.company.app.service.impl.ReportService;
import com.company.app.util.constant.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class CreateZReportCommand implements ICommand {
    private static final Logger LOG = Logger.getLogger(CreateZReportCommand.class);
    private final ReportService reportService;
    private final CheckService checkService;

    public CreateZReportCommand(ReportService reportService, CheckService checkService) {
        this.reportService = reportService;
        this.checkService = checkService;
    }

    /**
     * Generates reports type Z.
     * @param request request to read the command from
     * @param response
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        final String error = "error";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        formater.format(timestamp);
        Date date = new Date(timestamp.getTime());
        Report report = new Report();
        long count = 0;

        /** If there are checks with the "created" status or reports of type z for today return error to the user */
        count = checkService.getAllNewChecks();
        if (count>0){
            LOG.error("There were checks with 'CREATED' status in " + date);
            request.setAttribute("Zreport_error_created", error);
            forward(request, response, Path.C_LIST_REPORT);
            return;
        }

        count += reportService.getZReportsTypeByData(date);

        if (count>0){
            LOG.error("There were reports type Z in " + date);
            request.setAttribute("Zreport_error_Zreport", error);
            forward(request, response, Path.C_LIST_REPORT);
            return;
        }

        /** Find and set earnings for previous shifts */
        double sumBefore = checkService.findAllChecksTypeByDateBefore(date);
        report.setBeforeCash(sumBefore);
        LOG.info("Found sum for previous shifts");

        /** Find and set earnings for all time */
        double sumAll = checkService.getAllFinishedChecks();
        report.setTotalCash(sumAll);
        LOG.info("Found sum for all shifts");

        /** Create report */
        Timestamp ts= new Timestamp(System.currentTimeMillis());
        report.setDate(ts);
        report.setReportType(ReportType.Z);
        reportService.create(report);
        LOG.info("Report was created");
        request.setAttribute("index_created_Zreport", error);
        forward(request, response, Path.C_LIST_REPORT);
    }
}
