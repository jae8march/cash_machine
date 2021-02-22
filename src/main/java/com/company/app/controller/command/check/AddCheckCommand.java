package com.company.app.controller.command.check;

import com.company.app.controller.command.ICommand;
import com.company.app.dao.entity.Check;
import com.company.app.dao.entity.User;
import com.company.app.service.impl.CheckService;
import com.company.app.util.constant.Path;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddCheckCommand implements ICommand {
    private static final Logger LOG = Logger.getLogger(AddCheckCommand.class);
    private CheckService checkService;

    public AddCheckCommand(CheckService checkService) {
        this.checkService = checkService;
    }

    /**
     * Creates the check, set login and adds to the database.
     * @param request request to read the command from
     * @param response
     * @return
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        Check check = new Check();
        check.setUser(user);
        checkService.create(check);
        LOG.info("Check added success");

        ListCheckCommand list = new ListCheckCommand(checkService);
        list.execute(request,response);

        try {
            request.getServletContext().getRequestDispatcher(Path.ALL_CHECKS).forward(request, response);
        } catch (ServletException | IOException e) {
            LOG.error(e.getMessage());
        }
        return Path.ALL_CHECKS;
    }
}
