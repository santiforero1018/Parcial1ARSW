package edu.eci.arsw.math;

import edu.eci.arsw.SolutionThread;

///  <summary>
///  An implementation of the Bailey-Borwein-Plouffe formula for calculating hexadecimal
///  digits of pi.
///  https://en.wikipedia.org/wiki/Bailey%E2%80%93Borwein%E2%80%93Plouffe_formula
///  *** Translated from C# code: https://github.com/mmoroney/DigitsOfPi ***
///  </summary>
public class PiDigits {
    
    private Object lock = new Object();
    /**
     * Returns a range of hexadecimal digits of pi.
     * @param start The starting location of the range.
     * @param count The number of digits to return.
     * @param N the number of threads to do the operation.
     * @return An array containing the hexadecimal digits.
     */
    public static byte[] getDigits(int start, int count, int N) {
        if (start < 0) {
            throw new RuntimeException("Invalid Interval");
        }

        if (count < 0) {
            throw new RuntimeException("Invalid Interval");
        }

        byte[] digits = new byte[count];
        int division = count / N;
        SolutionThread[] threads = new SolutionThread[N];

        int countter = 1;
        for(int i = 0; i<N;i++){
            // int aux = countter*division;
            // countter++;
            // int auxEnd = (i == N - 1) ? (division):(aux+division);
            threads[i] = new SolutionThread(start, count, digits);
            threads[i].start();
        }
        
        for(int i = 0; i<N;i++){
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            
        }

        return digits;
    }

    // public void pause(){
    //     synchronized(digits){

    //     }
    // }


}
