package com.company.app.controller.command.user;

import com.company.app.controller.command.ICommand;
import com.company.app.service.impl.UserService;
import com.company.app.util.constant.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteUserCommand implements ICommand {
    private static final Logger LOG = Logger.getLogger(DeleteUserCommand.class);
    private final UserService userService;

    public DeleteUserCommand(UserService userService) {
        this.userService = userService;
    }

    /**
     * Deletes user and goes to user list page.
     * @param request request to read the command from
     * @param response
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        long id = Long.parseLong(request.getParameter("idUser"));
        String login = request.getParameter("login");

        if(userService.getCount(login, "cashier") > 0) {
            request.setAttribute("created_check_exist", "not delete ");
            ListUsersCommand list = new ListUsersCommand(userService);
            list.execute(request,response);
            return;
        } else if(userService.delete(id)){
            LOG.info("Delete successful for user " + id);
        }
        redirect(request, response, Path.C_LIST_USER);
    }
}
