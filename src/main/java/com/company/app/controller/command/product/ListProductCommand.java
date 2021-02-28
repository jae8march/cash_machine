package com.company.app.controller.command.product;

import com.company.app.controller.command.ICommand;
import com.company.app.dao.entity.Product;
import com.company.app.service.impl.ProductService;
import com.company.app.util.Pagination;
import com.company.app.util.constant.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ListProductCommand implements ICommand {
    private static final Logger LOG = Logger.getLogger(ListProductCommand.class);
    private final ProductService productService;

    public ListProductCommand(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Lists products in the specified sorting.
     * @param request request to read the command from
     * @param response
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String sortBy = request.getParameter("sort");

        if (sortBy == null || sortBy.equals("")) {
            sortBy = "code";
        }

        long page = Pagination.getPage(request.getParameter("page"));
        long nextPage = Pagination.getNextPage(request.getParameter("nextPage"), page);

        page = nextPage;

        long count = productService.getProductCount();
        long lastPage = count / Pagination.ROWS + ((count % Pagination.ROWS) == 0 ? 0 : 1);

        long offset = Pagination.getOffset(lastPage, page);

        if(lastPage < page){
            page = lastPage;
        }

        List<Product> products = productService.getProductsSorted(sortBy, Pagination.ROWS, offset);
        LOG.info("Found a list of products sorted and with a limit");
        request.setAttribute("sort", sortBy);
        request.setAttribute("products", products);
        request.setAttribute("page", page);
        request.setAttribute("lastPage", lastPage);

        forward(request, response, Path.ALL_PRODUCT);
    }
}
