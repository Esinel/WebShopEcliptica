package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import entities.Component;
import entities.CustomMachine;

public class CustomMachineDAO {

	public static ArrayList<CustomMachine> getAll() {
		ArrayList<CustomMachine> builtMachines = new ArrayList<>();
		CustomMachine machine;
		try {
			Connection connection = ConnectionManager.getConnection();
			String query = 
					"SELECT ID, DeviceName, Description FROM CustomDevice " +
					"WHERE deleted = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setBoolean(1, false);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				String description = resultSet.getString(3);
				machine = new CustomMachine(id, name, description);
				builtMachines.add(machine);
			}
			resultSet.close();
			preparedStatement.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return builtMachines;
	}
	 
	public static CustomMachine getById(int machId) {
		CustomMachine machine = null;
		try {
			Connection connection = ConnectionManager.getConnection();
			String query = 
					"SELECT ID, DeviceName, Description FROM CustomDevice " +
					"WHERE ID = ? AND deleted = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, machId);
			preparedStatement.setBoolean(2, false);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				String description = resultSet.getString(3);
				machine = new CustomMachine(id, name, description);
			}
			resultSet.close();
			preparedStatement.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return machine;
	}
	
	public static ArrayList<CustomMachine> getByName(String machineName) {
		ArrayList<CustomMachine> machines = new ArrayList<>();
		CustomMachine machine = null;
		try {
			Connection connection = ConnectionManager.getConnection();
			String query = 
					"SELECT * FROM CustomDevice " +
					"WHERE DeviceName LIKE ? AND deleted = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, '%'+machineName+'%');
			preparedStatement.setBoolean(2, false);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				String descriptionDb = resultSet.getString(3);
				
				machine = new CustomMachine(id, name, descriptionDb);
				machines.add(machine);
			}
			resultSet.close();
			preparedStatement.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return machines;
	}
	
	public static ArrayList<CustomMachine> getByDescription(String description) {
		ArrayList<CustomMachine> machines = new ArrayList<>();
		CustomMachine machine = null;
		try {
			Connection connection = ConnectionManager.getConnection();
			String query = 
					"SELECT * FROM CustomDevice " +
					"WHERE Description = ? AND deleted = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, description);
			preparedStatement.setBoolean(2, false);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				String descriptionDb = resultSet.getString(3);
				
				machine = new CustomMachine(id, name, descriptionDb);
				machines.add(machine);
			}
			resultSet.close();
			preparedStatement.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return machines;
	}
	
	public static boolean insert(CustomMachine machine) {
		boolean success = false;
		try {
			Connection connection = ConnectionManager.getConnection();
			String query = 
					"INSERT INTO CustomDevice (DeviceName, Description) " +
					"VALUES(?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, machine.getName());
			preparedStatement.setString(2, machine.getDescription());
			
			success = preparedStatement.executeUpdate() == 1;			
			preparedStatement.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return success;
	}
	
	public static boolean update(CustomMachine machine) {
		boolean success = false;
		
		try {
			Connection connection = ConnectionManager.getConnection();
			String query = 
					"UPDATE CustomDevice " +
					"SET DeviceName = ?, Description = ? " +
					"WHERE ID = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, machine.getName());
			preparedStatement.setString(2, machine.getDescription());
			preparedStatement.setInt(3, machine.getCode());
			
			success = preparedStatement.executeUpdate() == 1;
			preparedStatement.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return success;
	}
	
	public static boolean deleteLogical(int id) {
		boolean success = false;
		
		try {
			Connection connection = ConnectionManager.getConnection();
			String query =
					"UPDATE CustomDevice " + 
					"SET deleted = ? " + 
					"WHERE ID = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setBoolean(1, true);
			preparedStatement.setInt(2, id);
			
			success = preparedStatement.executeUpdate() == 1;
			preparedStatement.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return success;
	}
	
	
	public static int getLastId(){
		int id = 0;
		try {
			Connection connection = ConnectionManager.getConnection();
			String query = 
					"SELECT LAST_INSERT_ID();";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				id = resultSet.getInt(1);
			}
			resultSet.close();
			preparedStatement.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	
}
