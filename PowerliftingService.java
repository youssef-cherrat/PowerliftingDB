import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<Member> getMemberData() {
        List<Member> membersList = new ArrayList<>();
        try {
            dbDriver.connect();
            membersList = dbDriver.getMemberData();
            dbDriver.disconnect();
        } catch (SQLException e) {
            //
        }
        return membersList;
    }
}
