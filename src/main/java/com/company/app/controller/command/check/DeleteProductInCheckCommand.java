package com.company.app.controller.command.check;

import com.company.app.controller.command.ICommand;
import com.company.app.dao.entity.Check;
import com.company.app.dao.entity.Product;
import com.company.app.dao.entity.User;
import com.company.app.dao.entity.enumeration.UserRole;
import com.company.app.service.impl.CheckService;
import com.company.app.service.impl.ProductService;
import com.company.app.util.constant.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteProductInCheckCommand implements ICommand {
    private final CheckService checkService;
    private final ProductService productService;

    public DeleteProductInCheckCommand(CheckService checkService, ProductService productService) {
        this.checkService = checkService;
        this.productService = productService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        long code = Long.parseLong(request.getParameter("deleteCode"));
        long id = Long.parseLong(request.getParameter("deleteFromCheckId"));

        if(!user.getUserRole().equals(UserRole.CHIEF_CASHIER)){
            request.setAttribute("check_status_access", "error");
            DetailsCheckCommand list = new DetailsCheckCommand(checkService);
            list.execute(request, response);
            return;
        }
        Product product = productService.getProductById(code);
        Product productInCheck = checkService.getProductById(id, code);

        product.setQuantity(product.getQuantity()+productInCheck.getQuantity());
        double round = (double) Math.round((product.getWeight()+productInCheck.getWeight()) * 1000) / 1000;
        product.setWeight(round);

        Check check = checkService.getById(id);
        check.setCheckPrice((double) Math.round((check.getCheckPrice()-productInCheck.getPrice()) * 1000) / 1000);
        checkService.update(check);

        productService.update(product);
        checkService.delete(code);

        redirect(request, response, Path.C_DETAILS_CHECK);
    }
}
