package com.company.app.controller.command.report;

import com.company.app.controller.command.ICommand;
import com.company.app.util.constant.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListReportCommand implements ICommand {
    /**
     * Go to page with report.
     * @param request request to read the command from
     * @param response
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        forward(request, response, Path.REPORT);
    }
}
