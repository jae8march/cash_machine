package com.company.app.controller.command.user;

import com.company.app.controller.command.ICommand;
import com.company.app.dao.entity.User;
import com.company.app.dao.entity.enumeration.UserRole;
import com.company.app.service.impl.UserService;
import com.company.app.util.Pagination;
import com.company.app.util.constant.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ListUsersCommand implements ICommand {
    private static final Logger LOG = Logger.getLogger(ListUsersCommand.class);
    private final UserService userService;

    public ListUsersCommand(UserService userService) {
        this.userService = userService;
    }

    /**
     * Lists users in the specified sorting.
     * @param request request to read the command from
     * @param response
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String role = "role";
        long manager = userService.getCount(String.valueOf(UserRole.MANAGER), role);
        long admin = userService.getCount(String.valueOf(UserRole.ADMIN), role);
        long chiefCashier = userService.getCount(String.valueOf(UserRole.CHIEF_CASHIER), role);
        long cashier = userService.getCount(String.valueOf(UserRole.CASHIER), role);
        long all = manager + admin + chiefCashier + cashier;

        String sortBy = request.getParameter("sort");

        if (sortBy == null || sortBy.equals("")) {
            sortBy = "id";
        }

        long page = Pagination.getPage(request.getParameter("page"));
        long nextPage = Pagination.getNextPage(request.getParameter("nextPage"), page);

        page = nextPage;

        long lastPage = all / Pagination.ROWS + ((all % Pagination.ROWS) == 0 ? 0 : 1);

        long offset = Pagination.getOffset(lastPage, page);

        if(lastPage < page){
            page = lastPage;
        }

        List<User> users = userService.findNumberSorted(sortBy, Pagination.ROWS, offset);
        LOG.info("Found all users sorted and with a limit");
        request.setAttribute("sort", sortBy);
        request.setAttribute("page", page);
        request.setAttribute("lastPage", lastPage);
        request.setAttribute("user", users);
        request.setAttribute("manager", manager);
        request.setAttribute("admin", admin);
        request.setAttribute("chiefCashier", chiefCashier);
        request.setAttribute("cashier", cashier);
        request.setAttribute("all", all);

        forward(request, response, Path.ALL_USERS);
    }
}
