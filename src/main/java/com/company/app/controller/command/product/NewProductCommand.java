package com.company.app.controller.command.product;

import com.company.app.controller.command.ICommand;
import com.company.app.util.constant.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NewProductCommand implements ICommand {
    /**
     * Go to page NEW PRODUCT.
     * @param request request to read the command from
     * @param response
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        forward(request, response, Path.NEW_PRODUCT);
    }
}
