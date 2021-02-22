package com.company.app.controller.command.check;

import com.company.app.controller.command.ICommand;
import com.company.app.dao.entity.Product;
import com.company.app.service.impl.CheckService;
import com.company.app.service.impl.ProductService;
import com.company.app.util.constant.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteProductInCheckCommand implements ICommand {
    CheckService checkService;
    ProductService productService;

    public DeleteProductInCheckCommand(CheckService checkService, ProductService productService) {
        this.checkService = checkService;
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        long code = Long.parseLong(request.getParameter("deleteCode"));
        long id = Long.parseLong(request.getParameter("deleteFromCheckId"));

        Product product = productService.getProductById(code);
        Product productInCheck = checkService.getProductById(id, code);

        product.setQuantity(product.getQuantity()+productInCheck.getQuantity());
        product.setWeight((double) Math.round((product.getWeight()+productInCheck.getWeight()) * 100) / 100);
        productService.update(product);
        checkService.deleteProductInCheck(code);

        DetailsCheckCommand check = new DetailsCheckCommand(checkService);
        check.execute(request, response);

        request.setAttribute("checkId", code);
        try {
            response.sendRedirect(request.getContextPath() + Path.DETAILS_CHECK);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Path.DETAILS_CHECK;
    }
}
