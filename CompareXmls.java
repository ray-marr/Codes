/*
1) Opens database downloaded from Aladin, and opens database create by ExtractSkyCounts.
2) Ignores all database entries without a proper motion entry
3) Ignores all stars with pm outside of the threshold of the clusters pm (10m'')
4) Takes RA & DEC of remaining aladin entries and scans through cluster database for mathcing stars withing threshold (10 pixels)
5) All matches are output to a table
*/
//need to use poi.apache jar files for reading and writing excel files
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;


public class CompareXmls {

	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException{
		FileInputStream fis = new FileInputStream(new File("C:\\Users\\Ray\\Desktop\\Extended Project 2016\\Observational Images\\November 28 2016 (m39)\\m39 open cluster\\data\\lots\\testaladin_I324.xls"));
		FileInputStream fis2 = new FileInputStream(new File("C:\\Users\\Ray\\Desktop\\Extended Project 2016\\Observational Images\\November 28 2016 (m39)\\m39 open cluster\\data\\lots\\testcluster.xls"));

		HSSFWorkbook wb = new HSSFWorkbook(fis);
		HSSFSheet sheet = wb.getSheetAt(0);
		wb.close();
		HSSFWorkbook wb2 = new HSSFWorkbook(fis2);
		HSSFSheet sheet2 = wb2.getSheetAt(0);
		wb2.close();
		
		double cluster_pmra = -8.14;
		double cluster_pmdec = -19.66;
		double pmthreshold = 5.0;
		double degs_per_pixel = 0.011277052601898586/60;
		double deg_threshold = degs_per_pixel*3;
		
		System.out.println("Star"+ "\t" + "RA" + "\t" + "DEC" + "\t" + "Sky Counts V" + "\t" + "Sky Counts B" + "\t" + "Sky Flux V" + "\t" + "Sky Flux B" + "\t" + "Mag V" + "\t" + "Mag B" + "\t" + "B-V" + "\t" + "Absolute Mag V" + "\t" + "pm_ra (m'')" + "\t" + "pm_dec (m'')" + "\t" + "pm_threshold (m'')" + "\t" + "pixel threshold");
                
		for (int i = 0; i < 31274; i++){
		

				Row row = sheet.getRow(i);
                //check if pmra is an empty celltype(1) = empty, celltype(0) = number
                int entrycheck = row.getCell(15).getCellType();
				if(entrycheck == 1 ){}
                                else{
				
				Double cell_ra = row.getCell(1).getNumericCellValue();
				Double cell_dec = row.getCell(2).getNumericCellValue();
				Double cell_pmra = row.getCell(15).getNumericCellValue();
				Double cell_pmdec = row.getCell(17).getNumericCellValue();
				//ignore stars outside threshold
                                if(cell_pmra > cluster_pmra + pmthreshold){}
				else if(cell_pmra < cluster_pmra - pmthreshold){}
				else if(cell_pmdec > cluster_pmdec + pmthreshold){}
				else if(cell_pmdec < cluster_pmdec - pmthreshold){}
				
					else{
					for ( int j = 1; j<312; j++){
						Row row2 = sheet2.getRow(j);
						Double cluster_cell_ra = row2.getCell(1).getNumericCellValue();
                                                Double cluster_cell_dec = row2.getCell(2).getNumericCellValue();
						//ignore if aladin star ra is not within threshold of jth cluster star
                                                if (cell_ra > cluster_cell_ra + deg_threshold){}
						else if(cell_ra < cluster_cell_ra - deg_threshold){}
                                                else if(cell_dec > cluster_cell_dec + deg_threshold){}
                                                else if(cell_dec < cluster_cell_dec - deg_threshold){}
						else{
							System.out.println(row2.getCell(0) + "\t" + row2.getCell(1) + "\t" + row2.getCell(2) + "\t" + row2.getCell(3) + "\t" + row2.getCell(4) + "\t" + row2.getCell(5) + "\t" + row2.getCell(6) + "\t" + row2.getCell(7) + "\t" + row2.getCell(8) + "\t" + row2.getCell(9) + "\t" + row2.getCell(10) + "\t" + cell_pmra + "\t" + cell_pmdec + "\t" + pmthreshold + "\t" + deg_threshold/degs_per_pixel );
						}
					}}}
			
			}
		
		
		
	
		
		
		
	}	
}

