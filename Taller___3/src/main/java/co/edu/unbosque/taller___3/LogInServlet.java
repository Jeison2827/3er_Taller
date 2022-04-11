package co.edu.unbosque.taller___3;

import co.edu.unbosque.taller___3.dtos.User;
import co.edu.unbosque.taller___3.services.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "login", value = "/login")
public class LogInServlet extends HttpServlet {

    public void init() {}

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        List<User> users = new UserService().getUsers();

        User userFounded = users.stream()
                .filter(user -> username.equals(user.getUsername()) && password.equals(user.getPassword()))
                .findFirst()
                .orElse(null);

        if (userFounded != null) {
            request.setAttribute("role", userFounded.getRole());

            Cookie cookie = new Cookie("role", userFounded.getRole());
            cookie.setMaxAge(20);
            response.addCookie(cookie);

            RequestDispatcher dispatcher = request.getRequestDispatcher("./home.jsp");
            dispatcher.forward(request, response);

        } else {
            response.sendRedirect("./401.html");
        }
    }

    public void destroy() {}
}