import spark.Spark;

import java.io.File;
import java.util.List;

import static spark.Spark.*;

public class Application {

    public static void main(String[] args) throws Exception {

        //Default port is 4567, change it below to run on another port
//        port(4567);
        //runs scheduled sql query every # days (defined in config.json)
        ScheduledExecutor.execute(args[0]);

        //Check application is running
        Spark.get("/alive", (req, res) -> "I am alive!");

        //Make an SQL query (returns csv)
        post("/query/sql/csv", (request, response) ->{
            SQLConnector sqlConnector = new SQLConnector();
            String result = null;
            try {
                result = sqlConnector.querySql(request.body(), args[0]);
            }catch (Exception e){
                System.out.println(e);
            }
            return result;
        });


        post("/query/sql/xml", (request, response) ->{
            SQLConnector sqlConnector = new SQLConnector();
            XmlGenerator xmlGenerator = new XmlGenerator();
            String csvResult = null;
            try {
                csvResult = sqlConnector.querySql(request.body(),args[0]);
            }catch (Exception e){
                System.out.println(e);
            }
            String result = xmlGenerator.csvToXML(csvResult);

            return result;
        });

        post("/query/sql/xlsx", (request, response) ->{
            SQLConnector sqlConnector = new SQLConnector();
            XLSXGenerator xlsxGenerator = new XLSXGenerator();
            String csvResult = null;
            try {
                csvResult = sqlConnector.querySql(request.body(),args[0]);
            }catch (Exception e){
                System.out.println(e);
            }

           String fileName = xlsxGenerator.csvToXLSX(csvResult, args[0]);
            return fileName;
        });


        post("/fileList", (request, response) ->{
            System.out.println("running");
                    Configuration config = new Configuration(args[0]);
                    File folder = new File(config.getScheduledFilePath());
                    File[] listOfFiles = folder.listFiles();
                    StringBuilder sb = new StringBuilder();

                    for (int i = 0; i < listOfFiles.length; i++) {
                        if (listOfFiles[i].isFile()) {
                            sb.append(listOfFiles[i].getName());
                            if(i < (listOfFiles.length - 1)) {
                            sb.append(",");
                            }
                        }
                    }
                    return sb.toString();
                });

        post("/compareFiles", (request, response) ->{
            StringBuilder sb = new StringBuilder();
            String result = null;
            try{
            FileManager fileManager = new FileManager();
            Configuration config = new Configuration(args[0]);
            String[] fileNames = request.body().split(",");
            String fileType = fileNames[2];
            String filePath = config.getScheduledFilePath();

            List<String> file1 = fileManager.readFileAsLines(filePath+fileNames[0]);
            List<String> file1Dup = fileManager.readFileAsLines(filePath+fileNames[0]);
            List<String> file2 = fileManager.readFileAsLines(filePath+fileNames[1]);

            //removes all file 2 entries from file 1
            file1.removeAll(file2);
            //removes all file 1 entries from file 2
            file2.removeAll(file1Dup);
            //Merge unique entries
            file1.addAll(file2);


            sb.append(file1Dup.get(0));
            for (String s : file1) {
                sb.append("\n" + s);
                }

                String csvResult = sb.toString();

            if(fileType.equals("csv")){
                result = csvResult;
            }else if  (fileType.equals("xml")){
                XmlGenerator xmlGenerator = new XmlGenerator();
                result = xmlGenerator.csvToXML(csvResult);
            }else if  (fileType.equals("xlsx")){
                XLSXGenerator xlsxGenerator = new XLSXGenerator();
                String fileName = xlsxGenerator.csvToXLSXComparison(csvResult, args[0], fileNames[0], fileNames[1]);
                result = fileName;
            }


        }catch(Exception e){
                System.out.println(e);
                return "Internal Error.";
            }

            return result;
        });


        post("/query/sql/csv/hidden", (request, response) ->{
            SQLConnector sqlConnector = new SQLConnector();
            String result = null;
            Configuration config = new Configuration(args[0]);
            try {
                result = sqlConnector.querySql(config.getSqlConfig().getSql_hidden_query(), args[0]);
            }catch (Exception e){
                System.out.println(e);
            }
            return result;
        });


        post("/query/sql/xml/hidden", (request, response) ->{
            SQLConnector sqlConnector = new SQLConnector();
            XmlGenerator xmlGenerator = new XmlGenerator();
            String csvResult = null;
            Configuration config = new Configuration(args[0]);
            try {
                csvResult = sqlConnector.querySql(config.getSqlConfig().getSql_hidden_query(),args[0]);
            }catch (Exception e){
                System.out.println(e);
            }
            String result = xmlGenerator.csvToXML(csvResult);

            return result;
        });

        post("/query/sql/xlsx/hidden", (request, response) ->{
            SQLConnector sqlConnector = new SQLConnector();
            XLSXGenerator xlsxGenerator = new XLSXGenerator();
            String csvResult = null;
            Configuration config = new Configuration(args[0]);
            try {
                csvResult = sqlConnector.querySql(config.getSqlConfig().getSql_hidden_query(),args[0]);
            }catch (Exception e){
                System.out.println(e);
            }

            String fileName = xlsxGenerator.csvToXLSX(csvResult, args[0]);
            return fileName;
        });

    }

}
