
public class exponentialmethod {

	private static final double pow(double d, int f) {
		double h = 1;
		for (int i = f; i > 0; i--) {

			h = h * d;
		}

		return h;
	}

	private static final double exponential(double x) {

		double c = 1.0;
		for (int i = 1; i <= 100; i++) {
			double k = i;

			for (int j = i; j > 1; j--) {
				k = k * (j - 1);
			}
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
