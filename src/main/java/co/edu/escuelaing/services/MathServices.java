package co.edu.escuelaing.services;

/**
 * Math operations exposed through AWS Lambda.
 */
public final class MathServices {

    private MathServices() {
        // Utility class.
    }

    /**
     * Returns the square of an integer.
     *
     * @param i number to square
     * @return i multiplied by itself
     */
    public static Integer square(Integer i) {
        return i * i;
    }
}
