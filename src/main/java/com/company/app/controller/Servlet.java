package com.company.app.controller;

import com.company.app.controller.command.CommandFactory;
import com.company.app.controller.command.ICommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Main servlet controller.
 */
public class Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        proccessRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        proccessRequest(req, resp);
    }

    /**
     * Main method of this controller.
     */
    private void proccessRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        CommandFactory commandFactory = CommandFactory.commandFactory();
        ICommand command = commandFactory.commandFactory(req);
        command.execute(req,resp);
    }
}
