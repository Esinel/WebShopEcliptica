package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;

import dao.UserDAO;
import entities.User;


@WebServlet("/RegisterServlet")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
 
    public RegisterController() {
        super();
    }
 

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		String jsonUser = "{}";
		String username = (String) request.getParameter("username");
		ObjectMapper mapper = new ObjectMapper();
		try{
			User userDB = UserDAO.getByUserName(username);
			if (userDB != null){
				jsonUser = mapper.writeValueAsString(userDB);
				out.print(jsonUser);
				out.flush();
				out.close();
			} else {
				out.print(jsonUser);
				out.flush();
				out.close();
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		User user = (User) session.getAttribute("user");
		if (user != null) {
			response.sendRedirect("index.html");
			return;
		}
		
		String jsonUser = (String) request.getParameter("registeredUser");
		ObjectMapper mapper = new ObjectMapper();
		user = mapper.readValue(jsonUser, User.class);
		user.setUserType("USER");
		try{
			if (UserDAO.insert(user)){
			session.setAttribute("user", user);
			jsonUser = mapper.writeValueAsString(user);
			out.print(jsonUser);
			out.flush();
			out.close();
			} else {
				jsonUser = "{}";
				out.print(jsonUser);
				out.flush();
				out.close();
			}
		} catch(Exception e) {
			//response.setStatus(400);
            response.getWriter().write(e.getMessage());
		}
	}

}
