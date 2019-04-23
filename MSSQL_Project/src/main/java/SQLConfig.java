public class SQLConfig {
    private String sql_db_url;
    private String sql_db_name;
    private String sql_db_user;
    private String sql_db_pass;
    private String sql_db_query1;
    private int sql_query1_interval_days;
    private String sql_hidden_query;

    public SQLConfig(String url, String name, String user, String pass, String query1, int interval, String hiddenQuery){
        sql_db_url = url;
        sql_db_name = name;
        sql_db_user = user;
        sql_db_pass = pass;
        sql_db_query1 = query1;
        sql_query1_interval_days = interval;
        sql_hidden_query = hiddenQuery;
    }

    public String getSql_hidden_query() {
        return sql_hidden_query;
    }

    public void setSql_hidden_query(String sql_hidden_query) {
        this.sql_hidden_query = sql_hidden_query;
    }

    public String getSql_db_url() {
        return sql_db_url;
    }

    public void setSql_db_url(String sql_db_url) {
        this.sql_db_url = sql_db_url;
    }

    public String getSql_db_name() {
        return sql_db_name;
    }

    public void setSql_db_name(String sql_db_name) {
        this.sql_db_name = sql_db_name;
    }

    public String getSql_db_user() {
        return sql_db_user;
    }

    public void setSql_db_user(String sql_db_user) {
        this.sql_db_user = sql_db_user;
    }

    public String getSql_db_pass() {
        return sql_db_pass;
    }

    public void setSql_db_pass(String sql_db_pass) {
        this.sql_db_pass = sql_db_pass;
    }

    public String getSql_db_query1() {
        return sql_db_query1;
    }

    public void setSql_db_query1(String sql_db_query1) {
        this.sql_db_query1 = sql_db_query1;
    }

    public int getSql_query1_interval_days() {
        return sql_query1_interval_days;
    }

    public void setSql_query1_interval_days(int sql_query1_interval_days) {
        this.sql_query1_interval_days = sql_query1_interval_days;
    }
}
