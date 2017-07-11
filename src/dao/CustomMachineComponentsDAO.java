package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import entities.Component;

public class CustomMachineComponentsDAO {

	public static void getAll() {
		
		try { 
			Connection connection = ConnectionManager.getConnection();
			String query = 
					"SELECT CustomDeviceID, ComponentID FROM Customizing " +
					"WHERE deleted = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setBoolean(1, false);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				int customDeviceId = resultSet.getInt(1);
				int componentId = resultSet.getInt(2);
			}
			resultSet.close();
			preparedStatement.close();
		}catch(Exception e) {
			e.printStackTrace();
		}		
	}
	
	
	public static boolean insert(int machineId, Component component) {
		boolean success = false;
		try {
			Connection connection = ConnectionManager.getConnection();
			String query = 
					"INSERT INTO Customizing (CustomDeviceID, ComponentID) " +
					"VALUES(?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, machineId);
			preparedStatement.setInt(2, component.getCode());
			
			success = preparedStatement.executeUpdate() == 1;
			preparedStatement.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return success;
	}
	
	
	public static boolean update(int machineId, Component component) {
		boolean success = false;
		
		try {
			Connection connection = ConnectionManager.getConnection();
			String query = 
					//problemi sa querijem, brzinska popravka ;)
					"UPDATE IGNORE Customizing " +
					"SET ComponentID = ? " +
					"WHERE CustomDeviceID = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, component.getCode());
			preparedStatement.setInt(2, machineId);
			
			success = preparedStatement.executeUpdate() == 1;
			preparedStatement.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return success;
	} 
	
	public static ArrayList<Component> getById(int id) {
		ArrayList<Component> components = new ArrayList<>();
		Component component = null;
		try{
			Connection connection = ConnectionManager.getConnection();
			String query = 
					"SELECT ComponentID FROM Customizing WHERE CustomDeviceID = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int componentId = resultSet.getInt(1);
				component = ComponentDAO.getById(componentId);
				components.add(component);
			}
			resultSet.close();
			preparedStatement.close();
		} catch (Exception ex) {ex.printStackTrace();}
			return components;
	}
	
	public static int[] getIdsById(int id) {
		int[] componentIds = new int[5];
		int counter = 0;
		try{
			Connection connection = ConnectionManager.getConnection();
			String query = 
					"SELECT ComponentID FROM Customizing WHERE CustomDeviceID = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int componentId = resultSet.getInt(1);
				componentIds[counter] = componentId;
				counter++;
			}
			resultSet.close();
			preparedStatement.close();
		} catch (Exception ex) {ex.printStackTrace();}
		
		Arrays.sort(componentIds);
		return componentIds;
	}
}
