package edu.eci.arsw.math;

/**
 * Thread solución del parcial
 * @author Santiago Forero Yate, ARSW
 */
public class SolutionThread extends Thread {

    private static int DigitsPerSum = 8;
    private static double Epsilon = 1e-17;
    private int start;
    private int count;
    private byte[] digits;
    private int calculatedDigits;
    private Object key;
    public SolutionThread(int start, int count, byte[] digits, Object key, String name) {
        super(name);
        this.start = start;
        this.count = count;
        this.digits = digits;
        this.calculatedDigits = 0;
        this.key = key;
    }


    public void run() {
        
        double sum = 0;
        
            
            for (int i = 0; i < count; i++) {
                
                if (i % DigitsPerSum == 0) {
                    sum = 4 * sum(1, start)
                            - 2 * sum(4, start)
                            - sum(5, start)
                            - sum(6, start);
    
                    start += DigitsPerSum;
                }
    
                sum = 16 * (sum - Math.floor(sum));
                this.digits[i] = (byte) sum;
                this.calculatedDigits++;
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

    public int getCurrentCalculatedDigits(){
        return this.calculatedDigits;
    }

    
    public void pause(){
        synchronized(key){
            try {
                key.wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
