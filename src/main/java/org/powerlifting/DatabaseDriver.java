package org.powerlifting;

import javax.swing.plaf.nimbus.State;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.security.MessageDigest;

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
        String execQuery = "SELECT * FROM Executive WHERE Executive_Email = ? AND Executive_Password_Hash = ?";
        String memberQuery = "SELECT * FROM Member WHERE Member_Email = ? AND Member_Password_Hash = ?";
        try (PreparedStatement execStmt = connection.prepareStatement(execQuery);
             PreparedStatement memberStmt = connection.prepareStatement(memberQuery)) {
            execStmt.setString(1, email);
            execStmt.setString(2, passwordHash);
            ResultSet execRs = execStmt.executeQuery();
            boolean validExec = execRs.next();
            execRs.close();

            if (validExec) {
                return true;
            }

            memberStmt.setString(1, email);
            memberStmt.setString(2, passwordHash);
            ResultSet memberRs = memberStmt.executeQuery();
            boolean validMember = memberRs.next();
            memberRs.close();
            return validMember;
        }
    }

    public String checkUserRole (String email, String passwordHash) throws SQLException {
        // only called if validUser, so we know it is either an executive or member
        String execQuery = "SELECT * FROM Executive WHERE Executive_Email = ? AND Executive_Password_Hash = ?";
        try (PreparedStatement execStmt = connection.prepareStatement(execQuery)) {
            execStmt.setString(1, email);
            execStmt.setString(2, passwordHash);
            ResultSet execRs = execStmt.executeQuery();
            boolean validExec = execRs.next();
            execRs.close();

            if (validExec) {
                return "Executive";
            }
            // must be a member
            return "Member";
        }
    }

    public Member getMemberFromLogin(String email, String passwordHash) throws SQLException {
        Member member = null;
        String sql = "SELECT * FROM Member WHERE Member_Email = ? AND Member_Password_Hash = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setString(2, passwordHash);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int memberId = rs.getInt("Member_ID");
                int semesterId = rs.getInt("Semester_ID");
                String firstName = rs.getString("Member_First_Name");
                String lastName = rs.getString("Member_Last_Name");
                String gender = rs.getString("Member_Gender");
                int totalPracticesAttended = getTotalPracticesAttended(rs.getInt("Member_ID"));
                String dateOfBirth = rs.getString("Member_Date_of_Birth");
                String gradDate = rs.getString("Member_Grad_Date");
                float weightClass = rs.getFloat("Member_Weight_Class");
                float bestTotalKg = rs.getFloat("Member_Best_Total_KG");

                member = new Member(memberId, semesterId, firstName, lastName, gender, email, totalPracticesAttended, dateOfBirth, gradDate,weightClass, bestTotalKg);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return member;
    }

    public List<Member> getAllMemberData() throws SQLException {
        List<Member> members = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Member;");
            while (rs.next()) {
                int memberId = rs.getInt("Member_ID");
                int semesterId = rs.getInt("Semester_ID");
                String firstName = rs.getString("Member_First_Name");
                String lastName = rs.getString("Member_Last_Name");
                String gender = rs.getString("Member_Gender");
                String email = rs.getString("Member_Email");
                int totalPracticesAttended = getTotalPracticesAttended(rs.getInt("Member_ID"));
                String dateOfBirth = rs.getString("Member_Date_of_Birth");
                String gradDate = rs.getString("Member_Grad_Date");
                float weightClass = rs.getFloat("Member_Weight_Class");
                float bestTotalKg = rs.getFloat("Member_Best_Total_KG");


//                String passwordHash = rs.getString("Member_Password_Hash");
//                int totalPracticesAttended = getTotalPracticesAttended(rs.getInt("Member_ID"));

//                members.add(new Member(semesterId, firstName, lastName, dateOfBirth, gradDate, weightClass, bestTotalKg, gender, email));
                members.add(new Member(memberId, semesterId, firstName, lastName, gender, email, totalPracticesAttended, dateOfBirth, gradDate,weightClass, bestTotalKg));
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

    public List<Member> searchMembers(String firstName, String lastName, String email, String gender, Float weight, Float result, String semester) throws SQLException {
        List<Member> members = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM Member");
        query.append(" JOIN Semester ON Member.Semester_ID = Semester.Semester_ID WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (firstName != null && !firstName.isEmpty()) {
            query.append(" AND Member_First_Name LIKE ?");
            params.add("%" + firstName + "%");
        }
        if (lastName != null && !lastName.isEmpty()) {
            query.append(" AND Member_Last_Name LIKE ?");
            params.add("%" + lastName + "%");
        }
        if (email != null && !email.isEmpty()) {
            query.append(" AND Member_Email LIKE ?");
            params.add("%" + email + "%");
        }
        if (gender != null && !gender.isEmpty()) {
            query.append(" AND Member_Gender = ?");
            params.add(gender);
        }
        if (weight != null) {
            query.append(" AND Member_Weight_Class = ?");
            params.add(weight);
        }
        if (result != null) {
            query.append(" AND Member_Best_Total_KG = ?");
            params.add(result);
        }
        if (semester != null && !semester.isEmpty()) {
            query.append(" AND Semester.Session_Name = ?");
            params.add(semester);
        }

        try (PreparedStatement pstmt = connection.prepareStatement(query.toString())) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                members.add(new Member(
                        resultSet.getInt("Member_ID"),
                        resultSet.getInt("Semester_ID"),
                        resultSet.getString("Member_First_Name"),
                        resultSet.getString("Member_Last_Name"),
                        resultSet.getString("Member_Gender"),
                        resultSet.getString("Member_Email"),
                        getTotalPracticesAttended(resultSet.getInt("Member_ID")),
                        resultSet.getString("Member_Date_of_Birth"),
                        resultSet.getString("Member_Grad_Date"),
                        resultSet.getFloat("Member_Weight_Class"),
                        resultSet.getFloat("Member_Best_Total_KG")));
            }
        }
        return members;
    }

    //hash password***
    public void changePassword(String email, String oldPassword, String newPassword, String userType) throws SQLException{
        String query = "";
        if (userType.equalsIgnoreCase("member")) {
            query = "UPDATE Member SET Member_Password_Hash = '" + newPassword + "' WHERE Member_Email = '" + email + "'";
        } else {
            query = "UPDATE Executive SET Executive_Password_Hash = '" + newPassword + "' WHERE Executive_Email = '" + email + "'";
        }
        try{
            Statement statement = connection.createStatement();
            int rs = statement.executeUpdate(query);
            System.out.println(rs);
            if(rs != 0){
                System.out.println("Successfully changed password");
            } else {
                System.out.println("ERROR");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    //set password here
    public void addMember(Member member) throws SQLException {
        String sql = "INSERT INTO Member (Semester_ID, Member_First_Name, Member_Last_Name, Member_Date_of_Birth, Member_Grad_Date, Member_Weight_Class, Member_Best_Total_KG, Member_Gender, Member_Email, Member_Password_Hash) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
//            System.out.println("Inserting member with details:");
//            System.out.println("Semester_ID: " + member.getSemester_ID());
//            System.out.println("First_Name: " + member.getFirst_Name());
//            System.out.println("Last_Name: " + member.getLast_Name());
//            System.out.println("Date_of_Birth: " + member.getDate_of_Birth());
//            System.out.println("Grad_Date: " + member.getGrad_Date());
//            System.out.println("Weight_Class: " + member.getWeight_Class());
//            System.out.println("Best_Total_KG: " + member.getBest_Total_KG());
//            System.out.println("Gender: " + member.getGender());
//            System.out.println("Email: " + member.getEmail());
//
//            java.sql.Date Member_Date_of_Birth = java.sql.Date.valueOf(member.getDate_of_Birth());
//            java.sql.Date Member_Grad_Date = java.sql.Date.valueOf(member.getGrad_Date());
//
//            System.out.println("After casting to Date: " + Member_Date_of_Birth);
//            System.out.println("After casting to Date: " + Member_Grad_Date);

            pstmt.setInt(1, member.getSemester_ID());
            pstmt.setString(2, member.getFirst_Name());
            pstmt.setString(3, member.getLast_Name());
            pstmt.setString(4, member.getDate_of_Birth());
            pstmt.setString(5, member.getGrad_Date());
            pstmt.setFloat(6, member.getWeight_Class());
            pstmt.setFloat(7, member.getBest_Total_KG());
            pstmt.setString(8, member.getGender());
            pstmt.setString(9, member.getEmail());
            String password = member.getFirst_Name() + member.getLast_Name();
            pstmt.setString(10, password);
            pstmt.executeUpdate();
        }
    }

    public List<Event> searchMemberEvents(int memberId) throws SQLException {
        List<Event> eventsList = new ArrayList<>();
        String sql = "SELECT 'Practice' AS Event_Type, " +
                "p.Date AS Event_Date, " +
                "p.Location AS Event_Location " +
                "FROM Attendance a " +
                "JOIN Practice p ON a.Practice_ID = p.Practice_ID " +
                "WHERE a.Member_ID = ? AND a.Status = 'Present' " +
                "UNION ALL " +
                "SELECT 'Competition' AS Event_Type, " +
                "c.Competition_Date AS Event_Date, " +
                "c.Location AS Event_Location " +
                "FROM Competition_Member cm " +
                "JOIN Competition c ON cm.Competition_ID = c.Competition_ID " +
                "WHERE cm.Member_ID = ?;";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1, memberId);
            pstmt.setInt(2, memberId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String eventType = rs.getString("Event_Type");
                String eventDate = rs.getString("Event_Date");
                String eventLocation = rs.getString("Event_Location");
                Event event = new Event(memberId, eventType, eventDate, eventLocation);
//                System.out.println(event);
                eventsList.add(event);
            }
        } catch (SQLException e) {
            //
        }
        return eventsList;
    }

    //fetch alum
    public List<Alumni> getAllAlumniData() throws SQLException {
        List<Alumni> alumniList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Alumni;");
            while (rs.next()) {
                int alumniId = rs.getInt("Alumni_ID");
                String firstName = rs.getString("Alumni_First_Name");
                String lastName = rs.getString("Alumni_Last_Name");
                int classYear = rs.getInt("Alumni_Class_Year");
                String email = rs.getString("Alumni_Email");
                int semesterId = rs.getInt("Semester_ID");

                alumniList.add(new Alumni(alumniId, firstName, lastName, classYear, email, semesterId));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alumniList;
    }

    public List<Alumni> searchAlumni(String firstName, String lastName, String email, Integer classYear) throws SQLException {
        List<Alumni> alumniList = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM Alumni WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (firstName != null && !firstName.isEmpty()) {
            query.append(" AND Alumni_First_Name LIKE ?");
            params.add("%" + firstName + "%");
        }
        if (lastName != null && !lastName.isEmpty()) {
            query.append(" AND Alumni_Last_Name LIKE ?");
            params.add("%" + lastName + "%");
        }
        if (email != null && !email.isEmpty()) {
            query.append(" AND Alumni_Email LIKE ?");
            params.add("%" + email + "%");
        }
        if (classYear != null) {
            query.append(" AND Alumni_Class_Year = ?");
            params.add(classYear);
        }

        try (PreparedStatement pstmt = connection.prepareStatement(query.toString())) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                alumniList.add(new Alumni(
                        resultSet.getInt("Alumni_ID"),
                        resultSet.getString("Alumni_First_Name"),
                        resultSet.getString("Alumni_Last_Name"),
                        resultSet.getInt("Alumni_Class_Year"),
                        resultSet.getString("Alumni_Email"),
                        resultSet.getInt("Semester_ID")));
            }
        }
        return alumniList;
    }
    //delete member
    public void deleteMember(int memberId) throws SQLException {
        String deleteMemberSql = "DELETE FROM Member WHERE Member_ID = ?";
        String deleteCompetitionMemberSql = "DELETE FROM Competition_Member WHERE Member_ID = ?";
        String deleteAttendanceSql = "DELETE FROM Attendance WHERE Member_ID = ?";
        try (PreparedStatement deleteMemberStmt = connection.prepareStatement(deleteMemberSql);
             PreparedStatement deleteCompetitionMemberStmt = connection.prepareStatement(deleteCompetitionMemberSql);
             PreparedStatement deleteAttendanceStmt = connection.prepareStatement(deleteAttendanceSql)) {

            // managing transaction for more optimal and consistent deletion/update
            connection.setAutoCommit(false);

            // deleting member and making sure relevant records for attendance/practices and competitions are deleted

            deleteMemberStmt.setInt(1, memberId);
            deleteMemberStmt.executeUpdate();


            deleteCompetitionMemberStmt.setInt(1, memberId);
            deleteCompetitionMemberStmt.executeUpdate();


            deleteAttendanceStmt.setInt(1, memberId);
            deleteAttendanceStmt.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    //add practice
    public void addPracticeEvent(int memberId, String eventDate, String eventLocation) throws SQLException {
        String selectPracticeSQL = "SELECT Practice_ID FROM Practice WHERE Date = ? AND Location = ?";
        String insertPracticeSQL = "INSERT INTO Practice (Date, Location) VALUES (?, ?)";
        String insertAttendanceSQL = "INSERT INTO Attendance (Member_ID, Practice_ID, Status) VALUES (?, ?, 'Present')";

        try (PreparedStatement selectPracticeStmt = connection.prepareStatement(selectPracticeSQL);
             PreparedStatement practiceStmt = connection.prepareStatement(insertPracticeSQL, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement attendanceStmt = connection.prepareStatement(insertAttendanceSQL)) {

            selectPracticeStmt.setString(1, eventDate);
            selectPracticeStmt.setString(2, eventLocation);
            ResultSet rs = selectPracticeStmt.executeQuery();

            int practiceId;
            if (rs.next()) {
                practiceId = rs.getInt("Practice_ID");
            } else {
                practiceStmt.setString(1, eventDate);
                practiceStmt.setString(2, eventLocation);
                practiceStmt.executeUpdate();

                ResultSet generatedKeys = practiceStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    practiceId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Failed to insert Practice event, no ID obtained.");
                }
            }
            attendanceStmt.setInt(1, memberId);
            attendanceStmt.setInt(2, practiceId);
            attendanceStmt.executeUpdate();
        }
    }

    //add competition
    public void addCompetitionEvent(int memberId, String eventDate, String eventLocation) throws SQLException {
        String selectCompetitionSQL = "SELECT Competition_ID FROM Competition WHERE Competition_Date = ? AND Location = ?";
        String insertCompetitionSQL = "INSERT INTO Competition (Location, Competition_Date) VALUES (?, ?)";
        String insertCompetitionMemberSQL = "INSERT INTO Competition_Member (Competition_ID, Member_ID) VALUES (?, ?)";

        try (PreparedStatement selectCompetitionStmt = connection.prepareStatement(selectCompetitionSQL);
             PreparedStatement competitionStmt = connection.prepareStatement(insertCompetitionSQL, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement competitionMemberStmt = connection.prepareStatement(insertCompetitionMemberSQL)) {

            selectCompetitionStmt.setString(1, eventDate);
            selectCompetitionStmt.setString(2, eventLocation);
            ResultSet rs = selectCompetitionStmt.executeQuery();

            int competitionId;
            if (rs.next()) {
                competitionId = rs.getInt("Competition_ID");
            } else {
                competitionStmt.setString(1, eventLocation);
                competitionStmt.setString(2, eventDate);
                competitionStmt.executeUpdate();

                ResultSet generatedKeys = competitionStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    competitionId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Failed to insert Competition event, no ID obtained.");
                }
            }

            competitionMemberStmt.setInt(1, competitionId);
            competitionMemberStmt.setInt(2, memberId);
            competitionMemberStmt.executeUpdate();
        }
    }



}