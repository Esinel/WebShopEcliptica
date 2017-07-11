package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import entities.User;
import entities.User.Role;

public class AdminDAO {
	
	public static User getByUserNameAndPassword(String usName, String pass) {
		User admin = null;
		try {
			Connection connection = ConnectionManager.getConnection();
			String query = 
					"SELECT *" + 
					"FROM Users " + 
					"WHERE userName = ? AND passwordd = ? AND role = ADMIN";
			
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
				String role = resultSet.getString(8);
				admin = new User(firstName, lastName, userName, password, phoneNum, email);
				admin.setUserType(role);
			} 
			resultSet.close();
			preparedStatement.close();
		} catch (Exception ex) {ex.printStackTrace();}

		return admin;
	}
}
