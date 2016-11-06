
public class StarsAngle {
	
	

	public static double[] RadConvertion(double[][] Star) {
		double[][] a = new double[2][3];
		a[0][0] = 2 * Math.PI * Star[0][0] / 24;
		a[0][1] = 2 * Math.PI * Star[0][1] / (24 * 60);
		a[0][2] = 2 * Math.PI * Star[0][2] / (24 * 60 * 60);
		a[1][0] = 2 * Math.PI * Star[1][0] / 360;
		a[1][1] = 2 * Math.PI * Star[1][1] / (360 * 60);
		a[1][2] = 2 * Math.PI * Star[1][2] / (360 * 60 * 60);

		double[] f = { a[0][0] + a[0][1] + a[0][2], a[1][0] + a[1][1] + a[1][2] };
		// {Right Ascension f[0], Declination f[1]} in Radians
		return f;
	}

	private static double DotProduct(double[] a, double[] b) {
		double[] u = { Math.cos(a[1]) * Math.cos(a[0]), Math.cos(a[1]) * Math.sin(a[0]), Math.sin(a[1]) };
		double[] v = { Math.cos(b[1]) * Math.cos(b[0]), Math.cos(b[1]) * Math.sin(b[0]), Math.sin(b[1]) };
		double e = u[0] * v[0] + u[1] * v[1] + u[2] * v[2];
		return e;
	}

	private static double AngleBetween(double[][] Star1, double[][] Star2) {

		double[] c = RadConvertion(Star1);
		double[] d = RadConvertion(Star2);

		double Angle = Math.acos(DotProduct(c, d));

		return Angle;
	}

	public static void main(String[] args) {
		/////////////INPUT///////////////
		double[] RA1 = { 18.0, 53.0, 04.0232 };
		double[] Dec1 = { 32.0, 55.0, 32.650 };
		////////////////////////////////
		double[][] Star1 = { {RA1[0], RA1[1], RA1[2]}, {Dec1[0], Dec1[1], Dec1[2]} };
		
		/////////////INPUT///////////////
		double[] RA2 = { 18.0, 54.0, 36.9465 };
		double[] Dec2 = { 33.0, 5.0, 41.615 };
		////////////////////////////////
		double[][] Star2 = { {RA2[0], RA2[1], RA2[2]}, {Dec2[0], Dec2[1], Dec2[2]} };
	
	

		System.out.println("Star 1 has 	RA: " + RA1[0] + "h " + RA1[1] + "m " + RA1[2] + "s");
		System.out.println("		Dec: " + Dec1[0] + "º " + Dec1[1] + "' " + Dec1[2] + "''");
		System.out.println("Star 2 has 	RA: " + RA2[0] + "h " + RA2[1] + "m " + RA2[2] + "s");
		System.out.println("		Dec: " + Dec2[0] + "º " + Dec2[1] + "' " + Dec2[2] + "''");
		System.out.println();
		System.out.println("The angle between star 1 and 2 is: 	" + AngleBetween(Star1, Star2) + "	radians");
		System.out.println("			 		" + 360*60*AngleBetween(Star1, Star2)/(2*Math.PI) + " 	arc mins");
		System.out.println("			 		" + 360*60*60*AngleBetween(Star1, Star2)/(2*Math.PI) + " 	arc secs");
		
		/////////////INPUT///////////////
		double[] StarPixel1 = {2344.1467, 1628.6953};
		double[] StarPixel2 = {640.8903, 683.6762};
		////////////////////////////////
		double[] DeltaStarPixel = {StarPixel1[0] - StarPixel2[0], StarPixel1[1] - StarPixel2[1]};
		double PixelSeperation = Math.pow(Math.pow(DeltaStarPixel[0], 2) + Math.pow(DeltaStarPixel[1], 2), 0.5);
		System.out.println();
		System.out.println("Arcmins per pixel =	 		" + 360*60*AngleBetween(Star1, Star2)/(2*Math.PI*PixelSeperation) );
		System.out.println("FoV of Telescope in X = 	 	" + 3070*360*60*AngleBetween(Star1, Star2)/(2*Math.PI*PixelSeperation) + "	arc mins");
		System.out.println("FoV of Telescope in Y = 	 	" + 2047*360*60*AngleBetween(Star1, Star2)/(2*Math.PI*PixelSeperation) + "	arc mins");
		
		
	}
}
