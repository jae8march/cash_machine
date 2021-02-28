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

public class CreateXReportCommand implements ICommand {
    private static final Logger LOG = Logger.getLogger(CreateXReportCommand.class);
    private final ReportService reportService;
    private final CheckService checkService;

    public CreateXReportCommand(ReportService reportService, CheckService checkService) {
        this.reportService = reportService;
        this.checkService = checkService;
    }

    /**
     * Generates reports type X.
     * @param request request to read the command from
     * @param response
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        formater.format(timestamp);
        Date date = new Date(timestamp.getTime());
        Report report = new Report();

        long count = 0;

        /** If there are reports of type z for today return error to the user */
        count = reportService.getZReportsTypeByData(date);

        if(count>0){
            LOG.error("There were reports type Z in " + date);
            request.setAttribute("Xreport_error_Zreport", "error");
            forward(request, response, Path.C_LIST_REPORT);
            return;
        }
        LOG.info("There were no reports type Z in " + date);

        /** Find and set earnings for current shifts */
        double sumNow = checkService.findAllChecksTypeByData(date);
        report.setNowCash(sumNow);
        LOG.info("Found sum for this shift");

        /** Find and set earnings for previous shifts */
        double sumBefore = checkService.findAllChecksTypeByDateBefore(date);
        report.setBeforeCash(sumBefore);
        LOG.info("Found sum for previous shifts");

        /** Create report */
        Timestamp ts= new Timestamp(System.currentTimeMillis());
        report.setDate(ts);
        report.setReportType(ReportType.X);
        reportService.create(report);
        LOG.info("Report was created");
        request.setAttribute("index_created_Xreport", "error");
        forward(request, response, Path.C_LIST_REPORT);
    }
}
