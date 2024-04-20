package org.example;


import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;



public class WitchMethodWantToUse {

    public static void witchMethodWantTo() {

        Scanner scanner = new Scanner(System.in);
        try{
            while (true) {
                System.out.println("Choose an operation:");
                System.out.println("1. Create Table");
                System.out.println("2. Insert Data");
                System.out.println("3. Update Data");
                System.out.println("4. Delete Data");
                System.out.println("5. Get Data by ID");
                System.out.println("6. Get Data with Like Operator");
                System.out.println("7. Get information about all students ");
                System.out.println("8  Update Data by one column only");
                System.out.println("0. Exit");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        DBProcess.createTable();
                        break;
                    case 2:
                        try{
                            System.out.println("Enter student information:");
                            System.out.print("Name: ");
                            String name = scanner.next();
                            System.out.print("Surname: ");
                            String surname = scanner.next();
                            System.out.print("Description: ");
                            scanner.nextLine();
                            String description = scanner.nextLine();
                            System.out.print("Date of Birth (yyyy-mm-dd): ");
                            String dobString = scanner.next();


                            Date dob = Date.valueOf(dobString);


                            System.out.print("Teacher ID: ");
                            int teacherId = scanner.nextInt();


                            Student student = new Student(name, surname, description, dob, teacherId);
                            DBProcess.insertData(student);
                        }catch (Exception e){

                            System.out.println(e.getMessage()+"Wrong writing");
                        }



                        break;
                    case 3:
                        int minID = DBProcess.getMinID();
                        int maxID = DBProcess.getMaxID();

                        System.out.println("You can only choose from this range: "+minID+"-"+maxID);

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

                        DBProcess.updateData(student1);
                        break;
                    case 4:
                        System.out.println("Which id do you want to delete ? ");
                        int id = scanner.nextInt();
                        DBProcess.deleteDataById(id);
                        break;
                    case 5:
                        System.out.println("Which id do you want to search for ? ");
                        DBProcess.getDataById();
                        break;
                    case 6:
                        System.out.println("What letter do you want the name to start with? ");
                        DBProcess.getDataWithLikeOperator();
                        break;
                    case 7:
                        System.out.println("Get information about all students");
                        DBProcess.getAllStudentsAndPrint();
                        break;
                    case 8:
                        DBProcess.updateData2();
                        break;
                    case 0:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnection.closeConnection();
        }
    }

}
