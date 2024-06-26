package org.example;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DBProcess {
    private static Connection connection = DBConnection.getConnection();

    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;

    public static void createTable() {

        if (connection == null) {
            System.out.println("Bağlantı sağlanamadı!");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Write the name of the table you want to create: ");
        String tableName = scanner.next();
        try {
            String query = "CREATE TABLE " + tableName + "(    id          serial primary key,\n" +
                    "    name        varchar(32) not null,\n" +
                    "    surname     varchar(32),\n" +
                    "    description varchar(32),\n" +
                    "    dob         date,\n" +
                    "    created_at  timestamp default current_timestamp,\n" +
                    "    teacher_id  integer references teacher (id))";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            System.out.println("Table has been created successfully !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            //DBConnection.closeConnection();
        }

    }

    public static void insertData(Student student) {
        try {
            String query = "INSERT INTO student(name,surname,description,dob,teacher_id) VALUES(?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getSurname());
            preparedStatement.setString(3, student.getDescription());
            preparedStatement.setDate(4, student.getDateOfBirthday());
            preparedStatement.setInt(5, student.getTeacherId());

            preparedStatement.executeUpdate();
            System.out.println("DATA insert successfully !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        //DBConnection.closeConnection();
    }

    public static void updateData(Student student) {
        String query = "UPDATE student SET id = ?, name = ?, surname = ?, description = ?, dob = ?, teacher_id = ? WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, student.getIdUpdate());
            preparedStatement.setString(2, student.getName());
            preparedStatement.setString(3, student.getSurname());
            preparedStatement.setString(4, student.getDescription());
            preparedStatement.setDate(5, student.getDateOfBirthday());
            preparedStatement.setInt(6, student.getTeacherId());
            preparedStatement.setInt(7, student.getId());


            preparedStatement.executeUpdate();
            System.out.println("Table has successfully update! ");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    public static void updateData2() {
        Scanner scanner = new Scanner(System.in);

        try {
            int minID = getMinID();
            int maxID = getMaxID();


            System.out.println("Minimum ID: " + minID);
            System.out.println("Maximum ID: " + maxID);

            System.out.println("Enter student ID");
            int studentID = scanner.nextInt();


            String querySelect = "SELECT * FROM student WHERE id = ?";
            try {
                preparedStatement = connection.prepareStatement(querySelect);
                preparedStatement.setInt(1, studentID);
                resultSet = preparedStatement.executeQuery();

                if (!resultSet.next()) {
                    System.out.println("Student with ID " + studentID + " not found.");
                    System.out.println("Please enter an ID within the valid range.");
                }

            } catch (SQLException e) {
                System.out.println("Failed to fetch student data.");
                throw new RuntimeException(e);
            }


            System.out.println("Enter the column name to update (id, name, surname, description, dob, teacher_id):");
            String columnName = scanner.next();
            System.out.println("Enter the new value for " + columnName + ":");


            String newValue = scanner.next();

            Object newValueObj = null;
            switch (columnName) {
                case "id":
                case "teacher_id":
                    newValueObj = Integer.parseInt(newValue);
                    break;
                case "dob":
                    //newValueObj = Date.valueOf(newValue);
                    newValueObj = LocalDate.parse(newValue);
                    break;
                default:
                    newValueObj = newValue;
                    break;
            }


            String queryUpdate = "UPDATE student SET " + columnName + " = ? WHERE id = ?";
            try {
                preparedStatement = connection.prepareStatement(queryUpdate);
                preparedStatement.setObject(1, newValueObj);
                preparedStatement.setInt(2, studentID);
                preparedStatement.executeUpdate();
                System.out.println("Data successfully updated!");
            } catch (SQLException e) {
                System.out.println("Failed to update data.");
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            System.out.println("Failed to fetch min/max ID.");
            throw new RuntimeException(e);
        }
    }


    public static int getMinID() throws SQLException {
        String query = "SELECT MIN(id) FROM student";
        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int minID = resultSet.getInt(1);
        preparedStatement.close();
        return minID;
    }

    public static int getMaxID() throws SQLException {
        String query = "SELECT MAX(id) FROM student";
        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int maxID = resultSet.getInt(1);
        preparedStatement.close();
        return maxID;
    }


    public static void deleteDataById(int id) {
        String query = "DELETE from student WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("No student with ID " + id + " found in the database.");

                int minId = getMinID();
                int maxId = getMaxID();
                System.out.println("Please enter a valid ID within the existing range: (" + minId + "-" + maxId + ")");
            } else {
                System.out.println("Student with ID " + id + " successfully deleted from the database.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            //DBConnection.closeConnection();
        }
    }


    public static void getDataById() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter student ID");
        int studentID = scanner.nextInt();

        String query = "SELECT * FROM student WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, studentID);

            resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String description = resultSet.getString("description");
                Date dob = resultSet.getDate("dob");
                Timestamp createdAt = resultSet.getTimestamp("created_at");
                Integer teacherId = resultSet.getInt("teacher_id");

                System.out.println("ID: " + id + ", Name: " + name + ", Surname: " + surname +
                        ", Description: " + description + ", Date of Birth: " + dob +
                        ", Created At: " + createdAt + ", Teacher ID: " + teacherId);

            }

            if (!resultSet.next()) {
                System.out.println("Student with ID " + studentID + " not found.");
                System.out.println("Please enter an ID within the valid range.");

                int minID = getMinID();
                int maxID = getMaxID();
                System.out.println("You can only choose from this range: " + minID + "-" + maxID);
            }
        } catch (SQLException e) {
            System.out.println("Connection failure");
            throw new RuntimeException(e);
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            //DBConnection.closeConnection();

        }
    }


    public static void getDataWithLikeOperator() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write a little letter :");
        String letter = scanner.next();

        String query = "SELECT name,surname FROM student WHERE name ILIKE '" + letter + "%'";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                String name = resultSet.getString(1);
                String surname = resultSet.getString(2);

                System.out.println("Name: " + name + " surname: " + surname);
            }

            if (!resultSet.next()) {
                System.out.println("Your letter: " + "'" + letter + "'" + " not found.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            //DBConnection.closeConnection();
        }
    }


    public static List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();

        try {
            String query = "SELECT s.id,s.name,s.surname,s.description,s.dob,s.created_at,t.name AS teacher_name " +
                    "FROM student s " +
                    "INNER JOIN teacher t ON t.id = s.teacher_id;";

            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setName(resultSet.getString("name"));
                student.setSurname(resultSet.getString("surname"));
                student.setDescription(resultSet.getString("description"));
                student.setDateOfBirthday(resultSet.getDate("dob"));
                student.setCreatedAt(resultSet.getTimestamp("created_at"));
                student.setTeacherName(resultSet.getString("teacher_name"));

                studentList.add(student);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            //DBConnection.closeConnection();
        }
        return studentList;
    }


    public static List<Student> getAllStudentsAndPrint() {
        List<Student> studentList = getAllStudents();
        System.out.println("All students: ");
        for (Student student : studentList) {
            System.out.println(student);
        }
        return studentList;
    }

}

