package com.company.app.controller.command.check;

import com.company.app.controller.command.ICommand;
import com.company.app.dao.entity.Check;
import com.company.app.service.impl.CheckService;
import com.company.app.util.constant.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DetailsCheckCommand implements ICommand {
    private static final Logger LOG = Logger.getLogger(DetailsCheckCommand.class);
    CheckService checkService;

    public DetailsCheckCommand(CheckService checkService) {
        this.checkService = checkService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        long code = Long.parseLong(request.getParameter("checkId"));//ошибка из addProduct
        Check check = checkService.getById(code);
        check.getUser().getLogin();
        request.setAttribute("check", check);
        request.setAttribute("loginUser", check.getUser().getLogin());
        request.setAttribute("date", check.getCheckDate());
        request.setAttribute("checkId", check.getCheckId());
        return Path.DETAILS_CHECK;
    }
}
