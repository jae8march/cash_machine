package com.company.app.controller.command.report;
//TODO WORK
import com.company.app.controller.command.ICommand;
import com.company.app.dao.entity.Check;
import com.company.app.dao.entity.Report;
import com.company.app.dao.entity.enumeration.ReportType;
import com.company.app.service.impl.CheckService;
import com.company.app.service.impl.ReportService;
import com.company.app.util.constant.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

public class CreateZReportCommand implements ICommand {
    private static final Logger LOG = Logger.getLogger(CreateZReportCommand.class);
    ReportService reportService;
    CheckService checkService;

    public CreateZReportCommand(ReportService reportService, CheckService checkService) {
        this.reportService = reportService;
        this.checkService = checkService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        formater.format(timestamp);
        Report report = new Report();
        int count=0;

        List<Check> listOpenCheck = checkService.getAllNewChecks();
        for (Check r: listOpenCheck){
            Check rep=r;
            if(String.valueOf(rep.getCheckStatus()).equals("CREATED")){
                count++;
            }
        }

        List<Report> listToday = reportService.getAllReportsTypeByData(timestamp);
        for (Report r: listToday){//проверка наличия Z отчета за сегоднящний день
            Report rep=r;
            if(String.valueOf(rep.getReportType()).equals("Z")){
                count++;
            }
        }

        if(count>0){//Z отчет есть - прерываем комманду
//            request.setAttribute();
            return Path.CHIEF_CASHIER;
        }

        List<Check> checkBeforTime = checkService.findAllChecksTypeByDateBefore(timestamp);
        if(checkBeforTime.isEmpty()){
            report.setBeforeCash(0);
        } else {
            double sum = 0;
            for(Check c: checkBeforTime){
                Check ch=c;
                sum += ch.getCheckPrice();
            }
            report.setBeforeCash(sum);
        }

        List<Check> checkAllTime = checkService.getAllFinishedChecks();
        if(checkAllTime.isEmpty()){
            report.setTotalCash(0);
        } else {
            double sum = 0;
            for(Check c: checkAllTime){
                Check ch=c;
                sum += ch.getCheckPrice();
            }
            report.setTotalCash(sum);
        }

        Timestamp ts= new Timestamp(System.currentTimeMillis());
        report.setDate(ts);
        report.setReportType(ReportType.Z);
        reportService.create(report);

        return Path.REPORT;
    }
}
