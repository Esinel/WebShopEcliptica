package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dao.ComponentDAO;
import dao.CategoryDAO;
import entities.Category;
import entities.Component;


@WebServlet("/ComponentController")
public class ComponentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ComponentController() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String purpose = request.getParameter("purpose");
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		ObjectMapper mapper = new ObjectMapper();
		try{
			if ("getComponentById".equals(purpose)){
				String componentIdStr = request.getParameter("componentId"); 
				int componentId = Integer.parseInt(componentIdStr);
				Component component = ComponentDAO.getById(componentId);
				String jsonComponent = mapper.writeValueAsString(component);
				out.print(jsonComponent);
				out.flush(); 
				out.close();
			}else if ("getByName".equals(purpose)){
				// fancy search
				String componentName = request.getParameter("searchInput");
				Collection<Component> components = ComponentDAO.getByName(componentName);
				String jsonComponents = mapper.writeValueAsString(components);
				out.print(jsonComponents);
				out.flush();
				out.close();
			}else if ("filterByAll".equals(purpose)){
				String filterBundleJson = request.getParameter("filterBundleJson");
				mapper = new ObjectMapper();
				JsonNode jsonObj = mapper.readTree(filterBundleJson);
				
				int priceFrom = jsonObj.get("priceFrom").asInt();
				int priceTo = jsonObj.get("priceTo").asInt();
				int availQuant = jsonObj.get("availableQuantity").asInt();
				String description = jsonObj.get("description").asText();
				int categoryId = jsonObj.get("category").asInt();
				
				Collection<Component> components = ComponentDAO.getAllByFilter(priceFrom, priceTo, availQuant, description, categoryId);
				String jsonComponents = mapper.writeValueAsString(components);
				out.print(jsonComponents);
				out.flush();
				out.close();
				//client side various components 
			}else if ("getMonitors".equals(purpose)){
				Collection<Component> components = ComponentDAO.getAllMonitors();
				String jsonComponents = mapper.writeValueAsString(components);
				System.out.println(jsonComponents);
				out.print(jsonComponents);
				out.flush();
				out.close();  
			}else{ 
				//get all 
				Collection<Component> components = ComponentDAO.getAll();
				String jsonComponents = mapper.writeValueAsString(components);
				out.print(jsonComponents);
				out.flush();
				out.close();
			} 
		} catch(Exception e) {
			response.getWriter().write(e.getMessage());
		}
	} 
 
 

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String purpose = request.getParameter("purpose");
		 
		if ("update".equals(purpose)){
			doPut(request, response);
		}else if("delete".equals(purpose)){
			doDelete(request, response);
		}else{
			// Adding new component
			String name = request.getParameter("name");
			String priceString = request.getParameter("price");
			BigDecimal price = new BigDecimal(priceString);
			String availableQuantityString = request.getParameter("availableQuantity");
			int availableQuantity = Integer.parseInt(availableQuantityString);
			String description = request.getParameter("description");
			String categoryId = request.getParameter("category");
			Category category = CategoryDAO.getById(Integer.parseInt(categoryId)); 
			String linkManifacturer = request.getParameter("linkManifacturer");
			String image = request.getParameter("imageLink");
			
			Component component = new Component(name, price, availableQuantity, description, category, linkManifacturer, image);
			
			ComponentDAO.insert(component);
			response.sendRedirect("admin-panel.html");
		}
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String idStr = request.getParameter("compCode");
		int id = Integer.parseInt(idStr);
		String name = request.getParameter("name");
		String priceString = request.getParameter("price");
		BigDecimal price = new BigDecimal(priceString);
		String availableQuantityString = request.getParameter("availableQuantity");
		int availableQuantity = Integer.parseInt(availableQuantityString);
		String description = request.getParameter("description");
		String categoryId = request.getParameter("category");
		Category category = CategoryDAO.getById(Integer.parseInt(categoryId)); 
		String linkManifacturer = request.getParameter("linkManifacturer");
		String image = request.getParameter("imageLink");

		
		Component component = new Component(id, name, price, availableQuantity, description, category, linkManifacturer, image);
		
		ComponentDAO.update(component);
		response.sendRedirect("admin-panel.html");
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//delete desired component
		String idStr = request.getParameter("compId");
		int id = Integer.parseInt(idStr);
		 
		ComponentDAO.deleteLogical(id);
		response.sendRedirect("admin-panel.html");
	}
	
}
