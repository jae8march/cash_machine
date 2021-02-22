package com.company.app.controller.command.check;

import com.company.app.controller.command.ICommand;
import com.company.app.dao.entity.Check;
import com.company.app.dao.entity.Product;
import com.company.app.dao.entity.enumeration.CheckStatus;
import com.company.app.service.impl.CheckService;
import com.company.app.service.impl.ProductService;
import com.company.app.util.constant.Path;
import com.company.app.util.valid.Validator;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddProductInCheckCommand implements ICommand {
    private static final Logger LOG = Logger.getLogger(AddProductInCheckCommand.class);
    ProductService productService;
    CheckService checkService;


    public AddProductInCheckCommand(ProductService productService, CheckService checkService) {
        this.productService = productService;
        this.checkService = checkService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        long id = Long.parseLong(request.getParameter("addProductInCheck"));
        String addBy = request.getParameter("addBy");
        String designation = request.getParameter("designation");
        String howSold = request.getParameter("howSold");
        String howMany = request.getParameter("howMany");
        double newPrice;

        Check check = checkService.getById(id);
        if(!check.getCheckStatus().equals(CheckStatus.CREATED)){
            return Path.DETAILS_CHECK;
        }

        Product productInBase = new Product();
        Product productInCheck;

        if (addBy.equals("productName")) {
            productInBase = productService.getProductName(designation);
        } else if(addBy.equals("productCode")){
            productInBase = productService.getProductById(Long.parseLong(designation));
        }

        Product testProduct = checkService.getProductById(id,productInBase.getCode());

        if(testProduct != null){
            return Path.DETAILS_CHECK;
        }

        if(productInBase.getPrice() == 0){
            request.setAttribute("product_error_not_found", "Product does not exist ");
            DetailsCheckCommand list = new DetailsCheckCommand(checkService);
            list.execute(request,response);
            try {
                response.sendRedirect(request.getContextPath() + Path.DETAILS_CHECK);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Path.DETAILS_CHECK;
        }

        productInCheck = productInBase;

        if(productInCheck.isWeightSold() && isValidWeight(howMany, request, productInBase)){
            productInCheck.setWeight(Double.parseDouble(howMany));
            double newWeight = (double) Math.round((productInBase.getWeight() - Double.parseDouble(howMany)) * 100) / 100;
            productInBase.setWeight(newWeight);
            newPrice = productInBase.getPrice()*productInBase.getWeight();
            productInCheck.setPrice(newPrice);
        } else if(!productInBase.isWeightSold() && isValidQuantity(howMany, request, productInBase)){
            productInCheck.setQuantity(Long.parseLong(howMany));
            productInBase.setQuantity(productInBase.getQuantity()-Long.parseLong(howMany));
            newPrice = productInBase.getPrice()*productInBase.getQuantity();
            productInCheck.setPrice(newPrice);
        } else{
            return Path.DETAILS_CHECK;
        }

        productService.update(productInBase);
        productInBase.setCheck(check);
        productInBase.getCheck().setCheckId(id);
        checkService.updateContentCheck(productInBase);

        check.setCheckPrice(check.getCheckPrice()+newPrice);
        checkService.update(check);

        DetailsCheckCommand list = new DetailsCheckCommand(checkService);
        list.execute(request,response);//ошибка
        try {
            response.sendRedirect(request.getContextPath() + Path.DETAILS_CHECK);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Path.DETAILS_CHECK;
    }

    public boolean isValidWeight(String value, HttpServletRequest request, Product product) {
        if(!(Validator.isValidDouble(value)
                || Validator.isValidInt(value)
                ||Validator.isValidNull(value))){
            request.setAttribute("weight_number_error_message", "Weight must be a number");
            return false;
        } else if(product.getWeight()<Double.parseDouble(value)){
            request.setAttribute("weight_table_error_message", "There is no such quantity of goods");
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
            request.setAttribute("quantity_table_error_message", "There is no such quantity of goods");
            return false;
        }
        return true;
    }

}
