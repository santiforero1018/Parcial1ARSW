package edu.eci.arsw.math;

import edu.eci.arsw.SolutionThread;

///  <summary>
///  An implementation of the Bailey-Borwein-Plouffe formula for calculating hexadecimal
///  digits of pi.
///  https://en.wikipedia.org/wiki/Bailey%E2%80%93Borwein%E2%80%93Plouffe_formula
///  *** Translated from C# code: https://github.com/mmoroney/DigitsOfPi ***
///  </summary>
public class PiDigits {

    private static boolean stop = false;
    private static SolutionThread[] threads;

    /**
     * Returns a range of hexadecimal digits of pi.
     * 
     * @param start The starting location of the range.
     * @param count The number of digits to return.
     * @param N     the number of threads to do the operation.
     * @return An array containing the hexadecimal digits.
     */
    public static byte[] getDigits(int start, int count, int N, Object lock, String name) {
        while (true) {
            if (start < 0) {
                throw new RuntimeException("Invalid Interval");
            }

            if (count < 0) {
                throw new RuntimeException("Invalid Interval");
            }
            byte[] digits = new byte[count];
            int division = count / N;
            threads = new SolutionThread[N];

            int countter = 1;
            for (int i = 0; i < N; i++) {
                int aux = i * division;
                int auxEnd = (i == N - 1) ? (count) : (aux + division);
                threads[i] = new SolutionThread(aux, auxEnd, digits, lock, name);
                threads[i].start();
            }

            for (int i = 0; i < N; i++) {
                try {
                    threads[i].join();
                } catch (InterruptedException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }

            }

            return digits;

        }

    }

    public static void sayStop(){
        for(int i = 0; i< threads.length; i++){
            System.out.println(threads[i].getCurrentCalculatedDigits());
            threads[i].pause();
        }
    }
}
