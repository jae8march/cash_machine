package com.company.app.controller.command.product;
//TODO WORK
import com.company.app.controller.command.ICommand;
import com.company.app.service.impl.ProductService;
import com.company.app.util.constant.Path;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Command for delete a product from the database.
 */
public class DeleteProductCommand implements ICommand {
    private static final Logger LOG = Logger.getLogger(DeleteProductCommand.class);
    ProductService productService;

    public DeleteProductCommand(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        long code = Long.parseLong(request.getParameter("deleteCode"));

        if(productService.delete(code)){
            LOG.info("Delete successful for product " + code);
            request.setAttribute("index_message", "Product was deleted successfully");
        } else {
            LOG.info("Delete unsuccessful for product " + code);
            request.setAttribute("sql_error_message", "Product wasn't deleted");
        }

        ListProductCommand list = new ListProductCommand(productService);
        list.execute(request,response);
        try {
            response.sendRedirect(request.getContextPath() + Path.ALL_PRODUCT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Path.ALL_PRODUCT;
    }
}
