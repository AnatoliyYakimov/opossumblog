package com.yakimov.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.yakimov.entities.User;
import com.yakimov.entities.UserRecord;
import com.yakimov.exceptions.ActiveRecordException;
import com.yakimov.exceptions.ActiveRecordException.ErrorCode;
import com.yakimov.model.UsersTable;
import com.yakimov.validators.RegistrationValidator;

@WebServlet(name = "SignupServlet", urlPatterns = "/signup")
public class SignupServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger("SignupServlet");
    private static final long serialVersionUID = -4295600161280641411L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String passwordConfirmation = req.getParameter("passwordConfirmation");

        List<String> violations = new ArrayList<>();

        List<String> loginViolations = RegistrationValidator.validateLogin(login);
        List<String> passwordViolations = RegistrationValidator.validatePasswordAndConfirmation(password,
                passwordConfirmation);

        violations.addAll(loginViolations);
        violations.addAll(passwordViolations);

        if (violations.isEmpty()) {
            User user = new User(login, password);
            UserRecord record = new UserRecord(user);
            try {
                record.save();
                user = UsersTable.getInstance().getRecordByLogin(login).orElseThrow();
                String user_id = Integer.toString(user.getId());
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                session.setMaxInactiveInterval(24 * 60 * 60);
                resp.addCookie(new Cookie("user_id", user_id));
                resp.sendRedirect(req.getContextPath() + "/user");

            } catch (ActiveRecordException e) {
                if (e.getErrorCode().equals(ErrorCode.RECORD_EXISTS_ERROR)) {
                    violations.add("Login already in use");
                    req.setAttribute("violations", violations);
                    req.getRequestDispatcher("/login").forward(req, resp);
                } else {
                    logger.error("Error while saving user: " + e.getMessage());
                }
            }
        } else {
            req.setAttribute("violations", violations);
            req.getRequestDispatcher("/login").forward(req, resp);
        }
    }

}