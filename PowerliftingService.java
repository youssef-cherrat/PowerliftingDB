import java.sql.SQLException;

public class PowerliftingService {
    private DatabaseDriver dbDriver;

    public PowerliftingService(DatabaseDriver dbDriver) {
        this.dbDriver = dbDriver;
    }

    public boolean checkCredentials(String email, String password) {
        boolean userValid;
        try {
            dbDriver.connect();
            userValid = dbDriver.checkCredentials(email, password);
            dbDriver.disconnect();
        } catch (SQLException e) {
            userValid = false;
        }
        return userValid;
    }
}
