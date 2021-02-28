package com.company.app.controller.command.product;

import com.company.app.controller.command.ICommand;
import com.company.app.dao.entity.Product;
import com.company.app.service.impl.ProductService;
import com.company.app.util.constant.Path;
import com.company.app.util.valid.Validator;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddProductCommand implements ICommand {
    private static final Logger LOG = Logger.getLogger(AddProductCommand.class);
    private final ProductService productService;

    public AddProductCommand(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Checks input data and enters the product into the database.
     * @param request request to read the command from
     * @param response
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String weight = request.getParameter("weight");
        String quantity = request.getParameter("quantity");
        String weightSold = request.getParameter("weightSold");
        String error = "error";
        boolean howWeightSold=false;

        if(Validator.isValidNull(code)){
            LOG.error("Empty code");
            request.setAttribute("code_error_message", error);
            forward(request, response, Path.NEW_PRODUCT);
            return;
        }

        if(!Validator.isValidInt(code)){
            LOG.error("Not int code");
            request.setAttribute("code_int_error_message", error);
            forward(request, response, Path.NEW_PRODUCT);
            return;
        }

        if(Validator.isValidNull(name)){
            LOG.error("Empty name");
            request.setAttribute("name_error_message", error);
            forward(request, response, Path.NEW_PRODUCT);
            return;
        }

        if(Validator.isValidNull(price)){
            LOG.error("Empty price");
            request.setAttribute("price_error_message", error);
            forward(request, response, Path.NEW_PRODUCT);
            return;
        }

        if(!(Validator.isValidDouble(price) || Validator.isValidInt(price))){
            LOG.error("Not int or double price");
            request.setAttribute("price_int_error_message", error);
            forward(request, response, Path.NEW_PRODUCT);
            return;
        }

        if(Double.parseDouble(price)==0){
            LOG.error("Price == 0");
            request.setAttribute("price_int_error_message", error);
            forward(request, response, Path.NEW_PRODUCT);
            return;
        }

        if(Validator.isValidNull(quantity)){
            LOG.error("Empty quantity");
            request.setAttribute("quantity_error_message", error);
            forward(request, response, Path.NEW_PRODUCT);
            return;
        }

        if(!Validator.isValidInt(quantity)){
            LOG.error("Not int quantity");
            request.setAttribute("quantity_int_error_message", error);
            forward(request, response, Path.NEW_PRODUCT);
            return;
        }

        if(Validator.isValidNull(weight)){
            LOG.error("Not int or double weight");
            request.setAttribute("weight_error_message", error);
            forward(request, response, Path.NEW_PRODUCT);
            return;
        }

        if(!(Validator.isValidDouble(weight) || Validator.isValidInt(weight))){
            LOG.error("Not int or double weight");
            request.setAttribute("weight_number_error_message", error);
            forward(request, response, Path.NEW_PRODUCT);
            return;
        }

        if(weightSold.equals("yes")){
            howWeightSold=true;
        }

        double weightMath = (double) Math.round(Double.parseDouble(weight) * 100) / 100;

        Product product = new Product(Long.parseLong(code), name, Double.parseDouble(price), Long.parseLong(quantity), weightMath, howWeightSold);

        if(product.getQuantity()==0 && !product.isWeightSold()){
            LOG.error("No info: quantity");
            request.setAttribute("how_sold_quantity_error_message", error);
            forward(request, response, Path.NEW_PRODUCT);
            return;
        }

        if(product.getWeight()==0 && product.isWeightSold()){
            LOG.error("No info: weight");
            request.setAttribute("how_sold_weight_error_message", error);
            forward(request, response, Path.NEW_PRODUCT);
            return;
        }

        if (product.isWeightSold()){
            product.setQuantity(0);
        } else {
            product.setWeight(0);
        }

        if (!productService.create(product)){
            request.setAttribute("product_error_message", error);
            forward(request, response, Path.NEW_PRODUCT);
            return;
        }

        redirect(request, response, Path.C_LIST_PRODUCT);
    }
}
