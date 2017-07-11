package servlets;

import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import dao.UserDAO;
import entities.User;

 

@WebServlet("/LoginServlet")
//@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public LoginController() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
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
		 
		//preuzeti user sa login forme u obliku JSON
		String jsonUser = request.getParameter("loggedUser");
		//JSON usera prebacujemo u objekat klase User
		ObjectMapper mapper = new ObjectMapper();
		user = mapper.readValue(jsonUser, User.class);
	    
		try{
			User userDB = UserDAO.getByUserNameAndPassword(user.getUsername(), user.getPassword());
			if (userDB != null){
				session.setAttribute("user", userDB);
				jsonUser = mapper.writeValueAsString(userDB);
				out.print(jsonUser);
                out.flush();
                out.close();
			}else{
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
