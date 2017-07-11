package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dao.ComponentDAO;
import dao.CustomMachineComponentsDAO;
import dao.CustomMachineDAO;
import entities.Component;
import entities.CustomMachine;

@WebServlet("/CustomMachineController")
public class CustomMachineController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CustomMachineController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String purpose = request.getParameter("purpose");
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		ObjectMapper mapper = new ObjectMapper();
		try{
			if("getAll".equals(purpose)){
				ArrayList<CustomMachine> builtMachines = CustomMachineDAO.getAll();
				// iterate though machines and fill them with components
				for (int i=0; i<builtMachines.size(); i++){
					int machineId = builtMachines.get(i).getCode();
					ArrayList<Component> components = CustomMachineComponentsDAO.getById(machineId);
					builtMachines.get(i).setComponents(components);
				} 
				String jsonMachines = mapper.writeValueAsString(builtMachines);
				out.print(jsonMachines);
				out.flush();
				out.close();
			}else if ("getById".equals(purpose)){
				int machineId = Integer.parseInt(request.getParameter("machineId"));
				CustomMachine customMachine = CustomMachineDAO.getById(machineId); 
				ArrayList<Component> components = CustomMachineComponentsDAO.getById(machineId);
				customMachine.setComponents(components);
				String jsonMachine = mapper.writeValueAsString(customMachine);
				out.print(jsonMachine);
				out.flush();
				out.close();
			}else if ("getByName".equals(purpose)){
				//pretrazi po imenu (fancy search bar)
				String machineName = request.getParameter("searchInput");
				
				ArrayList<CustomMachine> builtMachines = CustomMachineDAO.getByName(machineName);
				for (int i=0; i<builtMachines.size(); i++){
					int machineId = builtMachines.get(i).getCode();
					ArrayList<Component> components = CustomMachineComponentsDAO.getById(machineId);
					builtMachines.get(i).setComponents(components);
				}
				String jsonMachines = mapper.writeValueAsString(builtMachines);
				out.print(jsonMachines);
				out.flush();
				out.close();
			}else if ("filterByAll".equals(purpose)){
				String filterBundleJson = request.getParameter("filterBundleJson");
				mapper = new ObjectMapper();
				JsonNode jsonObj = mapper.readTree(filterBundleJson);
				
				String description = jsonObj.get("description").asText();
				int vgaId = jsonObj.get("deviceVGA").asInt();
				int cpuId = jsonObj.get("deviceCPU").asInt();
				int ramId = jsonObj.get("deviceRAM").asInt();
				int mbId = jsonObj.get("deviceMB").asInt();
				int hddId = jsonObj.get("deviceHDD").asInt();
				int casingId = jsonObj.get("deviceCasing").asInt();
				
				int[] tempComponentCompareList = new int[5];
				tempComponentCompareList[0] = vgaId;
				tempComponentCompareList[1] = cpuId;
				tempComponentCompareList[2] = ramId;
				tempComponentCompareList[3] = mbId;
				tempComponentCompareList[4] = hddId;
				tempComponentCompareList[5] = casingId;
				Arrays.sort(tempComponentCompareList);
				
				ArrayList<CustomMachine> finalMachines = new ArrayList<>();
				
				ArrayList<CustomMachine> machinesByDescr = CustomMachineDAO.getByDescription(description);
				
				for (int i=0; i<machinesByDescr.size(); i++){
					CustomMachine currentMachine = machinesByDescr.get(i);
					int machineId = currentMachine.getCode();
					int[] componentIds = CustomMachineComponentsDAO.getIdsById(machineId);
					
					if (Arrays.equals(tempComponentCompareList, componentIds)){
						ArrayList<Component> components = CustomMachineComponentsDAO.getById(machineId);
						currentMachine.setComponents(components);
						finalMachines.add(currentMachine);
					} 
				}
				String jsonMachines = mapper.writeValueAsString(finalMachines);
				out.print(jsonMachines);
				out.flush();
				out.close();
			}
			   
		} catch(Exception e) { 
			response.getWriter().write(e.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String purpose = request.getParameter("purpose");
		if ("add".equals(purpose)){
			String customMachineJson = request.getParameter("customMachineJson");
			
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonObj = mapper.readTree(customMachineJson);
			String name = jsonObj.get("deviceName").asText();
			String description = jsonObj.get("deviceDescr").asText();
			
			int cpuId = jsonObj.get("deviceCPU").asInt();
			int mbId = jsonObj.get("deviceMB").asInt();
			int hddId = jsonObj.get("deviceHDD").asInt();
			int ramId = jsonObj.get("deviceRAM").asInt();
			int vgaId = jsonObj.get("deviceVGA").asInt();
			int casingId = jsonObj.get("deviceCasing").asInt();
			
			Component cpu = ComponentDAO.getById(cpuId);
			Component mb = ComponentDAO.getById(mbId);
			Component hdd = ComponentDAO.getById(hddId);
			Component ram = ComponentDAO.getById(ramId);
			Component vga = ComponentDAO.getById(vgaId);
			Component casing = ComponentDAO.getById(casingId);
			
			ArrayList<Component> components = new ArrayList<Component>();
			components.add(cpu);
			components.add(mb);
			components.add(hdd);
			components.add(ram);
			components.add(vga);
			components.add(casing);
			CustomMachine machine = new CustomMachine(name, description, components);
			
			CustomMachineDAO.insert(machine);
			int createdMachineId = CustomMachineDAO.getLastId();
			for (int i=0; i<components.size(); i++){
				CustomMachineComponentsDAO.insert(createdMachineId, components.get(i));
			}
		}else if ("update".equals(purpose)){
			doPut(request, response);
		}else if ("delete".equals(purpose)){
			doDelete(request, response);
		}
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String customMachineJson = request.getParameter("customMachineJson");
		//System.out.println(customMachineJson);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonObj = mapper.readTree(customMachineJson);
		
		int id = jsonObj.get("code").asInt();
		String name = jsonObj.get("name").asText();
		String description = jsonObj.get("description").asText();
		 
		//components
		int cpuID = jsonObj.get("deviceCPU").asInt();
		int mbID = jsonObj.get("deviceMB").asInt();
		int hddID = jsonObj.get("deviceHDD").asInt();
		int ramID = jsonObj.get("deviceRAM").asInt();
		int vgaID = jsonObj.get("deviceVGA").asInt();
		int caseID = jsonObj.get("deviceCasing").asInt();
		 
		
		Component cpu = ComponentDAO.getById(cpuID);
		Component mb = ComponentDAO.getById(mbID);
		Component hdd = ComponentDAO.getById(hddID);
		Component ram = ComponentDAO.getById(ramID);
		Component vga = ComponentDAO.getById(vgaID);
		Component casing = ComponentDAO.getById(caseID);
		
		ArrayList<Component> components = new ArrayList<Component>();
		components.add(cpu);
		components.add(mb);
		components.add(hdd);
		components.add(ram);
		components.add(vga);
		components.add(casing);
		
		CustomMachine machine = new CustomMachine(id, name, description, components);
		
		CustomMachineDAO.update(machine);
		
		for (int i=0; i<components.size(); i++){
			CustomMachineComponentsDAO.update(id, components.get(i));
		}
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int machineId = Integer.parseInt(request.getParameter("machineId"));
		
		CustomMachineDAO.deleteLogical(machineId);
	}
	
	
	

}
