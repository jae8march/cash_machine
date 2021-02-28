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
import com.company.app.util.valid.Validator;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddProductInCheckCommand implements ICommand {
    private static final Logger LOG = Logger.getLogger(AddProductInCheckCommand.class);
    private final ProductService productService;
    private final CheckService checkService;

    public AddProductInCheckCommand(ProductService productService, CheckService checkService) {
        this.productService = productService;
        this.checkService = checkService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        final String error = "error";
        User user = (User) request.getSession().getAttribute("user");
        if(!user.getUserRole().equals(UserRole.CASHIER)){
            LOG.error("user role: " + user.getUserRole().toString());
            request.setAttribute("check_status_access", error);
            DetailsCheckCommand checkCommand = new DetailsCheckCommand(checkService);
            checkCommand.execute(request, response);
            return;
        }

        long id = Long.parseLong(request.getParameter("addProductInCheck"));
        String addBy = request.getParameter("addBy");
        String designation = request.getParameter("designation");
        String howMany = request.getParameter("howMany");
        double newPrice;

        request.setAttribute("checkId", id);

        Check check = checkService.getById(id);
        if(!check.getCheckStatus().equals(CheckStatus.CREATED)){
            request.setAttribute("check_status_not_created", error);
            forward(request, response, Path.C_DETAILS_CHECK);
            return;
        }

        Product productInBase = new Product();
        Product productInCheck = new Product();

        if (addBy.equals("productName")) {
            productInBase = productService.getProductName(designation);
        } else if(addBy.equals("productCode")){
            if(!Validator.isValidInt(designation)){
                request.setAttribute("code_not_int", error);
                forward(request, response, Path.C_DETAILS_CHECK);
                return;
            }
            productInBase = productService.getProductById(Long.parseLong(designation));
        }

        if(productInBase ==null){
            request.setAttribute("product_not_exist", error);
            forward(request, response, Path.C_DETAILS_CHECK);
            return;
        }

        Product testProduct = checkService.getProductById(id,productInBase.getCode());

        if(testProduct == null){
            request.setAttribute("product_exist_check", error);
            forward(request, response, Path.C_DETAILS_CHECK);
            return;
        }

        if(productInBase.isWeightSold() && isValidWeight(howMany, request, productInBase)){
            double weight = Double.parseDouble(howMany);
            productInCheck.setWeight(weight);
            double round = (double) Math.round((productInBase.getWeight() - weight) * 1000) / 1000;
            productInBase.setWeight(round);
            newPrice = (double) Math.round((productInBase.getPrice() * weight) * 1000) / 1000;
            productInCheck.setPrice(newPrice);
        } else if(!productInBase.isWeightSold() && isValidQuantity(howMany, request, productInBase)){
            long quantity = Long.parseLong(howMany);
            productInCheck.setQuantity(quantity);
            productInBase.setQuantity(productInBase.getQuantity() - quantity);
            newPrice = (double) Math.round((productInBase.getPrice() * quantity) * 1000) / 1000;
            productInCheck.setPrice(newPrice);
        } else{
            forward(request, response, Path.C_DETAILS_CHECK);
            return;
        }

        productService.update(productInBase);

        productInCheck.setCode(productInBase.getCode());
        productInCheck.setName(productInBase.getName());
        productInCheck.setWeightSold(productInBase.isWeightSold());
        productInCheck.setCheck(check);
        checkService.updateContentCheck(productInCheck);

        check.setCheckPrice(check.getCheckPrice()+productInCheck.getPrice());
        checkService.update(check);

        redirect(request, response, Path.C_DETAILS_CHECK);
    }

    public boolean isValidWeight(String value, HttpServletRequest request, Product product) {
        if(!(Validator.isValidDouble(value)
                || Validator.isValidInt(value))
                ||Validator.isValidNull(value)){
            request.setAttribute("weight_number_error_message", "Weight must be a number");
            return false;
        } else if(product.getWeight()<Double.parseDouble(value)){
            request.setAttribute("table_error_message", "There is no such quantity of goods");
            return false;
        }
        return true;
    }

    public boolean isValidQuantity(String value, HttpServletRequest request, Product product) {
        if(!(Validator.isValidInt(value)
                ||Validator.isValidNull(value))){
            request.setAttribute("quantity_error_message", "Quantity must be a number");
            return false;
        } else if(product.getQuantity()<Double.parseDouble(value)){
            request.setAttribute("table_error_message", "There is no such quantity of goods");
            return false;
        }
        return true;
    }

}
