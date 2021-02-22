package com.company.app.controller.command.check;

import com.company.app.controller.command.ICommand;
import com.company.app.dao.entity.Product;
import com.company.app.service.impl.CheckService;
import com.company.app.service.impl.ProductService;
import com.company.app.util.constant.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ChangeStatusCheckCommand implements ICommand {
    private static final Logger LOG = Logger.getLogger(ChangeStatusCheckCommand.class);
    CheckService checkService;
    ProductService productService;

    public ChangeStatusCheckCommand(CheckService checkService) {
        this.checkService = checkService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        long id = Long.parseLong(request.getParameter("changeStatusId"));
        String status = request.getParameter("status");

        if(status.equals("CLOSED")){
            if(checkService.changeStatusCheck(id, "CLOSED")){
                LOG.info("Close successful for check " + id);
            }else {
                LOG.info("Close unsuccessful for check " + id);
            }
            ListCheckCommand check = new ListCheckCommand(checkService);
            check.execute(request, response);

            try {
                response.sendRedirect(request.getContextPath() + Path.ALL_CHECKS);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Path.ALL_CHECKS;
        }
        List<Product> productList = checkService.getAllProductInCheck(id);
        Product product;

        if(checkService.changeStatusCheck(id, "CANCELLED")){//устанавливает статус "отменен"
            LOG.info("Cancelled successful for product " + id);
        }else {
            LOG.info("Cancelled unsuccessful for product " + id);
        }
        for (Product p : productList){
            product = productService.getProductById(p.getCode());
            product.setQuantity(product.getQuantity()+p.getQuantity());
            product.setWeight(product.getWeight()+p.getWeight());
            if(!productService.update(product)){
                productService.create(product);
            }
        }

        ListCheckCommand check = new ListCheckCommand(checkService);
        check.execute(request, response);

        try {
            response.sendRedirect(request.getContextPath() + Path.ALL_CHECKS);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Path.ALL_CHECKS;

    }
}
