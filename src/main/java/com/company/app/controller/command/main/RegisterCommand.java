package com.company.app.controller.command.main;

import com.company.app.controller.command.ICommand;
import com.company.app.util.constant.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterCommand implements ICommand {
    /**
     * Go to page REGISTRATION.
     * @param request request to read the command from
     * @param response
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        forward(request, response, Path.REGISTRATION);
    }
}
