package com.company.app.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

public interface ICommand extends Serializable {
     long serialVersionUID = 1L;

    /**
     * This method reads a command from the request and processes it.
     * The result will be given as a page to forward to.
     *
     * @param request request to read the command from
     * @param response
     * @return forward page
     */
    String execute(HttpServletRequest request, HttpServletResponse response);
}