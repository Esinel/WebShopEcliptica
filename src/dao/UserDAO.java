package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import entities.User;

public class UserDAO {

	public static User getByUserName(String usName) {
		User user = null;
		try {
			Connection connection = ConnectionManager.getConnection();
			
			String query = 
					"SELECT * FROM Users " + 
					"WHERE userName = ?";
			
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, usName);

			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String firstName = resultSet.getString(1);
				String lastName = resultSet.getString(2);
				String userName = resultSet.getString(3);
				String password = resultSet.getString(4);
				String userRole = resultSet.getString(5);
				int phoneNum = resultSet.getInt(6);
				String email = resultSet.getString(7);
				user = new User(firstName, lastName, userName, password, phoneNum, email);
				user.setUserType(userRole);
			}
			resultSet.close();
			preparedStatement.close();
		} catch (Exception ex) {ex.printStackTrace();}

		return user;
	}

	public static User getByUserNameAndPassword(String usName, String pass) {
		User user = null;
		try {
			Connection connection = ConnectionManager.getConnection();
			System.out.println(connection);
			String query = 
					"SELECT *" + 
					"FROM Users " + 
					"WHERE userName = ? AND passwordd = ?";
			
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, usName);
			preparedStatement.setString(2, pass);

			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String firstName = resultSet.getString(1);
				String lastName = resultSet.getString(2);
				String userName = resultSet.getString(3);
				String password = resultSet.getString(4);
				String userRole = resultSet.getString(5);
				int phoneNum = resultSet.getInt(6);
				String email = resultSet.getString(7);
				user = new User(firstName, lastName, userName, password, phoneNum, email);
				user.setUserType(userRole);
			} 
			resultSet.close();
			preparedStatement.close();
		} catch (Exception ex) {ex.printStackTrace();}

		return user;
	}

	public static boolean insert(User user) {
		boolean success = false;
		try {
			Connection connection = ConnectionManager.getConnection();
			
			String query = 
					"INSERT INTO Users (firstName, lastName, userName, passwordd, role, phoneNumber, email) " + 
					"VALUES (?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, user.getFirstName());
			preparedStatement.setString(2, user.getLastName());
			preparedStatement.setString(3, user.getUsername());
			preparedStatement.setString(4, user.getPassword());
			preparedStatement.setString(5, user.getUserType().name());
			preparedStatement.setInt(6, user.getPhoneNumber());
			preparedStatement.setString(7, user.getEmail());

			success = preparedStatement.executeUpdate() == 1;

			preparedStatement.close();
		} catch (Exception ex) {ex.printStackTrace();}

		return success;
	}

	public static boolean update(User user) {
		boolean success = false;
		try {
			Connection connection = ConnectionManager.getConnection();
			
			String query = 
					"UPDATE Users " + 
					"SET firstName = ?, lastName = ?, password = ? " + 
					"WHERE userName = ?";
			
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, user.getFirstName());
			preparedStatement.setString(2, user.getLastName());
			preparedStatement.setString(3, user.getPassword());

			preparedStatement.setString(4, user.getUsername());

			success = preparedStatement.executeUpdate()  == 1;

			preparedStatement.close();
		} catch (Exception ex) {ex.printStackTrace();}

		return success;
	}
	
	public static boolean delete(String userName) {
		boolean success = false;
		try {
			Connection connection = ConnectionManager.getConnection();
			
			String query = 
					"DELETE FROM Users " + 
					"WHERE userName = ?";
			
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, userName);

			success = preparedStatement.executeUpdate() == 1;

			preparedStatement.close();
		} catch (Exception ex) {ex.printStackTrace();}

		return success;
	}

	public static Collection<User> getAll() {
		Collection<User> users = new ArrayList<>();
		try {
			Connection connection = ConnectionManager.getConnection();
			
			String query = 
					"SELECT *" + 
					"FROM Users";
			
			PreparedStatement preparedStatement = connection.prepareStatement(query);

			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String firstName = resultSet.getString(1);
				String lastName = resultSet.getString(2);
				String userName = resultSet.getString(3);
				String password = resultSet.getString(4);
				String userRole = resultSet.getString(5);
				int phoneNum = resultSet.getInt(6);
				String email = resultSet.getString(7);
				User user = new User(firstName, lastName, userName, password, phoneNum, email);
				user.setUserType(userRole);
				users.add(user);
			}

			resultSet.close();
			preparedStatement.close();
		} catch (Exception ex) {ex.printStackTrace();}

		return users;
	}
	
	public static Collection<User> findByUserName(String userNameFilter) {
		Collection<User> users = new ArrayList<>();
		try {
			Connection connection = ConnectionManager.getConnection();
			
			String query = 
					"SELECT *" + 
					"FROM Users " + 
					"WHERE userName LIKE ?";
			
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, "%" + userNameFilter + "%");

			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String firstName = resultSet.getString(1);
				String lastName = resultSet.getString(2);
				String userName = resultSet.getString(3);
				String password = resultSet.getString(4);
				String userRole = resultSet.getString(5);
				int phoneNum = resultSet.getInt(6);
				String email = resultSet.getString(7);
				User user = new User(firstName, lastName, userName, password, phoneNum, email);
				user.setUserType(userRole);
				users.add(user);
			}

			resultSet.close();
			preparedStatement.close();
		} catch (Exception ex) {ex.printStackTrace();}

		return users;
	}
	
}
