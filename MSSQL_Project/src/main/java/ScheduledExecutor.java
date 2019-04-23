import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutor {

    private static int count = 0;

    public static void execute(String configPath) throws InterruptedException, FileNotFoundException {
        Configuration config = new Configuration(configPath);
        SQLConnector sqlConnector = new SQLConnector();
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);

        Runnable task1 = () -> {
            String timeStamp = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
            String fileName = "Result_"+timeStamp+".csv";
            String fileAddress = config.getScheduledFilePath()+fileName;
            String csvResult = null;
            count++;
            System.out.println("Running...task1 - count : " + count);
            try {
               csvResult = sqlConnector.querySql(config.getSqlConfig().getSql_db_query1(), configPath);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            try {
                File file = new File(fileAddress);
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(csvResult);
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        };

        // init Delay = 5, repeat the task every 1 second
        ScheduledFuture<?> scheduledFuture = ses.scheduleAtFixedRate(
                task1, 0, config.getSqlConfig().getSql_query1_interval_days(), TimeUnit.DAYS);



    }
}
