package com.company.app.controller.command.main;

import com.company.app.controller.command.ICommand;
import com.company.app.dao.entity.User;
import com.company.app.dao.entity.enumeration.UserRole;
import com.company.app.service.impl.UserService;
import com.company.app.util.constant.Path;
import com.company.app.util.valid.Hash;
import com.company.app.util.valid.Validator;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignCommand implements ICommand {
    private static final Logger LOG = Logger.getLogger(SignCommand.class);
    private final UserService userService;

    public SignCommand(UserService userService) {
        this.userService = userService;
    }

    /**
     * Checks the input and finds the user in the database.
     * @param request request to read the command from
     * @param response
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter("login");
        String password = request.getParameter("pass");

        if(Validator.isValidNull(login)){
            request.setAttribute("login_error_message", "Fill in login");
            forward(request, response, Path.LOGIN);
            return;
        }

        if(Validator.isValidNull(password)){
            request.setAttribute("password_error_message", "Fill in form");
            forward(request, response, Path.LOGIN);
            return;
        }

        User user = userService.login(login);

        if(user.getUserRole()== null){
            request.setAttribute("sign_error_message", "This user not exists");
            forward(request, response, Path.LOGIN);
            return;
        }

        if(!Hash.isValidHash(password, user.getPassword() )){
            request.setAttribute("password_wrong_error_message", "Wrong password");
            forward(request, response, Path.LOGIN);
            return;
        }

        LOG.info("Login success for " + user.toString());

        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        request.setAttribute("name", user.getName());
        request.setAttribute("surname", user.getSurname());

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
