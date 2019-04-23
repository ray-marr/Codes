import java.io.FileNotFoundException;
import java.sql.*;


public class SQLConnector {

    public String querySql(String sqlQuery, String configPath) throws FileNotFoundException {
        Configuration config = new Configuration(configPath);
        StringBuilder result = new StringBuilder();
        String userDB = config.getSqlConfig().getSql_db_user();
        String passDB = config.getSqlConfig().getSql_db_pass();
        String urlDB = config.getSqlConfig().getSql_db_url();
        String dbName = config.getSqlConfig().getSql_db_name();

        try {
            // create sql database connection
            String myDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            String connectionURL = "jdbc:sqlserver://"+urlDB;
            Class.forName(myDriver);


            Connection conn = DriverManager.getConnection(connectionURL+";databaseName="+dbName+";user="+userDB+";password="+passDB+";");

            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(sqlQuery);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

//            StringBuilder logBuilder = new StringBuilder();
//            logBuilder.append("################## SQL Query Called ################\n");
//            logBuilder.append("userDB: "+userDB+"\n");
//            logBuilder.append("passDB: "+passDB+"\n");
//            logBuilder.append("urlDB: "+urlDB+"\n");
//            logBuilder.append("dbName: "+dbName+"\n");
//            logBuilder.append("driver: "+myDriver+"\n");
//            logBuilder.append("connectionURL: "+connectionURL+"\n");
//            logBuilder.append("sql Query: "+sqlQuery+"\n");
//            logBuilder.append("configPath: "+configPath+"\n");
//            logBuilder.append("\n\n\n");
//            FileManager fm = new FileManager();
//            fm.appendFile("/mnt/c/Users/Ray/Desktop/test.txt",logBuilder);



            // iterate through the result set
            for (int i = 1; i <= columnsNumber; i++) {
                result.append(rsmd.getColumnName(i).trim());
                if(i<columnsNumber){
                    result.append(",");
                }
            }
            while (rs.next())
            {
                result.append("\n");
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) result.append(",");
                    String columnValue = rs.getString(i);
                    result.append(columnValue.trim());
                }

            }
            st.close();
            conn.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
            for(StackTraceElement ste: e.getStackTrace()){
            System.err.println(ste);}
        }
        return result.toString();
    }

}


