package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.AdminDAO;
import entities.User;


@WebServlet("/AdminLoginController")
public class AdminLoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public AdminLoginController() {
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
		
		User admin = (User) session.getAttribute("admin");
		if (admin != null) {
			response.sendRedirect("admin-login.html");
			return;
		}
		
		String jsonAdmin = request.getParameter("loggedAdmin");
		ObjectMapper mapper = new ObjectMapper();
		admin = mapper.readValue(jsonAdmin, User.class);
		
		try{
			User adminDB = AdminDAO.getByUserNameAndPassword(admin.getUsername(), admin.getPassword());
			if (adminDB != null) {
				session.setAttribute("admin", adminDB);
				jsonAdmin = mapper.writeValueAsString(adminDB);
				out.println(jsonAdmin);
				out.flush();
				out.close();
				//??
				response.sendRedirect("admin-panel.html");
			}else{
				jsonAdmin = "{}";
				out.print(jsonAdmin);
                out.flush();
                out.close();
			}
		} catch(Exception e){
			out.write(e.getMessage());
			e.printStackTrace();
		}
	}

}
