import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Configuration {
    private SQLConfig sqlConfig;
    private String scheduledFilePath;
    private String xlsxFilePath;



    public Configuration(String configPath) throws FileNotFoundException {
        InputStream jsonConfigStream = new FileInputStream(new File(configPath));
        JSONTokener tokener = new JSONTokener(jsonConfigStream);
        JSONObject jsonConfig = new JSONObject(tokener);
        JSONObject sqlJson = (JSONObject) jsonConfig.get("sql");
        scheduledFilePath = jsonConfig.getString("scheduled_file_path");
        xlsxFilePath = jsonConfig.getString("xlsx_file_path");
        sqlConfig = new SQLConfig(
                sqlJson.getString("sql_db_url"),
                sqlJson.getString("sql_db_name"),
                sqlJson.getString("sql_db_user"),
                sqlJson.getString("sql_db_pass"),
                sqlJson.getString("sql_db_query1"),
                sqlJson.getInt("sql_query1_interval_days"),
                sqlJson.getString("sql_hidden_query"));

    }

    public SQLConfig getSqlConfig() {
        return sqlConfig;
    }

    public String getScheduledFilePath() {
        return scheduledFilePath;
    }

    public String getXlsxFilePath() {
        return xlsxFilePath;
    }
}
