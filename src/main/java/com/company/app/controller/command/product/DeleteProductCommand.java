package com.company.app.controller.command.product;

import com.company.app.controller.command.ICommand;
import com.company.app.service.impl.ProductService;
import com.company.app.util.constant.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteProductCommand implements ICommand {
    private static final Logger LOG = Logger.getLogger(DeleteProductCommand.class);
    private final ProductService productService;

    public DeleteProductCommand(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Command for delete a product from the database.
     * @param request request to read the command from
     * @param response
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        long code = Long.parseLong(request.getParameter("deleteCode"));

        if(productService.findInCreatedCheckProduct(code)){
            LOG.info("Delete unsuccessful for product " + code);
            request.setAttribute("product_exist_in_created_check_error", "error");
            ListProductCommand list = new ListProductCommand(productService);
            list.execute(request, response);
            return;
        } else if(productService.delete(code)){
            LOG.info("Delete successful for product " + code);
        }
        redirect(request, response, Path.C_LIST_PRODUCT);
    }
}
