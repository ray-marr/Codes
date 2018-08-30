import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import nom.tam.fits.Fits;
import nom.tam.fits.FitsException;

public class FitsManipulator {

	public Set<int[][]> rescaleFitsList(String[] fitsList, String directoryString)
			throws FitsException, IOException, InterruptedException {

		Set<int[][]> rescaledFitsList = new HashSet<int[][]>();
		int k = 0;
		int length = fitsList.length;

		while (k < length) {
			Fits f = new Fits(directoryString + "\\" + fitsList[k]);
			int[][] rescaledFits = rescaleCounts(f);
			rescaledFitsList.add(rescaledFits);
			k++;
		}
		return rescaledFitsList;
	}

	public Set<int[][]> removeMasterFromImages(Set<int[][]> rescaledFitsList, int[][] masterImage) {

		for (int[][] fitsImage : rescaledFitsList) {
			for (int i = 0; i < masterImage.length; i++) {
				for (int j = 0; j < masterImage[0].length; j++) {
					fitsImage[i][j] = fitsImage[i][j] - masterImage[i][j];

				}
			}
		}

		return rescaledFitsList;
	}

	public int[][] rescaleCounts(Fits f) throws FitsException, IOException {
		short[][] counts = (short[][]) f.getHDU(0).getKernel();
		double bScale = f.getHDU(0).getBScale();
		double bZero = f.getHDU(0).getBZero();
		int xAxis = counts.length;
		int yAxis = counts[0].length;
		// physical value = BSCALE * (storage value) + BZERO
		int[][] rescaledCounts = new int[xAxis][yAxis];

		for (int i = 0; i < xAxis; i++) {
			for (int j = 0; j < yAxis; j++) {
				rescaledCounts[i][j] = (int) ((bScale * counts[i][j]) + bZero);
			}
		}

		return rescaledCounts;
	}

	public int[][] getMeanFits(Set<int[][]> rescaledFitsList) {
		int fitsCounter = 0;
		int[][] firstFits = rescaledFitsList.iterator().next();
		int fitsMean[][] = new int[firstFits.length][firstFits[0].length];

		for (int[][] rescaledfits : rescaledFitsList) {

			for (int i = 0; i < fitsMean.length; i++) {
				for (int j = 0; j < fitsMean[0].length; j++) {
					fitsMean[i][j] = fitsMean[i][j] + rescaledfits[i][j];
				}
			}
			fitsCounter++;
		}

		for (int i = 0; i < fitsMean.length; i++) {
			for (int j = 0; j < fitsMean[0].length; j++) {
				fitsMean[i][j] = fitsMean[i][j] / fitsCounter;
				if (fitsMean[i][j] < 0) {
					fitsMean[i][j] = 0;
				}
			}
		}

		return fitsMean;
	}

	public int[][] getMedianFits(Set<int[][]> rescaledFitsList) {
		int[][] firstFits = rescaledFitsList.iterator().next();
		int[][] medianFits = new int[firstFits.length][firstFits[0].length];

		for (int i = 0; i < medianFits.length; i++) {
			for (int j = 0; j < medianFits[0].length; j++) {
				int[] cellCounts = new int[rescaledFitsList.size()];
				int position = 0;
				for (int[][] fitsImage : rescaledFitsList) {
					cellCounts[position] = fitsImage[i][j];
					position++;
				}
				Arrays.sort(cellCounts);

				if (rescaledFitsList.size() % 2 == 0) {
					medianFits[i][j] = (int) ((cellCounts[cellCounts.length / 2]
							+ (double) cellCounts[cellCounts.length / 2 - 1]) / 2);
				} else {
					medianFits[i][j] = cellCounts[cellCounts.length / 2];
				}
			}
		}

		return medianFits;
	}

	public double[][] rescaleMasterFlat(int[][] masterFlat){
		
		int counter = 0;
		long sumOfCounts = 0;
		for (int i = 0; i < masterFlat.length; i++) {
			for (int j = 0; j < masterFlat[0].length; j++) {
				sumOfCounts = sumOfCounts + masterFlat[i][j];
				counter++;
			}
		}
		
		double meanCounts = sumOfCounts / counter;
		double[][] scaledMasterFlat = new double[masterFlat.length][masterFlat[0].length];
		for (int i = 0; i < masterFlat.length; i++) {
			for (int j = 0; j < masterFlat[0].length; j++) {
			scaledMasterFlat[i][j] = masterFlat[i][j] / meanCounts;	
			}	
		}
	
		return scaledMasterFlat;
	}
	
	
	public Set<int[][]> flatDivisionOfImages(Set<int[][]> images, double[][] scaledMasterFlat){
		
		for(int[][] img : images){
			
			for(int i = 0; i < scaledMasterFlat.length; i++){
				for(int j = 0; j < scaledMasterFlat[0].length; j++){
					img[i][j] = (int)(img[i][j] / scaledMasterFlat[i][j]); 
				}
			}
			
		}
		return images;
	}
	
}
