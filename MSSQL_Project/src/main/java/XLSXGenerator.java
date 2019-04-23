import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class XLSXGenerator {


    public static String csvToXLSX(String csv, String configPath) throws FileNotFoundException {
        Configuration configuration = new Configuration(configPath);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        String fileName = "Results_"+timeStamp+".xlsx";

        String xlsxFileAddress = configuration.getXlsxFilePath() + fileName;
        try {
            XSSFWorkbook workBook = new XSSFWorkbook();
            XSSFSheet sheet = workBook.createSheet("sheet1");
            int RowNum=0;
            String[] csvLines = csv.split("\n");

            for(String currentLine: csvLines){
                String str[] = currentLine.split(",");

                XSSFRow currentRow=sheet.createRow(RowNum++);
                for(int i=0;i<str.length;i++){
                    currentRow.createCell(i).setCellValue(str[i]);
                }
            }

            FileOutputStream fileOutputStream =  new FileOutputStream(xlsxFileAddress);
            workBook.write(fileOutputStream);
            fileOutputStream.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage()+"Exception in try");
        }
        return xlsxFileAddress;
    }


    public static String csvToXLSXComparison(String csv, String configPath, String file1, String file2) throws FileNotFoundException {
        Configuration configuration = new Configuration(configPath);
        String fileNamelong = "Comparison_"+file1+"_"+file2+".xlsx";
        String fileName = fileNamelong.replace("Result_","").replace(".csv", "");
        String xlsxFileAddress = configuration.getXlsxFilePath() + fileName;
        try {
            XSSFWorkbook workBook = new XSSFWorkbook();
            XSSFSheet sheet = workBook.createSheet("sheet1");
            int RowNum=0;
            String[] csvLines = csv.split("\n");

            for(String currentLine: csvLines){
                String str[] = currentLine.split(",");
                XSSFRow currentRow=sheet.createRow(RowNum++);
                for(int i=0;i<str.length;i++){
                    currentRow.createCell(i).setCellValue(str[i]);
                }
            }

            FileOutputStream fileOutputStream =  new FileOutputStream(xlsxFileAddress);
            workBook.write(fileOutputStream);
            fileOutputStream.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage()+"Exception in try");
        }
        return xlsxFileAddress;
    }

}



