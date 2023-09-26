package edu.eci.arsw.math;

/**
 * Thread solución del parcial
 * 
 * @author Santiago Forero Yate, ARSW
 */
public class SolutionThread extends Thread {

    private static int DigitsPerSum = 8;
    private static double Epsilon = 1e-17;
    private int start;
    private int end;
    private byte[] digits;
    private byte[] temp;
    private int calculatedDigits;

    public SolutionThread(int start, int end, byte[] digits, String name) {
        super(name);
        this.start = start;
        this.end = end;
        this.digits = digits;
        this.calculatedDigits = 0;
        this.temp = new byte[end - start];
    }

    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        double sum = 0;

        int current = this.start;

        for (int i = 0; i < end - start; i++) {

            if (i % DigitsPerSum == 0) {
                sum = 4 * sum(1, current)
                        - 2 * sum(4, current)
                        - sum(5, current)
                        - sum(6, current);

                current += DigitsPerSum;
            }

            sum = 16 * (sum - Math.floor(sum));
            this.temp[i] = (byte) sum;
            this.calculatedDigits++;
        }

        synchronized (digits) {
            for (int i = 0; i < end - start; i++) {
                this.digits[start + i] = this.temp[i];
            }
        }

    }

    /// <summary>
    /// Returns the sum of 16^(n - k)/(8 * k + m) from 0 to k.
    /// </summary>
    /// <param name="m"></param>
    /// <param name="n"></param>
    /// <returns></returns>
    private static double sum(int m, int n) {
        double sum = 0;
        int d = m;
        int power = n;

        while (true) {
            double term;

            if (power > 0) {
                term = (double) hexExponentModulo(power, d) / d;
            } else {
                term = Math.pow(16, power) / d;
                if (term < Epsilon) {
                    break;
                }
            }

            sum += term;
            power--;
            d += 8;
        }

        return sum;
    }

    /// <summary>
    /// Return 16^p mod m.
    /// </summary>
    /// <param name="p"></param>
    /// <param name="m"></param>
    /// <returns></returns>
    private static int hexExponentModulo(int p, int m) {
        int power = 1;
        while (power * 2 <= p) {
            power *= 2;
        }

        int result = 1;

        while (power > 0) {
            if (p >= power) {
                result *= 16;
                result %= m;
                p -= power;
            }

            power /= 2;

            if (power > 0) {
                result *= result;
                result %= m;
            }
        }

        return result;
    }

    public int getCurrentCalculatedDigits() {
        return this.calculatedDigits;
    }

    public void pause() {
        synchronized (digits) {
            try {
                digits.wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
