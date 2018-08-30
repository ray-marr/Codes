
public class exponentialmethod {

	
	//gives result of x^n, by multiplying 1 by x, n iterations. where n is an integer value.
	private static final double pow(double d, int f) {
		double h = 1;
		for (int i = f; i > 0; i--) {

			h = h * d;
		}

		return h;
	}

	private static final double exponential(double x) {
		
		//first term in taylor expansion of exponential = 1
		double c = 1.0;
		//loop creating first 100 terms of taylor expansion
		for (int i = 1; i <= 100; i++) {
			double k = i;
			
			//creating the factoral for each expansion term, e.g. 1!, 2!, 3! and so on
			for (int j = i; j > 1; j--) {
				k = k * (j - 1);
			}
			//adds each expansion term, calls pow method and divides by previously calculated factorial k
			c = c + pow(x, i) / (k);
		}

		return c;

	}
//hello
	public static void main(String[] args) {

		for (int x = 0; x <= 10; x++) {
			System.out.print("x = " + x + "		");
			double y = exponential(x);
			System.out.println("e^x = " + y);

		}
		System.out.println("The exponential of 25 is: " + exponential(25));
	}
}
