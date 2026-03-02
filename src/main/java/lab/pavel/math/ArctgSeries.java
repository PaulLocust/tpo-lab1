package lab.pavel.math;

public final class ArctgSeries {
    private ArctgSeries() {
    }

    public static double arctg(double x, int terms) {
        if (terms <= 0) {
            throw new IllegalArgumentException("terms must be positive");
        }
        if (x < -1.0 || x > 1.0) {
            throw new IllegalArgumentException("x must be in [-1, 1] for this implementation");
        }
        if (x == 1.0) {
            return 0.7853981633974483;
        }
        if (x == -1.0) {
            return -0.7853981633974483;
        }

        double sum = 0.0;
        double power = x;
        for (int n = 0; n < terms; n++) {
            double sign = (n % 2 == 0) ? 1.0 : -1.0;
            int denominator = 2 * n + 1;
            sum += sign * power / denominator;
            power *= x * x;
        }
        return sum;
    }
}

