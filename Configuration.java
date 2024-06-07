import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Collectors;

public class Configuration {
    public static final String configurationFilename = "config.json";
    private String databaseFilename;
    public Configuration() { }
    public String getDatabaseFilename() {
        if (databaseFilename == null) {
            parseJsonConfigFile();
        }
        return databaseFilename;
    }
    private void parseJsonConfigFile() {
        try (InputStream inputStream = Objects.requireNonNull(Configuration.class.getResourceAsStream(configurationFilename));
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            var configContents = bufferedReader.lines().collect(Collectors.joining("\n"));
            JSONObject root = new JSONObject(configContents);
            databaseFilename = root.getString("database");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}