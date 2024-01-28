package org.example;


import java.sql.*;


public class FirstConnectionTry {
    private static final String dbURL = "jdbc:postgresql://localhost:5432/dvdrental";
    private static final String username = "postgres";
    private static final String password = "alma";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbURL, username, password);
    }


    public void readData() {
        try (Connection connection = getConnection()) {
            String sqlQuery = "SELECT * FROM teacher";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        String surname = resultSet.getString("surname");
                        String description = resultSet.getString("description");
                        int salary = resultSet.getInt("salary");


                        System.out.println("ID: " + id + ", Name: " + name + ", Surname: " + surname + ", description: "+description +", salary: "+salary);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void updateData() {
        try (Connection connection = getConnection()) {
            String sqlUpdate = "UPDATE teacher SET column1 = ?, column2 = ? WHERE condition";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate)) {
                preparedStatement.setString(1, "new_value1");
                preparedStatement.setString(2, "new_value2");


                int rowsUpdated = preparedStatement.executeUpdate();
                System.out.println(rowsUpdated + " rows updated successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



