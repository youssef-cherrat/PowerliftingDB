package org.powerlifting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseDriver {

    // SQLite connection URL
    private static final String URL = "jdbc:sqlite:powerlifting.sqlite";
    private Connection connection;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void connect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            throw new IllegalStateException("Connection already open.");
        }
        connection = DriverManager.getConnection(URL);
    }

    public void commit() throws SQLException {
        connection.commit();
    }

    public void rollback() throws SQLException {
        connection.rollback();
    }

    public void disconnect() throws SQLException {
        connection.close();
    }

//    public static void main(String[] args) {
//        try {
//            // Load JDBC driver
//            Class.forName("org.sqlite.JDBC");
//
//            // Establish connection
//            try (Connection connection = DriverManager.getConnection(URL)) {
//                try (Statement statement = connection.createStatement()) {
//                    // Create tables
//                    createTables(statement);
//
//                    // Insert initial data
//                    insertInitialData(statement);
//
//                    // Perform queries
//                    performQueries(statement);
//
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }
//    }

    private void createTables() throws SQLException {
        try {
            Statement statement = connection.createStatement();

            // Create Year table
            String createYearTable = "CREATE TABLE IF NOT EXISTS Year ("
                    + "Year_ID INTEGER PRIMARY KEY, "
                    + "Start_Date DATE, "
                    + "End_Date DATE);";

            // Create Semester table
            String createSemesterTable = "CREATE TABLE IF NOT EXISTS Semester ("
                    + "Semester_ID INTEGER PRIMARY KEY, "
                    + "Year_ID INTEGER, "
                    + "Session_Name TEXT, "
                    + "Member_Dues_Deadline DATETIME, "
                    + "Start_Date DATE, "
                    + "End_Date DATE, "
                    + "FOREIGN KEY (Year_ID) REFERENCES Year(Year_ID));";

            // Create Member table
            String createMemberTable = "CREATE TABLE IF NOT EXISTS Member ("
                    + "Member_ID INTEGER PRIMARY KEY, "
                    + "Semester_ID INTEGER, "
                    + "Member_First_Name TEXT, "
                    + "Member_Last_Name TEXT, "
                    + "Member_Date_of_Birth DATE, "
                    + "Member_Grad_Date DATE, "
                    + "Member_Weight_Class FLOAT, "
                    + "Member_Best_Total_KG FLOAT, "
                    + "Member_Gender TEXT, "
                    + "Member_Email TEXT, "
                    + "Member_Password_Hash TEXT, "
                    + "FOREIGN KEY (Semester_ID) REFERENCES Semester(Semester_ID));";

            // Create Executive table
            String createExecutiveTable = "CREATE TABLE IF NOT EXISTS Executive ("
                    + "Executive_ID INTEGER PRIMARY KEY, "
                    + "Semester_ID INTEGER, "
                    + "Executive_First_Name TEXT, "
                    + "Executive_Last_Name TEXT, "
                    + "Executive_Role TEXT, "
                    + "Executive_Email TEXT, "
                    + "Executive_Password_Hash TEXT, "
                    + "FOREIGN KEY (Semester_ID) REFERENCES Semester(Semester_ID));";

            // Create Alumni table
            String createAlumniTable = "CREATE TABLE IF NOT EXISTS Alumni ("
                    + "Alumni_ID INTEGER PRIMARY KEY, "
                    + "Alumni_First_Name TEXT, "
                    + "Alumni_Last_Name TEXT, "
                    + "Alumni_Class_Year INTEGER, "
                    + "Alumni_Email TEXT, "
                    + "Semester_ID INTEGER, "
                    + "FOREIGN KEY (Semester_ID) REFERENCES Semester(Semester_ID));";

            // Create Practice table
            String createPracticeTable = "CREATE TABLE IF NOT EXISTS Practice ("
                    + "Practice_ID INTEGER PRIMARY KEY, "
                    + "Date DATE, "
                    + "Location TEXT);";

            // Create Attendance table
            String createAttendanceTable = "CREATE TABLE IF NOT EXISTS Attendance ("
                    + "Member_ID INTEGER, "
                    + "Practice_ID INTEGER, "
                    + "Status TEXT, "
                    + "PRIMARY KEY (Member_ID, Practice_ID), "
                    + "FOREIGN KEY (Member_ID) REFERENCES Member(Member_ID), "
                    + "FOREIGN KEY (Practice_ID) REFERENCES Practice(Practice_ID));";

            // Create Competition table
            String createCompetitionTable = "CREATE TABLE IF NOT EXISTS Competition ("
                    + "Competition_ID INTEGER PRIMARY KEY, "
                    + "Location TEXT, "
                    + "Year_ID INTEGER, "
                    + "Competition_Date DATE, "
                    + "FOREIGN KEY (Year_ID) REFERENCES Year(Year_ID));";

            // Create Competition_Member table
            String createCompetitionMemberTable = "CREATE TABLE IF NOT EXISTS Competition_Member ("
                    + "Competition_ID INTEGER, "
                    + "Member_ID INTEGER, "
                    + "PRIMARY KEY (Competition_ID, Member_ID), "
                    + "FOREIGN KEY (Competition_ID) REFERENCES Competition(Competition_ID), "
                    + "FOREIGN KEY (Member_ID) REFERENCES Member(Member_ID));";

            statement.execute(createYearTable);
            statement.execute(createSemesterTable);
            statement.execute(createMemberTable);
            statement.execute(createExecutiveTable);
            statement.execute(createAlumniTable);
            statement.execute(createPracticeTable);
            statement.execute(createAttendanceTable);
            statement.execute(createCompetitionTable);
            statement.execute(createCompetitionMemberTable);
        } catch (SQLException e) {
            rollback();
            throw e;
        }
    }

    private void insertInitialData() throws SQLException {
        Statement statement = connection.createStatement();

        // Insert data into Year table
        String insertYears = "INSERT INTO Year (Year_ID, Start_Date, End_Date) VALUES "
                + "(1, '2024-01-01', '2024-12-31'), "
                + "(2, '2025-01-01', '2025-12-31');";

        // Insert data into Semester table
        String insertSemesters = "INSERT INTO Semester (Semester_ID, Year_ID, Session_Name, Member_Dues_Deadline, Start_Date, End_Date) VALUES "
                + "(1, 1, 'Spring 2024', '2024-01-15 23:59:59', '2024-01-10', '2024-05-20'), "
                + "(2, 1, 'Fall 2024', '2024-08-15 23:59:59', '2024-08-20', '2024-12-15'), "
                + "(3, 2, 'Spring 2025', '2025-01-15 23:59:59', '2025-01-10', '2025-05-20');";

        // Insert data into Member table
        String insertMembers = "INSERT INTO Member (Member_ID, Semester_ID, Member_First_Name, Member_Last_Name, Member_Date_of_Birth, Member_Grad_Date, Member_Weight_Class, Member_Best_Total_KG, Member_Gender, Member_Email, Member_Password_Hash) VALUES "
                + "(1, 1, 'John', 'Doe', '2000-01-01', '2024-05-20', 75.0, 500.0, 'Male', 'john.doe@example.com', 'hash1'), "
                + "(2, 1, 'Jane', 'Smith', '2001-02-01', '2025-05-20', 60.0, 450.0, 'Female', 'jane.smith@example.com', 'hash3'), "
                + "(3, 2, 'Mike', 'Brown', '1999-03-03', '2024-12-15', 82.5, 600.0, 'Male', 'mike.brown@example.com', 'hash4'), "
                + "(4, 3, 'Emily', 'Davis', '2002-04-04', '2025-12-15', 70.0, 550.0, 'Female', 'emily.davis@example.com', 'hash5');";

        // Insert data into Executive table
        String insertExecutives = "INSERT INTO Executive (Executive_ID, Semester_ID, Executive_First_Name, Executive_Last_Name, Executive_Role, Executive_Email, Executive_Password_Hash) VALUES "
                + "(1, 1, 'Alice', 'Smith', 'President', 'alice.smith@example.com', 'hash2'), "
                + "(2, 2, 'Robert', 'Lee', 'Vice President', 'robert.lee@example.com', 'hash6'), "
                + "(3, 3, 'Linda', 'White', 'Secretary', 'linda.white@example.com', 'hash7');";

        // Insert data into Alumni table
        String insertAlumni = "INSERT INTO Alumni (Alumni_ID, Alumni_First_Name, Alumni_Last_Name, Alumni_Class_Year, Alumni_Email, Semester_ID) VALUES "
                + "(1, 'Bob', 'Johnson', 2023, 'bob.johnson@example.com', 1), "
                + "(2, 'Sara', 'Williams', 2022, 'sara.williams@example.com', 1), "
                + "(3, 'Tom', 'Wilson', 2023, 'tom.wilson@example.com', 2);";

        // Insert data into Practice table
        String insertPractices = "INSERT INTO Practice (Practice_ID, Date, Location) VALUES "
                + "(1, '2024-02-01', 'Gym A'), "
                + "(2, '2024-02-03', 'Gym B'), "
                + "(3, '2024-02-05', 'Gym C'), "
                + "(4, '2024-08-21', 'Gym A');";

        // Insert data into Attendance table
        String insertAttendance = "INSERT INTO Attendance (Member_ID, Practice_ID, Status) VALUES "
                + "(1, 1, 'Present'), "
                + "(1, 2, 'Present'), "
                + "(1, 3, 'Absent'), "
                + "(2, 2, 'Present'), "
                + "(2, 4, 'Present'), "
                + "(3, 3, 'Present'), "
                + "(4, 4, 'Absent');";

        // Insert data into Competition table
        String insertCompetitions = "INSERT INTO Competition (Competition_ID, Location, Year_ID, Competition_Date) VALUES "
                + "(1, 'City Arena', 1, '2024-03-15'), "
                + "(2, 'Downtown Gym', 1, '2024-10-10'), "
                + "(3, 'UVA Stadium', 2, '2025-04-20');";

        // Insert data into Competition_Member table
        String insertCompetitionMembers = "INSERT INTO Competition_Member (Competition_ID, Member_ID) VALUES "
                + "(1, 1), "
                + "(1, 2), "
                + "(2, 1), "
                + "(2, 3), "
                + "(3, 4);";

        statement.execute(insertYears);
        statement.execute(insertSemesters);
        statement.execute(insertMembers);
        statement.execute(insertExecutives);
        statement.execute(insertAlumni);
        statement.execute(insertPractices);
        statement.execute(insertAttendance);
        statement.execute(insertCompetitions);
        statement.execute(insertCompetitionMembers);
    }

    private void performQueries() throws SQLException {
        Statement statement = connection.createStatement();

        // Execute and print all query results
        executeSelectAll(statement, "Year");
        executeSelectAll(statement, "Semester");
        executeSelectAll(statement, "Member");
        executeSelectAll(statement, "Executive");
        executeSelectAll(statement, "Alumni");
        executeSelectAll(statement, "Practice");
        executeSelectAll(statement, "Attendance");
        executeSelectAll(statement, "Competition");
        executeSelectAll(statement, "Competition_Member");

        // Additional specific queries
        selectFemaleMembers(statement);
        selectAverageBestTotalKG(statement);
        selectNumberOfPractices(statement);
        joinMemberAttendancePractice(statement);
        joinCompetitionMember(statement);
        joinSemesterMember(statement);
    }

    // Method to execute a SELECT * query and print results
    private static void executeSelectAll(Statement statement, String tableName) throws SQLException {
        String query = "SELECT * FROM " + tableName + ";";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }
        resultSet.close();
    }

    // Method to select female members
    private static void selectFemaleMembers(Statement statement) throws SQLException {
        String query = "SELECT * FROM Member WHERE Member_Gender = 'Female';";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            System.out.println(resultSet.getString("Member_First_Name") + " " + resultSet.getString("Member_Last_Name"));
        }
        resultSet.close();
    }

    // Method to calculate the average best total KG by weight class
    private static void selectAverageBestTotalKG(Statement statement) throws SQLException {
        String query = "SELECT Member_Weight_Class, AVG(Member_Best_Total_KG) AS AverageBestTotalKG FROM Member GROUP BY Member_Weight_Class;";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            System.out.println(resultSet.getFloat("Member_Weight_Class") + ": " + resultSet.getFloat("AverageBestTotalKG"));
        }
        resultSet.close();
    }

    // Method to count the number of practices attended by each member
    private static void selectNumberOfPractices(Statement statement) throws SQLException {
        String query = "SELECT Member_ID, COUNT(Practice_ID) AS NumberOfPractices FROM Attendance WHERE Status = 'Present' GROUP BY Member_ID;";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            System.out.println("Member ID " + resultSet.getInt("Member_ID") + ": " + resultSet.getInt("NumberOfPractices"));
        }
        resultSet.close();
    }

    // Method to join Member, Attendance, and Practice tables and print attendance records
    private static void joinMemberAttendancePractice(Statement statement) throws SQLException {
        String query = "SELECT Member.Member_ID, Member.Member_First_Name, Member.Member_Last_Name, Practice.Practice_ID, Practice.Date AS Practice_Date, Practice.Location AS Practice_Location, Attendance.Status AS Attendance_Status "
                + "FROM Member "
                + "JOIN Attendance ON Member.Member_ID = Attendance.Member_ID "
                + "JOIN Practice ON Attendance.Practice_ID = Practice.Practice_ID "
                + "ORDER BY Member.Member_ID, Practice.Date;";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("Member_ID") + ": " + resultSet.getString("Member_First_Name") + " " + resultSet.getString("Member_Last_Name") +
                    ", Practice ID: " + resultSet.getInt("Practice_ID") + ", Date: " + resultSet.getDate("Practice_Date") +
                    ", Location: " + resultSet.getString("Practice_Location") + ", Status: " + resultSet.getString("Attendance_Status"));
        }
        resultSet.close();
    }

    // Method to join Competition, Competition_Member, and Member tables and print competition records
    private static void joinCompetitionMember(Statement statement) throws SQLException {
        String query = "SELECT Competition.Competition_ID, Competition.Location AS Competition_Location, Competition.Competition_Date, Member.Member_ID, Member.Member_First_Name, Member.Member_Last_Name "
                + "FROM Competition "
                + "JOIN Competition_Member ON Competition.Competition_ID = Competition_Member.Competition_ID "
                + "JOIN Member ON Competition_Member.Member_ID = Member.Member_ID "
                + "ORDER BY Competition.Competition_Date, Competition.Competition_ID;";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("Competition_ID") + ": " + resultSet.getString("Competition_Location") + ", Date: " + resultSet.getDate("Competition_Date") +
                    ", Member ID: " + resultSet.getInt("Member_ID") + ", Name: " + resultSet.getString("Member_First_Name") + " " + resultSet.getString("Member_Last_Name"));
        }
        resultSet.close();
    }

    // Method to join Semester and Member tables and print enrollment records
    private static void joinSemesterMember(Statement statement) throws SQLException {
        String query = "SELECT Semester.Semester_ID, Semester.Session_Name, Semester.Start_Date, Semester.End_Date, Member.Member_ID, Member.Member_First_Name, Member.Member_Last_Name "
                + "FROM Semester "
                + "JOIN Member ON Semester.Semester_ID = Member.Semester_ID "
                + "ORDER BY Semester.Start_Date, Semester.Semester_ID;";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            System.out.println("Semester ID: " + resultSet.getInt("Semester_ID") + ", Session: " + resultSet.getString("Session_Name") +
                    ", Start Date: " + resultSet.getDate("Start_Date") + ", End Date: " + resultSet.getDate("End_Date") +
                    ", Member ID: " + resultSet.getInt("Member_ID") + ", Name: " + resultSet.getString("Member_First_Name") + " " + resultSet.getString("Member_Last_Name"));
        }
        resultSet.close();
    }

    // Method to search for a specific member
    private void searchMember(String searchTerm) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM Member WHERE Member_First_Name LIKE '%" + searchTerm + "%' OR Member_Last_Name LIKE '%" + searchTerm + "%';";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("Member_ID") + ": " + resultSet.getString("Member_First_Name") + " " + resultSet.getString("Member_Last_Name") +
                    ", Email: " + resultSet.getString("Member_Email") + ", Weight Class: " + resultSet.getFloat("Member_Weight_Class"));
        }
        resultSet.close();
    }

    // Method to check member credentials
    public boolean checkCredentials(String email, String passwordHash) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM Member WHERE Member_Email = '" + email + "' AND Member_Password_Hash = '" + passwordHash + "';";
        ResultSet resultSet = statement.executeQuery(query);
        boolean valid = resultSet.next();
        resultSet.close();
        return valid;
    }

    public List<Member> getMemberData() throws SQLException {
        List<Member> members = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT Member_ID, Member_First_Name, Member_Last_Name, Member_Gender, Member_Email FROM Member;");
            while (rs.next()) {
                int memberId = rs.getInt("Member_ID");
                String firstName = rs.getString("Member_First_Name");
                String lastName = rs.getString("Member_Last_Name");
                String gender = rs.getString("Member_Gender");
                String email = rs.getString("Member_Email");
                int totalPracticesAttended = getTotalPracticesAttended(memberId);
                members.add(new Member(firstName, lastName, gender, email, totalPracticesAttended));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }

    private int getTotalPracticesAttended(int memberId) throws SQLException {
        int totalPracticesAttended = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS TotalPracticesAttended FROM Attendance WHERE Member_ID = " + memberId + " AND Status = 'Present'");
            if (rs.next()) {
                totalPracticesAttended = rs.getInt("TotalPracticesAttended");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalPracticesAttended;
    }

    public List<Member> searchMembersByFirstName(String firstName) throws SQLException {
        List<Member> members = new ArrayList<>();
        String query = "SELECT * FROM Member WHERE Member_First_Name LIKE '%" + firstName + "%';";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                members.add(new Member(
                        resultSet.getString("Member_First_Name"),
                        resultSet.getString("Member_Last_Name"),
                        resultSet.getString("Member_Gender"),
                        resultSet.getString("Member_Email"),
                        getTotalPracticesAttended(resultSet.getInt("Member_ID"))
                ));
            }
        }
        return members;
    }

    public List<Member> searchMembersByLastName(String lastName) throws SQLException {
        List<Member> members = new ArrayList<>();
        String query = "SELECT * FROM Member WHERE Member_Last_Name LIKE '%" + lastName + "%';";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                members.add(new Member(
                        resultSet.getString("Member_First_Name"),
                        resultSet.getString("Member_Last_Name"),
                        resultSet.getString("Member_Gender"),
                        resultSet.getString("Member_Email"),
                        getTotalPracticesAttended(resultSet.getInt("Member_ID"))
                ));
            }
        }
        return members;
    }

    public List<Member> searchMembersByEmail(String email) throws SQLException {
        List<Member> members = new ArrayList<>();
        String query = "SELECT * FROM Member WHERE Member_Email LIKE '%" + email + "%';";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                members.add(new Member(
                        resultSet.getString("Member_First_Name"),
                        resultSet.getString("Member_Last_Name"),
                        resultSet.getString("Member_Gender"),
                        resultSet.getString("Member_Email"),
                        getTotalPracticesAttended(resultSet.getInt("Member_ID"))
                ));
            }
        }
        return members;
    }
}