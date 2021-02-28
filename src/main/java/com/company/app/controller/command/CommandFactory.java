package com.company.app.controller.command;

import com.company.app.controller.command.main.*;
import com.company.app.controller.command.product.*;
import com.company.app.controller.command.report.*;
import com.company.app.controller.command.check.*;
import com.company.app.controller.command.user.*;

import com.company.app.service.impl.CheckService;
import com.company.app.service.impl.ProductService;
import com.company.app.service.impl.ReportService;
import com.company.app.service.impl.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static CommandFactory factory = new CommandFactory();
    private Map<String, ICommand> commands = new HashMap<>();

    public CommandFactory() {
        /** Client commands */
        commands.put("register", new RegistrationCommand(new UserService()));
        commands.put("sign", new SignCommand(new UserService()));
        commands.put("logout", new LogoutCommand());
        commands.put("registerPage", new RegisterCommand());
        commands.put("loginPage", new LoginCommand());

        /** Manager commands*/
        commands.put("manager", new ManagerCommand());
        commands.put("addProduct", new AddProductCommand(new ProductService()));
        commands.put("newProduct", new NewProductCommand());
        commands.put("listProduct", new ListProductCommand(new ProductService()));
        commands.put("deleteProduct", new DeleteProductCommand(new ProductService()));

        /** Chief Cashier commands*/
        commands.put("chiefCashier", new ChiefCashierCommand());
        commands.put("createXReport", new CreateXReportCommand(new ReportService(), new CheckService()));
        commands.put("createZReport", new CreateZReportCommand(new ReportService(), new CheckService()));
        commands.put("listReport", new ListReportCommand());
        commands.put("findByData", new FindReportCommand(new ReportService()));
        commands.put("deleteFromCheck", new DeleteProductInCheckCommand(new CheckService(), new ProductService()));

        /** Cashier commands*/
        commands.put("cashier", new CashierCommand());
        commands.put("newCheck", new AddCheckCommand(new CheckService()));
        commands.put("addInCheck", new AddProductInCheckCommand(new ProductService(), new CheckService()));

        /** Cashier and Chief Cashier commands*/
        commands.put("detailsCheck", new DetailsCheckCommand(new CheckService()));
        commands.put("listCheck", new ListCheckCommand(new CheckService()));
        commands.put("changeStatus", new ChangeStatusCheckCommand(new CheckService(), new ProductService()));

        /** Admin commands*/
        commands.put("admin", new AdminCommand());
        commands.put("deleteUser", new DeleteUserCommand(new UserService()));
        commands.put("listUsers", new ListUsersCommand(new UserService()));
    }

    public static CommandFactory commandFactory() {
        if (factory == null) {
            factory = new CommandFactory();
        }
        return factory;
    }

    public ICommand commandFactory(HttpServletRequest request) {
        String action = request.getParameter("action");
        return commands.get(action);
    }
}
