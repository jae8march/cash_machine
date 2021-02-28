package com.company.app.controller.command.main;

import com.company.app.dao.entity.enumeration.UserRole;
import com.company.app.controller.command.ICommand;
import com.company.app.dao.entity.User;
import com.company.app.service.impl.UserService;
import com.company.app.util.constant.Path;
import com.company.app.util.valid.Validator;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RegistrationCommand implements ICommand {
    private static final Logger LOG = Logger.getLogger(RegistrationCommand.class);
    private final UserService userService;

    public RegistrationCommand(UserService userService){
        this.userService = userService;
    }

    /**
     * Checks input data and enters the user into the database.
     * @param request request to read the command from
     * @param response
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String login = request.getParameter("login");
        String password = request.getParameter("pass");
        String roleForm = request.getParameter("role");

        String role = roleForm.replace (' ', '_');

        User user = new User(name, surname, login, password, UserRole.valueOf(role.toUpperCase()));

        if(Validator.isValidNull(user.getName())){
            request.setAttribute("name_error_message", "Fill in name");
            forward(request, response, Path.REGISTRATION);
            return;
        }

        if(Validator.isValidNull(user.getSurname())){
            request.setAttribute("surname_error_message", "Fill in surname");
            forward(request, response, Path.REGISTRATION);
            return;
        }

        if(Validator.isValidNull(user.getLogin())){
            request.setAttribute("login_error_message", "Fill in login");
            forward(request, response, Path.REGISTRATION);
            return;
        }

        if(Validator.isValidNull(user.getPassword())){
            request.setAttribute("password_error_message", "Fill in form");
            forward(request, response, Path.REGISTRATION);
            return;
        }

        if (!Validator.isValidPassword(user.getPassword())){
            request.setAttribute("password_length_error_message", "Password must be at least 5 characters!");
            forward(request, response, Path.REGISTRATION);
            return;
        }

        if(!userService.create(user)){
            request.setAttribute("register_error_message", "This user exists");
            forward(request, response, Path.REGISTRATION);
            return;
        }
        LOG.info("Registration success for " + user.toString());

        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        if(user.getUserRole() == UserRole.MANAGER){
            forward(request, response, Path.C_MANAGER);
        } else if(user.getUserRole() == UserRole.CASHIER){
            forward(request, response, Path.C_CASHIER);
        } else if(user.getUserRole() == UserRole.ADMIN){
            forward(request, response, Path.C_ADMIN);
        } else
            forward(request, response, Path.C_CHIEF_CASHIER);
    }
}
