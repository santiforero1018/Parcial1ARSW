package edu.eci.arsw.math;

///  <summary>
///  An implementation of the Bailey-Borwein-Plouffe formula for calculating hexadecimal
///  digits of pi.
///  https://en.wikipedia.org/wiki/Bailey%E2%80%93Borwein%E2%80%93Plouffe_formula
///  *** Translated from C# code: https://github.com/mmoroney/DigitsOfPi ***
///  </summary>
public class PiDigits {

    // private static boolean stop = false;
    private static SolutionThread[] threads;
    private static byte[] digits;

    /**
     * Returns a range of hexadecimal digits of pi.
     * 
     * @param start The starting location of the range.
     * @param count The number of digits to return.
     * @param N     the number of threads to do the operation.
     * @return An array containing the hexadecimal digits.
     */
    public static byte[] getDigits(int start, int count, int N, String name) {
        while (true) {
            if (start < 0) {
                throw new RuntimeException("Invalid Interval");
            }

            if (count < 0) {
                throw new RuntimeException("Invalid Interval");
            }
            digits = new byte[count];
            int division = count / N;
            threads = new SolutionThread[N];

            // int countter = 1;
            for (int i = 0; i < N; i++) {
                int aux = i * division;
                // countter++;
                int auxEnd = (i == N - 1) ? (count) : (aux + division);
                threads[i] = new SolutionThread(aux, auxEnd, digits, name);
                threads[i].start();
            }

            for (SolutionThread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }

            }

            return digits;

        }

    }

    public static void sayStop() {
        synchronized (digits) {
            try {
                for(int i=0;i<threads.length;i++){
                    System.out.println(threads[i].getName() + " current digit of "+ i + " " +threads[i].getCurrentCalculatedDigits());
                }
                digits.wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public static void continuar(){
        synchronized(digits){
            digits.notifyAll();
        }
    }

     
}
