package com.company.app.controller.command.user;

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

public class RegisterCommand implements ICommand {
    private static final Logger LOG = Logger.getLogger(RegisterCommand.class);
    private UserService userService;

    public RegisterCommand(UserService userService){
        this.userService = userService;
    }

    /**
     * Checks input data and enters the user into the database.
     * @param request request to read the command from
     * @param response
     * @return
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String login = request.getParameter("login");
        String password = request.getParameter("pass");
        String roleForm = request.getParameter("role");

        String role = roleForm.replace (' ', '_');

        User user = new User(name, surname, login, password, UserRole.valueOf(role.toUpperCase()));

        if(Validator.isValidNull(user.getName())){
            request.setAttribute("name_error_message", "Fill in name");
            return Path.REGISTRATION;
        }

        if(Validator.isValidNull(user.getSurname())){
            request.setAttribute("surname_error_message", "Fill in surname");
            return Path.REGISTRATION;
        }

        if(Validator.isValidNull(user.getLogin())){
            request.setAttribute("login_error_message", "Fill in login");
            return Path.REGISTRATION;
        }

        if(Validator.isValidNull(user.getPassword())){
            request.setAttribute("password_error_message", "Fill in form");
            return Path.REGISTRATION;
        }

        if (!Validator.isValidPassword(user.getPassword())){
            request.setAttribute("password_length_error_message", "Password must be at least 5 characters!");
            return Path.REGISTRATION;
        }

        if(!userService.create(user)){
            request.setAttribute("register_error_message", "This user exists");
            return Path.REGISTRATION;
        }
        LOG.info("Registration success for " + user.toString());

        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        if(user.getUserRole() == UserRole.MANAGER){
            return Path.MANAGER;
        } else if(user.getUserRole() == UserRole.CASHIER){
            return Path.CASHIER;
        } else
            return Path.CHIEF_CASHIER;
    }
}
