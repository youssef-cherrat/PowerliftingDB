import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseDriver {

    // SQLite connection URL
    private static final String URL = "jdbc:sqlite:powerlifting.db";

    public static void main(String[] args) {
        try {
            // Load JDBC driver
            Class.forName("org.sqlite.JDBC");

            // Establish connection
            try (Connection connection = DriverManager.getConnection(URL)) {
                try (Statement statement = connection.createStatement()) {
                    // Create tables
                    createTables(statement);

                    // Insert initial data
                    insertInitialData(statement);

                    // Perform queries
                    performQueries(statement);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTables(Statement statement) throws SQLException {
        String createYearTable = "CREATE TABLE IF NOT EXISTS Year ("
                + "Year_ID INTEGER PRIMARY KEY, "
                + "Start_Date DATE, "
                + "End_Date DATE);";

        String createSemesterTable = "CREATE TABLE IF NOT EXISTS Semester ("
                + "Semester_ID INTEGER PRIMARY KEY, "
                + "Year_ID INTEGER, "
                + "Session_Name TEXT, "
                + "Member_Dues_Deadline DATETIME, "
                + "Start_Date DATE, "
                + "End_Date DATE, "
                + "FOREIGN KEY (Year_ID) REFERENCES Year(Year_ID));";

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

        String createExecutiveTable = "CREATE TABLE IF NOT EXISTS Executive ("
                + "Executive_ID INTEGER PRIMARY KEY, "
                + "Semester_ID INTEGER, "
                + "Executive_First_Name TEXT, "
                + "Executive_Last_Name TEXT, "
                + "Executive_Role TEXT, "
                + "Executive_Email TEXT, "
                + "Executive_Password_Hash TEXT, "
                + "FOREIGN KEY (Semester_ID) REFERENCES Semester(Semester_ID));";

        String createAlumniTable = "CREATE TABLE IF NOT EXISTS Alumni ("
                + "Alumni_ID INTEGER PRIMARY KEY, "
                + "Alumni_First_Name TEXT, "
                + "Alumni_Last_Name TEXT, "
                + "Alumni_Class_Year INTEGER, "
                + "Alumni_Email TEXT, "
                + "Semester_ID INTEGER, "
                + "FOREIGN KEY (Semester_ID) REFERENCES Semester(Semester_ID));";

        String createPracticeTable = "CREATE TABLE IF NOT EXISTS Practice ("
                + "Practice_ID INTEGER PRIMARY KEY, "
                + "Date DATE, "
                + "Location TEXT);";

        String createAttendanceTable = "CREATE TABLE IF NOT EXISTS Attendance ("
                + "Member_ID INTEGER, "
                + "Practice_ID INTEGER, "
                + "Status TEXT, "
                + "PRIMARY KEY (Member_ID, Practice_ID), "
                + "FOREIGN KEY (Member_ID) REFERENCES Member(Member_ID), "
                + "FOREIGN KEY (Practice_ID) REFERENCES Practice(Practice_ID));";

        String createCompetitionTable = "CREATE TABLE IF NOT EXISTS Competition ("
                + "Competition_ID INTEGER PRIMARY KEY, "
                + "Location TEXT, "
                + "Year_ID INTEGER, "
                + "Competition_Date DATE, "
                + "FOREIGN KEY (Year_ID) REFERENCES Year(Year_ID));";

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
    }

    private static void insertInitialData(Statement statement) throws SQLException {
        String insertYears = "INSERT INTO Year (Year_ID, Start_Date, End_Date) VALUES "
                + "(1, '2024-01-01', '2024-12-31'), "
                + "(2, '2025-01-01', '2025-12-31');";

        String insertSemesters = "INSERT INTO Semester (Semester_ID, Year_ID, Session_Name, Member_Dues_Deadline, Start_Date, End_Date) VALUES "
                + "(1, 1, 'Spring 2024', '2024-01-15 23:59:59', '2024-01-10', '2024-05-20'), "
                + "(2, 1, 'Fall 2024', '2024-08-15 23:59:59', '2024-08-20', '2024-12-15'), "
                + "(3, 2, 'Spring 2025', '2025-01-15 23:59:59', '2025-01-10', '2025-05-20');";

        String insertMembers = "INSERT INTO Member (Member_ID, Semester_ID, Member_First_Name, Member_Last_Name, Member_Date_of_Birth, Member_Grad_Date, Member_Weight_Class, Member_Best_Total_KG, Member_Gender, Member_Email, Member_Password_Hash) VALUES "
                + "(1, 1, 'John', 'Doe', '2000-01-01', '2024-05-20', 75.0, 500.0, 'Male', 'john.doe@example.com', 'hash1'), "
                + "(2, 1, 'Jane', 'Smith', '2001-02-01', '2025-05-20', 60.0, 450.0, 'Female', 'jane.smith@example.com', 'hash3'), "
                + "(3, 2, 'Mike', 'Brown', '1999-03-03', '2024-12-15', 82.5, 600.0, 'Male', 'mike.brown@example.com', 'hash4'), "
                + "(4, 3, 'Emily', 'Davis', '2002-04-04', '2025-12-15', 70.0, 550.0, 'Female', 'emily.davis@example.com', 'hash5');";

        String insertExecutives = "INSERT INTO Executive (Executive_ID, Semester_ID, Executive_First_Name, Executive_Last_Name, Executive_Role, Executive_Email, Executive_Password_Hash) VALUES "
                + "(1, 1, 'Alice', 'Smith', 'President', 'alice.smith@example.com', 'hash2'), "
                + "(2, 2, 'Robert', 'Lee', 'Vice President', 'robert.lee@example.com', 'hash6'), "
                + "(3, 3, 'Linda', 'White', 'Secretary', 'linda.white@example.com', 'hash7');";

        String insertAlumni = "INSERT INTO Alumni (Alumni_ID, Alumni_First_Name, Alumni_Last_Name, Alumni_Class_Year, Alumni_Email, Semester_ID) VALUES "
                + "(1, 'Bob', 'Johnson', 2023, 'bob.johnson@example.com', 1), "
                + "(2, 'Sara', 'Williams', 2022, 'sara.williams@example.com', 1), "
                + "(3, 'Tom', 'Wilson', 2023, 'tom.wilson@example.com', 2);";

        String insertPractices = "INSERT INTO Practice (Practice_ID, Date, Location) VALUES "
                + "(1, '2024-02-01', 'Gym A'), "
                + "(2, '2024-02-03', 'Gym B'), "
                + "(3, '2024-02-05', 'Gym C'), "
                + "(4, '2024-08-21', 'Gym A');";

        String insertAttendance = "INSERT INTO Attendance (Member_ID, Practice_ID, Status) VALUES "
                + "(1, 1, 'Present'), "
                + "(1, 2, 'Present'), "
                + "(1, 3, 'Absent'), "
                + "(2, 2, 'Present'), "
                + "(2, 4, 'Present'), "
                + "(3, 3, 'Present'), "
                + "(4, 4, 'Absent');";

        String insertCompetitions = "INSERT INTO Competition (Competition_ID, Location, Year_ID, Competition_Date) VALUES "
                + "(1, 'City Arena', 1, '2024-03-15'), "
                + "(2, 'Downtown Gym', 1, '2024-10-10'), "
                + "(3, 'UVA Stadium', 2, '2025-04-20');";

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

    private static void performQueries(Statement statement) throws SQLException {
        String selectAllYears = "SELECT * FROM Year;";
        String selectAllSemesters = "SELECT * FROM Semester;";
        String selectAllMembers = "SELECT * FROM Member;";
        String selectAllExecutives = "SELECT * FROM Executive;";
        String selectAllAlumni = "SELECT * FROM Alumni;";
        String selectAllPractices = "SELECT * FROM Practice;";
        String selectAllAttendance = "SELECT * FROM Attendance;";
        String selectAllCompetitions = "SELECT * FROM Competition;";
        String selectAllCompetitionMembers = "SELECT * FROM Competition_Member;";

        // Execute and print query results (example)
        statement.executeQuery(selectAllYears);
        statement.executeQuery(selectAllSemesters);
        statement.executeQuery(selectAllMembers);
        statement.executeQuery(selectAllExecutives);
        statement.executeQuery(selectAllAlumni);
        statement.executeQuery(selectAllPractices);
        statement.executeQuery(selectAllAttendance);
        statement.executeQuery(selectAllCompetitions);
        statement.executeQuery(selectAllCompetitionMembers);

    }
}
