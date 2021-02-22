package com.company.app.controller.command.check;
//TODO WORK
import com.company.app.controller.command.ICommand;
import com.company.app.dao.entity.Check;
import com.company.app.service.impl.CheckService;
import com.company.app.util.constant.Path;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ListCheckCommand implements ICommand {
    private static final Logger LOG = Logger.getLogger(ListCheckCommand.class);
    private CheckService checkService;
    private final int ROWS_ON_PAGE = 7;

    public ListCheckCommand(CheckService checkService) {
        this.checkService = checkService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        long page, checkAmount, nextPage, lastPage;
        String sortBy = request.getParameter("sort");

        if (sortBy == null || sortBy.equals("")) {
            sortBy = "status";
        }

        String pageString = request.getParameter("page");

        if (pageString == null || pageString.isEmpty()) {
            page = 1;
        } else {
            page = Long.parseLong(pageString);
        }

        String nextPageString = request.getParameter("nextPage");
        if ("previous".equals(nextPageString)) {
            nextPage = page - 1;
        } else if ("next".equals(nextPageString)) {
            nextPage = page + 1;
        } else {
            nextPage = page;
        }
        page = nextPage;

        try {
            checkAmount = checkService.getCheckCount();
        } catch (Exception e) {
            try {
                request.getServletContext().getRequestDispatcher(Path.ALL_CHECKS).forward(request, response);
            } catch (ServletException | IOException servletException) {
                servletException.printStackTrace();
            }
            return Path.ALL_CHECKS;
        }

        lastPage = checkAmount / ROWS_ON_PAGE + ((checkAmount % ROWS_ON_PAGE) == 0 ? 0 : 1);

        List<Check> checks;
        try {
            if (lastPage > page) {
                Long offset = (page - 1) * ROWS_ON_PAGE;
                checks = checkService.getCheckSorted(sortBy, ROWS_ON_PAGE, offset);
            } else {
                Long offset = (lastPage - 1) * ROWS_ON_PAGE;
                checks = checkService.getCheckSorted(sortBy, ROWS_ON_PAGE, offset);
                page = lastPage;
            }
            request.setAttribute("sort", sortBy);
            request.setAttribute("checks", checks);
            request.setAttribute("page", page);
            request.setAttribute("lastPage", lastPage);
        }
        catch (Exception e){
            LOG.error(e.getMessage());
        }
        try {
            request.getServletContext().getRequestDispatcher(Path.ALL_CHECKS).forward(request, response);
        } catch (ServletException | IOException e) {
            LOG.error(e.getMessage());
        }
        return Path.ALL_CHECKS;
    }
}
