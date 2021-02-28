package com.company.app.controller.command.check;

import com.company.app.controller.command.ICommand;
import com.company.app.dao.entity.Check;
import com.company.app.dao.entity.User;
import com.company.app.service.impl.CheckService;
import com.company.app.util.Pagination;
import com.company.app.util.constant.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ListCheckCommand implements ICommand {
    private static final Logger LOG = Logger.getLogger(ListCheckCommand.class);
    private final CheckService checkService;

    public ListCheckCommand(CheckService checkService) {
        this.checkService = checkService;
    }

    /**
     * Lists checks in the specified sorting. If the user's role is cashier, the list of his checks.
     * @param request request to read the command from
     * @param response
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        String sortBy = request.getParameter("sort");

        if (sortBy == null || sortBy.equals("")) {
            sortBy = "status";
        }

        long page = Pagination.getPage(request.getParameter("page"));
        long nextPage = Pagination.getNextPage(request.getParameter("nextPage"), page);

        page = nextPage;

        long count = checkService.getCheckCount();
        long lastPage = count / Pagination.ROWS + ((count % Pagination.ROWS) == 0 ? 0 : 1);

        long offset = Pagination.getOffset(lastPage, page);

        if(lastPage < page){
            page = lastPage;
        }

        List<Check> checks = checkService.getCheckSorted(sortBy, Pagination.ROWS, offset, user);
        LOG.info("Found all checks sorted and with a limit");
        request.setAttribute("sort", sortBy);
        request.setAttribute("checks", checks);
        request.setAttribute("page", page);
        request.setAttribute("lastPage", lastPage);

        forward(request, response, Path.ALL_CHECKS);
    }
}
