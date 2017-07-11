package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Statement;


import entities.Category;


public class CategoryDAO {

	
	public static List<Category> categorieList = new ArrayList<>();
	
	public static void loadAll() {
		Category subCategory;
		try {
			Connection connection = ConnectionManager.getConnection();
			String query = 
					"SELECT ID, CategoryName, Description, SubcategoryID FROM Category " + 
					"WHERE deleted = ? AND ID != 62";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setBoolean(1, false);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				String description = resultSet.getString(3);
				int subId = resultSet.getInt(4);
				if(subId == 0) { 
					subCategory = new Category();
					subCategory.setName("No subcategory");
				}else {
					subCategory = getById(subId);
					if (subCategory == null){
						subCategory = new Category();
						subCategory.setName("No subcategory");
					}
				}
				Category category = new Category(id, name, description, subCategory);
				categorieList.add(category);
			}
			resultSet.close();
			preparedStatement.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
		
	

	public static Category getById(int id) {
		Category category = null;
		try{
			Connection connection = ConnectionManager.getConnection();
			String query = 
					"SELECT * FROM Category WHERE ID = ? AND deleted = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			preparedStatement.setBoolean(2, false);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int catID = resultSet.getInt(1);
				String catName = resultSet.getString(2);
				String catDescr = resultSet.getString(3);
				int subCatID = resultSet.getInt(4);
				Category subCategory = new Category();
				subCategory.setCode(subCatID);
				category = new Category(catID, catName, catDescr, subCategory);
			}
			resultSet.close();
			preparedStatement.close();
		} catch (Exception ex) {ex.printStackTrace();}

			return category;
		}
	
	
	public static boolean insert(Category category) {
		boolean success = false;
		
		try {
			Connection connection = ConnectionManager.getConnection();
			String query = 
					"INSERT INTO Category (CategoryName, Description, SubcategoryID) " + 
					"VALUES (?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, category.getName());
			preparedStatement.setString(2, category.getDescription());
			preparedStatement.setLong(3, category.getSubcategory().getCode());
			
			success = preparedStatement.executeUpdate() == 1;
			
			preparedStatement.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return success;
	};
	
	public static boolean deleteLogical(int catId) {
		boolean success = false;
		
		try {
			Connection connection = ConnectionManager.getConnection();
			String query = 
					"UPDATE Category " +
					"SET deleted = ? " +
					"WHERE ID = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setBoolean(1, true);
			preparedStatement.setLong(2, catId);
			success = preparedStatement.executeUpdate() == 1;
			preparedStatement.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return success;
	}
	
	public static boolean update(Category category) {
		boolean success = false;
		
		try {
			Connection connection = ConnectionManager.getConnection();
			String query = 
					"UPDATE Category " +
					"SET CategoryName = ?, Description = ?, SubcategoryID = ? " + 
					"WHERE ID = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, category.getName());
			preparedStatement.setString(2, category.getDescription());
			preparedStatement.setLong(3, category.getSubcategory().getCode());
			preparedStatement.setLong(4, category.getCode());
			
			success = preparedStatement.executeUpdate() == 1;
			preparedStatement.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return success;
	}
	
	public static int getLastId() {
		int id = 0;
		try {
			Connection connection = ConnectionManager.getConnection();
			String query = 
					"SELECT MAX(ID) FROM Category;";
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
