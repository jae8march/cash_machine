package com.company.app.util.filter;

import com.company.app.dao.entity.User;
import com.company.app.dao.entity.enumeration.UserRole;
import com.company.app.util.constant.Path;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Blocks or allows access to actions on site depending on user's role.
 */
public class AccessFilter implements Filter {
    public static final Logger LOG= Logger.getLogger(AccessFilter.class);
    private static final Map<UserRole, List<String>> mapConfig = new HashMap<>();

    /**
     * Adds lists command for user
     * @param filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) {
        LOG.info("The Access Filter started working");
        List<String> commandClient = new ArrayList<>();
        Collections.addAll(commandClient, "register", "sign", "logout", "registerPage", "loginPage");
        mapConfig.put(UserRole.CLIENT, commandClient);

        LOG.info("Added a list of commands for client");

        List<String> commandManager = new ArrayList<>();
        Collections.addAll(commandManager, "manager", "addProduct", "listProduct", "deleteProduct",
                "newProduct");
        mapConfig.put(UserRole.MANAGER, commandManager);

        LOG.info("Added a list of commands for manager");

        List<String> commandChiefCashier = new ArrayList<>();
        Collections.addAll(commandChiefCashier, "chiefCashier", "createXReport", "createZReport", "listReport",
                "findByData", "deleteFromCheck", "detailsCheck", "listCheck", "changeStatus");
        mapConfig.put(UserRole.CHIEF_CASHIER, commandChiefCashier);

        LOG.info("Added a list of commands for chief cashier");

        List<String> commandCashier = new ArrayList<>();
        Collections.addAll(commandCashier, "cashier", "newCheck", "addInCheck", "changeStatus", "detailsCheck",
                "listCheck");
        mapConfig.put(UserRole.CASHIER, commandCashier);

        LOG.info("Added a list of commands for the cashier");

        List<String> commandAdmin = new ArrayList<>();
        Collections.addAll(commandAdmin, "admin", "deleteUser", "listUsers");
        mapConfig.put(UserRole.ADMIN, commandAdmin);

        LOG.info("Added a list of commands for admin");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        User user = (User) ((HttpServletRequest) req).getSession().getAttribute("user");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String errorUserUrl = request.getContextPath() + Path.ERROR;

        String action = request.getParameter("action");

        if (user == null) {
            user = new User();
            user.setUserRole(UserRole.CLIENT);
        }

        List<String> command = mapConfig.get(user.getUserRole());
        List<String> commandClient = mapConfig.get(UserRole.CLIENT);

        if(command.contains(action) || commandClient.contains(action)){
            LOG.info("User accessed the site");
            chain.doFilter(req, resp);
        } else {
            LOG.info("the user did not access the site");
            response.sendRedirect(errorUserUrl);
        }
    }

    @Override
    public void destroy() {
        LOG.info("The Access Filter has finished its work");
    }
}
