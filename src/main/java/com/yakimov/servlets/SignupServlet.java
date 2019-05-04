package com.yakimov.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yakimov.entities.User;
import com.yakimov.entities.UserRecord;
import com.yakimov.validators.RegistrationValidator;

@WebServlet(name = "SignupServlet", urlPatterns = "/signup")
public class SignupServlet extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = -4295600161280641411L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String passwordConfirmation = req.getParameter("passwordConfirmation");

        List<String> violations = RegistrationValidator.validate(login, password, passwordConfirmation);
        req.setAttribute("violations", violations);
        if (violations.isEmpty()) {
            UserRecord user = new UserRecord(new User(login, password));
            HttpSession session = req.getSession();
            resp.addCookie(new Cookie("user_id", login));
            resp.sendRedirect("/");
        } else {
            req.getRequestDispatcher("/login").forward(req, resp);
        }
    }

}