package com.company.app.controller.command.check;

import com.company.app.controller.command.ICommand;
import com.company.app.dao.entity.Check;
import com.company.app.dao.entity.User;
import com.company.app.service.impl.CheckService;
import com.company.app.util.constant.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddCheckCommand implements ICommand {
    private static final Logger LOG = Logger.getLogger(AddCheckCommand.class);
    private final CheckService checkService;

    public AddCheckCommand(CheckService checkService) {
        this.checkService = checkService;
    }

    /**
     * Creates the check, set login and adds to the database.
     * @param request request to read the command from
     * @param response
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        Check check = new Check();
        check.setUser(user);
        checkService.create(check);
        LOG.info("Check added success");
        redirect(request, response, Path.C_LIST_CHECK);
    }
}
