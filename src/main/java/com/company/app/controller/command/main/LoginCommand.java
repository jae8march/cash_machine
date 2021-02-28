package com.company.app.controller.command.main;

import com.company.app.controller.command.ICommand;
import com.company.app.util.constant.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginCommand implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        forward(request, response, Path.LOGIN);
    }
}
