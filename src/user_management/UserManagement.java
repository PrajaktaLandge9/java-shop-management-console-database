package user_management;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import db_operations.DBUtils;

public class UserManagement
{
	public static void userManagement()
	{
		Scanner sc = new Scanner(System.in);
		boolean canIKeepRunningTheProgram = true;
		while(canIKeepRunningTheProgram == true) 
		{
			System.out.println("\n Welcome to user management !");
			System.out.println("\n Select one of the options given below :");
			System.out.println("1. Add user : ");
			System.out.println("2. Edit user : " );
			System.out.println("3. Delete user : ");
			System.out.println("4. Search user : ");
			System.out.println("5. Quit");
			int optionSelectedByUser = sc.nextInt();
			if(optionSelectedByUser == UserOptions.QUIT)
			{
				System.out.println("\n User management application closed !");
				canIKeepRunningTheProgram = false;
			}
			else if(optionSelectedByUser == UserOptions.ADD_USER) 
			{
				addUser();
			}
			else if(optionSelectedByUser == UserOptions.SEARCH_USER) 
			{
				System.out.println("\n Enter user to be searched : \n"); 
				sc.nextLine();                                          
				String searchUserName = sc.nextLine();
				searchUser(searchUserName);
			}
			else if(optionSelectedByUser == UserOptions.DELETE_USER) 
			{
				System.out.println("\n Enter user to be deleted : \n");
				sc.nextLine();                                       
				String deleteUserName = sc.nextLine();
				deleteUser(deleteUserName);
				System.out.println("\n User deleted sucessfully.");
			}
			else if(optionSelectedByUser == UserOptions.EDIT_USER) 
			{
				System.out.println("\n Enter user to be edited : ");
				sc.nextLine();                                    
				String editUserName = sc.nextLine();
				editUser(editUserName);
				System.out.println("\n User edited successfully !");
			}
		}
	}
	public static void addUser()
	{
		User userObject = new User();
		Scanner sc = new Scanner(System.in);

		System.out.println("\n User name : ");
		userObject.userName = sc.nextLine();

		System.out.println(" Login name : ");
		userObject.loginName = sc.nextLine();

		System.out.println(" User role : ");
		userObject.userRole = sc.nextLine();

		System.out.println(" Password : ");
		userObject.password = sc.nextLine();

		System.out.println(" Confirm password : ");
		userObject.confirmPassword = sc.nextLine();

		System.out.println("\n User details are : \n");
		System.out.println("User name is : "+userObject.userName);
		System.out.println("Login name is : "+userObject.loginName);
		System.out.println("User role is : "+userObject.userRole);
		System.out.println("Password is : "+userObject.password);
		System.out.println("Confirm Password is : "+userObject.confirmPassword);

		String query = "INSERT INTO user(user_name, login_name, password, confirm_Password, user_role) VALUES ('"
				+ userObject.userName + "', '" + userObject.loginName + "', '" + userObject.password + "','"
				+ userObject.confirmPassword + "','" + userObject.userRole + "')";
		DBUtils.executeQuery(query);
	}

	public static void searchUser(String userName)
	{
		String query = "select * from user where user_name ='"+userName+ "' ";
		ResultSet rs = DBUtils.executeQueryGetResult(query);
		try 
		{
			while(rs.next())
			{
				if(rs.getString("user_name").equalsIgnoreCase(userName))
				{
					System.out.println(" User name is : "+ rs.getString("user_name"));
					System.out.println(" Login name is : "+ rs.getString("login_name"));
					System.out.println(" User role is : "+ rs.getString("user_role"));
					System.out.println(" Password is : "+ rs.getString("password"));
					System.out.println(" Confirm password is : "+ rs.getString("confirm_password"));
					return;
				}
			}
		}catch(Exception e)
		{
			System.out.println("\n User not found.");
		}
	}
	public static void deleteUser(String userName)
	{
		String query = "delete from user where user_name = '"+userName+"' ";
		DBUtils.executeQuery(query);
	}

	public static void editUser(String userName) {

		String query = "select * from user where user_name='"+userName+"' ";
		ResultSet rs = DBUtils.executeQueryGetResult(query);

		try {
			while (rs.next())
			{ 
				if (rs.getString("user_name").equalsIgnoreCase(userName)) {
					Scanner scanner = new Scanner(System.in);
					User user = new User();

					System.out.println("Editing user: "+userName);

					System.out.print("New User Name: ");
					user.userName = scanner.nextLine();

					System.out.print("New Login Name: ");
					user.loginName = scanner.nextLine();

					System.out.print("New Password: ");
					user.password = scanner.nextLine();

					System.out.print("New Confirm Password: ");
					user.confirmPassword = scanner.nextLine();

					System.out.print("New User Role: ");
					user.userRole = scanner.nextLine();

					String updateQuery = "update user set" + "user_name='"+user.userName+"', "+"login_name = '"+user.loginName+"', "
							+ "user_role='"+user.userRole+"', password='"+user.password+"', "+ "confirm_password='"+
							user.confirmPassword+"' where user_name='"+rs.getString("user_name")+"'";

					DBUtils.executeQuery(updateQuery);
					return;
				}
			}
		} 
		catch (Exception e) {
			System.out.println("User not found.");
		}
	}
    public static boolean validateUserAndPassword( String loginName, String password )
	{
    	String query = "select * from user where login_name ='"+loginName+"'and password='"+password+"' ";

		ResultSet rs = DBUtils.executeQueryGetResult(query);

		try {
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return false;
	}
}



