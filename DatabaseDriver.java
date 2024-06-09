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

        // Additional queries for specific requirements can be added here
    }
}
