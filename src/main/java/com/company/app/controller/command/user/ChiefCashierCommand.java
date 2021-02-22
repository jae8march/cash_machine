package com.company.app.controller.command.user;

import com.company.app.controller.command.ICommand;
import com.company.app.util.constant.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChiefCashierCommand implements ICommand {
    /**
     * Go to home page CHIEF_CASHIER.
     * @param request request to read the command from
     * @param response
     * @return
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return Path.CHIEF_CASHIER;
    }
}


