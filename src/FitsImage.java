import nom.tam.fits.FitsException;
import java.io.File;
import java.io.IOException;
import java.util.Set;

public class FitsImage {

	public static void main(String[] args) throws FitsException, IOException, InterruptedException {

		FitsBuilder fitsBuilder = new FitsBuilder();
		String biasDirectoryString = "C:\\Users\\Ray\\Desktop\\Test\\Bias";
		int[][] masterBias = fitsBuilder.buildMasterBias(biasDirectoryString);

		
		String darksDirectoryString = "C:\\Users\\Ray\\Desktop\\Test\\Darks";
		int[][] masterDark = fitsBuilder.buildMasterDark(darksDirectoryString, masterBias);
		
		
		String flatsDirectoryString = "C:\\Users\\Ray\\Desktop\\Test\\Flats";
		double[][] scaledMasterFlat = fitsBuilder.buildMasterFlat(flatsDirectoryString, masterBias, masterDark);
	
		
		String lightsDirectoryString = "C:\\Users\\Ray\\Desktop\\Test\\Lights";
		Set<int[][]> flatCorrectedLights = fitsBuilder.calibrateLights(lightsDirectoryString, masterBias, masterDark, scaledMasterFlat);
	
		
	}


	
}
