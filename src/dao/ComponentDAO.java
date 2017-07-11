package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;




import entities.Category;
import entities.Component;

public class ComponentDAO {

	public static Collection<Component> getAll() {
		Collection<Component> components = new ArrayList<>();
		try {
			Connection connection = ConnectionManager.getConnection();
			String query = 
					"SELECT ID, ComponentName, Price, Quantity, Description, CategoryID, ManufacturersLink, Image FROM Component " + 
					"WHERE deleted = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setBoolean(1, false);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				BigDecimal price = resultSet.getBigDecimal(3);
				int quantity = resultSet.getInt(4);
				String description = resultSet.getString(5);
				int categoryId = resultSet.getInt(6);
				Category category = CategoryDAO.getById(categoryId);
				String manufacturersLink = resultSet.getString(7);
				String image = resultSet.getString(8);
				
				Component component = new Component(id, name, price, quantity, description, category, manufacturersLink, image);
 				components.add(component);
			}
			resultSet.close();
			preparedStatement.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return components;
	}
	
	public static Collection<Component> getAllByFilter(int priceFrom, int priceTo, int availQuant, String description, int categoryId) {
		Collection<Component> components = new ArrayList<>();
		try {
			Connection connection = ConnectionManager.getConnection();
			String query = 
					"SELECT * FROM Component WHERE (Price BETWEEN ? AND ?) AND Quantity = ? AND Description = ?  AND CategoryID = ? AND deleted = ?"; 

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, priceFrom);
			preparedStatement.setInt(2, priceTo);
			preparedStatement.setInt(3, availQuant);
			preparedStatement.setString(4, description);
			preparedStatement.setInt(5, categoryId);
			preparedStatement.setBoolean(6, false);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				BigDecimal price = resultSet.getBigDecimal(3);
				int quantity = resultSet.getInt(4);
				String descriptionDb = resultSet.getString(5);
				int categoryIdDb = resultSet.getInt(6);
				Category category = CategoryDAO.getById(categoryIdDb);
				String manufacturersLink = resultSet.getString(7);
				String image = resultSet.getString(8);
				
				Component component = new Component(id, name, price, quantity, descriptionDb, category, manufacturersLink, image);
 				components.add(component);
			}
			resultSet.close();
			preparedStatement.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return components;
	}
	
	public static Component getById(int catId) {
		Component component = null;
		try{
			Connection connection = ConnectionManager.getConnection();
			String query = 
					"SELECT * from Component WHERE ID = ? AND deleted = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, catId);
			preparedStatement.setBoolean(2, false);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				BigDecimal price = resultSet.getBigDecimal(3);
				int quantity = resultSet.getInt(4);
				String description = resultSet.getString(5);
				int categoryId = resultSet.getInt(6);
				Category category = CategoryDAO.getById(categoryId);
				String manufacturersLink = resultSet.getString(7);
				String image = resultSet.getString(8);
				
				component = new Component(id, name, price, quantity, description, category, manufacturersLink, image);
			}
			resultSet.close();
			preparedStatement.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return component;
	}
	
	
	public static Collection<Component> getByName(String catName) {
		Collection<Component> components = new ArrayList<>();
		Component component = null;
		try{
			Connection connection = ConnectionManager.getConnection();
			String query = 
					"SELECT * from Component WHERE ComponentName LIKE ? AND deleted = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, '%'+catName+'%');
			preparedStatement.setBoolean(2, false);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				BigDecimal price = resultSet.getBigDecimal(3);
				int quantity = resultSet.getInt(4);
				String description = resultSet.getString(5);
				int categoryId = resultSet.getInt(6);
				Category category = CategoryDAO.getById(categoryId);
				String manufacturersLink = resultSet.getString(7);
				String image = resultSet.getString(8);
				
				component = new Component(id, name, price, quantity, description, category, manufacturersLink, image);
				components.add(component);
			}
			resultSet.close();
			preparedStatement.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return components;
	}
	
	
	public static boolean insert(Component component) {
		boolean success = false;
		
		try {
			Connection connection = ConnectionManager.getConnection();
			String query = 
					"INSERT INTO Component (ComponentName, Price, Quantity, Description, CategoryID, ManufacturersLink, Image) " +
					"VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, component.getName());
			preparedStatement.setBigDecimal(2, component.getPrice());
			preparedStatement.setInt(3, component.getAvailableQuantity());
			preparedStatement.setString(4, component.getDescription());
			preparedStatement.setLong(5, component.getCategory().getCode());
			preparedStatement.setString(6, component.getLinkManifacturer());
			preparedStatement.setString(7, component.getImage());
			
			success = preparedStatement.executeUpdate() == 1;
			
			preparedStatement.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return success;
	}
	
	public static boolean update(Component component) {
		boolean success = false;
		
		try {
			Connection connection = ConnectionManager.getConnection();
			String query = 
					"UPDATE Component " +
					"SET ComponentName = ?, Price = ?, Quantity = ?, Description = ?, CategoryID = ?, ManufacturersLink = ? , Image = ? " +
					"WHERE ID = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, component.getName());
			preparedStatement.setBigDecimal(2, component.getPrice());
			preparedStatement.setInt(3, component.getAvailableQuantity());
			preparedStatement.setString(4, component.getDescription());
			preparedStatement.setInt(5, component.getCategory().getCode());
			preparedStatement.setString(6, component.getLinkManifacturer());
			preparedStatement.setString(7, component.getImage());
			preparedStatement.setInt(8, component.getCode());
			
			success = preparedStatement.executeUpdate() == 1;
			preparedStatement.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return success;
	}
	
	public static boolean deleteLogical(int compId) {
		boolean success = false;
		
		try {
			Connection connection = ConnectionManager.getConnection();
			String query = 
					"UPDATE Component " + 
					"SET deleted = ? " +
					"WHERE id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setBoolean(1, true);
			preparedStatement.setLong(2, compId);
			success = preparedStatement.executeUpdate() == 1;
			preparedStatement.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return success;
	}
	
	// client - side
	
	public static Collection<Component> getAllMonitors() {
		Collection<Component> components = new ArrayList<>();
		try {
			Connection connection = ConnectionManager.getConnection();
			String query = 
					"SELECT * FROM Component WHERE CategoryID = 1 AND deleted = ?";
			// 1 for monitors
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setBoolean(1, false);
			ResultSet resultSet = preparedStatement.executeQuery(); 
			while(resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				BigDecimal price = resultSet.getBigDecimal(3);
				int quantity = resultSet.getInt(4);
				String description = resultSet.getString(5);
				int categoryId = resultSet.getInt(6);
				Category category = CategoryDAO.getById(categoryId);
				String manufacturersLink = resultSet.getString(7);
				String image = resultSet.getString(8);
				 
				Component component = new Component(id, name, price, quantity, description, category, manufacturersLink, image);
 				components.add(component);
			}
			resultSet.close();
			preparedStatement.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return components;
	}
	
}
