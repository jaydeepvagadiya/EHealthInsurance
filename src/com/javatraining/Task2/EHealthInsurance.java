package com.javatraining.Task2;

import java.util.Scanner;
import java.sql.*;

public class EHealthInsurance {

	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://10.1.3.2:3306/emp";
	static final String USER = "root";
	static final String PASS = "ZzeronSec!nd@1@2";

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		try {

			Class.forName(JDBC_DRIVER);

			Scanner scanner = new Scanner(System.in);
			System.out.print("Please enter your employee id: ");
			int getid = scanner.nextInt();
			scanner.close();
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();

			String sql = "SELECT * FROM employees WHERE id=" + getid;
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				int age = rs.getInt("age");
				String first = rs.getString("first_name");
				int weight = rs.getInt("weight");

				System.out.println("Hi " + first + ", Welcome to Employee Health Insurance premium calculator.");
				System.out.println();

				sql = "SELECT min_Age, max_Age, min_Weight, max_Weight, Health_Level, PAmount FROM insurancePremiumTable WHERE min_Age <="
						+ age + "&& max_Age >=" + age + "&& min_Weight <=" + weight + "&& max_Weight >=" + weight;
				rs = stmt.executeQuery(sql);

				if (rs.next()) {

					int minAge = rs.getInt("min_Age");
					int maxAge = rs.getInt("max_Age");

					String HealthLevel = rs.getString("Health_Level");
					int PAmount = rs.getInt("PAmount");

					System.out.println("Your age: " + age + "(" + "Age Level: " + minAge + "-" + maxAge + ")");
					System.out.println("Your weight: " + weight + "(" + "Health Level: " + HealthLevel + ")");
					System.out.println(
							"Your health insurance premium is ₹" + PAmount + "/-, determined by your age and weight.");

				} else {
					System.out.println("Sorry, you are not eligible for health insurance at this time.");
				}

			} else {
				System.out.println("Your id not match with our employee database.");
			}

			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		System.out.println("Goodbye! Have a great day.");

	}

}
