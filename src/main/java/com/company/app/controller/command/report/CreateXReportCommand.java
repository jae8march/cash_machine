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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

public class CreateXReportCommand implements ICommand {
    private static final Logger LOG = Logger.getLogger(CreateXReportCommand.class);
    ReportService reportService;
    CheckService checkService;

    public CreateXReportCommand(ReportService reportService, CheckService checkService) {
        this.reportService = reportService;
        this.checkService = checkService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        formater.format(timestamp);
        Report report = new Report();

        List<Report> listToday = reportService.getAllReportsTypeByData(timestamp);

        int count=0;
        for (Report r: listToday){//проверка наличия Z отчета за сегоднящний день
            if(String.valueOf(r.getReportType()).equals("Z")){
                count++;
            }
        }
        if(count>0){//Z отчет есть - прерываем комманду
//            request.setAttribute();
            return Path.REPORT;
        }


        List<Check> checkToday = checkService.findAllChecksTypeByData(timestamp);
        if(checkToday.isEmpty()){
            report.setNowCash(0);
        } else {
            double sum = 0;
            for(Check c: checkToday){
                sum += c.getCheckPrice();
            }
            report.setNowCash(sum);
        }

        List<Check> checkAllTime = checkService.findAllChecksTypeByDateBefore(timestamp);
        if(checkAllTime.isEmpty()){
            report.setBeforeCash(0);
        } else {
            double sum = 0;
            for(Check c: checkAllTime){
                sum += c.getCheckPrice();
            }
            report.setBeforeCash(sum);
        }

        Timestamp ts= new Timestamp(System.currentTimeMillis());
        report.setDate(ts);
        report.setReportType(ReportType.X);
        reportService.create(report);

        return Path.REPORT;
    }
}
