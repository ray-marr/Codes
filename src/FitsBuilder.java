import java.io.File;
import java.io.IOException;
import java.util.Set;

import nom.tam.fits.FitsException;

public class FitsBuilder {

	public int[][] buildMasterBias(String biasDirectoryString) throws FitsException, IOException, InterruptedException{
		File biasDirectory = new File(biasDirectoryString);
		String[] biasList = biasDirectory.list();
		
		FitsManipulator fitsManipulator = new FitsManipulator();
		Set<int[][]> rescaledBiasList = fitsManipulator.rescaleFitsList(biasList, biasDirectoryString);
		int[][] masterBias = fitsManipulator.getMeanFits(rescaledBiasList);
		System.out.println("Master Bias created!");
		System.out.println(masterBias[200][200]);
		
		return masterBias;
	}
	
	
	public int[][] buildMasterDark(String darksDirectoryString, int[][] masterBias) throws FitsException, IOException, InterruptedException{
		
	FitsManipulator fitsManipulator = new FitsManipulator();
	File darksDirectory = new File(darksDirectoryString);
	String[] darksList = darksDirectory.list();
	Set<int[][]> rescaledDarksList = fitsManipulator.rescaleFitsList(darksList, darksDirectoryString);
	Set<int[][]> biasRemovedDarks = fitsManipulator.removeMasterFromImages(rescaledDarksList, masterBias);
	//int[][] masterDark = fitsManipulator.getMeanFits(biasRemovedDarks);
	int[][] masterDark = fitsManipulator.getMedianFits(biasRemovedDarks);
	System.out.println("Master Dark created!");
	System.out.println(masterDark[200][200]);
	
	return masterDark;
	}
	
	
	public double[][] buildMasterFlat(String flatsDirectoryString, int[][] masterBias, int[][] masterDark) throws FitsException, IOException, InterruptedException{
	
		File flatsDirectory = new File(flatsDirectoryString);
		FitsManipulator fitsManipulator = new FitsManipulator();
		String[] flatsList = flatsDirectory.list();
		Set<int[][]> rescaledFlatsList = fitsManipulator.rescaleFitsList(flatsList, flatsDirectoryString);
		Set<int[][]> biasRemovedFlats = fitsManipulator.removeMasterFromImages(rescaledFlatsList, masterBias);
		Set<int[][]> darkBiasRemovedFlats = fitsManipulator.removeMasterFromImages(biasRemovedFlats, masterDark);
		//int[][] masterFlat = fitsManipulator.getMeanFits(darkBiasRemovedFlats);
		int[][] masterFlat = fitsManipulator.getMedianFits(darkBiasRemovedFlats);
		System.out.println("Master Flat created!");
		System.out.println(masterFlat[200][200]);
		
		double[][] scaledMasterFlat = fitsManipulator.rescaleMasterFlat(masterFlat);
		System.out.println("Scaled master Flat created!");
		System.out.println(scaledMasterFlat[200][200]);
		
		return scaledMasterFlat;
	}
	
	
	
	public Set<int[][]> calibrateLights(String lightsDirectoryString, int[][] masterBias, int[][] masterDark, double[][] scaledMasterFlat) throws FitsException, IOException, InterruptedException{
		FitsManipulator fitsManipulator = new FitsManipulator();
		
		File lightsDirectory = new File(lightsDirectoryString);
		String[] lightsList = lightsDirectory.list();
		Set<int[][]> rescaledLightsList = fitsManipulator.rescaleFitsList(lightsList, lightsDirectoryString);
		Set<int[][]> biasRemovedLights = fitsManipulator.removeMasterFromImages(rescaledLightsList, masterBias);
		Set<int[][]> darkBiasRemovedLights = fitsManipulator.removeMasterFromImages(biasRemovedLights, masterDark);
		Set<int[][]> flatCorrectedLights = fitsManipulator.flatDivisionOfImages(darkBiasRemovedLights, scaledMasterFlat);
		System.out.println("Callibrated Lights!");
		
		
		return flatCorrectedLights;
	}
}
