import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

public class CompareFilters {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		// Loads 2 excel files, one for Gaia data and one for my data
		FileInputStream fis = new FileInputStream(new File("C:\\Users\\Ray\\Desktop\\aladin_BV.xls"));
		FileInputStream fis2 = new FileInputStream(new File(
				"C:\\Users\\Ray\\Desktop\\Extended Project 2016\\Observational Images\\November 28 2016 (m39)\\m39 open cluster\\data\\lots\\final.xls"));

		// copys excel spreadsheets, then selects the first sheet from each
		// file.
		HSSFWorkbook wb = new HSSFWorkbook(fis);
		HSSFSheet sheet = wb.getSheetAt(0);
		wb.close();
		HSSFWorkbook wb2 = new HSSFWorkbook(fis2);
		HSSFSheet sheet2 = wb2.getSheetAt(0);
		wb2.close();

		// #degrees covered by one pixel, calculated elsewhere in arcminutes per
		// pixel, /60 to convert to degrees
		double degs_per_pixel = 0.011277052601898586 / 60;
		// Threshold # of degrees for matching Gaia star coordinates to my data
		double deg_threshold = degs_per_pixel * 3;
		// used later to list stars by in iterative number
		int starnumber = 0;

		// output the table column headers, seperated by a tab
		System.out.println("Star" + "\t" + "RA" + "\t" + "DEC" + "\t" + "Sky Counts I" + "\t" + "Sky Counts R" + "\t"
				+ "Sky Flux I" + "\t" + "Sky Flux R" + "\t" + "Mag I" + "\t" + "Mag R" + "\t" + "Cat Mag I" + "\t"
				+ "Cat Mag R" + "\t" + "Absolute Mag I" + "\t" + "I-R" + "\t" + "R-I cat" + "\t"
				+ "I-R cat" + "\t" + "pixel threshold");
		// input number of rows in alladindata.xls
		for (int i = 0; i < 8641; i++) {

			// fetches the data from row i of the Gaia excel file
			Row row = sheet.getRow(i);
			// check if R mag entry is an empty... celltype(1) = empty,
			// celltype(0) = number, if empty then do nothing
			// starts from cell 0
			int entrycheck = row.getCell(11).getCellType();
			// checking I mag
			int entrycheck2 = row.getCell(13).getCellType();
			if (entrycheck == 1) {
			} else {
				if (entrycheck2 == 1) {
				} else {

					// extracts coordinates and Imag Rmag of star i from
					// Gaia database
					Double cell_ra = row.getCell(2).getNumericCellValue();
					Double cell_dec = row.getCell(3).getNumericCellValue();
					Double cell_Rmag = row.getCell(11).getNumericCellValue();
					Double cell_Imag = row.getCell(13).getNumericCellValue();
					// ignore stars outside threshold - Input j rows from
					// final.xls
					for (int j = 1; j < 313; j++) {
						// loads row j from my excel data
						Row row2 = sheet2.getRow(j);
						// extracts coordinates of star j from my data
						Double cluster_cell_ra = row2.getCell(1).getNumericCellValue();
						Double cluster_cell_dec = row2.getCell(2).getNumericCellValue();
						// ignore if Gaia star coordinates is not within
						// threshold of jth cluster star
						if (cell_ra > cluster_cell_ra + deg_threshold) {
						} else if (cell_ra < cluster_cell_ra - deg_threshold) {
						} else if (cell_dec > cluster_cell_dec + deg_threshold) {
						} else if (cell_dec < cluster_cell_dec - deg_threshold) {
						} else {
							// starts at star 1, next match will be 2, then 3
							// etc
							starnumber = starnumber + 1;
							// if a coordinate match is found then output
							// details of that star from my data
							System.out.println(starnumber + "\t" + row2.getCell(1) + "\t" + row2.getCell(2) + "\t"
									+ row2.getCell(3) + "\t" + row2.getCell(4) + "\t" + row2.getCell(5) + "\t"
									+ row2.getCell(6) + "\t" + row2.getCell(7) + "\t" + row2.getCell(8) + "\t"
									+ cell_Rmag + "\t" + cell_Imag + "\t" + row2.getCell(10) + "\t" + row2.getCell(9)
									+ "\t" + (cell_Rmag - cell_Imag) + "\t"
									+ (cell_Imag - cell_Rmag) + "\t" + deg_threshold / degs_per_pixel);
						}
					}
				}
			}

		}

	}
}
