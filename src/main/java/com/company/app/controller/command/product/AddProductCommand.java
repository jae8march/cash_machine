package com.company.app.controller.command.product;

import com.company.app.controller.command.ICommand;
import com.company.app.controller.command.check.DetailsCheckCommand;
import com.company.app.dao.entity.Product;
import com.company.app.service.impl.ProductService;
import com.company.app.util.constant.Path;
import com.company.app.util.valid.Validator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddProductCommand implements ICommand {
    private static final Logger LOG = Logger.getLogger(AddProductCommand.class);
    ProductService productService;

    public AddProductCommand(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Checks input data and enters the product into the database.
     * @param request request to read the command from
     * @param response
     * @return
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String weight = request.getParameter("weight");
        String quantity = request.getParameter("quantity");
        String weightSold = request.getParameter("weightSold");
        boolean howWeightSold=false;

        if(Validator.isValidNull(code)){
            request.setAttribute("code_error_message", "Fill in code");
            return Path.NEW_PRODUCT;
        }

        if(!Validator.isValidInt(code)){
            request.setAttribute("code_int_error_message", "Code must be an integer");
            return Path.NEW_PRODUCT;
        }

        if(Validator.isValidNull(name)){
            request.setAttribute("name_error_message", "Fill in name");
            return Path.NEW_PRODUCT;
        }

        if(Validator.isValidNull(price)){
            request.setAttribute("price_error_message", "Fill in price");
            return Path.NEW_PRODUCT;
        }

        if(!(Validator.isValidDouble(price)
                || Validator.isValidInt(price))){
            request.setAttribute("price_int_error_message", "Price must be a number");
            return Path.NEW_PRODUCT;
        }

        if(Double.parseDouble(price)==0){
            request.setAttribute("price_int_error_message", "Price must be a number");
            return Path.NEW_PRODUCT;
        }

        if(Validator.isValidNull(quantity)){
            request.setAttribute("quantity_error_message", "Fill in quantity");
            return Path.NEW_PRODUCT;
        }

        if(!Validator.isValidInt(quantity)){
            request.setAttribute("quantity_int_error_message", "Quantity must be an integer");
            return Path.NEW_PRODUCT;
        }

        if(Validator.isValidNull(weight)){
            request.setAttribute("weight_error_message", "Fill in weight");
            return Path.NEW_PRODUCT;
        }

        if(!(Validator.isValidDouble(weight)
                || Validator.isValidInt(weight))){
            request.setAttribute("weight_number_error_message", "Weight must be a number");
            return Path.NEW_PRODUCT;
        }
        if(weightSold.equals("yes")){
            howWeightSold=true;
        }

        double weightMath = (double) Math.round(Double.parseDouble(weight) * 100) / 100;

        Product product = new Product(Long.parseLong(code), name, Double.parseDouble(price), Long.parseLong(quantity), weightMath, howWeightSold);

        if(product.getQuantity()==0 && !product.isWeightSold()){
            request.setAttribute("how_sold_quantity_error_message", "No information on quantity");
            return Path.NEW_PRODUCT;
        }

        if(product.getWeight()==0 && product.isWeightSold()){
            request.setAttribute("how_sold_weight_error_message", "No information on weight");
            return Path.NEW_PRODUCT;
        }

        if (product.isWeightSold()){
            product.setQuantity(0);
        } else {
            product.setWeight(0);
        }

        if (!productService.create(product)){
            request.setAttribute("product_error_message", "This product exists");
            return Path.NEW_PRODUCT;
        }

        LOG.info("Product added success for " + product.toString());
        ListProductCommand list = new ListProductCommand(productService);
        list.execute(request, response);

        try {
            request.getServletContext().getRequestDispatcher(Path.ALL_PRODUCT).forward(request, response);
        } catch (ServletException | IOException e) {
            LOG.error(e.getMessage());
        }
        return Path.ALL_PRODUCT;
    }
}
