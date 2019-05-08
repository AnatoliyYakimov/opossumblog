package com.yakimov.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.yakimov.exceptions.ActiveRecordException;
import com.yakimov.exceptions.ActiveRecordException.ErrorCode;
import com.yakimov.model.UsersTable;

@WebServlet("/loginservlet")
public class LoginServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger("LogInServlet");
    List<String> loginViolations = new ArrayList<>();
    private HttpSession session;
    private HttpServletResponse resp;
    private User user;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        session = req.getSession();
        if(userExistsAndPasswordCorrect(login, password)) {
            setCookiesAndSessionFor(user);
            redirectToUserPage(req, resp);
        }
        else {
            setViolationsAndReturnToLoginPage(req, resp);
        }
        
    }

    private boolean userExistsAndPasswordCorrect(String login, String password) {
        return userExsits(login) && isCorrectPassword(password);
    }

    private boolean userExsits(String login) {
        try {
            user = getUserFromDatabaseOrElseThrow(login);
            return true;
        } catch (ActiveRecordException e) {
            switch (e.getErrorCode()) {
            case RECORD_DOESNT_EXISTS_ERROR:
                loginViolations.add("User with username \"" + login + "\" doesn`t exist");
                break;
            default:
                loginViolations.add("Internal error, try later");
                logger.error(e.getMessage());
            }
            return false;
        }
    }

    private boolean isCorrectPassword(String password) {
        return user.getPassword().equals(password);
    }
    

    private void setCookiesAndSessionFor(User user) {
        session.setAttribute("user", user);
        String user_id = String.valueOf(user.getId());
        resp.addCookie(new Cookie("user_id", user_id));
    }
    
    private void redirectToUserPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getContextPath() + "/user");
    }

    private void setViolationsAndReturnToLoginPage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("loginViolations", loginViolations);
        req.getRequestDispatcher("/login").forward(req, resp);
    }

    private User getUserFromDatabaseOrElseThrow(String login) throws ActiveRecordException{
        Optional<User> registredUser = UsersTable.getInstance().getRecordByLogin(login);
        User user = registredUser.orElseThrow(() -> new ActiveRecordException(ErrorCode.RECORD_DOESNT_EXISTS_ERROR));
        return user;
    }
    
    
    
    
}
