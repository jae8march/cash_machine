package com.company.app.controller.command.check;

import com.company.app.controller.command.ICommand;
import com.company.app.dao.entity.Check;
import com.company.app.dao.entity.Product;
import com.company.app.service.impl.CheckService;
import com.company.app.util.Pagination;
import com.company.app.util.constant.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class DetailsCheckCommand implements ICommand {
    private static final Logger LOG = Logger.getLogger(DetailsCheckCommand.class);
    private final CheckService checkService;

    public DetailsCheckCommand(CheckService checkService) {
        this.checkService = checkService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        final String idChek = "checkId";
        Long code;
        if(request.getParameter(idChek)==null){
            LOG.error("check id==null");
            code = (Long) request.getSession().getAttribute(idChek);
        } else {
            code = Long.parseLong(request.getParameter(idChek));
            HttpSession session = request.getSession();
            session.setAttribute(idChek, code);
        }
        Check check = checkService.getById(code);
        check.getUser().getLogin();

        long page = Pagination.getPage(request.getParameter("page"));
        long nextPage = Pagination.getNextPage(request.getParameter("nextPage"), page);

        page = nextPage;

        long count = checkService.getCheckProductCount(check.getCheckId());
        long lastPage = count / Pagination.ROWS + ((count % Pagination.ROWS) == 0 ? 0 : 1);

        long offset = Pagination.getOffset(lastPage, page);

        if(lastPage < page){
            page = lastPage;
        }

        List<Product> products = checkService.getCheckProductSorted(check.getCheckId(), Pagination.ROWS, offset);

        request.setAttribute("page", page);
        request.setAttribute("lastPage", lastPage);
        request.setAttribute("check", check);
        request.setAttribute("products", products);
        request.setAttribute("loginUser", check.getUser().getLogin());
        request.setAttribute("date", check.getCheckDate());
        request.setAttribute(idChek, check.getCheckId());

        forward(request, response, Path.DETAILS_CHECK);
    }
}
