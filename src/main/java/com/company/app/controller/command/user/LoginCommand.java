package com.company.app.controller.command.user;

import com.company.app.controller.command.ICommand;
import com.company.app.dao.entity.User;
import com.company.app.dao.entity.enumeration.UserRole;
import com.company.app.service.impl.UserService;
import com.company.app.util.constant.Path;
import com.company.app.util.valid.Validator;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand implements ICommand {
    private static final Logger LOG = Logger.getLogger(LoginCommand.class);
    private UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    /**
     * Checks the input and finds the user in the database.
     * @param request request to read the command from
     * @param response
     * @return
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter("login");
        String password = request.getParameter("pass");

        if(Validator.isValidNull(login)){
            request.setAttribute("login_error_message", "Fill in login");
            return Path.LOGIN;
        }

        if(Validator.isValidNull(password)){
            request.setAttribute("password_error_message", "Fill in form");
            return Path.LOGIN;
        }

        User user = userService.login(login, password);

        if(user.getUserRole()== null){
            request.setAttribute("sign_error_message", "This user not exists");
            return Path.LOGIN;
        }
        LOG.info("Login success for " + user.toString());

        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        request.setAttribute("name", user.getName());
        request.setAttribute("surname", user.getSurname());

        if(user.getUserRole() == UserRole.MANAGER){
            return Path.MANAGER;
        } else if(user.getUserRole() == UserRole.CASHIER){
            return Path.CASHIER;
        } else
            return Path.CHIEF_CASHIER;
    }
}
