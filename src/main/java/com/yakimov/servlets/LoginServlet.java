package com.yakimov.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet("/loginservlet")
public class LoginServlet extends HttpServlet {
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String login = req.getParameter("login");
//        String password = req.getParameter("password");
//        UserAR user = new UserAR(login,password);
//
//        HttpSession session = req.getSession(false);
//
////        if(session == null){
////            if(Model.getInstance().getUsersList().contains(user.getLogin())){
////                session = req.getSession(true);
////                session.setAttribute("login", login);
////                session.setAttribute("password", password);
////            }
////            else {
////                req.setAttribute("loginState", "false");
////            }
//        }
//    }
}
