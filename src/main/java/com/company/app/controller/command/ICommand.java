package com.company.app.controller.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ICommand {
    /**
     * This method reads a command from the request and processes it.
     * The result will be given as a page to forward to.
     *
     * @param request request to read the command from
     * @param response
     */
    void execute(HttpServletRequest request, HttpServletResponse response);

    default void redirect(HttpServletRequest request, HttpServletResponse response, String path) {
        try {
            response.sendRedirect(request.getContextPath() + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    default void forward(HttpServletRequest request, HttpServletResponse response, String path) {
        try {
            request.getServletContext().getRequestDispatcher(path).forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}