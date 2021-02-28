package com.company.app.controller.command.check;

import com.company.app.controller.command.ICommand;
import com.company.app.dao.entity.Check;
import com.company.app.dao.entity.Product;
import com.company.app.dao.entity.User;
import com.company.app.dao.entity.enumeration.CheckStatus;
import com.company.app.dao.entity.enumeration.UserRole;
import com.company.app.service.impl.CheckService;
import com.company.app.service.impl.ProductService;
import com.company.app.util.constant.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ChangeStatusCheckCommand implements ICommand {
    private static final Logger LOG = Logger.getLogger(ChangeStatusCheckCommand.class);
    private final CheckService checkService;
    private final ProductService productService;

    public ChangeStatusCheckCommand(CheckService checkService, ProductService productService) {
        this.checkService = checkService;
        this.productService = productService;
    }
    /**
     * Changes check status. If the check has the status "cancelled", it cannot be changed.
     * @param request request to read the command from
     * @param response
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        final String error = "error";
        User user = (User) request.getSession().getAttribute("user");
        long id = Long.parseLong(request.getParameter("changeStatusId"));
        String status = request.getParameter("status");

        Check check = checkService.getById(id);

        if(status.equalsIgnoreCase("CLOSED")&& user.getUserRole().equals(UserRole.CASHIER)){
            if(check.getCheckStatus().equals(CheckStatus.CLOSED)){
                LOG.info("Close unsuccessful for check " + id);
                request.setAttribute("check_closed", error);
                DetailsCheckCommand checkCommand = new DetailsCheckCommand(checkService);
                checkCommand.execute(request, response);
                return;
            } else {
                checkService.changeStatusCheck(id, "CLOSED");
                DetailsCheckCommand checkCommand = new DetailsCheckCommand(checkService);
                checkCommand.execute(request, response);
                return;
            }
        } else if(status.equalsIgnoreCase("CANCELLED")&& user.getUserRole().equals(UserRole.CHIEF_CASHIER)){
            if(check.getCheckStatus().equals(CheckStatus.CANCELLED)){
                LOG.info("Cancelled unsuccessful for product " + id);
                request.setAttribute("check_cancelled", error);
                DetailsCheckCommand checkCommand = new DetailsCheckCommand(checkService);
                checkCommand.execute(request, response);
                return;
            } else {
                checkService.changeStatusCheck(id, "CANCELLED");
            }
        } else {
            request.setAttribute("check_status_access", error);
            DetailsCheckCommand checkCommand = new DetailsCheckCommand(checkService);
            checkCommand.execute(request, response);
            return;
        }
        List<Product> productList = checkService.getAllProductInCheck(id);
        Product product;

        for (Product p : productList){
            product = productService.getProductById(p.getCode());
            product.setQuantity(product.getQuantity()+p.getQuantity());
            product.setWeight(product.getWeight()+p.getWeight());
            if(!productService.update(product)){
                productService.create(product);
            }
        }

        redirect(request, response, Path.C_LIST_CHECK);
    }
}
