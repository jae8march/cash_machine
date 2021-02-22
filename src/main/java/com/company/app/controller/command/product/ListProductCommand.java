package com.company.app.controller.command.product;
//TODO WORK
import com.company.app.controller.command.ICommand;
import com.company.app.dao.entity.Product;
import com.company.app.service.impl.ProductService;
import com.company.app.util.constant.Path;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ListProductCommand implements ICommand {
    private static final Logger LOG = Logger.getLogger(ListProductCommand.class);
    private ProductService productService;

    private final int ROWS_ON_PAGE = 7;

    public ListProductCommand(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        long page, productAmount, nextPage, lastPage;
        String sortBy = request.getParameter("sort");

        if (sortBy == null || sortBy.equals("")) {
            sortBy = "code";
        }

        String pageString = request.getParameter("page");

        if (pageString == null || pageString.isEmpty()) {
            page = 1;
        } else {
            page = Long.parseLong(pageString);
        }

        String nextPageString = request.getParameter("nextPage");
        if ("previous".equals(nextPageString)) {
            nextPage = page - 1;
        } else if ("next".equals(nextPageString)) {
            nextPage = page + 1;
        } else {
            nextPage = page;
        }
        page = nextPage;

        try {
            productAmount = productService.getProductCount();
        } catch (Exception e) {
            try {
                request.getServletContext().getRequestDispatcher(Path.ALL_PRODUCT).forward(request, response);
            } catch (ServletException | IOException servletException) {
                servletException.printStackTrace();
            }
            return Path.ALL_PRODUCT;
        }

        lastPage = productAmount / ROWS_ON_PAGE
                + ((productAmount % ROWS_ON_PAGE) == 0 ? 0 : 1);

            List<Product> products;
        try {
            if (lastPage > page) {
                Long offset = (page - 1) * ROWS_ON_PAGE;
                products = productService.getProductsSorted(sortBy, ROWS_ON_PAGE, offset);
            } else {
                Long offset = (lastPage - 1) * ROWS_ON_PAGE;
                products = productService.getProductsSorted(sortBy, ROWS_ON_PAGE, offset);
                page = lastPage;
            }

            request.setAttribute("sort", sortBy);
            request.setAttribute("products", products);
            request.setAttribute("page", page);
            request.setAttribute("lastPage", lastPage);
        } catch (Exception e){
            LOG.error(e.getMessage());
        }


        try {
            request.getServletContext().getRequestDispatcher(Path.ALL_PRODUCT).forward(request, response);
        } catch (ServletException | IOException e) {
            LOG.error(e.getMessage());
        }
        return Path.ALL_PRODUCT;
    }
}
