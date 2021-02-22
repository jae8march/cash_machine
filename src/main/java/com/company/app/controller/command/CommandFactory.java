package com.company.app.controller.command;
//TODO WORK
import com.company.app.controller.command.product.*;
import com.company.app.controller.command.report.*;
import com.company.app.controller.command.user.*;
import com.company.app.controller.command.check.*;

import com.company.app.service.impl.CheckService;
import com.company.app.service.impl.ProductService;
import com.company.app.service.impl.ReportService;
import com.company.app.service.impl.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CommandFactory implements Serializable {
    static final long serialVersionUID = 1L;

    private static CommandFactory factory = new CommandFactory();
    private Map<String, ICommand> commands = new HashMap<>();

    public CommandFactory() {
        /** Client commands */
        commands.put("register", new RegisterCommand(new UserService()));
        commands.put("sign", new LoginCommand(new UserService()));
        commands.put("logout", new LogoutCommand());

        /** Manager commands*/
        commands.put("manager", new ManagerCommand());
        commands.put("manager/addProduct", new AddProductCommand(new ProductService()));
        commands.put("manager/listProduct", new ListProductCommand(new ProductService()));
        commands.put("manager/deleteProduct", new DeleteProductCommand(new ProductService()));

        /** Chief Cashier commands*/
        commands.put("chiefCashier", new ChiefCashierCommand());
        commands.put("chiefCashier/createXReport", new CreateXReportCommand(new ReportService(), new CheckService()));
        commands.put("chiefCashier/createZReport", new CreateZReportCommand(new ReportService(), new CheckService()));
        commands.put("chiefCashier/listReport", new ListReportCommand());
        commands.put("chiefCashier/findByData", new FindReportCommand(new ReportService()));

        commands.put("check/changeStatus", new ChangeStatusCheckCommand(new CheckService()));
        commands.put("chiefCashier/deleteFromCheck", new DeleteProductInCheckCommand(new CheckService(), new ProductService()));//TODO NOT WORKS

        /** Cashier commands*/
        commands.put("cashier", new CashierCommand());
        commands.put("cashier/newCheck", new AddCheckCommand(new CheckService()));
        commands.put("cashier/addInCheck", new AddProductInCheckCommand(new ProductService(), new CheckService()));

        /** Cashier and Chief Cashier commands*/
        commands.put("check/detailsCheck", new DetailsCheckCommand(new CheckService()));
        commands.put("check/listCheck", new ListCheckCommand(new CheckService()));
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
