package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DBProcess {
    private static Connection connection = DBConnection.getConnection();
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;


    public static void createTable() {
        //text
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
            DBConnection.closeConnection();
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
            //try with resources
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        DBConnection.closeConnection();

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


            int a = preparedStatement.executeUpdate();
            System.out.println(a + " Table has successfully update! ");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            DBConnection.closeConnection();
        }
    }


    public static void deleteDataById(int id) {
        String query = "DELETE from student WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            preparedStatement.execute();
            System.out.println("Table deleted from database !");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            DBConnection.closeConnection();
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
            DBConnection.closeConnection();

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
            DBConnection.closeConnection();
        }

    }


    public static void witchMethodWantTo() {


        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Choose an operation:");
            System.out.println("1. Create Table");
            System.out.println("2. Insert Data");
            System.out.println("3. Update Data");
            System.out.println("4. Delete Data");
            System.out.println("5. Get Data by ID");
            System.out.println("6. Get Data with Like Operator");
            System.out.println("7. Get information about all students ");
            System.out.println("0. Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    createTable();
                    break;
                case 2:
                    System.out.println("Enter student information:");
                    System.out.print("Name: ");
                    String name = scanner.next();
                    System.out.print("Surname: ");
                    String surname = scanner.next();
                    System.out.print("Description: ");
                    String description = scanner.next();
                    System.out.print("Date of Birth (yyyy-mm-dd): ");
                    String dobString = scanner.next();
                    Date dob = Date.valueOf(dobString);
                    System.out.print("Teacher ID: ");
                    int teacherId = scanner.nextInt();


                    Student student = new Student(name, surname, description, dob, teacherId);


                    insertData(student);
                    break;
                case 3:
                    System.out.println("Enter Student Information: ");
                    System.out.println("ID: (new)");
                    int id1 = scanner.nextInt();
                    System.out.println("Name: ");
                    String name1 = scanner.next();
                    System.out.println("Surname: ");
                    String surname1 = scanner.next();
                    System.out.println("Description: ");
                    String description1 = scanner.next();
                    System.out.println("Date of Birthday (yyyy-mm-dd) : ");
                    String dateOfBirthday = scanner.next();
                    Date dateOfBirthday1 = Date.valueOf(dateOfBirthday);
                    System.out.println("Teacher ID  (number)");
                    int teacherId1 = scanner.nextInt();
                    System.out.println("Which ID do you want to change this with? (number)");
                    int idUpdate1 = scanner.nextInt();

                    Student student1 = new Student(id1, name1, surname1, description1, dateOfBirthday1, teacherId1, idUpdate1);

                    updateData(student1);
                    break;
                case 4:
                    System.out.println("Which id do you want to delete ? ");
                    int id = scanner.nextInt();
                    deleteDataById(id);
                    break;
                case 5:
                    System.out.println("Which id do you want to search for ? ");
                    getDataById();
                    break;
                case 6:
                    System.out.println("What letter do you want the name to start with? ");
                    getDataWithLikeOperator();
                    break;
                case 7:
                    System.out.println("Get information about all students");
                    getAllStudentsAndPrint();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }


    public static List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();

        try {
            String query = "SELECT * FROM student";
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
                student.setTeacherId(resultSet.getInt("teacher_id"));

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

            DBConnection.closeConnection();
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

